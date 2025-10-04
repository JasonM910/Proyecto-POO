package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * La clase Administrador representa a un usuario con privilegios para gestionar eventos e inscripciones.
 * Permite crear eventos, registrar inscripciones gestionadas e ingresar tiempos de resultados.
 */
public class Administrador extends Usuario implements IRegistroTiempo {
    private final Map<String, Inscripcion> inscripcionesGestionadas = new HashMap<>();

    /**
     * Crea un nuevo administrador con los datos proporcionados.
     * @param idUsuario Identificador único del usuario.
     * @param correo Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     */
    public Administrador(String idUsuario, String correo, String contrasena) {
        super(idUsuario, correo, contrasena);
    }

    /**
     * Crea un evento nuevo.
     * @param evento El evento a crear.
     * @return El evento creado.
     * @throws NullPointerException si el evento es nulo.
     */
    public Evento crearEvento(Evento evento) {
        return Objects.requireNonNull(evento, "El evento no puede ser nulo");
    }

    /**
     * Registra una inscripción gestionada por el administrador.
     * @param inscripcion La inscripción a registrar.
     * @throws NullPointerException si la inscripción es nula.
     */
    public void registrarInscripcionGestionada(Inscripcion inscripcion) {
        Objects.requireNonNull(inscripcion, "La inscripcion no puede ser nula");
        inscripcionesGestionadas.put(inscripcion.getIdInscripcion(), inscripcion);
    }

    /**
     * Ingresa el tiempo de resultado para una inscripción gestionada.
     * @param inscripcion La inscripción a la que se le asigna el resultado.
     * @param resultado El resultado a asignar.
     * @throws NullPointerException si la inscripción o el resultado son nulos.
     */
    public void ingresarTiempo(Inscripcion inscripcion, Resultado resultado) {
        Objects.requireNonNull(inscripcion, "La inscripcion no puede ser nula");
        Objects.requireNonNull(resultado, "El resultado no puede ser nulo");
        registrarInscripcionGestionada(inscripcion);
        inscripcion.establecerResultado(resultado);
    }

    /**
     * Registra el tiempo de una inscripción gestionada.
     * @param idInscripcion Identificador de la inscripción.
     * @param tiempoSegundos Tiempo en segundos a registrar.
     * @return El resultado registrado.
     * @throws IllegalArgumentException si el tiempo es negativo o la inscripción no existe.
     */
    @Override
    public Resultado registrarTiempo(String idInscripcion, double tiempoSegundos) {
        if (tiempoSegundos <= 0) {
            throw new IllegalArgumentException("El tiempo debe ser positivo");
        }
        Inscripcion inscripcion = inscripcionesGestionadas.get(idInscripcion);
        if (inscripcion == null) {
            throw new IllegalArgumentException("No se encontro la inscripcion: " + idInscripcion);
        }
        Resultado resultado = new Resultado(
                "RES-" + idInscripcion,
                tiempoSegundos,
                0,
                0
        );
        inscripcion.establecerResultado(resultado);
        return resultado;
    }
}
