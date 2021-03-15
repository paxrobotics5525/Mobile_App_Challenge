package pax.mesa.tbd.ui.forums;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavAction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pax.mesa.tbd.PostMethods;

import pax.mesa.tbd.Prefs;
import pax.mesa.tbd.ui.forums.ClassHomeScreen;

public class ForumsFragment extends Fragment {

    private ForumsViewModel forumsViewModel;
    private View root;
    private ListView listView;
    private ListView postsList;
    private String[] classes;
    private int classIndex;
    private int postIndex;

    ArrayAdapter<String> adapter = null;

    private DatabaseReference mData;
    private DatabaseReference mPost;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        forumsViewModel = new ViewModelProvider(this).get(ForumsViewModel.class);
        root = inflater.inflate(R.layout.fragment_forums, container, false);

        mAuth = FirebaseAuth.getInstance();

        String list = Prefs.getPrefs(getContext()).getString("classes", "");
        classes = list.split("\t");
        listView = root.findViewById(R.id.forums_list);
        postsList = root.findViewById(R.id.class_posts_list);

        if (list == "") {
            //TODO: Check for no data
        } else {
            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, classes);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ForumsFragmentDirections.ActionForumsToClass action = ForumsFragmentDirections.actionForumsToClass();
                action.setClassName(classes[position]);
                Navigation.findNavController(root).navigate(action);
            }
        });

        return root;
    }
}