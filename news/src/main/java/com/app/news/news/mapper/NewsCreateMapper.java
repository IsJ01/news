package com.app.news.news.mapper;

import org.springframework.stereotype.Component;

import com.app.news.news.database.entity.News;
import com.app.news.news.dto.NewsCreateDto;

@Component
public class NewsCreateMapper extends Mapper<NewsCreateDto, News> {

    @Override
    public News map(NewsCreateDto object) {
        return News.builder()
            .userId(object.getUserId())
            .title(object.getTitle())
            .description(object.getDescription())
            .build();
    }

}
