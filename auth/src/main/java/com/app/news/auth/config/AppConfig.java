package com.app.news.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AuthConfiguration.class)
public class AppConfig {

}

