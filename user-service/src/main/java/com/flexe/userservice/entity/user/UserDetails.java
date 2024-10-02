package com.flexe.userservice.entity.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {

    private String userId;
    private String name;
    private String image;
    private String username;
    private String job;

    public UserDetails(){

    }

    public UserDetails(UserNode user){
        this.userId = user.getUserId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.image = user.getImage();
        this.job = user.getJob();
    }

    public UserDetails(UserDisplay user){
        this.userId = user.getUser().getId();
        this.name = user.getUser().getName();
        this.username = user.getUser().getUsername();
        this.image = user.getUser().getImage();
        this.job = user.getProfile().getJob();
    }

}
