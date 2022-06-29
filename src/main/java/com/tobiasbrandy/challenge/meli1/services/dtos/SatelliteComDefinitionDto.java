package com.tobiasbrandy.challenge.meli1.services.dtos;

import static com.tobiasbrandy.challenge.meli1.validation.Validate.*;
import static com.tobiasbrandy.challenge.meli1.validation.Validate.fieldPtr;

import java.util.List;
import java.util.function.Consumer;

import com.tobiasbrandy.challenge.meli1.validation.ErrorEntity;
import com.tobiasbrandy.challenge.meli1.validation.Validable;

public record SatelliteComDefinitionDto(String satellite, double distance, List<String> message) implements Validable {

    @Override
    public boolean errors(final Consumer<ErrorEntity> errors, final String ptr) {
        boolean ret = false;

        ret |= validateNotNull       (fieldPtr(ptr, "satellite"),   satellite,  errors);
        ret |= validatePositiveNumber(fieldPtr(ptr, "distance"),    distance,   errors);
        ret |= validateNotEmpty      (fieldPtr(ptr, "message"),     message, null, errors);

        return ret;
    }
}