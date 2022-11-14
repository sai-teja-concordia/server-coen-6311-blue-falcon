package com.bluefalcon.project.response;



import lombok.Builder;

import java.util.List;

@Builder
public class CategoryNewsResponse {

    private List<NewsResponse> categoryNews;
}
