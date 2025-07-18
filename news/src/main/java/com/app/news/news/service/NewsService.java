package com.app.news.news.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.news.news.database.repository.NewsRepository;
import com.app.news.news.dto.NewsCreateDto;
import com.app.news.news.dto.NewsPutDto;
import com.app.news.news.dto.NewsReadDto;
import com.app.news.news.mapper.NewsCreateMapper;
import com.app.news.news.mapper.NewsPutMapper;
import com.app.news.news.mapper.NewsReadMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsReadMapper newsReadMapper;
    private final NewsCreateMapper newsCreateMapper;
    private final NewsPutMapper newsPutMapper;

    public List<NewsReadDto> findAll() {
        return newsRepository.findAll().stream()
            .map(newsReadMapper::map)
            .toList();
    }

    public NewsReadDto findById(Long id) {
        return newsRepository.findById(id)
            .map(newsReadMapper::map)
            .orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }

    @Transactional
    public NewsReadDto create(NewsCreateDto newsCreateDto) {
        return Optional.of(newsCreateDto)
            .map(newsCreateMapper::map)
            .map(newsRepository::save)
            .map(newsReadMapper::map)
            .get();
    }

    @Transactional
    public NewsReadDto put(NewsPutDto newsPutDto, Long id) {
        return newsRepository.findById(id)
            .map(news -> newsPutMapper.mapCopy(newsPutDto, news))
            .map(newsRepository::saveAndFlush)
            .map(updateNews -> newsReadMapper.map(updateNews))
            .get();
    }

    @Transactional
    public boolean delete(Long id) {
        return newsRepository.findById(id)
            .map(entity -> {
                newsRepository.delete(entity);
                return true;
            })
            .orElse(null);
    }

}
