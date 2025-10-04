package model;

import java.util.Objects;

/**
 * La clase Multimedia representa un recurso multimedia asociado a un evento, como imágenes, videos o documentos.
 * Permite gestionar el tipo, la URL y la descripción del recurso.
 */
public class Multimedia {
    private final String idMultimedia;
    private TipoMultimedia tipo;
    private String url;
    private String descripcion;

    /**
     * Crea un nuevo recurso multimedia.
     * @param idMultimedia Identificador único del recurso.
     * @param tipo Tipo de multimedia.
     * @param url URL del recurso.
     * @param descripcion Descripción del recurso.
     * @throws NullPointerException si algún parámetro es nulo.
     */
    public Multimedia(String idMultimedia, TipoMultimedia tipo, String url, String descripcion) {
        this.idMultimedia = Objects.requireNonNull(idMultimedia, "El identificador del recurso no puede ser nulo");
        this.tipo = Objects.requireNonNull(tipo, "El tipo de multimedia no puede ser nulo");
        this.url = Objects.requireNonNull(url, "La URL no puede ser nula");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }

    /**
     * Obtiene el identificador único del recurso multimedia.
     * @return idMultimedia
     */
    public String getIdMultimedia() {
        return idMultimedia;
    }

    /**
     * Obtiene el tipo de multimedia del recurso.
     * @return tipo de multimedia
     */
    public TipoMultimedia getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de multimedia del recurso.
     * @param tipo Nuevo tipo de multimedia.
     * @throws NullPointerException si el tipo es nulo.
     */
    public void setTipo(TipoMultimedia tipo) {
        this.tipo = Objects.requireNonNull(tipo, "El tipo de multimedia no puede ser nulo");
    }

    /**
     * Obtiene la URL del recurso multimedia.
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL del recurso multimedia.
     * @param url Nueva URL.
     * @throws NullPointerException si la URL es nula.
     */
    public void setUrl(String url) {
        this.url = Objects.requireNonNull(url, "La URL no puede ser nula");
    }

    /**
     * Obtiene la descripción del recurso multimedia.
     * @return descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del recurso multimedia.
     * @param descripcion Nueva descripción.
     * @throws NullPointerException si la descripción es nula.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }
}
