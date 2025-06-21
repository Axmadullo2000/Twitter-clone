package entity;

import java.util.HashSet;
import java.util.Set;

public class PostLike {
    private Set<Integer> likedByUsers;

    public PostLike() {
        this.likedByUsers = new HashSet<>();
    }

    public void addLike(int userId) {
        likedByUsers.add(userId);
    }

    public int getLikesCount() {
        return likedByUsers.size();
    }

    public boolean isLikedByUser(int userId) {
        return likedByUsers.contains(userId);
    }

    public Set<Integer> getLikedUserIds() {
        return likedByUsers;
    }

    @Override
    public String toString() {
        return "Liked by user: %s".formatted(likedByUsers);
    }
}
