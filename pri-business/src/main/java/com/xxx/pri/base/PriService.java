package com.xxx.pri.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface PriService<T extends PriBaseEntity<ID>, ID extends Serializable> {

    <S extends T> S save(S entity);

    <S extends T> void update(S entity);

    <S extends T> Iterable<S> save(Iterable<S> entities);

    boolean exists(ID id);

    Iterable<T> findAll();

    Iterable<T> findAll(Iterable<ID> ids);

    long count();

    void delete(ID id);

    void delete(T entity);

    void deleteAll(ID[] ids);

    void deleteAll(Iterable<? extends T> entities);

    T findById(ID id);

    <S extends T> S findOne(S example);

    <S extends T> Iterable<S> findAll(S example);

    <S extends T> Iterable<S> findAll(S example, Sort sort);

    <S extends T> Page<S> findAll(S example, Pageable pageable);

    <S extends T> long count(S example);

    <S extends T> boolean exists(S example);

    Page<T> queryAll(Object criteria, Pageable pageable);

    List<T> queryAll(Object criteria);
}
