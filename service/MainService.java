package service;

import entity.Post;
import entity.User;
import enums.Status;
import enums.UserRole;
import repository.DataBase;

import java.util.ArrayList;

public class MainService {
    private DataBase dataBase = new DataBase();

    public boolean createAccount(String userName, String password) {

        for (User user: dataBase.getUserList()) {
            if (userName.length() < 5 || password.length() < 8 ) {
                throw new RuntimeException("❌ Username length must be more than 5, password length at least 8");
            }

            if (user.getUsername().equals(userName) ) {
                throw new RuntimeException("This username already exists!");
            }
        }


        int newUserId = dataBase.getUserId();
        User newUser = new User(newUserId, userName, password, UserRole.USER, Status.ACTIVE);

        dataBase.getUserList().add(newUser);

        dataBase.setUserId(newUserId + 1);
        dataBase.setCurrentUser(newUser);

        return true;
    }

    public String loginToAccount(String userName, String password) {
        for (User user: dataBase.getUserList()) {
            if (!user.getUsername().equals("admin") && !user.getPassword().equals("admin")) {
                if (userName.length() < 5 || password.length() < 8) {
                    throw new RuntimeException("❌ Username length must be more than 5, password length at least 8");
                }
            }

            if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                dataBase.setCurrentUser(user);
                return user.getRole().toString();
            }
        }

        return null;
    }

    public ArrayList<User> showUserList() {
        ArrayList<User> result = new ArrayList<>();

        for (User user: dataBase.getUserList()) {
            if (user.getId() != 0) {
                result.add(user);
            }
        }

        return result;
    }

    public boolean checkedUser(int userId) {
        for (User user: dataBase.getUserList()) {
            if (user.getId() == userId) {
                return true;
            }
        }

        return false;
    }

    public void blockUser(int userId) {
        for (User user: dataBase.getUserList()) {
            if (user.getId() == userId && user.getStatus().equals(Status.ACTIVE) ) {
                user.setStatus(Status.BLOCK);
                return;
            }
        }
    }

    public void activateUser(int userId) {
        for (User user: dataBase.getUserList()) {
            if (user.getId() == userId && user.getStatus().equals(Status.BLOCK) ) {
                user.setStatus(Status.ACTIVE);
                return;
            }
        }
    }

    public void deleteUser(int userId) {
        dataBase.getUserList().remove(userId);
    }

    public boolean checkPassword(String currentPass) {
        for (User user: dataBase.getUserList()) {
            if (user.getPassword().equals(currentPass)) {
                return true;
            }
        }

        return false;
    }

    public void changePassword(String currentPassword, String newPassword) {
        for (User user: dataBase.getUserList()) {
            if (user.getPassword().equals(currentPassword)) {
                user.setPassword(newPassword);
                return;
            }
        }
    }

    public boolean isCurrentUserBlocked() {
        User currentUser = dataBase.getCurrentUser();
        return currentUser.getStatus().equals(Status.BLOCK);
    }

    public ArrayList<Post> createPost(String title, String content) {
        Post post = new Post(dataBase.getPostId(), title, content, dataBase.getUserId());
        User currentUser = dataBase.getCurrentUser();
        ArrayList<User> userList = dataBase.getUserList();

        if (title.isEmpty() || content.isEmpty()) {
            throw new RuntimeException("Title or content fields are empty!");
        }

        for (User user: userList) {
            if (user.getStatus().equals(Status.BLOCK)) {
                return null;
            }
        }

        dataBase.getPostList().add(post);

        int newPostId = dataBase.getPostId();

        dataBase.setPostId(newPostId + 1);

        ArrayList<Post> userPosts = dataBase.getPostList();

        for (Post p: userPosts) {
            if (p.getUserId() == currentUser.getId() ) {
                userPosts.add(p);
            }
        }

        return userPosts;
    }

    public ArrayList<Post> showPosts() {
        User currentUser = dataBase.getCurrentUser();

        ArrayList<Post> userPosts = dataBase.getPostList();

        for (Post p: userPosts) {
            if (p.getUserId() == currentUser.getId()) {
                userPosts.add(p);
            }
        }

        return userPosts;
    }
}
