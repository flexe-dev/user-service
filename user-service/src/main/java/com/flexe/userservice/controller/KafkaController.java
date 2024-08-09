package com.flexe.userservice.controller;

import com.flexe.userservice.entity.user.User;
import com.flexe.userservice.entity.user.UserDisplay;
import com.flexe.userservice.producer.MessageProducer;
import com.flexe.userservice.service.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/kafka")
@CrossOrigin(origins = "http://localhost:3000")
public class KafkaController {

    @Autowired
    private UserInteractionService userInteractionService;

    @PostMapping("/user/node")
    public String createUserNode(@RequestBody UserDisplay user){
        userInteractionService.SaveUserNode(user);
        return "User Node Created";
    }

    @DeleteMapping("/user/node")
    public String deleteUserNode(@RequestBody UserDisplay user){
        userInteractionService.DeleteUserNode(user);
        return "User Node Created";
    }

}