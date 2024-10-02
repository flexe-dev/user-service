package com.flexe.userservice.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "User")
public class User{
    @Id
    private String id;
    private String email;
    private Date emailVerified;
    private String username;
    private String name;
    private String image;
    private Boolean onboarded;

    public User() {
    }

}