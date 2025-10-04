package service;

import model.Mensaje;
import model.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ComunicacionService {
    // Listas para almacenar los mensajes
    private final List<Mensaje> chatGeneral = new ArrayList<>();
    private final List<Mensaje> mensajesPrivados = new ArrayList<>();

    /**
     * Publica un mensaje en el chat general y lo envía a los destinatarios especificados.
     *
     * @param idMensaje   El ID del mensaje.
     * @param contenido   El contenido del mensaje.
     * @param remitente   El usuario que envía el mensaje.
     * @param destinatarios Una lista de usuarios que recibirán el mensaje.
     * @return El mensaje publicado.
     * @throws NullPointerException Si el remitente, contenido o destinatarios son nulos.
     */
    public Mensaje publicarMensajeGeneral(String idMensaje, String contenido, Usuario remitente,
                                          Iterable<? extends Usuario> destinatarios) {
        Objects.requireNonNull(remitente, "El remitente no puede ser nulo");
        Objects.requireNonNull(contenido, "El contenido no puede ser nulo");
        Objects.requireNonNull(destinatarios, "Los destinatarios no pueden ser nulos");
        Mensaje mensaje = new Mensaje(idMensaje, contenido, LocalDateTime.now(), remitente, null, true);
        chatGeneral.add(mensaje);
        Set<Usuario> destinatariosUnicos = new LinkedHashSet<>();
        destinatariosUnicos.add(remitente);
        for (Usuario destinatario : destinatarios) {
            if (destinatario != null) {
                destinatariosUnicos.add(destinatario);
            }
        }
        for (Usuario usuario : destinatariosUnicos) {
            usuario.registrarMensaje(mensaje);
        }
        return mensaje;
    }

    /**
     * Envía un mensaje privado de un remitente a un destinatario.
     *
     * @param idMensaje  El ID del mensaje.
     * @param contenido  El contenido del mensaje.
     * @param remitente  El usuario que envía el mensaje.
     * @param destinatario El usuario que recibe el mensaje.
     * @return El mensaje enviado.
     * @throws NullPointerException Si el remitente o destinatario son nulos.
     */
    public Mensaje enviarMensajePrivado(String idMensaje, String contenido, Usuario remitente, Usuario destinatario) {
        Objects.requireNonNull(remitente, "El remitente no puede ser nulo");
        Objects.requireNonNull(destinatario, "El destinatario no puede ser nulo");
        Mensaje mensaje = new Mensaje(idMensaje, contenido, LocalDateTime.now(), remitente, destinatario, false);
        mensajesPrivados.add(mensaje);
        remitente.registrarMensaje(mensaje);
        destinatario.registrarMensaje(mensaje);
        return mensaje;
    }

    /**
     * Obtiene todos los mensajes del chat general.
     *
     * @return Una lista inmodificable de los mensajes del chat general.
     */
    public List<Mensaje> obtenerChatGeneral() {
        return Collections.unmodifiableList(chatGeneral);
    }

    /**
     * Obtiene todos los mensajes enviados o recibidos por un usuario específico.
     *
     * @param usuario El usuario cuyos mensajes se desean obtener.
     * @return Una lista de mensajes relacionados con el usuario.
     * @throws NullPointerException Si el usuario es nulo.
     */
    public List<Mensaje> obtenerMensajesDeUsuario(Usuario usuario) {
        Objects.requireNonNull(usuario, "El usuario no puede ser nulo");
        return mensajesPrivados.stream()
                .filter(mensaje -> usuario.equals(mensaje.getRemitente()) || usuario.equals(mensaje.getDestinatario()))
                .collect(Collectors.toList());
    }
}
