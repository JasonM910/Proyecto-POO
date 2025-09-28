package model;

import java.util.Objects;

public class Resultados {
    private final String idResultado;
    private double tiempoFinal;
    private int posicion;
    private Categoria categoria;
    private Carrera carrera;

    public Resultados(String idResultado, double tiempoFinal, int posicion, Categoria categoria, Carrera carrera) {
        this.idResultado = Objects.requireNonNull(idResultado, "El identificador del resultado no puede ser nulo");
        this.categoria = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
        this.carrera = Objects.requireNonNull(carrera, "La carrera no puede ser nula");
        actualizarResultados(tiempoFinal, posicion);
    }

    public String getIdResultado() {
        return idResultado;
    }

    public double getTiempoFinal() {
        return tiempoFinal;
    }

    public int getPosicion() {
        return posicion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = Objects.requireNonNull(carrera, "La carrera no puede ser nula");
    }

    public void actualizarResultados(double tiempoFinal, int posicion) {
        if (tiempoFinal < 0) {
            throw new IllegalArgumentException("El tiempo final no puede ser negativo");
        }
        if (posicion <= 0) {
            throw new IllegalArgumentException("La posición debe ser positiva");
        }
        this.tiempoFinal = tiempoFinal;
        this.posicion = posicion;
    }
}
