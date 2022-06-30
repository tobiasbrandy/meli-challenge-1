package com.tobiasbrandy.challenge.meli1.resources.dtos;

import static com.tobiasbrandy.challenge.meli1.validation.Validate.*;
import static com.tobiasbrandy.challenge.meli1.validation.Validate.fieldPtr;

import java.util.List;
import java.util.function.Consumer;

import com.tobiasbrandy.challenge.meli1.services.dtos.SatelliteComDefinitionDto;
import com.tobiasbrandy.challenge.meli1.validation.ErrorEntity;
import com.tobiasbrandy.challenge.meli1.validation.Validable;

public record SatelliteTriangulationRequestDto(List<SatelliteComDefinitionDto> satellites) implements Validable {

    @Override
    public boolean errors(final Consumer<ErrorEntity> errors, final String ptr) {
        boolean ret = false;

        ret |= validateLength(fieldPtr(ptr, "distance"), satellites,
            3, 3,
            SatelliteComDefinitionDto::name,
            errors
        );

        return ret;
    }
}
