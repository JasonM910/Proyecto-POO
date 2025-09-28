package model;

import java.util.Objects;

public class Usuario {
    private final String idUsuario;
    private String nombre;
    private String contrasena;
    private String correo;
    private boolean sesionActiva;

    public Usuario(String idUsuario, String nombre, String contrasena, String correo) {
        this.idUsuario = Objects.requireNonNull(idUsuario, "El identificador de usuario no puede ser nulo");
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.contrasena = Objects.requireNonNull(contrasena, "La contraseña no puede ser nula");
        this.correo = Objects.requireNonNull(correo, "El correo no puede ser nulo");
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = Objects.requireNonNull(contrasena, "La contraseña no puede ser nula");
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = Objects.requireNonNull(correo, "El correo no puede ser nulo");
    }

    public boolean isSesionActiva() {
        return sesionActiva;
    }

    public boolean iniciarSesion(String usuario, String contrasena) {
        boolean credencialesValidas = this.idUsuario.equals(usuario) && this.contrasena.equals(contrasena);
        this.sesionActiva = credencialesValidas;
        return credencialesValidas;
    }

    public void cerrarSesion() {
        this.sesionActiva = false;
    }
}
