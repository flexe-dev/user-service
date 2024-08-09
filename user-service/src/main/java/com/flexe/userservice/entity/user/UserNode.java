package com.flexe.userservice.entity.user;

public class UserNode {
    private String userId;
    private String name;
    private String username;
    private String job;

    public UserNode(){
    }

    //Default Constructor
    public UserNode(UserDisplay user){
        this.userId = user.getUser().getId();
        this.name = user.getUser().getName();
        this.username = user.getUser().getUsername();
        this.job = user.getProfile().getJob();
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

}