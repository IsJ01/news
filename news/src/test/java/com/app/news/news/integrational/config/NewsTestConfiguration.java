package com.app.news.news.integrational.config;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.app.news.news.client.AuthClient;
import com.app.news.news.database.repository.NewsRepository;
import com.app.news.news.dto.UserReadDto;
import com.app.news.news.http.rest.NewsRestController;
import com.app.news.news.mapper.NewsCreateMapper;
import com.app.news.news.mapper.NewsPutMapper;
import com.app.news.news.mapper.NewsReadMapper;
import com.app.news.news.service.NewsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@TestConfiguration
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
@Slf4j
public class NewsTestConfiguration {

    @MockBean
    private AuthClient authClient;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsCreateMapper newsCreateMapper;

    @Autowired
    private NewsPutMapper newsPutMapper;

    @Bean
    public NewsRestController newsRestController() throws Exception  {
        var authClient = Mockito.mock(AuthClient.class);
        when(authClient.fetchUserById(2L)).thenReturn(
            new UserReadDto(2L, "test_user2", List.of())
        );
        
        when(authClient.fetchUserById(1L)).thenReturn(
            new UserReadDto(1L, "test_user", List.of())
        );

        NewsService newsService = new NewsService(newsRepository, newsReadMapper(), newsCreateMapper, newsPutMapper);

        return new NewsRestController(newsService, authClient);
    }

    @Bean
    public NewsReadMapper newsReadMapper() throws Exception  {
        var authClient = Mockito.mock(AuthClient.class);
        when(authClient.fetchUserById(2L)).thenReturn(
            new UserReadDto(2L, "test_user2", List.of())
        );
        
        when(authClient.fetchUserById(1L)).thenReturn(
            new UserReadDto(1L, "test_user", List.of())
        );
        return new NewsReadMapper(authClient);
    }
    

}
