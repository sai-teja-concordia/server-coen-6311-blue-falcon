package com.bluefalcon.project.request;

import com.bluefalcon.project.enums.SavedNewsEnum;
import lombok.Data;

@Data
public class SavedNewsRequest {

    private String newsId;
    private String userId;
    private SavedNewsEnum savedNewsEnum;
}
