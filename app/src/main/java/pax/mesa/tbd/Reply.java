package pax.mesa.tbd;

import java.util.Date;

public class Reply {

    public Post replyTo;
    public String replyContent;
    public String author;
    public Date replyDate;

    public Reply() {

    }

    public Reply(Post post, String content, User user, Date date) {
        this.replyTo = post;
        this.replyContent = content; //yoink from textbox again
        this.author = user.fName;
        this.replyDate = date;
    }
}
