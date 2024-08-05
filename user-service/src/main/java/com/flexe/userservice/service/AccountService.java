package com.flexe.userservice.service;

import com.flexe.userservice.entity.posts.UserPosts;
import com.flexe.userservice.entity.user.User;
import com.flexe.userservice.entity.user.UserAccount;
import com.flexe.userservice.entity.user.UserProfile;
import com.flexe.userservice.repository.UserProfileRepository;
import com.flexe.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PostService postService;

    public UserAccount findUserAccount(String userId){
        User user = userRepository.findById(userId).orElse(null);

        if(user == null) return null;

        //Consider Multi Threading Collection Calls
        UserProfile profile = userProfileRepository.findByUserId(userId);
        UserPosts posts = postService.findUserPosts(userId);
        return new UserAccount(user, profile, posts);
    }

    public UserAccount findUserAccountByUsername(String username){
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return null;

        //Consider Multi Threading Collection Calls
        UserProfile profile = userProfileRepository.findByUserId(user.getId());
        UserPosts posts = postService.findUserPosts(user.getId());
        return new UserAccount(user, profile, posts);
    }



}
