package com.example.cookbook.mapper;

public interface HistoryMapperToEntity<T, S> {
    T getHistoryEntity(S entity);
}
