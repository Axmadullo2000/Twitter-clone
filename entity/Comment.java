package entity;

import util.Date;

public class Comment {
    private String commentator;
    private String comment;
    private String time;

    public String getCommentator() {
        return commentator;
    }

    public Comment(String comment, String commentator) {
        this.commentator = commentator;
        this.comment = comment;
        this.time = Date.postedTime();
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return commentator + ": " + comment + " (" + time + ")";
    }
}
