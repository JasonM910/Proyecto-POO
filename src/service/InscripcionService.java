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
    private final Map<String, Inscripcion> inscripciones = new HashMap<>();

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

    public void registrarPago(String idInscripcion, Pago pago) {
        Inscripcion inscripcion = obtenerInscripcion(idInscripcion);
        boolean pagoRegistrado = inscripcion.pagar(pago);
        if (!pagoRegistrado) {
            throw new IllegalStateException("La inscripcion ya cuenta con un pago");
        }
    }

    public void confirmarInscripcion(String idInscripcion) {
        Inscripcion inscripcion = obtenerInscripcion(idInscripcion);
        inscripcion.confirmar();
    }

    public Optional<Inscripcion> buscarPorId(String idInscripcion) {
        return Optional.ofNullable(inscripciones.get(idInscripcion));
    }

    public List<Inscripcion> listarInscripciones() {
        return Collections.unmodifiableList(
                inscripciones.values().stream().collect(Collectors.toList())
        );
    }

    private Inscripcion obtenerInscripcion(String idInscripcion) {
        Inscripcion inscripcion = inscripciones.get(idInscripcion);
        if (inscripcion == null) {
            throw new IllegalArgumentException("Inscripcion no encontrada: " + idInscripcion);
        }
        return inscripcion;
    }
}
