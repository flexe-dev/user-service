package com.flexe.userservice.entity.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Setter
@Document(collection = "UserProfile")
public class UserProfile{
    @Id
    private String id;
    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;
    private String job;
    private Integer followers;
    private Integer following;
    private String company;
    private String location;
    private UserExternalLinks external;
    private String bio;
    private String pronouns;

    public UserProfile() {
    }

    public UserProfile(String userId){
        this.userId = userId;
        this.followers = 0;
        this.following = 0;
    }

    public UserProfile(String id, String userId, String job, Integer followers, Integer following, String company, String location, UserExternalLinks external, String bio, String pronouns, byte[] readMe) {
        this.id = id;
        this.userId = userId;
        this.job = job;
        this.followers = followers;
        this.following = following;
        this.company = company;
        this.location = location;
        this.external = external;
        this.bio = bio;
        this.pronouns = pronouns;
    }

    public void AddFollower(){
        this.followers++;
    }

    public void RemoveFollower(){
        this.followers--;
    }

    public void AddFollowing(){
        this.following++;
    }

    public void RemoveFollowing(){
        this.following--;
    }

}