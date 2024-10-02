package com.flexe.userservice.entity.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDisplay {
    private User user;
    private UserProfile profile;

    public UserDisplay(){

    }

    public UserDisplay(User user, UserProfile profile){
        this.user = user;
        this.profile = profile;
    }

}


