package pax.mesa.tbd.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pax.mesa.tbd.Prefs;
import pax.mesa.tbd.User;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private Button addClasses;
    private Button removeClasses;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private Switch darkModeSwitch;
    private LinearLayout classesLayout;

    private List<String> myClasses;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        addClasses = root.findViewById(R.id.addClassesButton);
        removeClasses = root.findViewById(R.id.removeClassesBtn);
        firstNameEdit = root.findViewById(R.id.firstNameEdit);
        lastNameEdit = root.findViewById(R.id.lastNameEdit);
        darkModeSwitch = root.findViewById(R.id.darkModeSwitch);
        classesLayout = root.findViewById(R.id.classesLayout);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        addClasses.setOnClickListener(onClick);
        removeClasses.setOnClickListener(onClick);
        firstNameEdit.setOnClickListener(onClick);
        lastNameEdit.setOnClickListener(onClick);
        darkModeSwitch.setOnClickListener(onClick);

        firstNameEdit.setText(Prefs.getPrefs(getContext()).getString("first_name", ""));
        lastNameEdit.setText(Prefs.getPrefs(getContext()).getString("last_name", ""));
        darkModeSwitch.setChecked(Prefs.getPrefs(getContext()).getBoolean("dark_mode", true));

        String data = Prefs.getPrefs(getContext()).getString("classes", "");
        if((data != null) && (data != "\t")){
            myClasses = new LinkedList<String>(Arrays.asList(data.split("\t")));
            updateClasses();
        }
        else{
            myClasses = new LinkedList<String>(Arrays.asList(""));
        }
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Prefs.insertString(getContext(), "first_name", firstNameEdit.getText().toString());
        Prefs.insertString(getContext(), "last_name", lastNameEdit.getText().toString());
        Prefs.insertBool(getContext(), "dark_mode", darkModeSwitch.isChecked());
    }

    private final View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == darkModeSwitch.getId()){
                if(darkModeSwitch.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
            else if(view.getId() == addClasses.getId()){
                EditClassesDialog newFragment = new EditClassesDialog(getContext(), getResources().getStringArray(R.array.classes), true, SettingsFragment.this);
                newFragment.show();

            }
            else if(view.getId() == removeClasses.getId()){
                EditClassesDialog newFragment = new EditClassesDialog(getContext(), myClasses.toArray(new String[0]),  false, SettingsFragment.this);
                newFragment.show();
            }
        }
    };

    public void addClass(String Class){
        myClasses.add(Class);
        updateClasses();
    }
    public void removeClass(String Class){
        myClasses.remove(Class);
        updateClasses();
    }

    private void updateClasses(){
        classesLayout.removeAllViews();
        String data = "";
        for(String clas : myClasses){
            data += clas + "\t";
            TextView text = new TextView(getContext());
            text.setText(clas);
            text.setTextSize(16);
            text.setPadding(0,30,0,30);
            classesLayout.addView(text);
        }
        Prefs.getPrefs(getContext()).edit().putString("classes", data).apply();

        //Push updated classes to db
        mDatabase.child("users").child(userID).child("classes").setValue(myClasses);
    }

    // Returns what classes the user wants to be included in, ordered
    static public String[] getClasses(Context context){
        SharedPreferences preferences = Prefs.getPrefs(context);
        String data = Prefs.getPrefs(context).getString("classes", "");
        if(data != null){
            return data.split("\t");
        }
        return null;
    }

    static public String getFirstName(Context context){
        SharedPreferences preferences = Prefs.getPrefs(context);
        return preferences.getString("first_name", "default name");
    }

    static public String getLastName(Context context){
        SharedPreferences preferences = Prefs.getPrefs(context);
        return preferences.getString("last_name", "default name");
    }

    static public Boolean isDarkTheme(Context context){
        SharedPreferences preferences = Prefs.getPrefs(context);
        return preferences.getBoolean("dark_mode", true);
    }

    static void setName(Context context, String firstName, String lastName){
        SharedPreferences preferences = Prefs.getPrefs(context);
        preferences.edit().putString("first_name", firstName).apply();
        preferences.edit().putString("last_name", lastName).apply();
    }

    static void setClasses(Context context, String[] classes){
        SharedPreferences preferences = Prefs.getPrefs(context);
        String data = "";
        for(String clas : classes){
            data += clas + "\t";
        }
        Prefs.getPrefs(context).edit().putString("classes", data).apply();
    }

    static void setDarkTheme(Context context, Boolean dark){
        SharedPreferences preferences = Prefs.getPrefs(context);
        preferences.edit().putBoolean("dark_mode", dark).apply();
    }
}