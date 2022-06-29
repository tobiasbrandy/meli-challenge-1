package com.tobiasbrandy.challenge.meli1.resources.dtos.input;

import static com.tobiasbrandy.challenge.meli1.validation.Validate.*;

import java.util.List;
import java.util.function.Consumer;

import com.tobiasbrandy.challenge.meli1.validation.ErrorEntity;
import com.tobiasbrandy.challenge.meli1.validation.Validable;

public record SplitSatelliteComDto(double distance, List<String> message) implements Validable {

    @Override
    public boolean errors(final Consumer<ErrorEntity> errors, final String ptr) {
        boolean ret = false;

        ret |= validatePositiveNumber(fieldPtr(ptr, "distance"),    distance,   errors);
        ret |= validateNotEmpty      (fieldPtr(ptr, "message"),     message,    errors);

        return ret;
    }
}
