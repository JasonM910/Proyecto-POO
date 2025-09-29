package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Corredor extends Usuario {
    private final String idCorredor;
    private String nombreCompleto;
    private String telefono;
    private LocalDate fechaNacimiento;
    private Genero genero;
    private TipoSangre tipoSangre;
    private final List<ContactoEmergencia> contactos = new ArrayList<>();
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    public Corredor(String idUsuario, String correo, String contrasena, String idCorredor, String nombreCompleto,
                    String telefono, LocalDate fechaNacimiento, Genero genero, TipoSangre tipoSangre) {
        super(idUsuario, correo, contrasena);
        this.idCorredor = Objects.requireNonNull(idCorredor, "El id del corredor no puede ser nulo");
        this.nombreCompleto = Objects.requireNonNull(nombreCompleto, "El nombre completo no puede ser nulo");
        this.telefono = Objects.requireNonNull(telefono, "El telefono no puede ser nulo");
        this.fechaNacimiento = Objects.requireNonNull(fechaNacimiento, "La fecha de nacimiento no puede ser nula");
        this.genero = Objects.requireNonNull(genero, "El genero no puede ser nulo");
        this.tipoSangre = Objects.requireNonNull(tipoSangre, "El tipo de sangre no puede ser nulo");
    }

    public String getIdCorredor() {
        return idCorredor;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = Objects.requireNonNull(nombreCompleto, "El nombre completo no puede ser nulo");
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = Objects.requireNonNull(telefono, "El telefono no puede ser nulo");
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = Objects.requireNonNull(fechaNacimiento, "La fecha de nacimiento no puede ser nula");
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = Objects.requireNonNull(genero, "El genero no puede ser nulo");
    }

    public TipoSangre getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(TipoSangre tipoSangre) {
        this.tipoSangre = Objects.requireNonNull(tipoSangre, "El tipo de sangre no puede ser nulo");
    }

    public List<ContactoEmergencia> getContactos() {
        return Collections.unmodifiableList(contactos);
    }

    public void agregarContacto(ContactoEmergencia contacto) {
        contactos.add(Objects.requireNonNull(contacto, "El contacto no puede ser nulo"));
    }

    public void eliminarContacto(String telefonoContacto) {
        contactos.removeIf(contacto -> contacto.getTelefono().equals(telefonoContacto));
    }

    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    public Inscripcion registrarseCarrera(Carrera carrera) {
        Objects.requireNonNull(carrera, "La carrera no puede ser nula");
        Inscripcion inscripcion = carrera.crearInscripcionPara(this);
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    public List<Resultado> verMisResultados() {
        return Collections.unmodifiableList(
                inscripciones.stream()
                        .map(Inscripcion::getResultado)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
    }
}
