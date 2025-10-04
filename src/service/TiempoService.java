package service;

import model.Administrador;
import model.Inscripcion;
import model.Resultado;

import java.util.Objects;

/**
 * La clase TiempoService gestiona el registro de tiempos y resultados de inscripciones en carreras.
 * Permite registrar tiempos y actualizar resultados a través de un administrador.
 */
public class TiempoService {
    /**
     * Registra el tiempo de un participante en una inscripción específica.
     *
     * @param administrador El administrador que realiza el registro.
     * @param idInscripcion El ID de la inscripción del participante.
     * @param tiempoSegundos El tiempo registrado en segundos.
     * @return El resultado del registro del tiempo.
     * @throws NullPointerException Si el administrador es nulo.
     */
    public Resultado registrarTiempo(Administrador administrador, String idInscripcion, double tiempoSegundos) {
        Objects.requireNonNull(administrador, "El administrador no puede ser nulo");
        return administrador.registrarTiempo(idInscripcion, tiempoSegundos);
    }

    /**
     * Ingresa el resultado de un participante en una inscripción específica.
     *
     * @param administrador El administrador que realiza el ingreso del resultado.
     * @param inscripcion La inscripción del participante.
     * @param tiempoSegundos El tiempo registrado en segundos.
     * @param posicionGeneral La posición general del participante.
     * @param posicionCategoria La posición en la categoría del participante.
     * @throws NullPointerException Si el administrador o la inscripción son nulos.
     */
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
