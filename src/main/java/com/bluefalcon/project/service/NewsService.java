package com.bluefalcon.project.service;

import com.bluefalcon.project.dao.NewsDao;
import com.bluefalcon.project.model.News;
import com.bluefalcon.project.response.CategoryNewsResponse;
import com.bluefalcon.project.response.NewsResponse;
import com.bluefalcon.project.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    NewsDao newsDao;

    @Autowired
    CommonUtils commonUtils;

    public NewsResponse getNews(String country) {
        List<News> listOfNews = newsDao.findByLocationAndDayId(country , commonUtils.getCurrentDayId());
        return NewsResponse.builder()
                .newsList(listOfNews)
                .build();
    }

    public CategoryNewsResponse getNews(List<String> categories) {
        return null;
    }
}
