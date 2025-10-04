package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * La clase Corredor representa a un participante en carreras, con datos personales, contactos de emergencia e inscripciones.
 * Permite gestionar la información del corredor, sus contactos y sus inscripciones en carreras.
 */
public class Corredor extends Usuario {
    private final String idCorredor;
    private String nombreCompleto;
    private String telefono;
    private LocalDate fechaNacimiento;
    private Genero genero;
    private TipoSangre tipoSangre;
    private final List<ContactoEmergencia> contactos = new ArrayList<>();
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    /**
     * Crea un nuevo corredor con los datos personales y médicos proporcionados.
     * @param idUsuario Identificador de usuario.
     * @param correo Correo electrónico.
     * @param contrasena Contraseña.
     * @param idCorredor Identificador único del corredor.
     * @param nombreCompleto Nombre completo del corredor.
     * @param telefono Teléfono del corredor.
     * @param fechaNacimiento Fecha de nacimiento.
     * @param genero Género del corredor.
     * @param tipoSangre Tipo de sangre del corredor.
     * @throws NullPointerException si algún parámetro es nulo.
     */
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

    /**
     * Obtiene el identificador único del corredor.
     * @return idCorredor
     */
    public String getIdCorredor() {
        return idCorredor;
    }

    /**
     * Obtiene el nombre completo del corredor.
     * @return nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo del corredor.
     * @param nombreCompleto Nuevo nombre completo.
     * @throws NullPointerException si el nombre es nulo.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = Objects.requireNonNull(nombreCompleto, "El nombre completo no puede ser nulo");
    }

    /**
     * Obtiene el teléfono del corredor.
     * @return teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del corredor.
     * @param telefono Nuevo teléfono.
     * @throws NullPointerException si el teléfono es nulo.
     */
    public void setTelefono(String telefono) {
        this.telefono = Objects.requireNonNull(telefono, "El telefono no puede ser nulo");
    }

    /**
     * Obtiene la fecha de nacimiento del corredor.
     * @return fechaNacimiento
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del corredor.
     * @param fechaNacimiento Nueva fecha de nacimiento.
     * @throws NullPointerException si la fecha es nula.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = Objects.requireNonNull(fechaNacimiento, "La fecha de nacimiento no puede ser nula");
    }

    /**
     * Obtiene el género del corredor.
     * @return género
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * Establece el género del corredor.
     * @param genero Nuevo género.
     * @throws NullPointerException si el género es nulo.
     */
    public void setGenero(Genero genero) {
        this.genero = Objects.requireNonNull(genero, "El genero no puede ser nulo");
    }

    /**
     * Obtiene el tipo de sangre del corredor.
     * @return tipoSangre
     */
    public TipoSangre getTipoSangre() {
        return tipoSangre;
    }

    /**
     * Establece el tipo de sangre del corredor.
     * @param tipoSangre Nuevo tipo de sangre.
     * @throws NullPointerException si el tipo de sangre es nulo.
     */
    public void setTipoSangre(TipoSangre tipoSangre) {
        this.tipoSangre = Objects.requireNonNull(tipoSangre, "El tipo de sangre no puede ser nulo");
    }

    /**
     * Obtiene la lista de contactos de emergencia del corredor.
     * @return Lista inmodificable de contactos.
     */
    public List<ContactoEmergencia> getContactos() {
        return Collections.unmodifiableList(contactos);
    }

    /**
     * Agrega un contacto de emergencia al corredor.
     * @param contacto Contacto a agregar.
     * @throws NullPointerException si el contacto es nulo.
     */
    public void agregarContacto(ContactoEmergencia contacto) {
        contactos.add(Objects.requireNonNull(contacto, "El contacto no puede ser nulo"));
    }

    /**
     * Elimina un contacto de emergencia por su teléfono.
     * @param telefonoContacto Teléfono del contacto a eliminar.
     */
    public void eliminarContacto(String telefonoContacto) {
        contactos.removeIf(contacto -> contacto.getTelefono().equals(telefonoContacto));
    }

    /**
     * Obtiene la lista de inscripciones del corredor.
     * @return Lista inmodificable de inscripciones.
     */
    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    /**
     * Registra al corredor en una carrera y categoría seleccionada.
     * @param carrera Carrera en la que se inscribe.
     * @param categoriaSeleccionada Categoría elegida.
     * @return Inscripción creada.
     * @throws NullPointerException si la carrera o la categoría son nulas.
     */
    public Inscripcion registrarseCarrera(Carrera carrera, Categoria categoriaSeleccionada) {
        Objects.requireNonNull(carrera, "La carrera no puede ser nula");
        Objects.requireNonNull(categoriaSeleccionada, "La categoria no puede ser nula");
        Inscripcion inscripcion = carrera.crearInscripcionPara(this, categoriaSeleccionada);
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    /**
     * Obtiene la lista de resultados de las inscripciones del corredor.
     * @return Lista inmodificable de resultados.
     */
    public List<Resultado> verMisResultados() {
        return Collections.unmodifiableList(
                inscripciones.stream()
                        .map(Inscripcion::getResultado)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
    }
}
