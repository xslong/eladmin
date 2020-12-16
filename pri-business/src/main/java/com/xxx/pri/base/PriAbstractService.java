package com.xxx.pri.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.google.common.collect.Iterables;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLTemplates;
import me.zhengjie.utils.QueryHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


/**
 * @author : xslong
 */
public abstract class PriAbstractService<T extends PriBaseEntity<ID>, ID extends Serializable> implements PriService<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired(required = false)
    protected SQLTemplates sqlTemplates;

    protected abstract PriBaseRepository<T, ID> getRepository();


    String getUsername() {
        return null;
    }

    private <S extends T> void updateOp(S entity) {
        updateOp(entity, getUsername());
    }

    private <S extends T> void updateOp(S entity, String op) {
        if (entity == null) {
            return;
        }
        long t = System.currentTimeMillis();
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(new Timestamp(t));
        }
        if (entity.getCreateBy() == null) {
            entity.setCreateBy(op);
        }
        entity.setUpdateTime(new Timestamp(t));
        entity.setUpdatedBy(op);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <S extends T> S save(S entity) {
        updateOp(entity);
        return getRepository().save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        String op = getUsername();
        for (S s : entities) {
            updateOp(s, op);
        }
        return getRepository().saveAll(entities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <S extends T> void update(S entity) {
        ID id = entity.getId();
        T db = findById(id);
        BeanUtil.copyProperties(entity, db, CopyOptions.create().setIgnoreNullValue(true));
        save(db);
    }

    @Override
    public boolean exists(ID id) {
        return getRepository().existsById(id);
    }

    @Override
    public Iterable<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return getRepository().findAllById(ids);
    }

    @Override
    public long count() {
        return getRepository().count();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Iterable<? extends T> entities) {
        if (entities != null && Iterables.size(entities) > 0) {
            getRepository().deleteAll(entities);
        }
    }

    @Override
    public void deleteAll(ID[] ids) {
        if (ids != null) {
            for (ID id : ids) {
                delete(id);
            }
        }
    }

    @Override
    public T findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    @Override
    public <S extends T> S findOne(S example) {
        Example<S> ex = Example.of(example);
        return getRepository().findOne(ex).orElse(null);
    }

    @Override
    public <S extends T> Iterable<S> findAll(S example) {
        Example<S> ex = Example.of(example);
        return getRepository().findAll(ex);
    }

    @Override
    public <S extends T> Iterable<S> findAll(S example, Sort sort) {
        Example<S> ex = Example.of(example);
        return getRepository().findAll(ex, sort);
    }

    @Override
    public <S extends T> Page<S> findAll(S example, Pageable pageable) {
        Example<S> ex = Example.of(example);
        return getRepository().findAll(ex, pageable);
    }

    @Override
    public <S extends T> long count(S example) {
        Example<S> ex = Example.of(example);
        return getRepository().count(ex);
    }

    @Override
    public <S extends T> boolean exists(S example) {
        Example<S> ex = Example.of(example);
        return getRepository().exists(ex);
    }

    public List<T> findBySql(String sql, Map<String, Object> parameters, Class<T> resultClass) {
        final Query nativeQuery = entityManager.createNativeQuery(sql, resultClass);
        parameters.forEach(nativeQuery::setParameter);
        return nativeQuery.getResultList();
    }

    public List<T> findBySql(String sql, List<Object> parameters, Class<T> resultClass) {
        final Query nativeQuery = entityManager.createNativeQuery(sql, resultClass);
        for (int i = 1; i <= parameters.size(); i++) {
            nativeQuery.setParameter(i, parameters.get(i - 1));
        }
        return nativeQuery.getResultList();
    }


    @Override
    public Page<T> queryAll(Object criteria, Pageable pageable) {
        return getRepository().findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
    }

    @Override
    public List<T> queryAll(Object criteria) {
        return getRepository().findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
    }

    public Integer countBySql(String sql, Map<String, Object> parameters) {
        final Query nativeQuery = entityManager.createNativeQuery(sql, Integer.class);
        parameters.forEach(nativeQuery::setParameter);
        return nativeQuery.getFirstResult();
    }

    protected JPASQLQuery<Void> buildQuery() {
        return new JPASQLQuery<Void>(entityManager, MySQLTemplates.builder().build());
    }

}
