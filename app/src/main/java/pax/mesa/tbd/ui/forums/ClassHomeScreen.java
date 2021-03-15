package pax.mesa.tbd.ui.forums;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pax.mesa.tbd.PostMethods;

public class ClassHomeScreen extends Fragment implements MyAdapter.ItemClickListener {

    private String className;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private DatabaseReference mData;
    private DatabaseReference mPost;

    private String postTitle;
    private String postContent;

    public List<Map<String, Object>> postList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = ClassHomeScreenArgs.fromBundle(getArguments()).getClassName();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_class_home_screen, container, false);

        mData = FirebaseDatabase.getInstance().getReference();
        mPost = mData.child("posts");

        adapter = new MyAdapter(getContext(), postList);

        recyclerView = root.findViewById(R.id.class_posts_list);
        recyclerView.setAdapter(adapter);

        //This is what gets all the post info from the db
        mPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear(); //empty the post array so when a post is deleted it doesnt show up again
                //this loops through all post nodes in db and then we add to the array
                for(DataSnapshot data : snapshot.getChildren()) {
                    Map<String, Object> post = (Map) data.getValue();
                    /*
                    Sends all the post maps into the array
                    You can grab info directly like this:
                    postList.get(index).get(info you want).toString();
                    "info you want" is something such as "class" or "author"
                    Check post class toMap() for reference
                    */

                    postList.add(post);
                }
                //refresh posts here
                PostMethods.displayPosts(className, postList, adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("[POSTS]", "Error getting posts");
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("New Post");
                View inflated = LayoutInflater.from(getContext()).inflate(R.layout.new_post_layout, (ViewGroup) root, false);
                builder.setView(inflated);

                final TextView pTitle = inflated.findViewById(R.id.i_post_title);
                final TextView pContent = inflated.findViewById(R.id.i_post_content);
                // Add the buttons
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(DialogInterface dialog, int id) {
                        postTitle = pTitle.getText().toString();
                        postContent = pContent.getText().toString();

                        PostMethods.doNewPost(className, postTitle, postContent, getContext());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return root;
    }
    @Override
    public void onItemClick(View view, int position) {
//        ClassHomeScreenDirections.ActionClassHomeScreenToPost action = ClassHomeScreenDirections.actionClassHomeScreenToPost();
//        action.setPostId(posts[position].id);
//        Navigation.findNavController(this.get).navigate(action);
    }
}