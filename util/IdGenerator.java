package util;

public class IdGenerator {
    private static int userId = 1;
    private static int postId = 1;

    public static int generateUserId() {
        return userId++;
    }

    public static int generatePostId() {
        return postId++;
    }

}
