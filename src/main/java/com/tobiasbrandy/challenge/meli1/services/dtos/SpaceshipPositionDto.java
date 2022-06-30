package com.tobiasbrandy.challenge.meli1.services.dtos;

import java.util.function.Consumer;

import com.tobiasbrandy.challenge.meli1.validation.ErrorEntity;
import com.tobiasbrandy.challenge.meli1.validation.Validable;

public record SpaceshipPositionDto(double x, double y) implements Validable {
    @Override
    public boolean errors(final Consumer<ErrorEntity> errors, final String ptr) {
        return false;
    }
}