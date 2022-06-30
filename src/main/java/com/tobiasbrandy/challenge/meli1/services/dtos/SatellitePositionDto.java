package com.tobiasbrandy.challenge.meli1.services.dtos;

import static com.tobiasbrandy.challenge.meli1.validation.Validate.fieldPtr;
import static com.tobiasbrandy.challenge.meli1.validation.Validate.validateNotNull;

import java.util.function.Consumer;

import com.tobiasbrandy.challenge.meli1.validation.ErrorEntity;
import com.tobiasbrandy.challenge.meli1.validation.Validable;

public record SatellitePositionDto(Double positionX, Double positionY) implements Validable {
    @Override
    public boolean errors(final Consumer<ErrorEntity> errors, final String ptr) {
        boolean ret = false;

        ret |= validateNotNull(fieldPtr(ptr, "positionX"), positionX,  errors);
        ret |= validateNotNull(fieldPtr(ptr, "positionY"), positionY,  errors);

        return ret;
    }
}
