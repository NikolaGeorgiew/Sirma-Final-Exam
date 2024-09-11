package com.example.finalexam.service;

import java.util.List;

public interface CrudService<T> {
    List<T> getAll();

    T getEntityById(Long id);

    T createEntity(T obj);

    T updateEntity(Long id, T updatedObj);

    void deleteEntity(Long id);
}
