package model;

import java.util.Objects;

public class Inscripcion {
    private final String idInscripcion;
    private String estado;
    private final Competidor competidor;
    private final Categoria categoria;
    private final Evento evento;
    private Pago pago;

    public Inscripcion(String idInscripcion, Competidor competidor, Categoria categoria, Evento evento) {
        this.idInscripcion = Objects.requireNonNull(idInscripcion, "El identificador de la inscripción no puede ser nulo");
        this.competidor = Objects.requireNonNull(competidor, "El competidor no puede ser nulo");
        this.categoria = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
        this.evento = Objects.requireNonNull(evento, "El evento no puede ser nulo");
        this.estado = "pendiente";
    }

    public String getIdInscripcion() {
        return idInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo");
    }

    public Competidor getCompetidor() {
        return competidor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Evento getEvento() {
        return evento;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public boolean confirmarInscripcion() {
        if (pago != null && pago.confirmarPago()) {
            estado = "confirmada";
            return true;
        }
        return false;
    }
}
