package com.tobiasbrandy.challenge.meli1.services.dtos;

import static com.tobiasbrandy.challenge.meli1.validation.Validate.fieldPtr;
import static com.tobiasbrandy.challenge.meli1.validation.Validate.validateNotNull;

import java.util.function.Consumer;

import com.tobiasbrandy.challenge.meli1.validation.ErrorEntity;
import com.tobiasbrandy.challenge.meli1.validation.Validable;

public record SatellitePositionDto(Long x, Long y) implements Validable {
    @Override
    public boolean errors(final Consumer<ErrorEntity> errors, final String ptr) {
        boolean ret = false;

        ret |= validateNotNull(fieldPtr(ptr, "x"), x,  errors);
        ret |= validateNotNull(fieldPtr(ptr, "y"), y,  errors);

        return ret;
    }
}
