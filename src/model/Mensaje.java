package model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * La clase Mensaje representa un mensaje enviado entre usuarios, con información de contenido, remitente, destinatario y fecha de envío.
 * Permite distinguir entre mensajes generales y mensajes dirigidos a un destinatario específico.
 */
public class Mensaje {
    private final String idMensaje;
    private String contenido;
    private LocalDateTime enviadoEn;
    private final Usuario remitente;
    private final Usuario destinatario;
    private final boolean general;

    /**
     * Crea un nuevo mensaje entre usuarios.
     * @param idMensaje Identificador único del mensaje.
     * @param contenido Contenido del mensaje.
     * @param enviadoEn Fecha y hora de envío.
     * @param remitente Usuario que envía el mensaje.
     * @param destinatario Usuario destinatario (puede ser nulo si es general).
     * @param general Indica si el mensaje es general.
     * @throws NullPointerException si algún parámetro obligatorio es nulo.
     */
    public Mensaje(String idMensaje, String contenido, LocalDateTime enviadoEn, Usuario remitente, Usuario destinatario,
                   boolean general) {
        this.idMensaje = Objects.requireNonNull(idMensaje, "El identificador del mensaje no puede ser nulo");
        this.contenido = Objects.requireNonNull(contenido, "El contenido no puede ser nulo");
        this.enviadoEn = Objects.requireNonNull(enviadoEn, "La fecha de envio no puede ser nula");
        this.remitente = Objects.requireNonNull(remitente, "El remitente no puede ser nulo");
        this.general = general;
        if (general) {
            this.destinatario = destinatario;
        } else {
            this.destinatario = Objects.requireNonNull(destinatario, "El destinatario no puede ser nulo");
        }
    }

    /**
     * Obtiene el identificador único del mensaje.
     * @return idMensaje
     */
    public String getIdMensaje() {
        return idMensaje;
    }

    /**
     * Obtiene el contenido del mensaje.
     * @return contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Establece el contenido del mensaje.
     * @param contenido Nuevo contenido.
     * @throws NullPointerException si el contenido es nulo.
     */
    public void setContenido(String contenido) {
        this.contenido = Objects.requireNonNull(contenido, "El contenido no puede ser nulo");
    }

    /**
     * Obtiene la fecha y hora en que se envió el mensaje.
     * @return fecha y hora de envío
     */
    public LocalDateTime getEnviadoEn() {
        return enviadoEn;
    }

    /**
     * Establece la fecha y hora de envío del mensaje.
     * @param enviadoEn Nueva fecha y hora de envío.
     * @throws NullPointerException si la fecha es nula.
     */
    public void setEnviadoEn(LocalDateTime enviadoEn) {
        this.enviadoEn = Objects.requireNonNull(enviadoEn, "La fecha de envio no puede ser nula");
    }

    /**
     * Obtiene el usuario que envió el mensaje.
     * @return remitente
     */
    public Usuario getRemitente() {
        return remitente;
    }

    /**
     * Obtiene el usuario destinatario del mensaje.
     * @return destinatario
     */
    public Usuario getDestinatario() {
        return destinatario;
    }

    /**
     * Indica si el mensaje es general (no dirigido a un destinatario específico).
     * @return true si es general, false si es dirigido.
     */
    public boolean esGeneral() {
        return general;
    }
}
