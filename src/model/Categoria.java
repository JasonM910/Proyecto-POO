package model;

import java.util.Objects;

/**
 * La clase Categoria representa una categoría de competencia con un rango etario definido.
 * Permite establecer el nombre y el rango de edades de la categoría.
 */
public class Categoria {
    private String nombre;
    private int edadMinima;
    private int edadMaxima;

    /**
     * Crea una nueva categoría con nombre y rango etario.
     * @param nombre Nombre de la categoría.
     * @param edadMinima Edad mínima permitida.
     * @param edadMaxima Edad máxima permitida.
     * @throws NullPointerException si el nombre es nulo.
     * @throws IllegalArgumentException si el rango de edades no es válido.
     */
    public Categoria(String nombre, int edadMinima, int edadMaxima) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre de la categoria no puede ser nulo");
        if (edadMinima < 0 || edadMaxima < edadMinima) {
            throw new IllegalArgumentException("El rango de edades no es valido");
        }
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }

    /**
     * Obtiene el nombre de la categoría.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría.
     * @param nombre Nuevo nombre.
     * @throws NullPointerException si el nombre es nulo.
     */
    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre de la categoria no puede ser nulo");
    }

    /**
     * Obtiene la edad mínima permitida en la categoría.
     * @return edad mínima
     */
    public int getEdadMinima() {
        return edadMinima;
    }

    /**
     * Obtiene la edad máxima permitida en la categoría.
     * @return edad máxima
     */
    public int getEdadMaxima() {
        return edadMaxima;
    }

    /**
     * Actualiza el rango etario de la categoría.
     * @param edadMinima Nueva edad mínima.
     * @param edadMaxima Nueva edad máxima.
     * @throws IllegalArgumentException si el rango de edades no es válido.
     */
    public void actualizarRangoEtario(int edadMinima, int edadMaxima) {
        if (edadMinima < 0 || edadMaxima < edadMinima) {
            throw new IllegalArgumentException("El rango de edades no es valido");
        }
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }
}
