package service;

import model.Carrera;
import model.Evento;
import model.EstadoEvento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventoService {
    // Mapa para almacenar los eventos por su ID
    private final Map<String, Evento> eventos = new HashMap<>();

    /**
     * Registra un nuevo evento.
     *
     * @param evento El evento a registrar.
     * @return El evento registrado.
     * @throws NullPointerException Si el evento es nulo.
     */
    public Evento registrarEvento(Evento evento) {
        Objects.requireNonNull(evento, "El evento no puede ser nulo");
        eventos.put(evento.getIdEvento(), evento);
        return evento;
    }

    /**
     * Busca un evento por su ID.
     *
     * @param idEvento El ID del evento a buscar.
     * @return Una Optional que contiene el evento si se encuentra, o vacía si no.
     */
    public Optional<Evento> buscarPorId(String idEvento) {
        return Optional.ofNullable(eventos.get(idEvento));
    }

    /**
     * Lista todos los eventos registrados.
     *
     * @return Una lista inmodificable de todos los eventos.
     */
    public List<Evento> listarEventos() {
        return Collections.unmodifiableList(new ArrayList<>(eventos.values()));
    }

    /**
     * Lista los eventos filtrados por su estado.
     *
     * @param estado El estado por el cual filtrar los eventos.
     * @return Una lista de eventos que coinciden con el estado especificado.
     * @throws NullPointerException Si el estado es nulo.
     */
    public List<Evento> listarPorEstado(EstadoEvento estado) {
        Objects.requireNonNull(estado, "El estado no puede ser nulo");
        return eventos.values().stream()
                .filter(evento -> evento.getEstado() == estado)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza el estado de un evento específico.
     *
     * @param idEvento   El ID del evento a actualizar.
     * @param nuevoEstado El nuevo estado del evento.
     * @throws IllegalArgumentException Si el evento no se encuentra.
     */
    public void actualizarEstado(String idEvento, EstadoEvento nuevoEstado) {
        Evento evento = eventos.get(idEvento);
        if (evento == null) {
            throw new IllegalArgumentException("Evento no encontrado: " + idEvento);
        }
        evento.actualizarEstado(nuevoEstado);
    }

    /**
     * Agrega una carrera a un evento específico.
     *
     * @param idEvento El ID del evento al que se agregará la carrera.
     * @param carrera  La carrera a agregar.
     * @throws IllegalArgumentException Si el evento no se encuentra.
     */
    public void agregarCarrera(String idEvento, Carrera carrera) {
        Evento evento = eventos.get(idEvento);
        if (evento == null) {
            throw new IllegalArgumentException("Evento no encontrado: " + idEvento);
        }
        evento.agregarCarrera(carrera);
    }
}
