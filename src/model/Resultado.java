package model;

import java.util.Objects;

public class Resultado {
    private final String idResultado;
    private double tiempoSegundos;
    private int posicionGeneral;
    private int posicionCategoria;

    public Resultado(String idResultado, double tiempoSegundos, int posicionGeneral, int posicionCategoria) {
        this.idResultado = Objects.requireNonNull(idResultado, "El identificador del resultado no puede ser nulo");
        actualizarDatos(tiempoSegundos, posicionGeneral, posicionCategoria);
    }

    public String getIdResultado() {
        return idResultado;
    }

    public double getTiempoSegundos() {
        return tiempoSegundos;
    }

    public int getPosicionGeneral() {
        return posicionGeneral;
    }

    public int getPosicionCategoria() {
        return posicionCategoria;
    }

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
