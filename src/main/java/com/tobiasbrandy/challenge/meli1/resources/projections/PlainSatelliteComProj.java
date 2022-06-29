package com.tobiasbrandy.challenge.meli1.resources.projections;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.tobiasbrandy.challenge.meli1.models.Satellite;
import com.tobiasbrandy.challenge.meli1.models.SatelliteCom;

public record PlainSatelliteComProj(String satellite, Instant receivedAt, double distance, List<String> message) {
    public static PlainSatelliteComProj fromSatellite(final Satellite sat) {
        final SatelliteCom com = sat.communication();
        return com == null
            ? null
            : new PlainSatelliteComProj(sat.name(), com.receivedAt(), com.distance(), com.message())
            ;
    }

    public static List<PlainSatelliteComProj> fromSatellites(final Collection<Satellite> sats) {
        return sats.stream()
            .map    (PlainSatelliteComProj::fromSatellite)
            .filter (Objects::nonNull)
            .toList ()
            ;
    }
}
