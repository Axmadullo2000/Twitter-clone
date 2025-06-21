package entity;

import java.util.ArrayList;
import java.util.List;

public class PostComment {
    private List<Comment> comments;

    public PostComment() {
        this.comments = new ArrayList<>();
    }

    public void addComment(String comment, String commentator) {
        comments.add(new Comment(comment, commentator));
    }

    public int getCommentsCount() {
        return comments.size();
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Comment: %s".formatted(comments);
    }
}
