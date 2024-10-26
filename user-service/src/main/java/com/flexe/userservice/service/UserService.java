package com.flexe.userservice.service;
import com.flexe.userservice.entity.user.*;
import com.flexe.userservice.repository.UserProfileRepository;
import com.flexe.userservice.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserInteractionService userInteractionService;

    //User Queries

    public User findUserById(String userId){
        return userRepository.findById(userId).orElse(null);
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserProfile findProfile(String userId){
        return userProfileRepository.findByUserId(userId);
    }

    public UserProfile findUserProfileOrThrow(String userId){
        UserProfile profile = findProfile(userId);
        if(profile == null) throw new IllegalArgumentException("User Profile Not Found");
        return profile;
    }

    public List<UserDetails> FindManyUsers(List<String> userIdList){
        if(userIdList == null || userIdList.isEmpty()) return List.of();

        HashMap<String, UserDisplay> userMap = new HashMap<>();
        userIdList.forEach(id -> {userMap.put(id, new UserDisplay());
        });

        // Retrieve All User objects
        List<User> users = userRepository.findAllInIdList(userIdList.stream().toList());

        // Allocate each user object to its respective Class
        users.forEach(user -> {
            userMap.get(user.getId()).setUser(user);
        });

        // Fill the remaining UserDisplay objects with the respective UserProfile objects
        List<UserProfile> profiles = userProfileRepository.findAllInUserIdList(userIdList.stream().toList());
        profiles.forEach(profile -> {
            userMap.get(profile.getUserId()).setProfile(profile);
        });

        // Filter out any incomplete UserDisplay objects
        return List.copyOf(userMap.values()).stream().filter(user -> user.getProfile() != null && user.getUser() != null ).map(UserDisplay::toUserDetails).toList();
    }

    public UserDisplay findUserDisplay(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) return null;
        UserProfile profile = userProfileRepository.findByUserId(userId);
        return new UserDisplay(user, profile);
    }

    public UserDisplay findUserDisplayByUsername(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) return null;

        return new UserDisplay(user, findUserProfileOrThrow(user.getId()));
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }


    //User Creation
    public UserDisplay onboardUser(UserDisplay user){
        if(user.getProfile().getId().isEmpty() || user.getProfile().getUserId() == null){
            user.getProfile().setId(new ObjectId().toHexString());
        }
        return updateUser(user);
    }

    //User Modification
    public UserDisplay updateUser(UserDisplay user){
        userRepository.save(user.getUser());
        userProfileRepository.save(user.getProfile());
        userInteractionService.SaveUserNode(user);
        return user;
    }

    //User Deletion
    public void deleteUserAccount(String userId){
        UserDisplay account = findUserDisplay(userId);
        if(account == null){
            throw new IllegalArgumentException("User Not Found");
        }

        userRepository.delete(account.getUser());
        userProfileRepository.delete(account.getProfile());
        //Send Request To Post Service

        //Send Message To Interaction Service
        userInteractionService.DeleteUserNode(account);
    }

    public void FollowUser(String userId, String targetId){
        UserProfile userProfile = findUserProfileOrThrow(userId);
        UserProfile targetProfile = findUserProfileOrThrow(targetId);

        userProfile.AddFollowing();
        targetProfile.AddFollower();
        userInteractionService.FollowUser(userProfile.getUserId(), targetProfile.getUserId());
        SaveUserInteractionChanges(userProfile, targetProfile);
    }

    public void UnfollowUser(String userId, String targetId){
        UserProfile userProfile = findUserProfileOrThrow(userId);
        UserProfile targetProfile = findUserProfileOrThrow(targetId);

        userProfile.RemoveFollowing();
        targetProfile.RemoveFollower();
        userInteractionService.UnfollowUser(userProfile.getUserId(), targetProfile.getUserId());
        SaveUserInteractionChanges(userProfile, targetProfile);
    }

    public void SaveUserInteractionChanges(UserProfile user, UserProfile target){
        userProfileRepository.save(user);
        userProfileRepository.save(target);
    }

}
