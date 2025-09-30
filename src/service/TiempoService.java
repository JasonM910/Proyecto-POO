package service;

import model.Administrador;
import model.Inscripcion;
import model.Resultado;

import java.util.Objects;

public class TiempoService {

    public Resultado registrarTiempo(Administrador administrador, String idInscripcion, double tiempoSegundos) {
        Objects.requireNonNull(administrador, "El administrador no puede ser nulo");
        return administrador.registrarTiempo(idInscripcion, tiempoSegundos);
    }

    public void ingresarResultado(Administrador administrador, Inscripcion inscripcion, double tiempoSegundos,
                                  int posicionGeneral, int posicionCategoria) {
        Objects.requireNonNull(administrador, "El administrador no puede ser nulo");
        Objects.requireNonNull(inscripcion, "La inscripcion no puede ser nula");
        Resultado resultado = new Resultado(
                "RES-" + inscripcion.getIdInscripcion(),
                tiempoSegundos,
                posicionGeneral,
                posicionCategoria
        );
        administrador.ingresarTiempo(inscripcion, resultado);
    }
}
