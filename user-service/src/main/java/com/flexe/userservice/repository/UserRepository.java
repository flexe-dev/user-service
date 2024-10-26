package com.flexe.userservice.repository;

import com.flexe.userservice.entity.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ 'username' : ?0 }")
    Optional<User> findByUsername(String username);

    @Query("{ 'email' : ?0 }")
    Optional<User> findByEmail(String email);

    @Query("{ 'id' : { $in : ?0 } }")
    List<User> findAllInIdList(List<String> userIdList);

}