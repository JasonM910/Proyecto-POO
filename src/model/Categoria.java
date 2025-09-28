package model;

import java.util.Objects;

public class Categoria {
    private final String idCategoria;
    private String nombre;
    private String descripcion;
    private int edadMinima;
    private int edadMaxima;
    private String genero;
    private String horarioAsignado;

    public Categoria(String idCategoria, String nombre, String descripcion, int edadMinima, int edadMaxima, String genero) {
        this.idCategoria = Objects.requireNonNull(idCategoria, "El identificador de la categoría no puede ser nulo");
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripción no puede ser nula");
        this.genero = Objects.requireNonNull(genero, "El género no puede ser nulo");
        if (edadMinima < 0 || edadMaxima < edadMinima) {
            throw new IllegalArgumentException("El rango de edades no es válido");
        }
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = Objects.requireNonNull(descripcion, "La descripción no puede ser nula");
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        if (edadMinima < 0 || edadMinima > edadMaxima) {
            throw new IllegalArgumentException("La edad mínima no es válida");
        }
        this.edadMinima = edadMinima;
    }

    public int getEdadMaxima() {
        return edadMaxima;
    }

    public void setEdadMaxima(int edadMaxima) {
        if (edadMaxima < edadMinima) {
            throw new IllegalArgumentException("La edad máxima no es válida");
        }
        this.edadMaxima = edadMaxima;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = Objects.requireNonNull(genero, "El género no puede ser nulo");
    }

    public String getHorarioAsignado() {
        return horarioAsignado;
    }

    public void asignarHorario(String horario) {
        this.horarioAsignado = Objects.requireNonNull(horario, "El horario no puede ser nulo");
    }
}
