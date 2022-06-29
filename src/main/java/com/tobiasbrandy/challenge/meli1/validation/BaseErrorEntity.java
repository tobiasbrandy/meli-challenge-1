package com.tobiasbrandy.challenge.meli1.validation;

import java.util.List;

public record BaseErrorEntity(int errorCode, String message) implements ErrorEntity {

    @Override
    public List<Integer> getCodes() {
        return List.of(errorCode);
    }
}
