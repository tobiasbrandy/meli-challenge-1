package com.tobiasbrandy.challenge.meli1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tobiasbrandy.challenge.meli1.models.SatelliteCom;
import com.tobiasbrandy.challenge.meli1.repositories.utils.InsertRepositoryFragment;

@Repository
public interface SatelliteComRepository extends CrudRepository<SatelliteCom, String>, InsertRepositoryFragment<SatelliteCom> {
}
