package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class Usuario {
    private final String idUsuario;
    private final String correo;
    private String contrasena;
    private boolean sesionActiva;
    private final List<Mensaje> mensajes = new ArrayList<>();
    private final Set<String> mensajesRegistrados = new HashSet<>();

    protected Usuario(String idUsuario, String correo, String contrasena) {
        this.idUsuario = Objects.requireNonNull(idUsuario, "El identificador de usuario no puede ser nulo");
        this.correo = Objects.requireNonNull(correo, "El correo no puede ser nulo");
        this.contrasena = Objects.requireNonNull(contrasena, "La contrasena no puede ser nula");
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        boolean credencialesValidas = this.correo.equals(correo) && this.contrasena.equals(contrasena);
        this.sesionActiva = credencialesValidas;
        return credencialesValidas;
    }

    public void cerrarSesion() {
        this.sesionActiva = false;
    }

    public boolean isSesionActiva() {
        return sesionActiva;
    }

    public void cambiarContrasena(String contrasenaAnterior, String nuevaContrasena) {
        if (!Objects.equals(this.contrasena, contrasenaAnterior)) {
            throw new IllegalArgumentException("La contrasena anterior no coincide");
        }
        this.contrasena = Objects.requireNonNull(nuevaContrasena, "La contrasena no puede ser nula");
    }

    public void registrarMensaje(Mensaje mensaje) {
        Mensaje mensajeNoNulo = Objects.requireNonNull(mensaje, "El mensaje no puede ser nulo");
        if (mensajeNoNulo.esGeneral()) {
            return;
        }
        if (mensajesRegistrados.add(mensajeNoNulo.getIdMensaje())) {
            mensajes.add(mensajeNoNulo);
        }
    }

    public List<Mensaje> getMensajes() {
        return Collections.unmodifiableList(mensajes);
    }
}
