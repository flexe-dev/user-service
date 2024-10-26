package com.flexe.userservice.service;

import com.flexe.userservice.entity.user.UserDisplay;
import com.flexe.userservice.entity.user.UserInteraction;
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
    KafkaTemplate<String, UserInteraction> userInteractionKafkaTemplate;

    public void SaveUserNode(UserDisplay user){
        SendUserNodeMessage(user, UserNodeModificationEnum.SAVE);
    }

    public void DeleteUserNode(UserDisplay user){
        SendUserNodeMessage(user, UserNodeModificationEnum.DELETE);
    }

    public void FollowUser(String userId, String targetId){
        UserInteraction interaction = new UserInteraction(userId, targetId);
        SendUserInteractionMessage(interaction, UserInteractionEnum.FOLLOW);
    }
    public void UnfollowUser(String userId, String targetId){
        UserInteraction interaction = new UserInteraction(userId, targetId);
        SendUserInteractionMessage(interaction, UserInteractionEnum.UNFOLLOW);
    }

    public void SendUserNodeMessage(UserDisplay user, UserNodeModificationEnum action){
        userKafkaTemplate.send("user-node-action", action.toString(), new UserNode(user));
    }

    public void SendUserInteractionMessage(UserInteraction interaction, UserInteractionEnum action){
        userInteractionKafkaTemplate.send("user-interaction", action.toString(), interaction);
    }


}
