package question6.question6_User.Profile_api;

import java.util.*;
import java.util.stream.Collectors;

/**
 * UserProfileService handles all business logic for user profile management
 * Uses in-memory storage for simplicity (can be replaced with database)
 */
public class UserProfileService {
    private final Map<Long, UserProfile> userProfiles = new HashMap<>();
    private Long nextUserId = 1L;

    /**
     * Create a new user profile
     */
    public UserProfile createUserProfile(UserProfile profile) {
        if (profile.getUserId() == null) {
            profile.setUserId(nextUserId++);
        } else {
            if (profile.getUserId() >= nextUserId) {
                nextUserId = profile.getUserId() + 1;
            }
        }
        userProfiles.put(profile.getUserId(), profile);
        return profile;
    }

    /**
     * Get user profile by ID
     */
    public UserProfile getUserProfileById(Long userId) {
        return userProfiles.get(userId);
    }

    /**
     * Get all user profiles
     */
    public List<UserProfile> getAllUserProfiles() {
        return new ArrayList<>(userProfiles.values());
    }

    /**
     * Update user profile
     */
    public UserProfile updateUserProfile(Long userId, UserProfile profile) {
        if (!userProfiles.containsKey(userId)) {
            return null;
        }
        profile.setUserId(userId);
        userProfiles.put(userId, profile);
        return profile;
    }

    /**
     * Delete user profile
     */
    public boolean deleteUserProfile(Long userId) {
        if (userProfiles.containsKey(userId)) {
            userProfiles.remove(userId);
            return true;
        }
        return false;
    }

    /**
     * Search user profiles by username
     */
    public List<UserProfile> searchByUsername(String username) {
        return userProfiles.values().stream()
                .filter(profile -> profile.getUsername().toLowerCase().contains(username.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Search user profiles by country
     */
    public List<UserProfile> searchByCountry(String country) {
        return userProfiles.values().stream()
                .filter(profile -> profile.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }

    /**
     * Search user profiles by age range
     */
    public List<UserProfile> searchByAgeRange(int minAge, int maxAge) {
        return userProfiles.values().stream()
                .filter(profile -> profile.getAge() >= minAge && profile.getAge() <= maxAge)
                .collect(Collectors.toList());
    }

    /**
     * Activate user profile
     */
    public UserProfile activateUserProfile(Long userId) {
        UserProfile profile = userProfiles.get(userId);
        if (profile != null) {
            profile.setActive(true);
            userProfiles.put(userId, profile);
        }
        return profile;
    }

    /**
     * Deactivate user profile
     */
    public UserProfile deactivateUserProfile(Long userId) {
        UserProfile profile = userProfiles.get(userId);
        if (profile != null) {
            profile.setActive(false);
            userProfiles.put(userId, profile);
        }
        return profile;
    }

    /**
     * Check if user exists by username
     */
    public boolean userExistsByUsername(String username) {
        return userProfiles.values().stream()
                .anyMatch(profile -> profile.getUsername().equalsIgnoreCase(username));
    }
}
