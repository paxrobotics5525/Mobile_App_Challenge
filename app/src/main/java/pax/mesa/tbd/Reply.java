package pax.mesa.tbd;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Reply {

    public String replyTo;
    public String replyContent;
    public String author;
    public String replyDate;

    public Reply() {

    }

    public Reply(String postID, String content, FirebaseUser user, String date) {
        this.replyTo = postID;
        this.replyContent = content; //yoink from textbox again
        this.author = user.getUid();
        this.replyDate = date;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("post", replyTo);
        result.put("content", replyContent);
        result.put("author", author);
        result.put("date", replyDate);

        return result;
    }
}