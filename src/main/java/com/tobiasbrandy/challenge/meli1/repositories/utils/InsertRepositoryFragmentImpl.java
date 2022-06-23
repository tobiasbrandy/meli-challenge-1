package com.tobiasbrandy.challenge.meli1.repositories.utils;

import java.util.Objects;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

public class InsertRepositoryFragmentImpl<T> implements InsertRepositoryFragment<T> {

    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    public InsertRepositoryFragmentImpl(final JdbcAggregateTemplate jdbcAggregateTemplate) {
        this.jdbcAggregateTemplate = Objects.requireNonNull(jdbcAggregateTemplate);
    }

    @Override
    public <S extends T> S insert(final S entity) {
        return jdbcAggregateTemplate.insert(entity);
    }

    @Override
    public <S extends T> S update(final S entity) {
        return jdbcAggregateTemplate.update(entity);
    }
}
