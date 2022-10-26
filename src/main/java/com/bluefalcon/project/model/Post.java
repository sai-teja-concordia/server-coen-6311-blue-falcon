package com.bluefalcon.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("post")
public class Post {

    @Id
    private String id;
    private String title;
    private String author;
    private String url;
    private String urlToImage;
    private String description;
    private String content;
    private Long publishedAtEpoch;
    private String sourceName;
    private String location;

    public List<Post> getPosts(String location){
        return  null;
    }

    public List<Post> savePosts(List<Post> posts){
        return  null;
    };


}
