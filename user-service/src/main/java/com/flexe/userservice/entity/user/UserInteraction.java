package com.flexe.userservice.entity.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class UserInteraction {
    @NonNull
    private String userId;
    @NonNull
    private String targetId;

    public UserInteraction() {
    }

    public UserInteraction(@NonNull String userId, @NonNull String targetId) {
        this.userId = userId;
        this.targetId = targetId;
    }

}
