package pax.mesa.tbd.ui.forums;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

class Post{
    int id;
    String title;
    public Post(int id, String title){
        this.id=id;
        this.title=title;
    }
}

public class ClassHomeScreen extends Fragment implements MyAdapter.ItemClickListener {

    private String className;
    private RecyclerView recyclerView;
    private ArrayList<Post> posts;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = ClassHomeScreenArgs.fromBundle(getArguments()).getClassName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_class_home_screen, container, false);
        posts = new ArrayList<>();
        loadPosts();
        adapter = new MyAdapter(getContext(), posts);
        recyclerView = root.findViewById(R.id.class_posts_list);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // Add the buttons
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                builder.setTitle("New Post");
                builder.setView(R.layout.new_post_layout);
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
    private void loadPosts(){
        //TODO: load posts
        //This code is for testing purposes and should be removed
        posts.add(new Post(12,"Title 1"));
        posts.add(new Post(12,"Title 2"));
        posts.add(new Post(12,"Title 3"));
        posts.add(new Post(12,"Title 4"));
        posts.add(new Post(12,"Title 5"));
        posts.add(new Post(12,"Title 6"));
    }
    private void createPost(String title, String text){
        //TODO: create posts
    }


}