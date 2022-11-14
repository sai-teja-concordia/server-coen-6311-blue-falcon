package com.bluefalcon.project.response;



import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryNewsResponse {

    private List<NewsResponse> categoryNews;
}
