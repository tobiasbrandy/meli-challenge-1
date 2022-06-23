package com.tobiasbrandy.challenge.meli1.models;

public class ApplicationException extends RuntimeException {
    private final int statusCode;
    private final int errorCode;

    public ApplicationException(final int statusCode, final int errorCode, final String message) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode  = errorCode;
    }

    public ApplicationException(final int errorCode, final String message) {
        this(400, errorCode, message);
    }

    public ErrorEntity toEntity() {
        return new ErrorEntity(errorCode, getMessage());
    }

    public record ErrorEntity(int errorCode, String message) {
    }

    public int getStatusCode() {
        return statusCode;
    }
    public int getErrorCode() {
        return errorCode;
    }
}
