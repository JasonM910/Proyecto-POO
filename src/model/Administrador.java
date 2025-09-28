package model;

import java.util.Objects;

public class Administrador extends Usuario {
    private String rol;

    public Administrador(String idUsuario, String nombre, String contrasena, String correo, String rol) {
        super(idUsuario, nombre, contrasena, correo);
        this.rol = Objects.requireNonNull(rol, "El rol del administrador no puede ser nulo");
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = Objects.requireNonNull(rol, "El rol del administrador no puede ser nulo");
    }

    public Evento crearEvento(Evento evento) {
        return Objects.requireNonNull(evento, "El evento no puede ser nulo");
    }

    public Evento actualizarEvento(Evento evento) {
        return Objects.requireNonNull(evento, "El evento no puede ser nulo");
    }

    public boolean eliminarEvento(Evento evento) {
        return evento != null;
    }
}
