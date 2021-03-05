package com.example.cookbook.mapper;

public interface MapperToDto<T, S> {
    S getDto(T entity);
}
