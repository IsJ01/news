package com.app.news.news.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewsCreateDto {
    Long userId;
    String title;
    String description;
}
