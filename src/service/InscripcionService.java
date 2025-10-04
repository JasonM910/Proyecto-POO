package service;

import model.Administrador;
import model.Carrera;
import model.Categoria;
import model.Corredor;
import model.Inscripcion;
import model.Pago;
import model.TallaCamiseta;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class InscripcionService {
    // Mapa para almacenar las inscripciones por su ID
    private final Map<String, Inscripcion> inscripciones = new HashMap<>();

    /**
     * Registra una nueva inscripción para un corredor en una carrera específica.
     *
     * @param carrera La carrera en la que se inscribe el corredor.
     * @param corredor El corredor que se inscribe.
     * @param categoria La categoría en la que se inscribe el corredor.
     * @param talla La talla de camiseta del corredor.
     * @param administrador El administrador que gestiona la inscripción.
     * @return La inscripción registrada.
     * @throws NullPointerException Si alguno de los parámetros es nulo.
     */
    public Inscripcion registrarInscripcion(Carrera carrera, Corredor corredor, Categoria categoria,
                                            TallaCamiseta talla, Administrador administrador) {
        Objects.requireNonNull(carrera, "La carrera no puede ser nula");
        Objects.requireNonNull(corredor, "El corredor no puede ser nulo");
        Objects.requireNonNull(categoria, "La categoria no puede ser nula");
        Objects.requireNonNull(talla, "La talla no puede ser nula");
        Objects.requireNonNull(administrador, "El administrador no puede ser nulo");

        Inscripcion inscripcion = corredor.registrarseCarrera(carrera, categoria);
        inscripcion.asignarTallaCamiseta(talla);
        inscripciones.put(inscripcion.getIdInscripcion(), inscripcion);
        administrador.registrarInscripcionGestionada(inscripcion);
        return inscripcion;
    }

    /**
     * Registra un pago para una inscripción específica.
     *
     * @param idInscripcion El ID de la inscripción.
     * @param pago El pago a registrar.
     * @throws IllegalArgumentException Si la inscripción no se encuentra.
     * @throws IllegalStateException Si la inscripción ya tiene un pago registrado.
     */
    public void registrarPago(String idInscripcion, Pago pago) {
        Inscripcion inscripcion = obtenerInscripcion(idInscripcion);
        boolean pagoRegistrado = inscripcion.pagar(pago);
        if (!pagoRegistrado) {
            throw new IllegalStateException("La inscripcion ya cuenta con un pago");
        }
    }

    /**
     * Confirma una inscripción específica.
     *
     * @param idInscripcion El ID de la inscripción a confirmar.
     * @throws IllegalArgumentException Si la inscripción no se encuentra.
     */
    public void confirmarInscripcion(String idInscripcion) {
        Inscripcion inscripcion = obtenerInscripcion(idInscripcion);
        inscripcion.confirmar();
    }

    /**
     * Busca una inscripción por su ID.
     *
     * @param idInscripcion El ID de la inscripción a buscar.
     * @return Una Optional que contiene la inscripción si se encuentra, o vacía si no.
     */
    public Optional<Inscripcion> buscarPorId(String idInscripcion) {
        return Optional.ofNullable(inscripciones.get(idInscripcion));
    }

    /**
     * Lista todas las inscripciones registradas.
     *
     * @return Una lista inmodificable de todas las inscripciones.
     */
    public List<Inscripcion> listarInscripciones() {
        return Collections.unmodifiableList(
                inscripciones.values().stream().collect(Collectors.toList())
        );
    }

    // Método privado para obtener una inscripción y manejar el caso de no encontrada
    private Inscripcion obtenerInscripcion(String idInscripcion) {
        Inscripcion inscripcion = inscripciones.get(idInscripcion);
        if (inscripcion == null) {
            throw new IllegalArgumentException("Inscripcion no encontrada: " + idInscripcion);
        }
        return inscripcion;
    }
}
