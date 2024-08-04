package com.flexe.userservice.entity.user;

public class UserDisplay {
    private User user;
    private UserProfile profile;

    public UserDisplay(){

    }

    public UserDisplay(User user, UserProfile profile){
        this.user = user;
        this.profile = profile;
    }

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

}


