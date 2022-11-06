package com.bluefalcon.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("news")
public class News {

    @Id
    private String id;
    private String title;
    private String author;
    private String url;
    private String urlToImage;
    private String category;
    private String description;
    private String content;
    private Long publishedAtEpoch;
    private String sourceName;
    private String location;
    private Integer dayId;

    public List<News> getNews(String location){
        return  null;
    }

    public List<News> saveNews(List<News> posts){
        return  null;
    };

}
