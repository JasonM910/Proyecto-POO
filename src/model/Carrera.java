package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * La clase Carrera representa una competencia deportiva con categorías, inscripciones y evento asociado.
 * Permite gestionar la inscripción de corredores, la apertura/cierre de inscripciones y la administración de categorías.
 */
public class Carrera {
    private final String idCarrera;
    private final String nombre;
    private double distanciaKm;
    private LocalDate fecha;
    private boolean inscripcionAbierta;
    private Evento evento;
    private final List<Categoria> categorias = new ArrayList<>();
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    /**
     * Crea una nueva carrera con los datos proporcionados.
     * @param idCarrera Identificador único de la carrera.
     * @param nombre Nombre de la carrera.
     * @param distanciaKm Distancia en kilómetros.
     * @param fecha Fecha de realización de la carrera.
     * @throws NullPointerException si algún parámetro es nulo.
     * @throws IllegalArgumentException si la distancia es negativa o cero.
     */
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

    /**
     * Obtiene el identificador único de la carrera.
     * @return idCarrera
     */
    public String getIdCarrera() {
        return idCarrera;
    }

    /**
     * Obtiene el nombre de la carrera.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la distancia de la carrera en kilómetros.
     * @return distanciaKm
     */
    public double getDistanciaKm() {
        return distanciaKm;
    }

    /**
     * Establece la distancia de la carrera en kilómetros.
     * @param distanciaKm Nueva distancia.
     * @throws IllegalArgumentException si la distancia es negativa o cero.
     */
    public void setDistanciaKm(double distanciaKm) {
        if (distanciaKm <= 0) {
            throw new IllegalArgumentException("La distancia debe ser positiva");
        }
        this.distanciaKm = distanciaKm;
    }

    /**
     * Obtiene la fecha de realización de la carrera.
     * @return fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de realización de la carrera.
     * @param fecha Nueva fecha.
     * @throws NullPointerException si la fecha es nula.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = Objects.requireNonNull(fecha, "La fecha de la carrera no puede ser nula");
    }

    /**
     * Indica si la inscripción está abierta.
     * @return true si está abierta, false si está cerrada.
     */
    public boolean isInscripcionAbierta() {
        return inscripcionAbierta;
    }

    /**
     * Obtiene el evento asociado a la carrera.
     * @return evento
     */
    public Evento getEvento() {
        return evento;
    }

    void definirEvento(Evento evento) {
        this.evento = Objects.requireNonNull(evento, "El evento de la carrera no puede ser nulo");
    }

    /**
     * Abre la inscripción para la carrera.
     */
    public void abrirInscripcion() {
        this.inscripcionAbierta = true;
    }

    /**
     * Cierra la inscripción para la carrera.
     */
    public void cerrarInscripcion() {
        this.inscripcionAbierta = false;
    }

    /**
     * Agrega una categoría a la carrera.
     * @param categoria Categoría a agregar.
     * @throws NullPointerException si la categoría es nula.
     */
    public void agregarCategoria(Categoria categoria) {
        categorias.add(Objects.requireNonNull(categoria, "La categoria no puede ser nula"));
    }

    /**
     * Obtiene la lista de categorías de la carrera.
     * @return Lista inmodificable de categorías.
     */
    public List<Categoria> getCategorias() {
        return Collections.unmodifiableList(categorias);
    }

    /**
     * Obtiene la lista de inscripciones de la carrera.
     * @return Lista inmodificable de inscripciones.
     */
    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    /**
     * Crea una inscripción para un corredor en una categoría seleccionada.
     * @param corredor Corredor a inscribir.
     * @param categoriaSeleccionada Categoría elegida por el corredor.
     * @return La inscripción creada.
     * @throws IllegalStateException si la inscripción no está abierta.
     * @throws NullPointerException si el corredor es nulo.
     * @throws IllegalArgumentException si la categoría no pertenece a la carrera.
     */
    public Inscripcion crearInscripcionPara(Corredor corredor, Categoria categoriaSeleccionada) {
        if (!inscripcionAbierta) {
            throw new IllegalStateException("La inscripcion para esta carrera no esta abierta");
        }
        Objects.requireNonNull(corredor, "El corredor no puede ser nulo");
        Categoria categoria = categorias.stream()
                .filter(cat -> cat.getNombre().equalsIgnoreCase(categoriaSeleccionada.getNombre()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("La categoria seleccionada no pertenece a la carrera"));
        String idInscripcion = String.format("INS-%s-%03d", idCarrera, inscripciones.size() + 1);
        int numeroDorsal = inscripciones.size() + 100;
        Inscripcion inscripcion = new Inscripcion(idInscripcion, numeroDorsal, corredor, this, categoria);
        inscripciones.add(inscripcion);
        return inscripcion;
    }
}
