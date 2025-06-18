package entity;

public class UserPost {

    public Integer userId;
    private Integer postId;

    public Integer getUserId() {
        return userId;
    }

    public UserPost(Integer userId, Integer postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
