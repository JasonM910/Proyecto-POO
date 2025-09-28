package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Carrera {
    private final String idCarrera;
    private String nombre;
    private double distancia;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private Categoria categoria;
    private final List<Competidor> competidores = new ArrayList<>();

    public Carrera(String idCarrera, String nombre, double distancia, String fecha, String horaInicio, String horaFin, Categoria categoria) {
        this.idCarrera = Objects.requireNonNull(idCarrera, "El identificador de la carrera no puede ser nulo");
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
        this.distancia = distancia;
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
        this.horaInicio = Objects.requireNonNull(horaInicio, "La hora de inicio no puede ser nula");
        this.horaFin = Objects.requireNonNull(horaFin, "La hora de fin no puede ser nula");
        this.categoria = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
    }

    public String getIdCarrera() {
        return idCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        if (distancia <= 0) {
            throw new IllegalArgumentException("La distancia debe ser positiva");
        }
        this.distancia = distancia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = Objects.requireNonNull(horaInicio, "La hora de inicio no puede ser nula");
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = Objects.requireNonNull(horaFin, "La hora de fin no puede ser nula");
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
    }

    public List<Competidor> getCompetidores() {
        return Collections.unmodifiableList(competidores);
    }

    public void administrarCompetidores(Competidor competidor) {
        competidores.add(Objects.requireNonNull(competidor, "El competidor no puede ser nulo"));
    }
}
