package com.flexe.userservice.entity.user;

import com.flexe.userservice.entity.posts.UserPosts;
import com.flexe.userservice.entity.posts.media.MediaPost;
import com.flexe.userservice.entity.posts.text.TextPost;

public class UserAccount {
    public User user;
    public UserProfile profile;
    public MediaPost[] mediaPosts;
    public TextPost[] textPosts;

    public UserAccount(){

    }

    public UserAccount(User user, UserProfile profile, UserPosts posts){
        this.user = user;
        this.profile = profile;
        this.mediaPosts = posts.getMediaPosts();
        this.textPosts = posts.getTextPosts();
    }

    public UserAccount(UserDisplay user, UserPosts posts){
        this.user = user.getUser();
        this.profile = user.getProfile();
        this.mediaPosts = posts.getMediaPosts();
        this.textPosts = posts.getTextPosts();
    }

    public UserAccount(User user, UserProfile profile, MediaPost[] mediaPosts, TextPost[] textPosts){
        this.user = user;
        this.profile = profile;
        this.mediaPosts = mediaPosts;
        this.textPosts = textPosts;
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public MediaPost[] getMediaPosts() {
        return mediaPosts;
    }

    public void setMediaPosts(MediaPost[] mediaPosts) {
        this.mediaPosts = mediaPosts;
    }

    public TextPost[] getTextPosts() { return textPosts;}

    public void setTextPosts(TextPost[] textPosts) {this.textPosts = textPosts;}
}



