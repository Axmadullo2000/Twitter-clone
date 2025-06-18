package entity;

import enums.Status;
import enums.UserRole;

public class User extends UserPost {

    private Integer id;
    private String username;
    private String password;
    private UserRole role;
    private Status status;

    public User(int id, String username, String password, UserRole role, Status status, int userId, int postId) {
        super(userId, postId);
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return "User { " +
                "id = " + id +
                ", username = '" + username + '\'' +
                ", password = '" + password + '\'' +
                ", role = " + role +
                ", status = " + status +
                '}';
    }
}
