package com.tobiasbrandy.challenge.meli1.validation;

import java.util.Locale;
import java.util.Objects;

public class ApplicationException extends RuntimeException {
    private final int           statusCode;
    private final ErrorEntity   errorEntity;

    public ApplicationException(final int statusCode, final ErrorEntity errorEntity) {
        super();
        this.statusCode     = statusCode;
        this.errorEntity    = Objects.requireNonNull(errorEntity);
    }

    public ApplicationException(final int statusCode, final int errorCode, final String message, final Object ...args) {
        this(statusCode, new BaseErrorEntity(errorCode, String.format(Locale.ROOT, message, args)));
    }

    public ApplicationException(final int errorCode, final String message, final Object ...args) {
        this(400, errorCode, message, args);
    }

    public int getStatusCode() {
        return statusCode;
    }
    public ErrorEntity getErrorEntity() {
        return errorEntity;
    }
}
