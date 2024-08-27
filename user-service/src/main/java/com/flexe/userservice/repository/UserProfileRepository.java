package com.flexe.userservice.repository;

import com.flexe.userservice.entity.user.UserProfile;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
    @Query("{ 'userId' : ObjectId(?0) }")
    UserProfile findByUserId(String userId);

    @DeleteQuery("{ 'userId' : ObjectId(?0) }")
    void deleteProfileByUserId(String userId);
}