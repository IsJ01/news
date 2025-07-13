package com.app.news.news.mapper;

public abstract class Mapper<F, T> {

    public T map(F object) {
        return null;
    }

    public T mapCopy(F fromObject, T toObject) {
        return null;
    }
    
}
