package model;

import java.util.Objects;

public class ContactoEmergencia {
    private String nombre;
    private String telefono;
    private String relacion;

    public ContactoEmergencia(String nombre, String telefono, String relacion) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.telefono = Objects.requireNonNull(telefono, "El telefono no puede ser nulo");
        this.relacion = Objects.requireNonNull(relacion, "La relacion no puede ser nula");
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getRelacion() {
        return relacion;
    }

    public void actualizarContacto(String nombre, String telefono, String relacion) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.telefono = Objects.requireNonNull(telefono, "El telefono no puede ser nulo");
        this.relacion = Objects.requireNonNull(relacion, "La relacion no puede ser nula");
    }
}
