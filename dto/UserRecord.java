package dto;

public record UserRecord(String userName, String password) {
    public UserRecord {
        if (userName.length() < 5 || password.length() < 8 ) {
            throw new RuntimeException("❌ Username length must be more than 5, password length at least 8");
        }
    }
}
