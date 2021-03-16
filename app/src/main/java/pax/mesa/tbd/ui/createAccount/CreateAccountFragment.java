package pax.mesa.tbd.ui.createAccount;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

import pax.mesa.tbd.MainActivity;
import pax.mesa.tbd.Prefs;
import pax.mesa.tbd.User;
import pax.mesa.tbd.preferences.EditClassesDialog;
import pax.mesa.tbd.preferences.SettingsFragment;
import pax.mesa.tbd.ui.login.LoginFragmentDirections;

public class CreateAccountFragment extends Fragment {
    private CreateAccountViewModel createAccountViewModel;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;

    private LinearLayout classesLayout;

    private List<String> myClasses = new ArrayList<>();

    public CreateAccountFragment() {
        super(R.layout.fragment_create_account);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createAccountViewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

        //show action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        View root = inflater.inflate(R.layout.fragment_create_account, container, false);

        classesLayout = root.findViewById(R.id.classesLayout);

        EditText targetEmail = root.findViewById(R.id.i_reg_email);
        EditText targetPass = root.findViewById(R.id.i_reg_password);
        EditText targetConPass = root.findViewById(R.id.i_con_password);
        EditText userFName = root.findViewById(R.id.i_fName);
        EditText userLName = root.findViewById(R.id.i_lName);

        Button buttonReg = root.findViewById(R.id.b_reg_account);
        Button buttonClasses = root.findViewById(R.id.b_choose_classes);

        buttonClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add classes dialog
                EditClassesDialog newFragment = new EditClassesDialog(getContext(),
                        getResources().getStringArray(R.array.classes), true, CreateAccountFragment.this);
                newFragment.show();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = targetEmail.getText().toString();
                String password = targetPass.getText().toString();
                String finalPass = targetConPass.getText().toString();
                String firstName = userFName.getText().toString();
                String lastName = userLName.getText().toString();
                List userClasses = myClasses;

                if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getActivity(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty() || finalPass.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a password or confirm your password.", Toast.LENGTH_SHORT).show();
                } else if(!finalPass.equals(password)) {
                    Toast.makeText(getActivity(), "The entered passwords do not match.", Toast.LENGTH_SHORT).show();
                } else {
                    doRegister(email, finalPass, firstName, lastName, userClasses);
                }
            }
        });

        return root;
    }

    public void doRegister(String Email, String Password, String fName, String lName, List Classes) {
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Successfully created account.", Toast.LENGTH_SHORT).show();

                    //Add user to database with information
                    createUser(Email, fName, lName, Classes);

                    //Go to home screen
                    NavDirections action = CreateAccountFragmentDirections.actionCreateToHome();
                    Navigation.findNavController(getView()).navigate(action);
                } else {
                    Toast.makeText(getActivity(), "Failed to created account.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createUser(String email, String fName, String lName, List Classes) {
        FirebaseUser fUser = mAuth.getCurrentUser();
        User user = new User(fName, lName, email, Classes);

        mData.child("users").child(fUser.getUid()).setValue(user);
    }

    //Literally just yoinked from Ben's settings frag
    public void addClass(String Class){
        myClasses.add(Class);
        updateClasses();
    }

    /*public void removeClass(String Class){
        myClasses.remove(Class);
        updateClasses();
    }*/

    private void updateClasses(){
        String data = "";
        for(String doClass : myClasses){
            data += doClass + "\t";
        }

        Prefs.getPrefs(getContext()).edit().putString("classes", data).apply();
    }
}