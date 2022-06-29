package com.tobiasbrandy.challenge.meli1.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.tobiasbrandy.challenge.meli1.models.Satellite;
import com.tobiasbrandy.challenge.meli1.repositories.utils.InsertRepositoryFragment;

public interface SatelliteRepository extends CrudRepository<Satellite, String>, InsertRepositoryFragment<Satellite> {
    List<Satellite> findAll();

    @Modifying
    @Query("TRUNCATE ONLY satellite_com")
    void deleteAllComs();
}
