package com.tobiasbrandy.challenge.meli1.services;

import java.util.List;
import java.util.Optional;

import com.tobiasbrandy.challenge.meli1.models.Satellite;
import com.tobiasbrandy.challenge.meli1.models.SatelliteCom;

public interface SatelliteService {

    Satellite createSatellite(final String name, final long positionX, final long positionY);

    Satellite updateSatellite(final String name, final long positionX, final long positionY);

    Optional<Satellite> findSatellite(final String name);

    List<Satellite> listSatellites();

    void deleteSatellite(final String satellite);

    void deleteAllSatellites();

    Satellite publishSatelliteCom(final String satellite, final double distance, final List<String> message);

    Optional<SatelliteCom> findSatelliteCom(final String satellite);

    void deleteSatelliteCom(final String satellite);

    void deleteAllSatelliteComs();

    void triangulatePosition(final List<SatelliteCom> coms);
}
