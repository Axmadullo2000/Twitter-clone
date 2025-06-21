package repository;

import entity.Post;
import entity.User;
import entity.UserPost;
import enums.Status;
import enums.UserRole;

import java.util.ArrayList;

public class DataBase {
    private User currentUser;

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Post> postList = new ArrayList<>();
    private ArrayList<UserPost> userPostList = new ArrayList<>();

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public User getUserById(int id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
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
        User admin = new User(0,"admin65419", "12345678", UserRole.ADMIN, Status.ACTIVE, 0, 0 );
        userList.add(admin);
    }

}
