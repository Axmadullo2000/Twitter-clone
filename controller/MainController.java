package controller;

import dto.UserRecord;
import entity.Comment;
import entity.Post;
import entity.User;
import enums.Status;
import enums.UserRole;
import service.MainService;
import static util.Util.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainController {

    private MainService mainService = new MainService();

    public void start() {

        while (true) {
            try {
                System.out.println("""
                1. Sign in
                2. Sign up
                0. Exit""");
                int option = getInteger("Choose an option");

                switch (option) {
                    case 1 -> {
                        loginPage();
                    }
                    case 2 -> {
                        registerPage();
                    }
                    case 0 -> {
                        return;
                    }
                }
            }catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

    }

    private void registerPage() {
        System.out.println("Create your account here!");

        try {
            String userName = getText("Enter your username");
            String password = getText("Enter your password");

            UserRecord userRecord = new UserRecord(userName, password);

            if (mainService.createAccount(userRecord)) {
                System.out.println("Your successfully achievements start here!");

                UserPage();
            }
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loginPage() {
        System.out.println("Log in to your account");

        try {
            String userName = getText("Enter username");
            String password = getText("Enter password");

            UserRecord userRecord = new UserRecord(userName, password);

            String role = mainService.loginToAccount(userRecord);

            if (role != null) {
                if (role.equals(UserRole.ADMIN.toString())) {
                    AdminPage();
                }

                else if (role.equals(UserRole.USER.toString())) {
                    UserPage();
                }
            }else {
                System.out.println("User is not registered in our program");
            }

        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    /* Admin Page */
    private void AdminPage() {
        while (true) {
            System.out.print("""
                    ******************************************
                            Welcome to Admin Page
                    ******************************************
                    """);
            System.out.print("""
                    1. Show all users
                    2. Block user
                    3. Activate user
                    4. Remove user
                    5. Change own password
                    0. Log out
                    """);

            int option = getInteger("Choose an option");

            switch (option) {
                case 1 -> {
                    showAllUsers();
                }

                case 2 -> {
                    blockUser();
                }

                case 3 -> {
                    activateUser();
                }

                case 4 -> {
                    removeUser();
                }

                case 5 -> {
                    changeAdminPassword();
                }

                case 0 -> {
                    return;
                }

                default -> {
                    System.out.println("Invalid element entered");
                    return;
                }
            }
        }
    }

    private void showAllUsers() {

        ArrayList<User> userData = mainService.showUserList();

        if (mainService.showUserList().isEmpty()) {
            System.out.print("""
                    -------------------
                    User doesn't exists
                    -------------------
                    """);
        }else {
            System.out.println("User lists");

            for (User data : userData) {
                System.out.println(data);
            }
        }
    }

    private void blockUser() {
        ArrayList <User> userData = mainService.showUserList();

        if (mainService.showUserList().isEmpty()) {
            System.out.print("""
                    -------------------
                    User doesn't exists
                    -------------------
                    """);
        }else {
            System.out.println("Active users");

            System.out.println("Which one user would you like to block");

            for (User user: userData) {
                if (user.getStatus().equals(Status.ACTIVE) && user.getId() != 0 ) {
                    System.out.println(user);
                }
            }

            int userId = getInteger("Choose user id");

            if (!mainService.checkedUser(userId)) {
                System.out.println("User not found");
                return;
            }

            mainService.blockUser(userId);
        }
    }

    private void activateUser() {
        ArrayList <User> userData = mainService.showUserList();

        if (mainService.showUserList().isEmpty()) {
            System.out.print("""
                    -------------------
                    User doesn't exists
                    -------------------
                    """);
        }else {
            System.out.println("Blocked users");

            int counter = 0;

            for (User user: userData) {
                if (user.getStatus().equals(Status.BLOCK)) {
                    counter++;
                }
            }

            if (counter == 0) {
                System.out.print("""
                        --------------
                        User not found
                        --------------
                        """);
                return;
            }

            System.out.println("Which one user would you like to active");

            for (User user: userData) {
                if (user.getStatus().equals(Status.BLOCK) && user.getId() != 0) {
                    System.out.println(user);
                }
            }

            int userId = getInteger("Choose user id");

            mainService.activateUser(userId);
        }
    }

    private void removeUser() {
        System.out.println("Delete user");

        if (mainService.showUserList().isEmpty()) {
            System.out.print("""
                    -------------------
                    User doesn't exists
                    -------------------
                    """);
            return;
        }

        System.out.println("Which one user would you like to remove");
        showAllUsers();

        int userId = getInteger("Choose user id");

        if (!mainService.checkedUser(userId)) {
            System.out.println("User not found");
            return;
        }

        mainService.deleteUser(userId);
    }

    private void changeAdminPassword() {
        System.out.println("Change Admin Password");

        String currentPass = getText("Enter current password");

        if (!mainService.checkPassword(currentPass)) {
            System.out.print("""
                    ----------------------------
                    ❌ Enter current password!!!
                    ----------------------------
                   """);
            return;
        }

        String newPassword = getText("Enter new password");

        if (newPassword.length() < 8) {
            System.out.println("Password must be consist more than 8 elements");
            return;
        }

        mainService.changePassword(currentPass, newPassword);
    }

    /* User Page */

    private void UserPage() {
        while (true) {
            System.out.println("""
                    ******************************************
                            Welcome to User Page
                    ******************************************
                    """);

            System.out.print("""
                    1. Create post
                    2. Show posts
                    3. Global posts
                    4. Change own password
                    0. Log out
                    """);

            int option = getInteger("Choose an option");

            switch (option) {
                case 1 -> {
                    createPost();
                }

                case 2 -> {
                    showMyPosts();
                }

                case 3 -> {
                    globalPosts();
                }

                case 4 -> {
                    changeUserPassword();
                }

                case 0 -> {
                    return;
                }

                default -> {
                    System.out.println("Invalid element entered");
                }
            }
        }
    }

    private void createPost() {
        System.out.println("Create Post");

        if (mainService.isCurrentUserBlocked()) {
            System.out.println("You are blocked by admins");
            return;
        }

        String title = getText("Enter title");
        String content = getText("Enter content");

        try {
            ArrayList<Post> data = mainService.createPost(title, content);

            System.out.println("------------------------------------------");

            for (Post post: data) {
                if (post != null) {
                    System.out.println(post);
                }
            }
        }catch (RuntimeException e) {
            System.out.println("------------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------");
        }
    }

    private void showMyPosts() {
        if (mainService.showPosts().isEmpty()) {
            System.out.println("----------------");
            System.out.println("Nothing to show!");
            System.out.println("----------------");
            return;
        }

        System.out.println("Posts list");

        for (Post post: mainService.showPosts()) {
            System.out.println(post);

            Set<Integer> likedUserIds = post.getPostLike().getLikedUserIds();
            Set<String> likedUsernames = new HashSet<>();

            for (int userId : likedUserIds) {
                likedUsernames.add(mainService.getUsernameById(userId));
            }

            System.out.println("Liked by: " + likedUsernames);

            System.out.println("Comments:");

            for (Comment comment : post.getPostComment().getComments()) {
                System.out.println(" - " + comment);
            }
        }

        int postId = getInteger("Enter post ID to interact with");
        int currentUserId = mainService.getCurrentUserId();

        if (currentUserId == -1) {
            System.out.println("Error: No user is logged in!");
            return;
        }

        Post selectedPost = mainService.getPostById(postId, currentUserId);

        if (selectedPost == null ) {
            System.out.println("Post not found for this user!");
            return;
        }

        mainService.incrementPostViewsCount(postId);

        System.out.println("""
            1. Like this post
            2. Comment on this post
            3. View likes, comments, and views
            0. Go back
        """);

        int postOption = getInteger("Choose an option");

        switch (postOption) {
            case 1 -> likePost(selectedPost);
            case 2 -> commentsPost(selectedPost);
            case 3 -> showPostInteractions(selectedPost);
        }
    }


    private void globalPosts() {
        if (mainService.getAllPosts().isEmpty()) {
            System.out.println("----------------");
            System.out.println("Nothing to show!");
            System.out.println("----------------");
            return;
        }

        System.out.println("Posts list");

        System.out.println("----------------------------------------");

        ArrayList<Post> allPosts = mainService.getAllPosts();

        if (allPosts == null) {
            System.out.println("Post not found!");
            return;
        }

        for (Post post: mainService.getAllPosts()) {
            System.out.println(post);

            printPostDetails(post);
        }

        int postId = getInteger("Choose one post id");

        try {
            Post selectedPost = mainService.getGlobalPostById(postId);

            mainService.incrementGloballyPostViewsCount(selectedPost.getId());

            System.out.println("""
                1. Like this post
                2. Comment on this post
                3. View likes, comments, and views
                0. Go back
            """);

        int postOption = getInteger("Choose an option");

        switch (postOption) {
            case 1 -> likePost(selectedPost);
            case 2 -> commentsPost(selectedPost);
            case 3 -> showPostInteractions(selectedPost);
        }

        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printPostDetails(Post post) {
        Set<Integer> likedUserIds = post.getPostLike().getLikedUserIds();
        Set<String> likedUsernames = new HashSet<>();
        for (int userId : likedUserIds) {
            likedUsernames.add(mainService.getUsernameById(userId));
        }

        System.out.println("Liked by: " + likedUsernames);

        System.out.println("Comments:");
        for (Comment comment : post.getPostComment().getComments()) {
            System.out.println(" - " + comment);
        }

        System.out.println("----------------------------------------");
    }

    private void likePost(Post post) {

        boolean liked = mainService.addLike(post.getId(), mainService.getCurrentUserId());

        if (liked) {
            System.out.println("Successfully liked!");
        } else {
            Post checkPost = mainService.getGlobalPostById(post.getId());
            if (checkPost == null) {
                System.out.println("Error: Post not found!");
            } else {
                System.out.println("You have already liked this post!");
            }
        }
    }

    private void commentsPost(Post post) {
        String comment = getText("Enter your comment");
        if (mainService.addComment(post.getId(), comment)) {
            System.out.println("Comment added!");
        }else {
            System.out.println("Post with ID " + post.getId() + " not found!");
        }

    }

    private void showPostInteractions(Post post) {
        System.out.println("Likes: " + post.getPostLike().getLikesCount());
        System.out.println("Comments: " + post.getPostComment().getCommentsCount());
        System.out.println("Views: " + post.getViewsCount());

        System.out.println("Comment list: ");
        for (Comment comment : post.getPostComment().getComments()) {
            System.out.println(comment);
        }
    }

    private void changeUserPassword() {
        System.out.println("Change User Password");

        String currentPass = getText("Enter current password");

        if (!mainService.checkPassword(currentPass)) {
            System.out.println("❌ Enter current password!");
            return;
        }

        String newPassword = getText("Enter new password");

        mainService.changePassword(currentPass, newPassword);
    }
}
