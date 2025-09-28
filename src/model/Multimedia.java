package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Multimedia {
    private final String idMultimedia;
    private String tipo;
    private String url;
    private String descripcion;
    private final List<String> comentarios = new ArrayList<>();

    public Multimedia(String idMultimedia, String tipo, String url, String descripcion) {
        this.idMultimedia = Objects.requireNonNull(idMultimedia, "El identificador del recurso multimedia no puede ser nulo");
        this.tipo = Objects.requireNonNull(tipo, "El tipo no puede ser nulo");
        this.url = Objects.requireNonNull(url, "La URL no puede ser nula");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripción no puede ser nula");
    }

    public String getIdMultimedia() {
        return idMultimedia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = Objects.requireNonNull(tipo, "El tipo no puede ser nulo");
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
        this.descripcion = Objects.requireNonNull(descripcion, "La descripción no puede ser nula");
    }

    public List<String> getComentarios() {
        return List.copyOf(comentarios);
    }

    public void agregarComentarios(String comentario) {
        comentarios.add(Objects.requireNonNull(comentario, "El comentario no puede ser nulo"));
    }
}
