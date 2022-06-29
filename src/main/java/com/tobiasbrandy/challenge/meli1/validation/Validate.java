package com.tobiasbrandy.challenge.meli1.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public final class Validate {
    private Validate() {
        // static class
    }

    public static final int VALIDATION_ERROR = 100;

    public static <T extends Validable> T validOrFail(final T o, final int limit) {
        final List<ErrorEntity> errors = new ArrayList<>(0);
        o.errors(errors::add, Validable.BASE_PTR);
        if(!errors.isEmpty()) {
            throw new ApplicationException(400, new MultipleErrorEntity(errors.subList(0, Math.max(1, Math.min(errors.size(), limit)))));
        }
        return o;
    }

    public static String fieldPtr(final String ptr, final String field) {
        return ptr + '/' + field;
    }

    public static boolean fail(final ErrorEntity errorEntity, final Consumer<ErrorEntity> errors) {
        if(errors != null) {
            errors.accept(errorEntity);
        }
        return true;
    }

    public static BaseErrorEntity fieldValidationError(final int errorCode, final String ptr, final String message) {
        return new BaseErrorEntity(errorCode, "Validation error for " + ptr + ": " + message);
    }

    private static BaseErrorEntity validationError(final int validationErrorCode, final String ptr, final String message) {
        return fieldValidationError(VALIDATION_ERROR + validationErrorCode, ptr, message);
    }

    public static BaseErrorEntity validateNotNullFailed(final String ptr) {
        return validationError(1, ptr, "field must not be null");
    }

    public static boolean validateNotNull(final String ptr, final Object value, final Consumer<ErrorEntity> errors) {
        boolean ret = false;
        if(value == null) {
            ret = fail(validateNotNullFailed(ptr), errors);
        } else if(value instanceof Validable) {
            ret = ((Validable) value).errors(errors, ptr);
        }
        return ret;
    }

    public static boolean validateNull(final String ptr, final Object value, final Consumer<ErrorEntity> errors) {
        boolean ret = false;
        if(null != value) {
            ret = fail(validationError(2, ptr, "field must be null"), errors);
        }
        return ret;
    }

    public static boolean validatePositiveNumber(final String ptr, final Number value, final Consumer<ErrorEntity> errors) {
        boolean ret = false;
        if (null == value) {
            ret = fail(validateNotNullFailed(ptr), errors);
        } else {
            if(value.doubleValue() <= 0) {
                ret = fail(validationError(3, ptr, "field is not positive"), errors);
            }
        }
        return ret;
    }

    public static boolean validateNotEmpty(final String ptr, final Collection<?> value, final Consumer<ErrorEntity> errors) {
        boolean ret = false;
        if (null == value) {
            ret = fail(validateNotNullFailed(ptr), errors);
        } else {
            if(value.isEmpty()) {
                ret = fail(validationError(4, ptr, "field must not be empty"), errors);
            }
        }
        return ret;
    }
}
