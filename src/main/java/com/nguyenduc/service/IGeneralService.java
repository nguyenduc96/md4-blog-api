package com.nguyenduc.service;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();

    Iterable<T> findAll(int page, int size);

    Optional<T> findById(Long id);

    T save(T t);

    void delete(Long id);
}
