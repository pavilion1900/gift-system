package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Service<D> {

    D save(D dto);

    List<D> findAll(Pageable pageable);

    D findById(Integer id);

    D update(Integer id, D dto);

    void delete(Integer id);
}
