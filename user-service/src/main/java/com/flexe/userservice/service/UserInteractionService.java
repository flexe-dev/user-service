package com.flexe.userservice.service;

import com.flexe.userservice.entity.posts.UserPosts;
import com.flexe.userservice.entity.user.User;
import com.flexe.userservice.entity.user.UserDisplay;
import com.flexe.userservice.entity.user.UserNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.flexe.userservice.enums.UserInteractionEnums.*;

@Service
public class UserInteractionService {

    @Autowired
    KafkaTemplate<String, UserNode> userKafkaTemplate;

    @Autowired
    KafkaTemplate<String, UserPosts> postKafkaTemplate;

    public void SaveUserNode(UserDisplay user){
        SendUserNodeMessage(user, UserNodeModificationEnum.SAVE);
    }

    public void DeleteUserNode(UserDisplay user){
        SendUserNodeMessage(user, UserNodeModificationEnum.DELETE);
    }

    public void DeleteUserPosts(UserPosts posts){
        postKafkaTemplate.send("user-post-delete", posts);
    }

    public void SendUserNodeMessage(UserDisplay user, UserNodeModificationEnum action){
        userKafkaTemplate.send("user-node-action", action.toString(), new UserNode(user));
    }


}
