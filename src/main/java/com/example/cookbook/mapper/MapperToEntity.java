package com.example.cookbook.mapper;

public interface MapperToEntity<T, S> {
    T getEntity(S dto);
}
