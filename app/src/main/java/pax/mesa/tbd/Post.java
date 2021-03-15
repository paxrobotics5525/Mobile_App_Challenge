package pax.mesa.tbd;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post {
    private String title;
    private String postContent;
    private String author;
    private String postDate;
    private List postReplies;
    private String postID;
    private String userClass;


    public Post() {

    }

    public Post(String id, String title, String content, String Class, FirebaseUser user, String date) {
        this.title = title; //yoink title from text box
        this.postContent = content; //yoink content from text box again
        this.author = user.getUid();
        this.postDate = date;
        this.userClass = Class;
        this.postID = id;
    }

    public void addReply(Reply reply) {
        this.postReplies.add(reply);

        //push replies to db

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", postID);
        result.put("class", userClass);
        result.put("author", author);
        result.put("date", postDate);
        result.put("title", title);
        result.put("content", postContent);
        result.put("replies", postReplies);

        return result;
    }

    public String getKey() {
        return postID;
    }

    public String getPostTitle() {
        return title;
    }

    public void setPostTitle(String postTitle) {
        this.title = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public List getPostReplies() {
        return postReplies;
    }

    public void setPostReplies(List postReplies) {
        this.postReplies = postReplies;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    //Add a method to "delete" a post meaning it doesnt show or sets title etc to show it was deleted
}
