package model;

import java.util.Objects;

public class Categoria {
    private String nombre;
    private int edadMinima;
    private int edadMaxima;

    public Categoria(String nombre, int edadMinima, int edadMaxima) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre de la categoria no puede ser nulo");
        if (edadMinima < 0 || edadMaxima < edadMinima) {
            throw new IllegalArgumentException("El rango de edades no es valido");
        }
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre de la categoria no puede ser nulo");
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public int getEdadMaxima() {
        return edadMaxima;
    }

    public void actualizarRangoEtario(int edadMinima, int edadMaxima) {
        if (edadMinima < 0 || edadMaxima < edadMinima) {
            throw new IllegalArgumentException("El rango de edades no es valido");
        }
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }
}
