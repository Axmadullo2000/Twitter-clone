package service;

import dto.UserRecord;
import entity.Post;
import entity.User;
import enums.Status;
import enums.UserRole;
import repository.DataBase;
import util.IdGenerator;

import java.util.ArrayList;

public class MainService {
    private DataBase dataBase = new DataBase();

    public boolean createAccount(UserRecord userRecord) {

        for (User user: dataBase.getUserList()) {
            if (user.getUsername().equals(userRecord.userName()) ) {
                throw new RuntimeException("This username already exists!");
            }
        }

        int newUserId = IdGenerator.generateUserId();
        User newUser = new User(newUserId, userRecord.userName(), userRecord.password(), UserRole.USER, Status.ACTIVE, newUserId, 0);

        dataBase.getUserList().add(newUser);
        dataBase.setCurrentUser(newUser);

        return true;
    }

    public String loginToAccount(UserRecord userRecord) {
        for (User user: dataBase.getUserList()) {
            if (user.getUsername().equals(userRecord.userName()) && user.getPassword().equals(userRecord.password())) {
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
        if (title.isEmpty() || content.isEmpty()) {
            throw new RuntimeException("Title or content fields are empty!");
        }

        User currentUser = dataBase.getCurrentUser();

        if (currentUser.getStatus().equals(Status.BLOCK)) {
            throw new RuntimeException("❌ Your account is blocked. Can not create a post.");
        }

        Post post = new Post(IdGenerator.generatePostId(), title, content, currentUser.getId());
        dataBase.getPostList().add(post);

        ArrayList<Post> userPosts = new ArrayList<>();

        for (Post p : dataBase.getPostList()) {
            if (p.getUserId() == currentUser.getId()) {
                userPosts.add(p);
            }
        }

        return userPosts;
    }

    public ArrayList<Post> getAllPosts() {
        // todo
        return dataBase.getPostList();
    }

    public ArrayList<Post> showPosts() {
        User currentUser = dataBase.getCurrentUser();

        ArrayList<Post> userPosts = new ArrayList<>();

        for (Post p: dataBase.getPostList()) {
            if (p.getUserId() == currentUser.getId()) {
                userPosts.add(p);
            }
        }

        return userPosts;
    }

    public boolean addLike(int postId, int likerUserId) {
        Post post = getGlobalPostById(postId);

        if (post == null || post.getPostLike().isLikedByUser(likerUserId) ) {
            return false;
        }

        post.getPostLike().addLike(likerUserId);

        return true;
    }

    public boolean addComment(int commentId, String comment) {
        Post post = getGlobalPostById(commentId);

        if (post != null) {
            String commentator = getUsernameById(getCurrentUserId());
            post.getPostComment().addComment(comment, commentator);
            return true;
        }

        return false;
    }

    public void incrementPostViewsCount(int postId) {
        Post post = getGlobalPostById(postId);

        if (post != null) {
            post.incrementViews();
        }
    }

    public void incrementGloballyPostViewsCount(int postId) {
        Post post = getGlobalPostById(postId);

        if (post != null) {
            post.incrementViews();
        }
    }

    public Post getPostById(int postId, int currentUserId) {
        for (Post post : dataBase.getPostList()) {
            if (post.getId() == postId && post.getUserId() == currentUserId ) {
                return post;
            }
        }

        return null;
    }

    public Post getGlobalPostById(int postId) {
        for (Post post : dataBase.getPostList()) {
            if (post.getId() == postId ) {
                return post;
            }
        }

        return null;
    }

    public int getCurrentUserId() {
        User currentUser = dataBase.getCurrentUser();

        if (currentUser == null) return -1;

        return currentUser.getId();
    }

    public String getUsernameById(int userId) {
        User user = dataBase.getUserById(userId);
        return user != null ? user.getUsername() : "Unknown";
    }

}
