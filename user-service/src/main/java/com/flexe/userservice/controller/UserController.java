package com.flexe.userservice.controller;

import com.flexe.userservice.entity.user.User;
import com.flexe.userservice.entity.user.UserAccount;
import com.flexe.userservice.entity.user.UserDisplay;
import com.flexe.userservice.entity.user.UserProfile;
import com.flexe.userservice.service.AccountService;
import com.flexe.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.sentry.Sentry;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService userAccountService;

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

    @GetMapping("/find/id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable String id){

            User user = userService.findUserById(id);
            if(user == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            return ResponseEntity.ok(user);
    }

    @GetMapping("/find/username/{username}")
    @ResponseBody
    public ResponseEntity<User> findUserByUsername(@PathVariable String username){
        User user = userService.findUserByUsername(username);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email){
            User user = userService.findUserByEmail(email);
            if(user == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            return ResponseEntity.ok(user);
    }

    @GetMapping("/profile/find/{userId}")
    public UserProfile findProfileFromUser(@PathVariable String userId){
        return userService.findProfile(userId);
    }

    @GetMapping("/account/find/{userId}")
    public ResponseEntity<UserAccount> findUserAccount(@PathVariable String userId){
        UserAccount account = userAccountService.findUserAccount(userId);
        if(account == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping("/display/find/{userId}")
    public ResponseEntity<UserDisplay> findUserDisplayByUserId(@PathVariable String userId){
        UserDisplay display = userService.findUserDisplay(userId);
        if(display == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.ok(display);
    }

    @GetMapping("/account/find/username/{username}")
    public ResponseEntity<UserAccount> findUserAccountByUsername(@PathVariable String username){
        UserAccount account = userAccountService.findUserAccountByUsername(username);
        if(account == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.ok(account);
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody UserAccount account){
        try{
            userService.deleteUserAccount(account);
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
}

