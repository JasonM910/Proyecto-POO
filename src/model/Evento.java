package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * La clase Evento representa un evento deportivo que puede contener varias carreras y recursos multimedia.
 * Permite gestionar la información general del evento, su estado, carreras asociadas y recursos multimedia.
 */
public class Evento {
    private final String idEvento;
    private String nombre;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;
    private EstadoEvento estado;
    private String ubicacion;
    private TipoActividad tipoActividad;
    private final List<Carrera> carreras = new ArrayList<>();
    private final List<Multimedia> multimedia = new ArrayList<>();

    /**
     * Crea un nuevo evento deportivo con los datos proporcionados.
     * @param idEvento Identificador único del evento.
     * @param nombre Nombre del evento.
     * @param fecha Fecha de realización.
     * @param hora Hora de inicio.
     * @param descripcion Descripción del evento.
     * @param ubicacion Ubicación del evento.
     * @param tipoActividad Tipo de actividad del evento.
     * @throws NullPointerException si algún parámetro es nulo.
     */
    public Evento(String idEvento, String nombre, LocalDate fecha, LocalTime hora, String descripcion,
                  String ubicacion, TipoActividad tipoActividad) {
        this.idEvento = Objects.requireNonNull(idEvento, "El identificador del evento no puede ser nulo");
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
        this.hora = Objects.requireNonNull(hora, "La hora no puede ser nula");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.ubicacion = Objects.requireNonNull(ubicacion, "La ubicacion no puede ser nula");
        this.tipoActividad = Objects.requireNonNull(tipoActividad, "El tipo de actividad no puede ser nulo");
        this.estado = EstadoEvento.Programada;
    }

    /**
     * Obtiene el identificador único del evento.
     * @return idEvento
     */
    public String getIdEvento() {
        return idEvento;
    }

    /**
     * Obtiene el nombre del evento.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del evento.
     * @param nombre Nuevo nombre.
     * @throws NullPointerException si el nombre es nulo.
     */
    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
    }

    /**
     * Obtiene la fecha de realización del evento.
     * @return fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de realización del evento.
     * @param fecha Nueva fecha.
     * @throws NullPointerException si la fecha es nula.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
    }

    /**
     * Obtiene la hora de inicio del evento.
     * @return hora
     */
    public LocalTime getHora() {
        return hora;
    }

    /**
     * Establece la hora de inicio del evento.
     * @param hora Nueva hora.
     * @throws NullPointerException si la hora es nula.
     */
    public void setHora(LocalTime hora) {
        this.hora = Objects.requireNonNull(hora, "La hora no puede ser nula");
    }

    /**
     * Obtiene la descripción del evento.
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del evento.
     * @param descripcion Nueva descripción.
     * @throws NullPointerException si la descripción es nula.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }

    /**
     * Obtiene el estado actual del evento.
     * @return estado
     */
    public EstadoEvento getEstado() {
        return estado;
    }

    /**
     * Actualiza el estado del evento.
     * @param nuevoEstado Nuevo estado.
     * @throws NullPointerException si el estado es nulo.
     */
    public void actualizarEstado(EstadoEvento nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado, "El estado no puede ser nulo");
    }

    /**
     * Obtiene la ubicación del evento.
     * @return ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Establece la ubicación del evento.
     * @param ubicacion Nueva ubicación.
     * @throws NullPointerException si la ubicación es nula.
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = Objects.requireNonNull(ubicacion, "La ubicacion no puede ser nula");
    }

    /**
     * Obtiene el tipo de actividad del evento.
     * @return tipoActividad
     */
    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    /**
     * Establece el tipo de actividad del evento.
     * @param tipoActividad Nuevo tipo de actividad.
     * @throws NullPointerException si el tipo de actividad es nulo.
     */
    public void setTipoActividad(TipoActividad tipoActividad) {
        this.tipoActividad = Objects.requireNonNull(tipoActividad, "El tipo de actividad no puede ser nulo");
    }

    /**
     * Obtiene la lista de carreras asociadas al evento.
     * @return Lista inmodificable de carreras.
     */
    public List<Carrera> getCarreras() {
        return Collections.unmodifiableList(carreras);
    }

    /**
     * Agrega una carrera al evento.
     * @param carrera Carrera a agregar.
     * @throws NullPointerException si la carrera es nula.
     */
    public void agregarCarrera(Carrera carrera) {
        Objects.requireNonNull(carrera, "La carrera no puede ser nula");
        carrera.definirEvento(this);
        carreras.add(carrera);
    }

    /**
     * Obtiene la lista de recursos multimedia asociados al evento.
     * @return Lista inmodificable de recursos multimedia.
     */
    public List<Multimedia> getMultimedia() {
        return Collections.unmodifiableList(multimedia);
    }

    /**
     * Agrega un recurso multimedia al evento.
     * @param recurso Recurso multimedia a agregar.
     * @throws NullPointerException si el recurso es nulo.
     */
    public void agregarMultimedia(Multimedia recurso) {
        multimedia.add(Objects.requireNonNull(recurso, "El recurso multimedia no puede ser nulo"));
    }
}
