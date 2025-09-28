package model;

import java.time.LocalDate;
import java.util.Objects;

public class RegistroTiempo {

    public Resultados registrarTiempo(Competidor competidor, double tiempoSegundos, LocalDate fecha, Carrera carrera) {
        Objects.requireNonNull(competidor, "El competidor no puede ser nulo");
        Objects.requireNonNull(fecha, "La fecha no puede ser nula");
        Objects.requireNonNull(carrera, "La carrera no puede ser nula");
        if (tiempoSegundos <= 0) {
            throw new IllegalArgumentException("El tiempo registrado debe ser positivo");
        }

        Resultados resultados = new Resultados(
                "RES-" + competidor.getNumeroCompetidor() + "-" + carrera.getIdCarrera(),
                tiempoSegundos,
                1,
                competidor.getCategoria(),
                carrera
        );
        competidor.setResultados(resultados);
        return resultados;
    }
}
