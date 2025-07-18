package com.app.news.news.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class NewsPutDto {

    @NotEmpty
    String title;

    @NotEmpty
    String description;
    
}

