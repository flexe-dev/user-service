package com.flexe.userservice.controller;

import com.flexe.userservice.entity.response.ErrorResponse;
import com.flexe.userservice.entity.user.*;
import com.flexe.userservice.exceptions.UserNotFoundException;
import com.flexe.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.sentry.Sentry;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/onboard")
    public ResponseEntity<UserDisplay> createProfile(@RequestBody UserDisplay user){
        try{
        UserDisplay savedUser = userService.onboardUser(user);
        return ResponseEntity.ok(savedUser);
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/follow/{userId}/{targetId}")
    public ResponseEntity<String> followUser(@PathVariable String userId, @PathVariable String targetId){
        try{
            userService.FollowUser(userId, targetId);
            return ResponseEntity.ok("User followed");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/unfollow/{userId}/{targetId}")
    public ResponseEntity<String> unfollowUser(@PathVariable String userId, @PathVariable String targetId){
        try{
            userService.UnfollowUser(userId, targetId);
            return ResponseEntity.ok("User unfollowed");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/block/{userId}/{targetId}")
    public ResponseEntity<String> blockUser(@PathVariable String userId, @PathVariable String targetId){
        try{
//            userService.blockUser(userId, targetId);
            return ResponseEntity.ok("User blocked");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable String id){

            User user = userService.findUserById(id);
            if(user == null){
                throw new UserNotFoundException("User not found");
            }
            return ResponseEntity.ok(user);
    }

    @GetMapping("/find/username/{username}")
    @ResponseBody
    public ResponseEntity<User> findUserByUsername(@PathVariable String username){
        User user = userService.findUserByUsername(username);
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email){
            User user = userService.findUserByEmail(email);
            if(user == null){
                throw new UserNotFoundException("User not found");
            }
            return ResponseEntity.ok(user);
    }

    @GetMapping("/display/find/{userId}")
    public ResponseEntity<UserDisplay> findUserDisplayByUserId(@PathVariable String userId){
        UserDisplay display = userService.findUserDisplay(userId);
        if(display == null){
            throw new UserNotFoundException("User not found");
        }
        return ResponseEntity.ok(display);
    }

    @GetMapping("/display/find/username/{username}")
    public ResponseEntity<UserDisplay> findUserDisplayByUsername(@PathVariable String username){
        UserDisplay display = userService.findUserDisplayByUsername(username);
        if(display == null){
            throw new UserNotFoundException("User not found");
        }
        return ResponseEntity.ok(display);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<UserDisplay> updateUser(@RequestBody UserDisplay display){
        try{
            UserDisplay updatedUser = userService.updateUser(display);
            return ResponseEntity.ok(updatedUser);
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId){
        try{
            userService.deleteUserAccount(userId);
            return ResponseEntity.ok("User deleted");
        }
        catch (Exception e){
            Sentry.captureException(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/error")
    @ResponseBody
    public void throwError(){
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("User service is running");
    }
}


