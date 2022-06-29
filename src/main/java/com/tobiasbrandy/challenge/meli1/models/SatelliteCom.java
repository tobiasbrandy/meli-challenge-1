package com.tobiasbrandy.challenge.meli1.models;

import static com.tobiasbrandy.challenge.meli1.validation.Validate.*;

import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

import com.tobiasbrandy.challenge.meli1.validation.ErrorEntity;
import com.tobiasbrandy.challenge.meli1.validation.Validable;

public record SatelliteCom(Instant receivedAt, double distance, List<String> message) implements Validable {

    @Override
    public boolean errors(final Consumer<ErrorEntity> errors, final String ptr) {
        boolean ret = false;

        ret |= validateNotNull(fieldPtr(ptr,        "receivedAt"),  receivedAt, errors);
        ret |= validatePositiveNumber(fieldPtr(ptr, "distance"),    distance,   errors);
        ret |= validateNotEmpty(fieldPtr(ptr,       "message"),     message, null, errors);

        return ret;
    }
}
