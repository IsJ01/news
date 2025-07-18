package com.app.news.news.mapper;

import org.springframework.stereotype.Component;

import com.app.news.news.database.entity.News;
import com.app.news.news.dto.NewsPutDto;

@Component
public class NewsPutMapper extends Mapper<NewsPutDto, News> {

    @Override
    public News mapCopy(NewsPutDto fromObject, News toObject) {
        toObject.setTitle(fromObject.getTitle());
        toObject.setDescription(fromObject.getDescription());
        return toObject;
    }

}
