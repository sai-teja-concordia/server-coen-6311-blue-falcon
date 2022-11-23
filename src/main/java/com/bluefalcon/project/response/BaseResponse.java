package com.bluefalcon.project.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {

    private String message;
}
