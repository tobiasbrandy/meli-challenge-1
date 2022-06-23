package com.tobiasbrandy.challenge.meli1.repositories.utils;

/** Fragment that offers the possibility of insert/update differentiation */
public interface InsertRepositoryFragment<T> {
    <S extends T> S insert(S entity);

    <S extends T> S update(S entity);
}
