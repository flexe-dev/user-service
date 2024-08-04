package com.flexe.userservice.service;
import com.flexe.userservice.entity.user.User;
import com.flexe.userservice.entity.user.UserAccount;
import com.flexe.userservice.entity.user.UserDisplay;
import com.flexe.userservice.entity.user.UserProfile;
import com.flexe.userservice.repository.UserProfileRepository;
import com.flexe.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;

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

    public UserProfile initialiseUser(String userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) return null;
        UserProfile profile = new UserProfile(user.get().getId());
        //Send Kafka Message to generate User Node
        return userProfileRepository.save(profile);

    }

    //User Modification

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public UserProfile updateProfile(UserProfile userProfile){
        return userProfileRepository.save(userProfile);
    }

    public UserAccount updateUserAccount(UserAccount account){
        userRepository.save(account.getUser());
        userProfileRepository.save(account.getProfile());
        return account;
    }
}
