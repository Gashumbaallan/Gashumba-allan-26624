package question6.question6_User_Profile_api;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user-profiles")
@CrossOrigin(origins = "*")
public class UserProfileController {

    private List<UserProfile> userProfiles = new ArrayList<>();
    private Long nextId = 1L;

    // CREATE - Add a new user profile
    @PostMapping
    public ApiResponse<UserProfile> createUserProfile(@RequestBody UserProfile userProfile) {
        userProfile.setUserId(nextId++);
        userProfiles.add(userProfile);
        return new ApiResponse<>(true, "User profile created successfully", userProfile);
    }

    // READ - Get all user profiles
    @GetMapping
    public ApiResponse<List<UserProfile>> getAllUserProfiles() {
        if (userProfiles.isEmpty()) {
            return new ApiResponse<>(true, "No user profiles found", userProfiles);
        }
        return new ApiResponse<>(true, "User profiles retrieved successfully", userProfiles);
    }

    // READ - Get a specific user profile by ID
    @GetMapping("/{userId}")
    public ApiResponse<UserProfile> getUserProfileById(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfiles.stream()
                .filter(up -> up.getUserId().equals(userId))
                .findFirst();

        if (userProfile.isPresent()) {
            return new ApiResponse<>(true, "User profile retrieved successfully", userProfile.get());
        } else {
            return new ApiResponse<>(false, "User profile not found", null);
        }
    }

    // UPDATE - Update a user profile
    @PutMapping("/{userId}")
    public ApiResponse<UserProfile> updateUserProfile(@PathVariable Long userId, @RequestBody UserProfile updatedProfile) {
        Optional<UserProfile> existingProfile = userProfiles.stream()
                .filter(up -> up.getUserId().equals(userId))
                .findFirst();

        if (existingProfile.isPresent()) {
            UserProfile profile = existingProfile.get();
            profile.setUsername(updatedProfile.getUsername());
            profile.setEmail(updatedProfile.getEmail());
            profile.setFullName(updatedProfile.getFullName());
            profile.setAge(updatedProfile.getAge());
            profile.setCountry(updatedProfile.getCountry());
            profile.setBio(updatedProfile.getBio());
            return new ApiResponse<>(true, "User profile updated successfully", profile);
        } else {
            return new ApiResponse<>(false, "User profile not found", null);
        }
    }

    // DELETE - Delete a user profile
    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUserProfile(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfiles.stream()
                .filter(up -> up.getUserId().equals(userId))
                .findFirst();

        if (userProfile.isPresent()) {
            userProfiles.remove(userProfile.get());
            return new ApiResponse<>(true, "User profile deleted successfully", "User " + userId + " deleted");
        } else {
            return new ApiResponse<>(false, "User profile not found", null);
        }
    }

    // SEARCH - Search by username
    @GetMapping("/search/username")
    public ApiResponse<List<UserProfile>> searchByUsername(@RequestParam String username) {
        List<UserProfile> results = userProfiles.stream()
                .filter(up -> up.getUsername().toLowerCase().contains(username.toLowerCase()))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return new ApiResponse<>(true, "No user profiles found with username: " + username, results);
        }
        return new ApiResponse<>(true, "User profiles found", results);
    }

    // SEARCH - Search by country
    @GetMapping("/search/country")
    public ApiResponse<List<UserProfile>> searchByCountry(@RequestParam String country) {
        List<UserProfile> results = userProfiles.stream()
                .filter(up -> up.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return new ApiResponse<>(true, "No user profiles found in country: " + country, results);
        }
        return new ApiResponse<>(true, "User profiles found", results);
    }

    // SEARCH - Search by age range
    @GetMapping("/search/age-range")
    public ApiResponse<List<UserProfile>> searchByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        List<UserProfile> results = userProfiles.stream()
                .filter(up -> up.getAge() >= minAge && up.getAge() <= maxAge)
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return new ApiResponse<>(true, "No user profiles found in age range " + minAge + " to " + maxAge, results);
        }
        return new ApiResponse<>(true, "User profiles found", results);
    }

    // ACTIVATE - Activate a user profile
    @PutMapping("/{userId}/activate")
    public ApiResponse<UserProfile> activateUserProfile(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfiles.stream()
                .filter(up -> up.getUserId().equals(userId))
                .findFirst();

        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            profile.setActive(true);
            return new ApiResponse<>(true, "User profile activated successfully", profile);
        } else {
            return new ApiResponse<>(false, "User profile not found", null);
        }
    }

    // DEACTIVATE - Deactivate a user profile
    @PutMapping("/{userId}/deactivate")
    public ApiResponse<UserProfile> deactivateUserProfile(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfiles.stream()
                .filter(up -> up.getUserId().equals(userId))
                .findFirst();

        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            profile.setActive(false);
            return new ApiResponse<>(true, "User profile deactivated successfully", profile);
        } else {
            return new ApiResponse<>(false, "User profile not found", null);
        }
    }

    // Get active users only
    @GetMapping("/filter/active")
    public ApiResponse<List<UserProfile>> getActiveUsers() {
        List<UserProfile> activeUsers = userProfiles.stream()
                .filter(UserProfile::isActive)
                .collect(Collectors.toList());

        if (activeUsers.isEmpty()) {
            return new ApiResponse<>(true, "No active user profiles found", activeUsers);
        }
        return new ApiResponse<>(true, "Active user profiles retrieved successfully", activeUsers);
    }

    // Get inactive users only
    @GetMapping("/filter/inactive")
    public ApiResponse<List<UserProfile>> getInactiveUsers() {
        List<UserProfile> inactiveUsers = userProfiles.stream()
                .filter(up -> !up.isActive())
                .collect(Collectors.toList());

        if (inactiveUsers.isEmpty()) {
            return new ApiResponse<>(true, "No inactive user profiles found", inactiveUsers);
        }
        return new ApiResponse<>(true, "Inactive user profiles retrieved successfully", inactiveUsers);
    }
}
