package com.tobiasbrandy.challenge.meli1.validation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public sealed interface ErrorEntity permits BaseErrorEntity, MultipleErrorEntity {
    @JsonIgnore
    List<Integer> getCodes();
}
