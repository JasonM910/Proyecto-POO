package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Mensaje {
    private final String idMensaje;
    private String contenido;
    private LocalDateTime enviadoEn;
    private final Usuario remitente;
    private final Usuario destinatario;
    private final boolean general;

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

    public String getIdMensaje() {
        return idMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = Objects.requireNonNull(contenido, "El contenido no puede ser nulo");
    }

    public LocalDateTime getEnviadoEn() {
        return enviadoEn;
    }

    public void setEnviadoEn(LocalDateTime enviadoEn) {
        this.enviadoEn = Objects.requireNonNull(enviadoEn, "La fecha de envio no puede ser nula");
    }

    public Usuario getRemitente() {
        return remitente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public boolean esGeneral() {
        return general;
    }
}
