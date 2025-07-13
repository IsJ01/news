package com.app.news.news.mapper;

import org.springframework.stereotype.Component;

import com.app.news.news.client.AuthClient;
import com.app.news.news.database.entity.News;
import com.app.news.news.dto.NewsReadDto;
import com.app.news.news.dto.UserReadDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NewsReadMapper extends Mapper<News, NewsReadDto> {

    private final AuthClient authClient;

    @Override
    public NewsReadDto map(News object) {
        return NewsReadDto.builder()
            .id(object.getId())
            .user(authClient.fetchUserById(object.getUserId()))
            .title(object.getTitle())
            .description(object.getDescription())
            .publishedDate(object.getPublishedDate())
            .build();
    }

}
