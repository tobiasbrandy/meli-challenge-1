package com.tobiasbrandy.challenge.meli1.validation;

import java.util.function.Consumer;

@FunctionalInterface
public interface Validable {
    String BASE_PTR = "";

    boolean errors(final Consumer<ErrorEntity> errors, final String ptr);

    static <T extends Validable> T validOrFail(final T o) {
        if(o == null) {
            throw new ApplicationException(400, new BaseErrorEntity(Validate.VALIDATION_ERROR + 1, "Entity must not be null"));
        }
        return Validate.validOrFail(o, 10);
    }
}
