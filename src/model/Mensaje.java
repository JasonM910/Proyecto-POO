package model;

import java.util.Objects;

public class Mensaje {
    private final String idMensaje;
    private String contenido;
    private String fecha;
    private String estado;
    private final Usuario remitente;
    private final Usuario destinatario;

    public Mensaje(String idMensaje, String contenido, String fecha, Usuario remitente, Usuario destinatario) {
        this.idMensaje = Objects.requireNonNull(idMensaje, "El identificador del mensaje no puede ser nulo");
        this.contenido = Objects.requireNonNull(contenido, "El contenido no puede ser nulo");
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
        this.remitente = Objects.requireNonNull(remitente, "El remitente no puede ser nulo");
        this.destinatario = Objects.requireNonNull(destinatario, "El destinatario no puede ser nulo");
        this.estado = "borrador";
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
    }

    public String getEstado() {
        return estado;
    }

    public Usuario getRemitente() {
        return remitente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void enviar() {
        this.estado = "enviado";
    }

    public boolean eliminar() {
        this.estado = "eliminado";
        return true;
    }
}
