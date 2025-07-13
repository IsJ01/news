package com.app.news.news.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.news.news.database.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

}
