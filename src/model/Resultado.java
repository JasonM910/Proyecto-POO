package model;

import java.util.Objects;

/**
 * La clase Resultado representa el resultado de una inscripción en una carrera, incluyendo tiempo y posiciones.
 */
public class Resultado {
    private final String idResultado;
    private double tiempoSegundos;
    private int posicionGeneral;
    private int posicionCategoria;

    /**
     * Crea un nuevo resultado para una inscripción.
     * @param idResultado Identificador único del resultado.
     * @param tiempoSegundos Tiempo en segundos registrado.
     * @param posicionGeneral Posición general obtenida.
     * @param posicionCategoria Posición en la categoría obtenida.
     * @throws NullPointerException si el identificador es nulo.
     * @throws IllegalArgumentException si el tiempo no es positivo o las posiciones son negativas.
     */
    public Resultado(String idResultado, double tiempoSegundos, int posicionGeneral, int posicionCategoria) {
        this.idResultado = Objects.requireNonNull(idResultado, "El identificador del resultado no puede ser nulo");
        actualizarDatos(tiempoSegundos, posicionGeneral, posicionCategoria);
    }

    /**
     * Obtiene el identificador único del resultado.
     * @return idResultado
     */
    public String getIdResultado() {
        return idResultado;
    }

    /**
     * Obtiene el tiempo registrado en segundos.
     * @return tiempo en segundos
     */
    public double getTiempoSegundos() {
        return tiempoSegundos;
    }

    /**
     * Obtiene la posición general obtenida.
     * @return posición general
     */
    public int getPosicionGeneral() {
        return posicionGeneral;
    }

    /**
     * Obtiene la posición obtenida en la categoría.
     * @return posición en la categoría
     */
    public int getPosicionCategoria() {
        return posicionCategoria;
    }

    /**
     * Actualiza los datos del resultado.
     * @param tiempoSegundos Nuevo tiempo en segundos.
     * @param posicionGeneral Nueva posición general.
     * @param posicionCategoria Nueva posición en la categoría.
     * @throws IllegalArgumentException si el tiempo no es positivo o las posiciones son negativas.
     */
    public void actualizarDatos(double tiempoSegundos, int posicionGeneral, int posicionCategoria) {
        if (tiempoSegundos <= 0) {
            throw new IllegalArgumentException("El tiempo debe ser positivo");
        }
        if (posicionGeneral < 0 || posicionCategoria < 0) {
            throw new IllegalArgumentException("Las posiciones no pueden ser negativas");
        }
        this.tiempoSegundos = tiempoSegundos;
        this.posicionGeneral = posicionGeneral;
        this.posicionCategoria = posicionCategoria;
    }
}
