package com.bluefalcon.project.service;

import com.bluefalcon.project.dao.NewsDao;
import com.bluefalcon.project.model.News;
import com.bluefalcon.project.response.CategoryNewsResponse;
import com.bluefalcon.project.response.NewsResponse;
import com.bluefalcon.project.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    NewsDao newsDao;

    @Autowired
    CommonUtils commonUtils;

    public List<News> getAllNews(){
        return newsDao.findAll();
    }

    public NewsResponse getNews(String country) {
        Integer currentDayId = commonUtils.getCurrentDayId();
        List<News> listOfNews = newsDao.findByLocationAndDayId(country , commonUtils.getCurrentDayId());
        return NewsResponse.builder()
                .newsList(listOfNews)
                .build();
    }

    public CategoryNewsResponse getNews(List<String> categories) {
        Integer currentDayId = commonUtils.getCurrentDayId();
        List<NewsResponse> listOfNewsResponse= new ArrayList<>();

        System.out.println("size----------->   "+categories.size());
        for (int i=0;i< categories.size();i++){

               List<News> listOfNews = newsDao.findByCategoryAndDayId(categories.get(i), currentDayId);
               NewsResponse newsResponse  = NewsResponse.builder().newsList(listOfNews).category(categories.get(i)).build();
               listOfNewsResponse.add(newsResponse);
        }


        return CategoryNewsResponse.builder()
                .categoryNews(listOfNewsResponse)
                .build();

    }
}
