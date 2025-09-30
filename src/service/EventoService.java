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
    private final Map<String, Evento> eventos = new HashMap<>();

    public Evento registrarEvento(Evento evento) {
        Objects.requireNonNull(evento, "El evento no puede ser nulo");
        eventos.put(evento.getIdEvento(), evento);
        return evento;
    }

    public Optional<Evento> buscarPorId(String idEvento) {
        return Optional.ofNullable(eventos.get(idEvento));
    }

    public List<Evento> listarEventos() {
        return Collections.unmodifiableList(new ArrayList<>(eventos.values()));
    }

    public List<Evento> listarPorEstado(EstadoEvento estado) {
        Objects.requireNonNull(estado, "El estado no puede ser nulo");
        return eventos.values().stream()
                .filter(evento -> evento.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public void actualizarEstado(String idEvento, EstadoEvento nuevoEstado) {
        Evento evento = eventos.get(idEvento);
        if (evento == null) {
            throw new IllegalArgumentException("Evento no encontrado: " + idEvento);
        }
        evento.actualizarEstado(nuevoEstado);
    }

    public void agregarCarrera(String idEvento, Carrera carrera) {
        Evento evento = eventos.get(idEvento);
        if (evento == null) {
            throw new IllegalArgumentException("Evento no encontrado: " + idEvento);
        }
        evento.agregarCarrera(carrera);
    }
}
