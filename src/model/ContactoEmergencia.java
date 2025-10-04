package model;

import java.util.Objects;

/**
 * La clase ContactoEmergencia representa un contacto de emergencia asociado a un corredor.
 * Incluye nombre, teléfono y relación con el corredor.
 */
public class ContactoEmergencia {
    private String nombre;
    private String telefono;
    private String relacion;

    /**
     * Crea un nuevo contacto de emergencia.
     * @param nombre Nombre del contacto.
     * @param telefono Teléfono del contacto.
     * @param relacion Relación con el corredor.
     * @throws NullPointerException si algún parámetro es nulo.
     */
    public ContactoEmergencia(String nombre, String telefono, String relacion) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.telefono = Objects.requireNonNull(telefono, "El telefono no puede ser nulo");
        this.relacion = Objects.requireNonNull(relacion, "La relacion no puede ser nula");
    }

    /**
     * Obtiene el nombre del contacto de emergencia.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el teléfono del contacto de emergencia.
     * @return teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Obtiene la relación del contacto con el corredor.
     * @return relación
     */
    public String getRelacion() {
        return relacion;
    }

    /**
     * Actualiza los datos del contacto de emergencia.
     * @param nombre Nuevo nombre.
     * @param telefono Nuevo teléfono.
     * @param relacion Nueva relación.
     * @throws NullPointerException si algún parámetro es nulo.
     */
    public void actualizarContacto(String nombre, String telefono, String relacion) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.telefono = Objects.requireNonNull(telefono, "El telefono no puede ser nulo");
        this.relacion = Objects.requireNonNull(relacion, "La relacion no puede ser nula");
    }
}
