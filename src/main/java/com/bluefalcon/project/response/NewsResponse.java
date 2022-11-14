package com.bluefalcon.project.response;

import com.bluefalcon.project.model.News;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewsResponse{

    private List<News> newsList;

    private String category;
}
