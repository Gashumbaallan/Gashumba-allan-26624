package question6.question6_User.Profile_api;

import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * UserProfileController handles all HTTP requests for user profile management
 * Provides CRUD operations, search, and profile activation/deactivation
 */
@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {
    private final UserProfileService userProfileService = new UserProfileService();

    /**
     * Create a new user profile
     * POST /api/profiles
     */
    @PostMapping
    public ApiResponse<UserProfile> createUserProfile(@RequestBody UserProfile profile) {
        try {
            // Validate input
            if (profile.getUsername() == null || profile.getUsername().trim().isEmpty()) {
                return new ApiResponse<>(false, "Username is required");
            }
            if (profile.getEmail() == null || profile.getEmail().trim().isEmpty()) {
                return new ApiResponse<>(false, "Email is required");
            }

            // Check if username already exists
            if (userProfileService.userExistsByUsername(profile.getUsername())) {
                return new ApiResponse<>(false, "Username already exists");
            }

            UserProfile createdProfile = userProfileService.createUserProfile(profile);
            return new ApiResponse<>(true, "User profile created successfully", createdProfile);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error creating user profile: " + e.getMessage());
        }
    }

    /**
     * Get all user profiles
     * GET /api/profiles
     */
    @GetMapping
    public ApiResponse<List<UserProfile>> getAllUserProfiles() {
        try {
            List<UserProfile> profiles = userProfileService.getAllUserProfiles();
            return new ApiResponse<>(true, "User profiles retrieved successfully", profiles);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error retrieving user profiles: " + e.getMessage());
        }
    }

    /**
     * Get user profile by ID
     * GET /api/profiles/{userId}
     */
    @GetMapping("/{userId}")
    public ApiResponse<UserProfile> getUserProfileById(@PathVariable Long userId) {
        try {
            UserProfile profile = userProfileService.getUserProfileById(userId);
            if (profile == null) {
                return new ApiResponse<>(false, "User profile not found");
            }
            return new ApiResponse<>(true, "User profile retrieved successfully", profile);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error retrieving user profile: " + e.getMessage());
        }
    }

    /**
     * Update user profile
     * PUT /api/profiles/{userId}
     */
    @PutMapping("/{userId}")
    public ApiResponse<UserProfile> updateUserProfile(@PathVariable Long userId, @RequestBody UserProfile profile) {
        try {
            UserProfile existingProfile = userProfileService.getUserProfileById(userId);
            if (existingProfile == null) {
                return new ApiResponse<>(false, "User profile not found");
            }

            // Validate input
            if (profile.getUsername() != null && profile.getUsername().trim().isEmpty()) {
                return new ApiResponse<>(false, "Username cannot be empty");
            }

            UserProfile updatedProfile = userProfileService.updateUserProfile(userId, profile);
            return new ApiResponse<>(true, "User profile updated successfully", updatedProfile);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error updating user profile: " + e.getMessage());
        }
    }

    /**
     * Delete user profile
     * DELETE /api/profiles/{userId}
     */
    @DeleteMapping("/{userId}")
    public ApiResponse<Void> deleteUserProfile(@PathVariable Long userId) {
        try {
            UserProfile profile = userProfileService.getUserProfileById(userId);
            if (profile == null) {
                return new ApiResponse<>(false, "User profile not found");
            }

            boolean deleted = userProfileService.deleteUserProfile(userId);
            if (deleted) {
                return new ApiResponse<>(true, "User profile deleted successfully");
            } else {
                return new ApiResponse<>(false, "Failed to delete user profile");
            }
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error deleting user profile: " + e.getMessage());
        }
    }

    /**
     * Search user profiles by username
     * GET /api/profiles/search/username?name={username}
     */
    @GetMapping("/search/username")
    public ApiResponse<List<UserProfile>> searchByUsername(@RequestParam String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return new ApiResponse<>(false, "Search parameter cannot be empty");
            }

            List<UserProfile> profiles = userProfileService.searchByUsername(name);
            if (profiles.isEmpty()) {
                return new ApiResponse<>(true, "No profiles found for the given username", profiles);
            }
            return new ApiResponse<>(true, "User profiles retrieved successfully", profiles);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error searching user profiles: " + e.getMessage());
        }
    }

    /**
     * Search user profiles by country
     * GET /api/profiles/search/country?country={country}
     */
    @GetMapping("/search/country")
    public ApiResponse<List<UserProfile>> searchByCountry(@RequestParam String country) {
        try {
            if (country == null || country.trim().isEmpty()) {
                return new ApiResponse<>(false, "Country parameter cannot be empty");
            }

            List<UserProfile> profiles = userProfileService.searchByCountry(country);
            if (profiles.isEmpty()) {
                return new ApiResponse<>(true, "No profiles found for the given country", profiles);
            }
            return new ApiResponse<>(true, "User profiles retrieved successfully", profiles);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error searching user profiles: " + e.getMessage());
        }
    }

    /**
     * Search user profiles by age range
     * GET /api/profiles/search/age?minAge={minAge}&maxAge={maxAge}
     */
    @GetMapping("/search/age")
    public ApiResponse<List<UserProfile>> searchByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        try {
            if (minAge < 0 || maxAge < 0 || minAge > maxAge) {
                return new ApiResponse<>(false, "Invalid age range provided");
            }

            List<UserProfile> profiles = userProfileService.searchByAgeRange(minAge, maxAge);
            if (profiles.isEmpty()) {
                return new ApiResponse<>(true, "No profiles found in the given age range", profiles);
            }
            return new ApiResponse<>(true, "User profiles retrieved successfully", profiles);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error searching user profiles: " + e.getMessage());
        }
    }

    /**
     * Activate user profile
     * PUT /api/profiles/{userId}/activate
     */
    @PutMapping("/{userId}/activate")
    public ApiResponse<UserProfile> activateUserProfile(@PathVariable Long userId) {
        try {
            UserProfile profile = userProfileService.getUserProfileById(userId);
            if (profile == null) {
                return new ApiResponse<>(false, "User profile not found");
            }

            UserProfile activatedProfile = userProfileService.activateUserProfile(userId);
            return new ApiResponse<>(true, "User profile activated successfully", activatedProfile);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error activating user profile: " + e.getMessage());
        }
    }

    /**
     * Deactivate user profile
     * PUT /api/profiles/{userId}/deactivate
     */
    @PutMapping("/{userId}/deactivate")
    public ApiResponse<UserProfile> deactivateUserProfile(@PathVariable Long userId) {
        try {
            UserProfile profile = userProfileService.getUserProfileById(userId);
            if (profile == null) {
                return new ApiResponse<>(false, "User profile not found");
            }

            UserProfile deactivatedProfile = userProfileService.deactivateUserProfile(userId);
            return new ApiResponse<>(true, "User profile deactivated successfully", deactivatedProfile);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error deactivating user profile: " + e.getMessage());
        }
    }
}
