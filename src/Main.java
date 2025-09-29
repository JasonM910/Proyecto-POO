import model.Administrador;
import model.Carrera;
import model.Categoria;
import model.ContactoEmergencia;
import model.Corredor;
import model.Evento;
import model.EstadoEvento;
import model.Genero;
import model.Inscripcion;
import model.Multimedia;
import model.Pago;
import model.Resultado;
import model.TallaCamiseta;
import model.TipoMultimedia;
import model.TipoSangre;
import service.ComunicacionService;
import service.EventoService;
import service.InscripcionService;
import service.TiempoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EventoService eventoService = new EventoService();
        InscripcionService inscripcionService = new InscripcionService();
        TiempoService tiempoService = new TiempoService();
        ComunicacionService comunicacionService = new ComunicacionService();

        Administrador administrador = new Administrador("ADM-1", "admin@evento.com", "segura");

        Evento evento = new Evento(
                "EVT-1",
                "Carrera del Informatico",
                LocalDate.of(2024, 10, 1),
                "Evento anual para la comunidad de tecnologia",
                "Campus San Carlos"
        );
        eventoService.registrarEvento(evento);

        Carrera carrera10K = new Carrera(
                "CAR-10",
                "Carrera 10K",
                10.0,
                evento.getFecha()
        );
        carrera10K.agregarCategoria(new Categoria("General", 18, 99));
        carrera10K.abrirInscripcion();
        evento.agregarCarrera(carrera10K);
        evento.actualizarEstado(EstadoEvento.Programada);

        Corredor corredor = new Corredor(
                "USR-1",
                "ana@example.com",
                "contrasena",
                "COR-1",
                "Ana Perez",
                "555-0101",
                LocalDate.of(1994, 3, 21),
                Genero.Femenino,
                TipoSangre.OPositivo
        );
        corredor.agregarContacto(new ContactoEmergencia("Luis Perez", "555-1234", "Hermano"));

        Inscripcion inscripcion = inscripcionService.registrarInscripcion(
                carrera10K,
                corredor,
                TallaCamiseta.M,
                administrador
        );

        inscripcionService.registrarPago(
                inscripcion.getIdInscripcion(),
                new Pago("PAY-1", new BigDecimal("35.00"), "Inscripcion Carrera 10K", LocalDateTime.now())
        );
        inscripcionService.confirmarInscripcion(inscripcion.getIdInscripcion());

        tiempoService.ingresarResultado(administrador, inscripcion, 3600, 12, 2);

        evento.agregarMultimedia(new Multimedia(
                "MM-1",
                TipoMultimedia.Video,
                "https://example.com/calentamiento",
                "Video de calentamiento previo a la carrera"
        ));

        comunicacionService.publicarMensajeGeneral(
                "MSG-1",
                "Bienvenidos a la Carrera del Informatico",
                administrador
        );
        comunicacionService.enviarMensajePrivado(
                "MSG-2",
                "Tu inscripcion ha sido confirmada",
                administrador,
                corredor
        );

        Resultado resultado = inscripcion.getResultado();
        System.out.println("Evento: " + evento.getNombre() + " | Estado: " + evento.getEstado());
        System.out.println("Inscripcion " + inscripcion.getIdInscripcion() + " -> Estado: " + inscripcion.getEstado());
        System.out.println("Tiempo registrado: " + (resultado != null ? resultado.getTiempoSegundos() : "N/A"));
        System.out.println("Mensajes del corredor: " + corredor.getMensajes().size());
        System.out.println("Resultados del corredor: " + corredor.verMisResultados().size());
    }
}
