package com.tobiasbrandy.challenge.meli1.validation;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record MultipleErrorEntity(List<ErrorEntity> errors) implements ErrorEntity {

    public MultipleErrorEntity(final List<ErrorEntity> errors) {
        this.errors = Objects.requireNonNull(errors);
    }

    @Override
    public List<Integer> getCodes() {
        return errors.stream()
            .map(ErrorEntity::getCodes)
            .flatMap(Collection::stream)
            .collect(Collectors.toList())
            ;
    }
}
