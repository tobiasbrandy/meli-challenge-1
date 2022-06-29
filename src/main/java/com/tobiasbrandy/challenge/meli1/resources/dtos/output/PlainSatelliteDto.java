package com.tobiasbrandy.challenge.meli1.resources.dtos.output;

import java.util.Collection;
import java.util.List;

import com.tobiasbrandy.challenge.meli1.models.Satellite;

public record PlainSatelliteDto(String name, long positionX, long positionY) {
    public static PlainSatelliteDto fromSatellite(final Satellite sat) {
        return new PlainSatelliteDto(sat.name(), sat.positionX(), sat.positionY());
    }

    public static List<PlainSatelliteDto> fromSatellites(final Collection<Satellite> sats) {
        return sats.stream()
            .map(PlainSatelliteDto::fromSatellite)
            .toList()
            ;
    }
}
