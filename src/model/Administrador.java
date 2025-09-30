package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Administrador extends Usuario implements IRegistroTiempo {
    private final Map<String, Inscripcion> inscripcionesGestionadas = new HashMap<>();

    public Administrador(String idUsuario, String correo, String contrasena) {
        super(idUsuario, correo, contrasena);
    }

    public Evento crearEvento(Evento evento) {
        return Objects.requireNonNull(evento, "El evento no puede ser nulo");
    }

    public void registrarInscripcionGestionada(Inscripcion inscripcion) {
        Objects.requireNonNull(inscripcion, "La inscripcion no puede ser nula");
        inscripcionesGestionadas.put(inscripcion.getIdInscripcion(), inscripcion);
    }

    public void ingresarTiempo(Inscripcion inscripcion, Resultado resultado) {
        Objects.requireNonNull(inscripcion, "La inscripcion no puede ser nula");
        Objects.requireNonNull(resultado, "El resultado no puede ser nulo");
        registrarInscripcionGestionada(inscripcion);
        inscripcion.establecerResultado(resultado);
    }

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
