import model.*;

public class Main {
    public static void main(String[] args) {
        Categoria categoria = new Categoria("CAT-1", "General", "Categoría abierta", 18, 65, "Mixto");
        Evento evento = new Evento("EVT-1", "Maratón", "2024-10-01", "Ciudad", "Evento principal", "Planificado", "Carrera");
        Carrera carrera = new Carrera("CAR-1", "10K", 10_000, "2024-10-01", "08:00", "10:00", categoria);
        evento.agregarCarrera(carrera);

        Competidor competidor = new Competidor("USR-1", "Ana Pérez", "segura", "ana@example.com", "001", categoria);
        ContactoEmergencia contacto = new ContactoEmergencia("Luis Pérez", "555-1234", "Hermano");
        competidor.setContactoEmergencia(contacto);

        RegistroTiempo registroTiempo = new RegistroTiempo();
        Resultados resultados = registroTiempo.registrarTiempo(competidor, 3600, java.time.LocalDate.now(), carrera);

        System.out.println("Resultado registrado para " + competidor.getNombre() + ": " + resultados.getTiempoFinal() + " segundos");
    }
}
