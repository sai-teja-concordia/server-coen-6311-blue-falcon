package com.bluefalcon.project.request;

import com.bluefalcon.project.enums.FriendRequestStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendRequest {
    
    private String fromUserId;
    
    private String toUserId;
    
    private FriendRequestStatus status;
}
