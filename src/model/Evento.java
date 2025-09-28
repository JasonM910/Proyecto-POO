package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Evento {
    private final String idEvento;
    private String nombre;
    private String fecha;
    private String ubicacion;
    private String descripcion;
    private String estado;
    private String tipo;
    private final List<Carrera> carreras = new ArrayList<>();
    private final List<Multimedia> multimedia = new ArrayList<>();

    public Evento(String idEvento, String nombre, String fecha, String ubicacion, String descripcion, String estado, String tipo) {
        this.idEvento = Objects.requireNonNull(idEvento, "El identificador del evento no puede ser nulo");
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
        this.ubicacion = Objects.requireNonNull(ubicacion, "La ubicación no puede ser nula");
        this.descripcion = Objects.requireNonNull(descripcion, "La descripción no puede ser nula");
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo");
        this.tipo = Objects.requireNonNull(tipo, "El tipo no puede ser nulo");
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = Objects.requireNonNull(ubicacion, "La ubicación no puede ser nula");
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = Objects.requireNonNull(descripcion, "La descripción no puede ser nula");
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo");
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = Objects.requireNonNull(tipo, "El tipo no puede ser nulo");
    }

    public List<Carrera> getCarreras() {
        return Collections.unmodifiableList(carreras);
    }

    public void agregarCarrera(Carrera carrera) {
        carreras.add(Objects.requireNonNull(carrera, "La carrera no puede ser nula"));
    }

    public List<Multimedia> getMultimedia() {
        return Collections.unmodifiableList(multimedia);
    }

    public void agregarMultimedia(Multimedia recurso) {
        multimedia.add(Objects.requireNonNull(recurso, "El recurso multimedia no puede ser nulo"));
    }
}
