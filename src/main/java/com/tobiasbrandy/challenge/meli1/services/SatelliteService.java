package com.tobiasbrandy.challenge.meli1.services;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;

import com.tobiasbrandy.challenge.meli1.models.ApplicationException;
import com.tobiasbrandy.challenge.meli1.models.SatelliteCom;
import com.tobiasbrandy.challenge.meli1.repositories.SatelliteComRepository;

@Service
public class SatelliteService {
    private final SatelliteComRepository satelliteComRepo;

    @Inject
    public SatelliteService(
        final SatelliteComRepository satelliteComRepo
    ) {
        this.satelliteComRepo = Objects.requireNonNull(satelliteComRepo);
    }

    public SatelliteCom createSatelliteCom(final String name, final double distance, final List<String> message) {
        try {
            return satelliteComRepo.insert(new SatelliteCom(name, distance, message));
        } catch(final DbActionExecutionException e) {
            throw new ApplicationException(701, "Error during satellite insertion: " + e.getMessage());
        }
    }

    public SatelliteCom updateSatelliteCom(final String name, final double distance, final List<String> message) {
        try {
            return satelliteComRepo.update(new SatelliteCom(name, distance, message));
        } catch(final DbActionExecutionException e) {
            throw new ApplicationException(702, "Error during satellite update: " + e.getMessage());
        }
    }

}
