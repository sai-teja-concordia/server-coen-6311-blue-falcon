package com.bluefalcon.project.dao;

import com.bluefalcon.project.model.News;
import com.bluefalcon.project.response.NewsResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsDao extends MongoRepository<News, String> {

    List<News> findByLocationAndDayId(String location, Integer dayId);
    List<News> findByCategoryAndDayId(String category, Integer dayId);
}
