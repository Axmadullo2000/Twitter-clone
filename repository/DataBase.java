package repository;

import entity.Post;
import entity.User;
import entity.UserPost;
import enums.Status;
import enums.UserRole;

import java.util.ArrayList;

public class DataBase {

    private int userId = 1;
    private int postId = 1;

    private User currentUser;

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Post> postList = new ArrayList<>();
    private ArrayList<UserPost> userPostList = new ArrayList<>();

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    public ArrayList<UserPost> getUserPostList() {
        return userPostList;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    {
        User admin = new User(0,"admin", "admin", UserRole.ADMIN, Status.ACTIVE );
        userList.add(admin);
    }

}
