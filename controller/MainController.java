package controller;

import entity.Post;
import entity.User;
import enums.Status;
import enums.UserRole;
import service.MainService;
import util.Util;

import java.util.ArrayList;

public class MainController {

    private MainService mainService = new MainService();

    Util util = new Util();

    public void start() {

        while (true) {
            try {
                System.out.println("""
                1. Sign in
                2. Sign up
                0. Exit""");
                int option = util.getInteger("Choose an option");

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
            String userName = util.getText("Enter your username");
            String password = util.getText("Enter your password");

            if (mainService.createAccount(userName, password)) {
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
            String userName = util.getText("Enter username");
            String password = util.getText("Enter password");

            String role = mainService.loginToAccount(userName, password);

            if (role != null) {
                if (role.equals(UserRole.ADMIN.toString())) {
                    AdminPage();
                }

                if (role.equals(UserRole.USER.toString())) {
                    UserPage();
                }
            }else {
                System.out.println("Invalid password or username");
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

            int option = util.getInteger("Choose an option");

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

            int userId = util.getInteger("Choose user id");

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

            int userId = util.getInteger("Choose user id");

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

        int userId = util.getInteger("Choose user id");

        if (!mainService.checkedUser(userId)) {
            System.out.println("User not found");
            return;
        }

        mainService.deleteUser(userId);
    }

    private void changeAdminPassword() {
        System.out.println("Change Admin Password");

        String currentPass = util.getText("Enter current password");

        if (!mainService.checkPassword(currentPass)) {
            System.out.print("""
                    ----------------------------
                    ❌ Enter current password!!!
                    ----------------------------
                   """);
            return;
        }

        String newPassword = util.getText("Enter new password");

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
                    3. Change own password
                    0. Log out
                    """);

            int option = util.getInteger("Choose an option");

            switch (option) {
                case 1 -> {
                    createPost();
                }

                case 2 -> {
                    showPosts();
                }

                case 3 -> {
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

        String title = util.getText("Enter title");
        String content = util.getText("Enter content");

        try {
            ArrayList<Post> data = mainService.createPost(title, content);

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

    private void showPosts() {
        if (mainService.showPosts().isEmpty()) {
            System.out.println("----------------");
            System.out.println("Nothing to show!");
            System.out.println("----------------");
            return;
        }

        System.out.println("Posts list");

        for (Post post: mainService.showPosts()) {
            System.out.println(post);
        }
    }

    private void changeUserPassword() {
        System.out.println("Change User Password");

        String currentPass = util.getText("Enter current password");

        if (!mainService.checkPassword(currentPass)) {
            System.out.println("❌ Enter current password!");
            return;
        }

        String newPassword = util.getText("Enter new password");

        mainService.changePassword(currentPass, newPassword);
    }
}
