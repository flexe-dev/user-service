package com.flexe.userservice.entity.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNode extends UserDetails {

    public UserNode(){
    }

    public UserNode(UserDisplay user){
        super(user);
    }

}