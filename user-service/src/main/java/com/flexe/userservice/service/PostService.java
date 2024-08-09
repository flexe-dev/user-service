package com.flexe.userservice.service;

import com.flexe.userservice.entity.posts.UserPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PostService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${POST_SERVICE_URL}")
    private String POST_SERVICE_URL;

    public UserPosts findUserPosts(String userId){
        WebClient client = webClientBuilder.baseUrl(POST_SERVICE_URL).build();
        ResponseEntity<UserPosts> userPostResponse = client.get()
                .uri("/post/user/" + userId)
                .retrieve()
                .toEntity(UserPosts.class)
                .block();

        if(userPostResponse == null) return null;
        return userPostResponse.getBody();
    }

    public String deleteAllUserPosts(UserPosts posts){
        WebClient client = webClientBuilder.baseUrl(POST_SERVICE_URL).build();
        ResponseEntity<String> response =
                client.method(HttpMethod.DELETE)
                .uri("/post/delete")
                .bodyValue(posts)
                .retrieve()
                .toEntity(String.class)
                .block();

        if(response == null) return null;
        return response.getBody();
    }
}
