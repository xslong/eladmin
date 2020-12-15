package com.xxx.pri.base;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@NoRepositoryBean
public interface PriBaseRepository<T, ID> extends PagingAndSortingRepository<T, ID>,
        QuerydslPredicateExecutor<T>, QueryByExampleExecutor<T>, JpaSpecificationExecutor<T> {
}
