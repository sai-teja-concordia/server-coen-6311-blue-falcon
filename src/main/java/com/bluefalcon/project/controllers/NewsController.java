package com.bluefalcon.project.controllers;

import com.bluefalcon.project.response.CategoryNewsResponse;
import com.bluefalcon.project.response.NewsResponse;
import com.bluefalcon.project.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bluefalcon.project.model.News;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class NewsController {

    @Autowired
    NewsService newsService;

    @GetMapping(value = "/news/trending", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsResponse> getTrendingNews(@RequestParam("country") String country) {
        return ResponseEntity.ok(newsService.getNews(country));
    }

    @GetMapping(value = "/news/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryNewsResponse> getCategoryNews(@RequestParam("categories") List<String> categories) {
        return ResponseEntity.ok(newsService.getNews(categories));
    }

    @GetMapping(value="/news/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<News>> getNews(){
        return ResponseEntity.ok(newsService.getAllNews());
    }
}
