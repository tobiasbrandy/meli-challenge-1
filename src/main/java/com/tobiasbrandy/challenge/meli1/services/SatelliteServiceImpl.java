package com.tobiasbrandy.challenge.meli1.services;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.relational.core.conversion.DbActionExecutionException;

import com.tobiasbrandy.challenge.meli1.models.Satellite;
import com.tobiasbrandy.challenge.meli1.models.SatelliteCom;
import com.tobiasbrandy.challenge.meli1.repositories.SatelliteRepository;
import com.tobiasbrandy.challenge.meli1.services.dtos.SatelliteComDefinitionDto;
import com.tobiasbrandy.challenge.meli1.services.dtos.SatelliteTriangulationResultDto;
import com.tobiasbrandy.challenge.meli1.validation.ErrorCodes;

@Named
public class SatelliteServiceImpl implements SatelliteService {
    private final SatelliteRepository   satelliteRepo;
    private final Clock                 clock;

    @Inject
    public SatelliteServiceImpl(
        final SatelliteRepository   satelliteRepo,
        final Clock                 clock
    ) {
        this.satelliteRepo  = Objects.requireNonNull(satelliteRepo);
        this.clock          = Objects.requireNonNull(clock);
    }

    @Override
    public Satellite createSatellite(final String name, final long positionX, final long positionY) {
        try {
            return satelliteRepo.insert(new Satellite(name, positionX, positionY, null));
        } catch(final DbActionExecutionException e) {
            throw ErrorCodes.satelliteCreationFailed(e.getMessage());
        }
    }

    @Override
    public Satellite updateSatellite(final String name, final long positionX, final long positionY) {
        try {
            return satelliteRepo.update(new Satellite(name, positionX, positionY, null));
        } catch(final DbActionExecutionException e) {
            throw ErrorCodes.satelliteComUpdateFailed(e.getMessage());
        }
    }

    @Override
    public Optional<Satellite> findSatellite(final String name) {
        return satelliteRepo.findById(name);
    }

    @Override
    public List<Satellite> listSatellites() {
        return satelliteRepo.findAll();
    }

    @Override
    public void deleteSatellite(final String satellite) {
        satelliteRepo.deleteById(satellite);
    }

    @Override
    public void deleteAllSatellites() {
        satelliteRepo.deleteAll();
    }

    @Override
    public Satellite publishSatelliteCom(final String satellite, final double distance, final List<String> message) {
        final Satellite sat = satelliteRepo.findById(satellite).orElseThrow(() -> ErrorCodes.satelliteNotFound(satellite));
        final SatelliteCom com = new SatelliteCom(clock.instant(), distance, message);
        return satelliteRepo.save(new Satellite(satellite, sat.positionX(), sat.positionY(), com));
    }

    @Override
    public Optional<SatelliteCom> findSatelliteCom(final String satellite) {
        return satelliteRepo.findById(satellite).map(Satellite::communication);
    }

    @Override
    public void deleteSatelliteCom(final String satellite) {
        final Satellite sat = satelliteRepo.findById(satellite).orElseThrow(() -> ErrorCodes.satelliteNotFound(satellite));
        satelliteRepo.save(new Satellite(sat.name(), sat.positionX(), sat.positionY(), null));
    }

    @Override
    public void deleteAllSatelliteComs() {
        satelliteRepo.deleteAllComs();
    }

    @Override
    public SatelliteTriangulationResultDto triangulateSatellitesFromNames(final List<String> satelliteNames) {
        final List<Satellite> satellites = satelliteRepo.findAllById(satelliteNames);

        // Validation
        if(satellites.size() != 3) {
            throw ErrorCodes.satellitesForTriangulationNotFound();
        } else if(satellites.stream().anyMatch(sat -> sat.communication() == null)) {
            throw ErrorCodes.satellitesComForTriangulationNotFound();
        }

        return triangulateSatellites(satellites);
    }

    @Override
    public SatelliteTriangulationResultDto triangulateSatellitesFromComs(final List<SatelliteComDefinitionDto> satellitesDef) {
        final List<Satellite> satellites = satelliteRepo.findAllById(satellitesDef.stream()
            .map(SatelliteComDefinitionDto::satellite)
            .toList()
        );

        // Validation
        if(satellites.size() != 3) {
            throw ErrorCodes.satellitesForTriangulationNotFound();
        }

        // Tenemos que armar los satellites de nuevo para incorporarles la informacion de las coms recibidas, y no las guardadas
        final List<Satellite> updatedSats = new ArrayList<>(3);
        for(final Satellite sat : satellites) {
            final SatelliteCom com = satellitesDef.stream()
                .filter(def -> def.satellite().equals(sat.name()))
                .findFirst()
                .map(def -> new SatelliteCom(clock.instant(), def.distance(), def.message()))
                .orElseThrow() // Unreachable state
                ;
            updatedSats.add(new Satellite(sat.name(), sat.positionX(), sat.positionY(), com));
        }

        return triangulateSatellites(updatedSats);
    }

    @Override
    public SatelliteTriangulationResultDto triangulateSatellites(final List<Satellite> satellites) {
        // TODO(tobi)
        return null;
    }
}
