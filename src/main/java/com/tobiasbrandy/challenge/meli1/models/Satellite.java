package com.tobiasbrandy.challenge.meli1.models;

import static com.tobiasbrandy.challenge.meli1.validation.Validate.fieldPtr;
import static com.tobiasbrandy.challenge.meli1.validation.Validate.validateNotNull;

import java.util.function.Consumer;

import org.springframework.data.annotation.Id;

import com.tobiasbrandy.challenge.meli1.validation.ErrorEntity;
import com.tobiasbrandy.challenge.meli1.validation.Validable;

public record Satellite(@Id String name, long positionX, long positionY, SatelliteCom communication) implements Validable {

    @Override
    public boolean errors(final Consumer<ErrorEntity> errors, final String ptr) {
        boolean ret = false;

        ret |= validateNotNull(fieldPtr(ptr, "name"), name, errors);
        ret |= communication != null && validateNotNull(fieldPtr(ptr, "communication"), communication, errors);

        return ret;
    }
}
