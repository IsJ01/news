package com.app.news.auth.database.entity;

public interface BaseEntity<T> {

    T getId();
    
    void setId(T id);

}
