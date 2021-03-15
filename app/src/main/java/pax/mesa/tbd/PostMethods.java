package pax.mesa.tbd;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pax.mesa.tbd.ui.forums.MyAdapter;

import static java.security.AccessController.getContext;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PostMethods {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private static FirebaseUser currentUser = mAuth.getCurrentUser();

    public static void doNewPost(String userClass, String title, String content, Context context) {
        if(!title.isEmpty() || !content.isEmpty()) {
            LocalDateTime time = LocalDateTime.now();

            String key = mData.child("posts").push().getKey();
            Post post = new Post(key, title, content, userClass, currentUser, formatter.format(time));
            Map<String, Object> postValues = post.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/posts/" + key, postValues);

            mData.updateChildren(childUpdates);
        } else {
            Toast.makeText(context, "Title or content field was left blank.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void doNewReply(String pID, String content) {
        LocalDateTime time = LocalDateTime.now();

        String key = mData.child("posts").child(pID).push().getKey();
        Reply reply = new Reply(pID, content, currentUser, formatter.format(time));
        Map<String, Object> replyValues = reply.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + pID + "/" + "/replies/" + key, replyValues);

        mData.updateChildren(childUpdates);
    }

    public static void displayPosts(String className, List<Map<String, Object>> posts, MyAdapter adapter) {
        //checks if what was clicked is the same as the class mentioned in the post object
        //if a post is in the class that was clicked, we want to show all those posts
        for(int i = 0; i < posts.size(); i++) {
            if (className.equals(posts.get(i).get("class").toString())) {
                //add posts to adapter??? maybe make it show title and then the author underneath

                Log.d("[POSTS]", posts.get(i).get("title").toString()); //this is to check if it was working
            }
        }
    }
}
