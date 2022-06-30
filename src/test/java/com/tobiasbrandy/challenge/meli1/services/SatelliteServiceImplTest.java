package com.tobiasbrandy.challenge.meli1.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tobiasbrandy.challenge.meli1.models.Satellite;
import com.tobiasbrandy.challenge.meli1.models.SatelliteCom;
import com.tobiasbrandy.challenge.meli1.services.dtos.SpaceshipPositionDto;
import com.tobiasbrandy.challenge.meli1.validation.ApplicationException;

class SatelliteServiceImplTest {

    private static final Instant FIXED = Instant.ofEpochSecond(123456);
    private static final List<String> EMPTY_MESSAGE = List.of("empty");

    @Test
    void testCalculateSpaceshipPosition() {
        final SpaceshipPositionDto position = SatelliteServiceImpl.calculateSpaceshipPosition(List.of(
            new Satellite("kenobi",     -500, -200, new SatelliteCom(FIXED, 485.4122,  EMPTY_MESSAGE)),
            new Satellite("skywalker",  100,  -100, new SatelliteCom(FIXED, 265.7536,  EMPTY_MESSAGE)),
            new Satellite("sato",       500,  100,  new SatelliteCom(FIXED, 600.5206,  EMPTY_MESSAGE))
        ));
        assertEquals(-100, position.x(), 0.001);
        assertEquals(75, position.y(), 0.001);
    }

    @Test
    void testMergeSatelliteMessages() {
        assertEquals("este es un mensaje", SatelliteServiceImpl.mergeSatelliteMessages(List.of(
            List.of("", "este", "es", "un", "mensaje"),
            List.of("este", "", "un", "mensaje"),
            List.of("", "", "es", "", "mensaje")
        )));

        assertEquals("este es un mensaje secreto", SatelliteServiceImpl.mergeSatelliteMessages(List.of(
            List.of("este", "", "", "mensaje", ""),
            List.of("", "es", "", "", "secreto"),
            List.of("este", "", "un", "", "")
        )));

        assertEquals("este es un mensaje secreto", SatelliteServiceImpl.mergeSatelliteMessages(List.of(
            List.of("este", "", "", "mensaje", ""),
            List.of("", "es", "", "", "secreto"),
            List.of("", "un", "", "") // lista mas corta que el mensaje total!
        )));

        // mucho padding
        assertEquals("este es un mensaje secreto", SatelliteServiceImpl.mergeSatelliteMessages(List.of(
            List.of("","este", "", "", "mensaje", ""),
            List.of("","","","", "es", "", "", "secreto"),
            List.of("","","","","","","","", "", "un", "", "")
        )));

        //Mensajes no coinciden
        assertThrows(ApplicationException.class, () -> SatelliteServiceImpl.mergeSatelliteMessages(List.of(
            List.of("este", "", "", "mensaje", ""),
            List.of("", "es", "", "", "secreto"),
            List.of("este", "", "un", "", "publico") // No coinciden. secreto != publico
        )));
    }

}