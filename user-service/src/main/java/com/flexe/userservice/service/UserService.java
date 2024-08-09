package com.flexe.userservice.service;
import com.flexe.userservice.entity.user.User;
import com.flexe.userservice.entity.user.UserAccount;
import com.flexe.userservice.entity.user.UserDisplay;
import com.flexe.userservice.entity.user.UserProfile;
import com.flexe.userservice.repository.UserProfileRepository;
import com.flexe.userservice.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public UserDisplay findUserDisplay(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        UserProfile profile = userProfileRepository.findByUserId(userId);
        if(user == null || profile == null) return null;
        return new UserDisplay(user, profile);
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
    public UserAccount deleteUserAccount(UserAccount account){
        userRepository.delete(account.getUser());
        userProfileRepository.delete(account.getProfile());
        //Send Request To Post Service

        //Send Message To Interaction Service
        userInteractionService.DeleteUserNode(new UserDisplay(account));
        return account;
    }
}
