package com.tobiasbrandy.challenge.meli1.resources.projections;

import java.util.Collection;
import java.util.List;

import com.tobiasbrandy.challenge.meli1.models.Satellite;

public record PlainSatelliteProj(String name, long positionX, long positionY) {
    public static PlainSatelliteProj fromSatellite(final Satellite sat) {
        return new PlainSatelliteProj(sat.name(), sat.positionX(), sat.positionY());
    }

    public static List<PlainSatelliteProj> fromSatellites(final Collection<Satellite> sats) {
        return sats.stream()
            .map(PlainSatelliteProj::fromSatellite)
            .toList()
            ;
    }
}
