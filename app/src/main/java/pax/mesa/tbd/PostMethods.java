package pax.mesa.tbd;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class PostMethods {

    public static void doNewPost(DatabaseReference data, String userClass, FirebaseUser author, String title, String content, String date) {
        String key = data.child("posts").push().getKey();
        Post post = new Post(key, title, content, userClass, author, date);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);

        data.updateChildren(childUpdates);
    }

    public static void doNewReply(DatabaseReference data, String pID, FirebaseUser author, String content, String date) {
        String key = data.child("posts").child(pID).push().getKey();
        Reply reply = new Reply(pID, content, author, date);
        Map<String, Object> replyValues = reply.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + pID + "/" + "/replies/" + key, replyValues);

        data.updateChildren(childUpdates);
    }
}
