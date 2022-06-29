package com.tobiasbrandy.challenge.meli1.resources.dtos.output;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.tobiasbrandy.challenge.meli1.models.Satellite;
import com.tobiasbrandy.challenge.meli1.models.SatelliteCom;

public record PlainSatelliteComDto(String satellite, Instant receivedAt, double distance, List<String> message) {
    public static PlainSatelliteComDto fromSatellite(final Satellite sat) {
        final SatelliteCom com = sat.communication();
        return com == null
            ? null
            : new PlainSatelliteComDto(sat.name(), com.receivedAt(), com.distance(), com.message())
            ;
    }

    public static List<PlainSatelliteComDto> fromSatellites(final Collection<Satellite> sats) {
        return sats.stream()
            .map(PlainSatelliteComDto::fromSatellite)
            .filter(Objects::nonNull)
            .toList()
            ;
    }
}
