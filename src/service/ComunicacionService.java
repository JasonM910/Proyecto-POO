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
    private final List<Mensaje> chatGeneral = new ArrayList<>();
    private final List<Mensaje> mensajesPrivados = new ArrayList<>();

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

    public Mensaje enviarMensajePrivado(String idMensaje, String contenido, Usuario remitente, Usuario destinatario) {
        Objects.requireNonNull(remitente, "El remitente no puede ser nulo");
        Objects.requireNonNull(destinatario, "El destinatario no puede ser nulo");
        Mensaje mensaje = new Mensaje(idMensaje, contenido, LocalDateTime.now(), remitente, destinatario, false);
        mensajesPrivados.add(mensaje);
        remitente.registrarMensaje(mensaje);
        destinatario.registrarMensaje(mensaje);
        return mensaje;
    }

    public List<Mensaje> obtenerChatGeneral() {
        return Collections.unmodifiableList(chatGeneral);
    }

    public List<Mensaje> obtenerMensajesDeUsuario(Usuario usuario) {
        Objects.requireNonNull(usuario, "El usuario no puede ser nulo");
        return mensajesPrivados.stream()
                .filter(mensaje -> usuario.equals(mensaje.getRemitente()) || usuario.equals(mensaje.getDestinatario()))
                .collect(Collectors.toList());
    }
}
