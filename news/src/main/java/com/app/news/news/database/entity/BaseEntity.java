package com.app.news.news.database.entity;

public interface BaseEntity<T> {

    T getId();

    void setId(T id);
    
}
