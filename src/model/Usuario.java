package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Clase abstracta Usuario que representa a un usuario del sistema, con credenciales, sesión y mensajes.
 * Proporciona métodos para gestionar la sesión, cambiar contraseña y registrar mensajes.
 */
public abstract class Usuario {
    private final String idUsuario;
    private final String correo;
    private String contrasena;
    private boolean sesionActiva;
    private final List<Mensaje> mensajes = new ArrayList<>();
    private final Set<String> mensajesRegistrados = new HashSet<>();

    /**
     * Crea un nuevo usuario con credenciales.
     * @param idUsuario Identificador único del usuario.
     * @param correo Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @throws NullPointerException si algún parámetro es nulo.
     */
    protected Usuario(String idUsuario, String correo, String contrasena) {
        this.idUsuario = Objects.requireNonNull(idUsuario, "El identificador de usuario no puede ser nulo");
        this.correo = Objects.requireNonNull(correo, "El correo no puede ser nulo");
        this.contrasena = Objects.requireNonNull(contrasena, "La contrasena no puede ser nula");
    }

    /**
     * Obtiene el identificador único del usuario.
     * @return idUsuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Inicia sesión si las credenciales son válidas.
     * @param correo Correo electrónico.
     * @param contrasena Contraseña.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean iniciarSesion(String correo, String contrasena) {
        boolean credencialesValidas = this.correo.equals(correo) && this.contrasena.equals(contrasena);
        this.sesionActiva = credencialesValidas;
        return credencialesValidas;
    }

    /**
     * Cierra la sesión del usuario.
     */
    public void cerrarSesion() {
        this.sesionActiva = false;
    }

    /**
     * Indica si la sesión del usuario está activa.
     * @return true si la sesión está activa, false en caso contrario.
     */
    public boolean isSesionActiva() {
        return sesionActiva;
    }

    /**
     * Cambia la contraseña del usuario si la anterior coincide.
     * @param contrasenaAnterior Contraseña actual.
     * @param nuevaContrasena Nueva contraseña.
     * @throws IllegalArgumentException si la contraseña anterior no coincide.
     * @throws NullPointerException si la nueva contraseña es nula.
     */
    public void cambiarContrasena(String contrasenaAnterior, String nuevaContrasena) {
        if (!Objects.equals(this.contrasena, contrasenaAnterior)) {
            throw new IllegalArgumentException("La contrasena anterior no coincide");
        }
        this.contrasena = Objects.requireNonNull(nuevaContrasena, "La contrasena no puede ser nula");
    }

    /**
     * Registra un mensaje recibido por el usuario si no es general y no ha sido registrado previamente.
     * @param mensaje Mensaje a registrar.
     * @throws NullPointerException si el mensaje es nulo.
     */
    public void registrarMensaje(Mensaje mensaje) {
        Mensaje mensajeNoNulo = Objects.requireNonNull(mensaje, "El mensaje no puede ser nulo");
        if (mensajeNoNulo.esGeneral()) {
            return;
        }
        if (mensajesRegistrados.add(mensajeNoNulo.getIdMensaje())) {
            mensajes.add(mensajeNoNulo);
        }
    }

    /**
     * Obtiene la lista de mensajes registrados por el usuario.
     * @return Lista inmodificable de mensajes.
     */
    public List<Mensaje> getMensajes() {
        return Collections.unmodifiableList(mensajes);
    }
}
