package com.app.news.news.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewsReadDto {
    Long id;
    UserReadDto user;
    String title;
    String description;
    LocalDate publishedDate;
}
