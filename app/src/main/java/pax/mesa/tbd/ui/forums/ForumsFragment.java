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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavAction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private String[] classes;
    ArrayAdapter<String> adapter = null;

    private DatabaseReference mData;
    private DatabaseReference mPost;
    private FirebaseAuth mAuth;

    public List<Map<String, Object>> postList = new ArrayList<Map<String, Object>>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        forumsViewModel = new ViewModelProvider(this).get(ForumsViewModel.class);
        root = inflater.inflate(R.layout.fragment_forums, container, false);

        String list = Prefs.getPrefs(getContext()).getString("classes", "");
        classes = list.split("\t");
        listView = (ListView) root.findViewById(R.id.forums_list);
        if (list == "") {
            //TODO: Check for no data
        }
        else {
            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, classes);
            listView.setAdapter(adapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ForumsFragmentDirections.ActionForumsToClass action = ForumsFragmentDirections.actionForumsToClass();
                action.setClassName(classes[position]);
                Navigation.findNavController(root).navigate(action);
            }
        });
        View root = inflater.inflate(R.layout.fragment_forums, container, false);

        mData = FirebaseDatabase.getInstance().getReference();
        mPost = mData.child("posts");

        mAuth = FirebaseAuth.getInstance();

        Button bTestPost = root.findViewById(R.id.b_testPost);
        Button bTestReply = root.findViewById(R.id.b_testReply);

        mPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot data : snapshot.getChildren()) {
                    Map<String, Object> post = (Map) data.getValue();
                    //String basic = post.get("title").toString() + " by " + post.get("author").toString();
                    postList.add(post);
                    Log.d("POSTS", String.valueOf(postList));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bTestPost.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                LocalDateTime time = LocalDateTime.now();

                PostMethods.doNewPost(mData, "AP Physics 2", mAuth.getCurrentUser(), "Pog", "This is a test.", dateFormat.format(time));
            }
        });

        bTestReply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                LocalDateTime time = LocalDateTime.now();

                PostMethods.doNewReply(mData, postList.get(0).get("id").toString(), mAuth.getCurrentUser(), "This is a reply", dateFormat.format(time));
            }
        });

        return root;
    }
}