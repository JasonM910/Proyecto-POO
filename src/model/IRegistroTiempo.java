package model;

/**
 * Interfaz para el registro de tiempos en inscripciones de eventos deportivos.
 * Permite definir el método para registrar el tiempo de un corredor en una inscripción específica.
 */
public interface IRegistroTiempo {
    /**
     * Registra el tiempo de un corredor en una inscripción.
     * @param idInscripcion Identificador de la inscripción.
     * @param tiempoSegundos Tiempo en segundos a registrar.
     * @return El resultado registrado.
     */
    Resultado registrarTiempo(String idInscripcion, double tiempoSegundos);
}