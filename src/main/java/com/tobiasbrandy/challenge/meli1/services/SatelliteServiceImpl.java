package com.tobiasbrandy.challenge.meli1.services;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.tobiasbrandy.challenge.meli1.models.Satellite;
import com.tobiasbrandy.challenge.meli1.models.SatelliteCom;
import com.tobiasbrandy.challenge.meli1.repositories.SatelliteRepository;
import com.tobiasbrandy.challenge.meli1.services.dtos.SatelliteComDefinitionDto;
import com.tobiasbrandy.challenge.meli1.services.dtos.SatelliteTriangulationResultDto;
import com.tobiasbrandy.challenge.meli1.services.dtos.SpaceshipPositionDto;
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
    public Satellite createSatellite(final String name, final double positionX, final double positionY) {
        try {
            return satelliteRepo.insert(new Satellite(name, positionX, positionY, null));
        } catch(final DbActionExecutionException e) {
            throw ErrorCodes.satelliteCreationFailed(e.getMessage());
        }
    }

    @Override
    public Satellite updateSatellite(final String name, final double positionX, final double positionY) {
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
            .map(SatelliteComDefinitionDto::name)
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
                .filter(def -> def.name().equals(sat.name()))
                .findFirst()
                .map(def -> new SatelliteCom(clock.instant(), def.distance(), def.message()))
                .orElseThrow() // Unreachable state
                ;
            updatedSats.add(new Satellite(sat.name(), sat.positionX(), sat.positionY(), com));
        }

        return triangulateSatellites(updatedSats);
    }

    /**
     * Funcion principal de logica del challenge.
     * La misma consta de dos partes importantes: El calculo del mensaje enviado, positionY el calculo de la posicion de la nave.
     *
     * Para el calculo de la posicion de la nave...
     *
     */
    @Override
    public SatelliteTriangulationResultDto triangulateSatellites(final List<Satellite> satellites) {
        final SpaceshipPositionDto spaceshipPos = calculateSpaceshipPosition(satellites);
        final String message = mergeSatelliteMessages(satellites.stream()
            .map(Satellite::communication)
            .map(SatelliteCom::message)
            .toList()
        );
        return new SatelliteTriangulationResultDto(spaceshipPos, message);
    }

    /**
     * Para el calculo de la posicion de la nave utilizamos la tecnica de trilateracion, que a partir de al menos
     *  3 puntos positionY las distancias al mismo, puede obtener la ubicacion del punto en cuestion.
     * Utilizamos una implementacion ya existente provista por una libreria, de esta manera la solucion es mas robusta.
     *
     * En caso de que los circulos formados por las posiciones de los satelites positionY la distancia a la nave no interseccionen,
     *  retornamos un valor best effort, en vez de fallar.
     *
     * <a href="https://github.com/lemmingapex/trilateration">Trilateration library reference</a>
     */
    /* testing */ static SpaceshipPositionDto calculateSpaceshipPosition(final List<Satellite> satellites) {
        final double[][] positions = satellites.stream()
            .map(sat -> new double[]{sat.positionX(), sat.positionY()})
            .toArray(double[][]::new)
            ;
        final double[] distances = satellites.stream()
            .map(Satellite::communication)
            .mapToDouble(SatelliteCom::distance)
            .toArray()
            ;

        final LeastSquaresOptimizer.Optimum trilaterationResult = new NonLinearLeastSquaresSolver(
            new TrilaterationFunction(positions, distances),
            new LevenbergMarquardtOptimizer()
        ).solve();

        final double[] resultPoint = trilaterationResult.getPoint().toArray();
        return new SpaceshipPositionDto(resultPoint[0], resultPoint[1]);
    }

    /**
     * Para el calculo del mensaje enviado se asume que el defasaje se encuentra siempre al principio del mensaje,
     *  es decir, las listas de strings siempre terminan cuando termina el mensaje.
     *
     * Teniendo en cuenta esto, para calcular el defasaje lo que haremos sera establecer
     *  cual es la primer palabra de cualquiera de los mensajes.
     *
     * La longitud de esta lista menos el índice de dicha primera palabra nos da como resultado la longitud del mensaje.
     * Una vez calculado esto, es sencillo eliminar las palabras extra, positionY mergear los mensajes.
     *
     * Precondicion: messages posee al menos un elemento.
     */
    /* testing */ static String mergeSatelliteMessages(final List<List<String>> messages) {
        // El primer paso es obtener la longitud del mensaje
        // Hacemos length - idx de primer palabra. Nos quedamos con el maximo.
        final int messageLen = messages.stream()
            .mapToInt(message -> message.size() - firstNonEmptyStringIdx(message))
            .max()
            .orElseThrow() // messages siempre tiene al menos un elemento
            ;

        if(messageLen == 0) {
            return "";
        }

        // Ahora mergeamos todos los distintos mensajes en el array ret.
        // Hasta que todas las propiedades estan llenas, o iteremos por todos los mensajes, lo que suceda primero.
        final String[] ret = new String[messageLen];
        Arrays.fill(ret, "");

        for(final List<String> message : messages) {
            // Si es negativo, el mensaje no llego entero, por lo que arrancamos "mas tarde"
            final int startingIdx = message.size() - messageLen;

            int idx = -Math.min(startingIdx, 0);
            for(final ListIterator<String> propIter = message.listIterator(Math.max(startingIdx, 0)); propIter.hasNext(); ) {
                final String prop = propIter.next();

                if(!"".equals(prop)) {
                    if("".equals(ret[idx])) {
                        // Encontramos una nueva prop que commitear a ret
                        ret[idx] = prop;
                    } else if(!ret[idx].equals(prop)) {
                        // Los mensajes no coinciden
                        throw ErrorCodes.satellitesComMessagesDontMatch();
                    }
                }
                idx++;
            }
        }

        // Retornamos directamente la concatenacion separada por espacios, eliminando los string vacios
        return Arrays.stream(ret)
            .filter(Predicate.not(""::equals))
            .collect(Collectors.joining(" "))
            ;
    }

    /** Returns idx of first non empty string, or collection length if there is none */
    private static int firstNonEmptyStringIdx(final Collection<String> strings) {
        int idx = 0;
        for(final String s : strings) {
            if(!"".equals(s)) {
                // We are done
                break;
            }
            idx++;
        }
        return idx;
    }
}
