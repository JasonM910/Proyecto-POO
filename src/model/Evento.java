package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public String getIdEvento() {
        return idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = Objects.requireNonNull(hora, "La hora no puede ser nula");
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void actualizarEstado(EstadoEvento nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado, "El estado no puede ser nulo");
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = Objects.requireNonNull(ubicacion, "La ubicacion no puede ser nula");
    }

    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(TipoActividad tipoActividad) {
        this.tipoActividad = Objects.requireNonNull(tipoActividad, "El tipo de actividad no puede ser nulo");
    }

    public List<Carrera> getCarreras() {
        return Collections.unmodifiableList(carreras);
    }

    public void agregarCarrera(Carrera carrera) {
        Objects.requireNonNull(carrera, "La carrera no puede ser nula");
        carrera.definirEvento(this);
        carreras.add(carrera);
    }

    public List<Multimedia> getMultimedia() {
        return Collections.unmodifiableList(multimedia);
    }

    public void agregarMultimedia(Multimedia recurso) {
        multimedia.add(Objects.requireNonNull(recurso, "El recurso multimedia no puede ser nulo"));
    }
}
