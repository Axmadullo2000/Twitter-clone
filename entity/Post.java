package entity;

import util.IdGenerator;

public class Post {
    private int id;
    private String title;
    private String comment;
    private int userId;

    private PostLike postLike;
    private PostComment postComment;
    private int viewsCount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Post(int id, String title, String comment, int userId) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.userId = userId;

        this.postLike = new PostLike();
        this.postComment = new PostComment();
        this.viewsCount = 0;
    }

    public Post() {
        this.postLike = new PostLike();
        this.postComment = new PostComment();
        this.viewsCount = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return comment;
    }

    public void setContent(String comment) {
        this.comment = Post.this.comment;
    }

    public PostLike getPostLike() {
        return postLike;
    }

    public PostComment getPostComment() {
        return postComment;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void incrementViews() {
        this.viewsCount++;
    }

    @Override
    public String toString() {
        return """
        Post Details:
        ----------------------------------------
        ID: %d
        Title: %s
        Content: %s
        User ID: %d
        Views: %d""".formatted(
            id,
            title,
            comment,
            userId,
            viewsCount
        );
    }
}
