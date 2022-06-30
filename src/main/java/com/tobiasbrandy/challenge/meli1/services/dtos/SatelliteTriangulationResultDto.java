package com.tobiasbrandy.challenge.meli1.services.dtos;

import static com.tobiasbrandy.challenge.meli1.validation.Validate.*;
import static com.tobiasbrandy.challenge.meli1.validation.Validate.fieldPtr;

import java.util.function.Consumer;

import com.tobiasbrandy.challenge.meli1.validation.ErrorEntity;
import com.tobiasbrandy.challenge.meli1.validation.Validable;

public record SatelliteTriangulationResultDto(SpaceshipPositionDto position, String message) implements Validable {

    @Override
    public boolean errors(final Consumer<ErrorEntity> errors, final String ptr) {
        boolean ret = false;

        ret |= validateNotNull(fieldPtr(ptr, "satellite"),  position,   errors);
        ret |= validateNotNull(fieldPtr(ptr, "message"),    message,    errors);

        return ret;
    }
}