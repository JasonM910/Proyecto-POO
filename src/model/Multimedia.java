package model;

import java.util.Objects;

public class Multimedia {
    private final String idMultimedia;
    private TipoMultimedia tipo;
    private String url;
    private String descripcion;

    public Multimedia(String idMultimedia, TipoMultimedia tipo, String url, String descripcion) {
        this.idMultimedia = Objects.requireNonNull(idMultimedia, "El identificador del recurso no puede ser nulo");
        this.tipo = Objects.requireNonNull(tipo, "El tipo de multimedia no puede ser nulo");
        this.url = Objects.requireNonNull(url, "La URL no puede ser nula");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }

    public String getIdMultimedia() {
        return idMultimedia;
    }

    public TipoMultimedia getTipo() {
        return tipo;
    }

    public void setTipo(TipoMultimedia tipo) {
        this.tipo = Objects.requireNonNull(tipo, "El tipo de multimedia no puede ser nulo");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = Objects.requireNonNull(url, "La URL no puede ser nula");
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }
}
