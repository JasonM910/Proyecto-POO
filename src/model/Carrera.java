package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Carrera {
    private final String idCarrera;
    private final String nombre;
    private double distanciaKm;
    private LocalDate fecha;
    private boolean inscripcionAbierta;
    private Evento evento;
    private final List<Categoria> categorias = new ArrayList<>();
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    public Carrera(String idCarrera, String nombre, double distanciaKm, LocalDate fecha) {
        this.idCarrera = Objects.requireNonNull(idCarrera, "El identificador de la carrera no puede ser nulo");
        this.nombre = Objects.requireNonNull(nombre, "El nombre de la carrera no puede ser nulo");
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia debe ser positiva");
        }
        this.distanciaKm = distanciaKm;
        this.fecha = Objects.requireNonNull(fecha, "La fecha de la carrera no puede ser nula");
        this.inscripcionAbierta = false;
    }

    public String getIdCarrera() {
        return idCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia debe ser positiva");
        }
        this.distanciaKm = distanciaKm;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = Objects.requireNonNull(fecha, "La fecha de la carrera no puede ser nula");
    }

    public boolean isInscripcionAbierta() {
        return inscripcionAbierta;
    }

    public Evento getEvento() {
        return evento;
    }

    void definirEvento(Evento evento) {
        this.evento = Objects.requireNonNull(evento, "El evento de la carrera no puede ser nulo");
    }

    public void abrirInscripcion() {
        this.inscripcionAbierta = true;
    }

    public void cerrarInscripcion() {
        this.inscripcionAbierta = false;
    }

    public void agregarCategoria(Categoria categoria) {
        categorias.add(Objects.requireNonNull(categoria, "La categoria no puede ser nula"));
    }

    public List<Categoria> getCategorias() {
        return Collections.unmodifiableList(categorias);
    }

    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    public Inscripcion crearInscripcionPara(Corredor corredor) {
        if (!inscripcionAbierta) {
            throw new IllegalStateException("La inscripcion para esta carrera no esta abierta");
        }
        Objects.requireNonNull(corredor, "El corredor no puede ser nulo");
        String idInscripcion = String.format("INS-%s-%03d", idCarrera, inscripciones.size() + 1);
        int numeroDorsal = inscripciones.size() + 100;
        Inscripcion inscripcion = new Inscripcion(idInscripcion, numeroDorsal, corredor, this);
        inscripciones.add(inscripcion);
        return inscripcion;
    }
}
