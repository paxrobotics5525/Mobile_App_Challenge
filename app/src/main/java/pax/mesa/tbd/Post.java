package pax.mesa.tbd;

import java.util.Date;
import java.util.List;

public class Post {
    public String postTitle;
    public String postContent;
    public String author;
    public Date postDate;
    public List postReplies;

    public Post() {

    }

    public Post(String title, String content, User user, Date date) {
        this.postTitle = title; //yoink title from text box
        this.postContent = content; //yoink content from text box again
        this.author = user.fName;
        this.postDate = date;
    }

    public void addReply(Reply reply) {
        this.postReplies.add(reply);
    }

    //Add a method to "delete" a post meaning it doesnt show or sets title etc to show it was deleted
}
