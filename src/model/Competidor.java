package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Competidor extends Usuario {
    private final String numeroCompetidor;
    private Categoria categoria;
    private Resultados resultados;
    private ContactoEmergencia contactoEmergencia;
    private final List<Mensaje> mensajes = new ArrayList<>();

    public Competidor(String idUsuario, String nombre, String contrasena, String correo, String numeroCompetidor, Categoria categoria) {
        super(idUsuario, nombre, contrasena, correo);
        this.numeroCompetidor = Objects.requireNonNull(numeroCompetidor, "El número de competidor no puede ser nulo");
        this.categoria = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
    }

    public String getNumeroCompetidor() {
        return numeroCompetidor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
    }

    public Resultados getResultados() {
        return resultados;
    }

    public void setResultados(Resultados resultados) {
        this.resultados = resultados;
    }

    public ContactoEmergencia getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(ContactoEmergencia contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public List<Mensaje> getMensajes() {
        return new ArrayList<>(mensajes);
    }

    public void agregarMensaje(Mensaje mensaje) {
        mensajes.add(Objects.requireNonNull(mensaje, "El mensaje no puede ser nulo"));
    }

    public Inscripcion inscribirse(Evento evento, Categoria categoriaSeleccionada) {
        Objects.requireNonNull(evento, "El evento no puede ser nulo");
        Objects.requireNonNull(categoriaSeleccionada, "La categoría seleccionada no puede ser nula");
        Inscripcion inscripcion = new Inscripcion("INS-" + numeroCompetidor + "-" + evento.getIdEvento(), this, categoriaSeleccionada, evento);
        return inscripcion;
    }
}
