package entity;

import enums.Status;
import enums.UserRole;

public class User extends UserPost {

    private Integer id;
    private String userName;
    private String password;
    private UserRole role;
    private Status status;
    private int postCounter = 0;

    public int getPostIdCounter() {
        return postIdCounter;
    }

    public int getPostCounter() {
        return postCounter;
    }

    private int postIdCounter;

    public int getNextPostId() {
        return ++postCounter;
    }

    public User(int id, String userName, String password, UserRole role, Status status, int userId, int postId) {
        super(userId, postId);
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.status = status;
        this.postIdCounter = 0;
        this.postCounter = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "User information:\n id: %d, username: %s, password: %s, role: %s, status: %s"
                .formatted(id, userName, password, role, status);
    }
}
