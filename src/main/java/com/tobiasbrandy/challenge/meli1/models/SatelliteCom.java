package com.tobiasbrandy.challenge.meli1.models;

import java.util.List;

import org.springframework.data.annotation.Id;

public record SatelliteCom(@Id String name, double distance, List<String> message) {

}
