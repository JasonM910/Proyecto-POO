import model.Administrador;
import model.Carrera;
import model.Categoria;
import model.ContactoEmergencia;
import model.Corredor;
import model.Evento;
import model.EstadoEvento;
import model.EstadoInscripcion;
import model.Genero;
import model.Inscripcion;
import model.Mensaje;
import model.Multimedia;
import model.Pago;
import model.Resultado;
import model.TallaCamiseta;
import model.TipoActividad;
import model.TipoMultimedia;
import model.TipoSangre;
import model.Usuario;
import service.ComunicacionService;
import service.EventoService;
import service.InscripcionService;
import service.TiempoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Scanner;

/**
 * La clase Main es el punto de entrada de la aplicacion.
 * Proporciona una interfaz de consola para que administradores y corredores interactuen con el sistema.
 * Permite gestionar eventos, inscripciones, tiempos y comunicacion entre usuarios.
 */
public class Main {
    // Scanner estatico para leer entradas del usuario
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        EventoService eventoService = new EventoService();
        InscripcionService inscripcionService = new InscripcionService();
        TiempoService tiempoService = new TiempoService();
        ComunicacionService comunicacionService = new ComunicacionService();

        Map<String, Administrador> administradores = new HashMap<>();
        Map<String, Corredor> corredores = new HashMap<>();

        inicializarDatos(eventoService, inscripcionService, tiempoService, comunicacionService, administradores, corredores);

        boolean salir = false;
        while (!salir) {
            System.out.println("=================================");
            System.out.println(" Sistema Carrera del Informatico");
            System.out.println("=================================");
            System.out.println("1. Iniciar sesion como administrador");
            System.out.println("2. Iniciar sesion como corredor");
            System.out.println("0. Salir");
            int opcion = leerEntero("Seleccione una opcion: ");
            switch (opcion) {
                case 1:
                    manejarSesionAdministrador(eventoService, inscripcionService, tiempoService,
                            comunicacionService, administradores, corredores);
                    break;
                case 2:
                    manejarSesionCorredor(eventoService, inscripcionService, comunicacionService,
                            administradores, corredores);
                    break;
                case 0:
                    System.out.println("Hasta pronto.");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
                    break;
            }
        }
    }

    /**
     * Metodo para inicializar datos de prueba en el sistema.
     */
    private static void inicializarDatos(EventoService eventoService,
                                         InscripcionService inscripcionService,
                                         TiempoService tiempoService,
                                         ComunicacionService comunicacionService,
                                         Map<String, Administrador> administradores,
                                         Map<String, Corredor> corredores) {
        Administrador adminPrincipal = new Administrador("ADM-1", "admin@evento.com", "segura");
        administradores.put(adminPrincipal.getCorreo(), adminPrincipal);
        Administrador adminLogistica = new Administrador("ADM-2", "logistica@evento.com", "coordinacion");
        administradores.put(adminLogistica.getCorreo(), adminLogistica);
        Administrador adminComunicaciones = new Administrador("ADM-3", "comunicaciones@evento.com", "informar");
        administradores.put(adminComunicaciones.getCorreo(), adminComunicaciones);

        Evento carreraInformatico = new Evento(
                "EVT-1",
                "Carrera del Informatico",
                LocalDate.of(2024, 10, 1),
                LocalTime.of(8, 0),
                "Evento anual para la comunidad de tecnologia",
                "Campus San Carlos",
                TipoActividad.CARRERA
        );
        carreraInformatico.actualizarEstado(EstadoEvento.Programada);
        Carrera carrera5KInformatico = new Carrera("CAR-05", "Carrera 5K", 5.0, carreraInformatico.getFecha());
        carrera5KInformatico.agregarCategoria(new Categoria("Estudiantes", 16, 30));
        carrera5KInformatico.agregarCategoria(new Categoria("Libre", 18, 70));
        carrera5KInformatico.abrirInscripcion();
        Carrera carrera10KInformatico = new Carrera("CAR-10", "Carrera 10K", 10.0, carreraInformatico.getFecha());
        carrera10KInformatico.agregarCategoria(new Categoria("General", 18, 65));
        carrera10KInformatico.agregarCategoria(new Categoria("Master", 36, 80));
        carrera10KInformatico.abrirInscripcion();
        carreraInformatico.agregarCarrera(carrera5KInformatico);
        carreraInformatico.agregarCarrera(carrera10KInformatico);
        carreraInformatico.agregarMultimedia(new Multimedia(
                "MM-1",
                TipoMultimedia.Video,
                "https://example.com/calentar",
                "Rutina de calentamiento previa a la carrera"
        ));
        carreraInformatico.agregarMultimedia(new Multimedia(
                "MM-2",
                TipoMultimedia.Documento,
                "https://example.com/reglamento.pdf",
                "Reglamento oficial del evento"
        ));
        carreraInformatico.agregarMultimedia(new Multimedia(
                "MM-3",
                TipoMultimedia.Imagen,
                "https://example.com/recorrido-10k.png",
                "Mapa del recorrido 10K"
        ));
        eventoService.registrarEvento(carreraInformatico);

        Evento trailNocturno = new Evento(
                "EVT-2",
                "Trail Nocturno del Bosque",
                LocalDate.of(2024, 11, 15),
                LocalTime.of(19, 30),
                "Recorrido nocturno por senderos iluminados",
                "Parque Metropolitano",
                TipoActividad.CARRERA
        );
        trailNocturno.actualizarEstado(EstadoEvento.Programada);
        Carrera trail21K = new Carrera("CAR-21", "Trail 21K", 21.0, trailNocturno.getFecha());
        trail21K.agregarCategoria(new Categoria("Elite", 21, 60));
        trail21K.agregarCategoria(new Categoria("Master", 30, 75));
        trail21K.abrirInscripcion();
        Carrera trail10K = new Carrera("CAR-11", "Trail 10K", 10.0, trailNocturno.getFecha());
        trail10K.agregarCategoria(new Categoria("General", 18, 65));
        trail10K.abrirInscripcion();
        trailNocturno.agregarCarrera(trail21K);
        trailNocturno.agregarCarrera(trail10K);
        trailNocturno.agregarMultimedia(new Multimedia(
                "MM-4",
                TipoMultimedia.Video,
                "https://example.com/trail-nocturno",
                "Consejos de seguridad para el trail nocturno"
        ));
        trailNocturno.agregarMultimedia(new Multimedia(
                "MM-5",
                TipoMultimedia.Imagen,
                "https://example.com/trail-altimetria.png",
                "Altimetria del recorrido 21K"
        ));
        eventoService.registrarEvento(trailNocturno);

        Evento rutaCiclista = new Evento(
                "EVT-3",
                "Ruta Ciclista Solidaria",
                LocalDate.of(2025, 1, 20),
                LocalTime.of(6, 30),
                "Ciclismo recreativo para apoyar iniciativas sociales",
                "Avenida Central",
                TipoActividad.CICLISMO
        );
        rutaCiclista.actualizarEstado(EstadoEvento.EnCurso);
        Carrera circuito60K = new Carrera("CAR-60", "Circuito 60K", 60.0, rutaCiclista.getFecha());
        circuito60K.agregarCategoria(new Categoria("General", 18, 70));
        circuito60K.abrirInscripcion();
        Carrera circuito25K = new Carrera("CAR-25", "Circuito 25K", 25.0, rutaCiclista.getFecha());
        circuito25K.agregarCategoria(new Categoria("Juvenil", 16, 25));
        circuito25K.agregarCategoria(new Categoria("Adultos", 26, 60));
        circuito25K.abrirInscripcion();
        rutaCiclista.agregarCarrera(circuito60K);
        rutaCiclista.agregarCarrera(circuito25K);
        rutaCiclista.agregarMultimedia(new Multimedia(
                "MM-6",
                TipoMultimedia.Documento,
                "https://example.com/ruta-ciclista.pdf",
                "Guia de puntos de asistencia y normas"
        ));
        rutaCiclista.agregarMultimedia(new Multimedia(
                "MM-7",
                TipoMultimedia.Imagen,
                "https://example.com/altimetria-25k.png",
                "Altimetria del circuito 25K"
        ));
        eventoService.registrarEvento(rutaCiclista);

        Evento triatlonUrbano = new Evento(
                "EVT-4",
                "Triatlon Urbano",
                LocalDate.of(2024, 6, 8),
                LocalTime.of(7, 0),
                "Triatlon sprint con recorrido urbano",
                "Bahia Central",
                TipoActividad.TRIATLON
        );
        triatlonUrbano.actualizarEstado(EstadoEvento.Finalizada);
        Carrera triSprint = new Carrera("CAR-TRI", "Triatlon Sprint", 25.75, triatlonUrbano.getFecha());
        triSprint.agregarCategoria(new Categoria("Sprint", 18, 60));
        triSprint.agregarCategoria(new Categoria("Relevos", 18, 70));
        triSprint.abrirInscripcion();
        triatlonUrbano.agregarCarrera(triSprint);
        triatlonUrbano.agregarMultimedia(new Multimedia(
                "MM-8",
                TipoMultimedia.Video,
                "https://example.com/triatlon-brief",
                "Briefing oficial del triatlon"
        ));
        triatlonUrbano.agregarMultimedia(new Multimedia(
                "MM-9",
                TipoMultimedia.Documento,
                "https://example.com/tri-reglamento.pdf",
                "Reglamento y recomendaciones"
        ));
        eventoService.registrarEvento(triatlonUrbano);

        Corredor ana = new Corredor(
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
        ana.agregarContacto(new ContactoEmergencia("Luis Perez", "555-1234", "Hermano"));
        ana.agregarContacto(new ContactoEmergencia("Carolina Gomez", "555-4567", "Amiga"));
        corredores.put(ana.getCorreo(), ana);

        Corredor carlos = new Corredor(
                "USR-2",
                "carlos@example.com",
                "segura",
                "COR-2",
                "Carlos Gomez",
                "555-0202",
                LocalDate.of(1988, 7, 12),
                Genero.Masculino,
                TipoSangre.A
        );
        carlos.agregarContacto(new ContactoEmergencia("Daniela Gomez", "555-6543", "Esposa"));
        carlos.agregarContacto(new ContactoEmergencia("Miguel Gomez", "555-3311", "Hermano"));
        corredores.put(carlos.getCorreo(), carlos);

        Corredor maria = new Corredor(
                "USR-3",
                "maria@example.com",
                "clave",
                "COR-3",
                "Maria Lopez",
                "555-0303",
                LocalDate.of(1999, 12, 5),
                Genero.Femenino,
                TipoSangre.B
        );
        maria.agregarContacto(new ContactoEmergencia("Laura Lopez", "555-8899", "Madre"));
        corredores.put(maria.getCorreo(), maria);

        Corredor diego = new Corredor(
                "USR-4",
                "diego@example.com",
                "trail2024",
                "COR-4",
                "Diego Ramirez",
                "555-0404",
                LocalDate.of(1983, 2, 28),
                Genero.Masculino,
                TipoSangre.AB
        );
        diego.agregarContacto(new ContactoEmergencia("Ricardo Ramirez", "555-7788", "Padre"));
        diego.agregarContacto(new ContactoEmergencia("Sofia Ramirez", "555-6677", "Hermana"));
        corredores.put(diego.getCorreo(), diego);

        Corredor laura = new Corredor(
                "USR-5",
                "laura@example.com",
                "maraton",
                "COR-5",
                "Laura Fernandez",
                "555-0505",
                LocalDate.of(1992, 9, 15),
                Genero.Femenino,
                TipoSangre.ONegativo
        );
        laura.agregarContacto(new ContactoEmergencia("Julio Fernandez", "555-7780", "Padre"));
        laura.agregarContacto(new ContactoEmergencia("Silvia Ruiz", "555-8890", "Pareja"));
        corredores.put(laura.getCorreo(), laura);

        Corredor sergio = new Corredor(
                "USR-6",
                "sergio@example.com",
                "rodaje",
                "COR-6",
                "Sergio Ramirez",
                "555-0606",
                LocalDate.of(1978, 4, 9),
                Genero.Masculino,
                TipoSangre.B
        );
        sergio.agregarContacto(new ContactoEmergencia("Helena Ramirez", "555-9900", "Esposa"));
        corredores.put(sergio.getCorreo(), sergio);

        eventoService.buscarPorId("EVT-1").ifPresent(evento -> {
            evento.getCarreras().stream()
                    .filter(c -> "Carrera 10K".equals(c.getNombre()))
                    .findFirst()
                    .ifPresent(carrera -> {
                        Categoria categoria = carrera.getCategorias().stream()
                                .filter(cat -> "General".equalsIgnoreCase(cat.getNombre()))
                                .findFirst()
                                .orElse(carrera.getCategorias().get(0));
                        Inscripcion inscripcionAna10K = inscripcionService.registrarInscripcion(carrera, ana, categoria, TallaCamiseta.M, adminPrincipal);
                        inscripcionService.registrarPago(inscripcionAna10K.getIdInscripcion(), new Pago("PAY-ANA-10K", new BigDecimal("35000"), "Pago completo", LocalDateTime.now().minusDays(5)));
                        inscripcionService.confirmarInscripcion(inscripcionAna10K.getIdInscripcion());
                        tiempoService.ingresarResultado(adminPrincipal, inscripcionAna10K, 3605.2, 5, 2);
                    });

            evento.getCarreras().stream()
                    .filter(c -> "Carrera 5K".equals(c.getNombre()))
                    .findFirst()
                    .ifPresent(carrera -> {
                        Categoria categoria = carrera.getCategorias().get(0);
                        Inscripcion inscripcionMaria5K = inscripcionService.registrarInscripcion(carrera, maria, categoria, TallaCamiseta.S, adminComunicaciones);
                        inscripcionService.registrarPago(inscripcionMaria5K.getIdInscripcion(), new Pago("PAY-MARIA-5K", new BigDecimal("20000"), "Transferencia", LocalDateTime.now().minusDays(3)));
                        inscripcionService.confirmarInscripcion(inscripcionMaria5K.getIdInscripcion());
                    });
        });

        eventoService.buscarPorId("EVT-2").ifPresent(evento -> {
            evento.getCarreras().stream()
                    .filter(c -> "Trail 21K".equals(c.getNombre()))
                    .findFirst()
                    .ifPresent(carrera -> {
                        Categoria categoria = carrera.getCategorias().get(0);
                        Inscripcion inscripcionCarlos21K = inscripcionService.registrarInscripcion(carrera, carlos, categoria, TallaCamiseta.L, adminLogistica);
                        inscripcionService.registrarPago(inscripcionCarlos21K.getIdInscripcion(), new Pago("PAY-CARLOS-21K", new BigDecimal("45000"), "Pago con tarjeta", LocalDateTime.now().minusDays(2)));
                        inscripcionService.confirmarInscripcion(inscripcionCarlos21K.getIdInscripcion());
                        tiempoService.ingresarResultado(adminLogistica, inscripcionCarlos21K, 7920.78, 12, 4);
                    });

            evento.getCarreras().stream()
                    .filter(c -> "Trail 10K".equals(c.getNombre()))
                    .findFirst()
                    .ifPresent(carrera -> {
                        if (!carrera.isInscripcionAbierta()) {
                            carrera.abrirInscripcion();
                        }
                        Categoria categoria = carrera.getCategorias().get(0);
                        inscripcionService.registrarInscripcion(carrera, diego, categoria, TallaCamiseta.XL, adminPrincipal);
                    });
        });

        eventoService.buscarPorId("EVT-3").ifPresent(evento -> {
            evento.getCarreras().stream()
                    .filter(c -> "Circuito 60K".equals(c.getNombre()))
                    .findFirst()
                    .ifPresent(carrera -> {
                        Categoria categoria = carrera.getCategorias().get(0);
                        Inscripcion inscripcionDiego60K = inscripcionService.registrarInscripcion(carrera, diego, categoria, TallaCamiseta.L, adminPrincipal);
                        inscripcionService.registrarPago(inscripcionDiego60K.getIdInscripcion(), new Pago("PAY-DIEGO-60K", new BigDecimal("40000"), "Pago corporativo", LocalDateTime.now().minusDays(2)));
                        inscripcionService.confirmarInscripcion(inscripcionDiego60K.getIdInscripcion());
                    });

            evento.getCarreras().stream()
                    .filter(c -> "Circuito 25K".equals(c.getNombre()))
                    .findFirst()
                    .ifPresent(carrera -> {
                        Categoria categoria = carrera.getCategorias().stream()
                                .filter(cat -> "Adultos".equalsIgnoreCase(cat.getNombre()))
                                .findFirst()
                                .orElse(carrera.getCategorias().get(0));
                        Inscripcion inscripcionLaura25K = inscripcionService.registrarInscripcion(carrera, laura, categoria, TallaCamiseta.M, adminLogistica);
                        inscripcionService.registrarPago(inscripcionLaura25K.getIdInscripcion(), new Pago("PAY-LAURA-25K", new BigDecimal("30000"), "Pago en linea", LocalDateTime.now().minusDays(1)));
                    });
        });

        eventoService.buscarPorId("EVT-4").ifPresent(evento -> {
            evento.getCarreras().stream()
                    .findFirst()
                    .ifPresent(carrera -> {
                        Categoria categoria = carrera.getCategorias().get(0);
                        Inscripcion inscripcionSergioTri = inscripcionService.registrarInscripcion(carrera, sergio, categoria, TallaCamiseta.XL, adminPrincipal);
                        inscripcionService.registrarPago(inscripcionSergioTri.getIdInscripcion(), new Pago("PAY-SERGIO-TRI", new BigDecimal("52000"), "Registro anticipado", LocalDateTime.now().minusWeeks(2)));
                        inscripcionService.confirmarInscripcion(inscripcionSergioTri.getIdInscripcion());
                        tiempoService.ingresarResultado(adminPrincipal, inscripcionSergioTri, 4120.5, 18, 5);
                    });
        });

        Set<Usuario> participantesChat = new LinkedHashSet<>();
        participantesChat.addAll(administradores.values());
        participantesChat.addAll(corredores.values());

        comunicacionService.publicarMensajeGeneral(
                "MSG-100",
                "Bienvenidos al calendario 2024, revisen las nuevas fechas ya publicadas",
                adminComunicaciones,
                participantesChat
        );
        comunicacionService.publicarMensajeGeneral(
                "MSG-101",
                "Recuerden completar su ficha medica antes del inicio de cada evento",
                adminPrincipal,
                participantesChat
        );

        comunicacionService.enviarMensajePrivado(
                "MSG-200",
                "Hola Ana, confirma si necesitas apoyo de hidratacion extra",
                adminLogistica,
                ana
        );
        comunicacionService.enviarMensajePrivado(
                "MSG-201",
                "Gracias por la informacion, ya cargue mi comprobante de pago",
                carlos,
                adminLogistica
        );
        comunicacionService.enviarMensajePrivado(
                "MSG-202",
                "Nos vemos en la charla tecnica del trail",
                diego,
                adminComunicaciones
        );
    }

    /**
     * Maneja la sesion de un administrador, mostrando el menu y procesando las opciones.
     */
    private static void manejarSesionAdministrador(EventoService eventoService,
                                                   InscripcionService inscripcionService,
                                                   TiempoService tiempoService,
                                                   ComunicacionService comunicacionService,
                                                   Map<String, Administrador> administradores,
                                                   Map<String, Corredor> corredores) {
        Administrador administrador = autenticarAdministrador(administradores);
        if (administrador == null) {
            return;
        }
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menu Administrador ---");
            System.out.println("1. Listar eventos y recursos");
            System.out.println("2. Crear evento");
            System.out.println("3. Agregar carrera a evento");
            System.out.println("4. Abrir/cerrar inscripcion de carrera");
            System.out.println("5. Registrar nuevo corredor");
            System.out.println("6. Registrar inscripcion de un corredor");
            System.out.println("7. Registrar pago y confirmar inscripcion");
            System.out.println("8. Registrar resultado");
            System.out.println("9. Publicar mensaje general");
            System.out.println("10. Actualizar estado de evento");
            System.out.println("11. Ver chat general");
            System.out.println("12. Enviar mensaje a corredor");
            System.out.println("13. Agregar recurso multimedia a evento");
            System.out.println("0. Cerrar sesion");
            int opcion = leerEntero("Seleccione una opcion: ");
            switch (opcion) {
                case 1:
                    listarEventos(eventoService);
                    break;
                case 2:
                    crearEvento(eventoService);
                    break;
                case 3:
                    agregarCarrera(eventoService);
                    break;
                case 4:
                    cambiarEstadoInscripcion(eventoService);
                    break;
                case 5:
                    registrarCorredor(corredores);
                    break;
                case 6:
                    registrarInscripcion(inscripcionService, eventoService, administrador, corredores);
                    break;
                case 7:
                    registrarPago(inscripcionService);
                    break;
                case 8:
                    registrarResultado(tiempoService, inscripcionService, administrador);
                    break;
                case 9:
                    publicarMensajeGeneral(comunicacionService, administrador, administradores, corredores);
                    break;
                case 10:
                    actualizarEstadoEvento(eventoService);
                    break;
                case 11:
                    mostrarChatGeneral(comunicacionService);
                    break;
                case 12:
                    enviarMensajeACorredor(comunicacionService, administrador, corredores);
                    break;
                case 13:
                    agregarMultimediaAEvento(eventoService);
                    break;
                case 0:
                    administrador.cerrarSesion();
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }

    /**
     * Maneja la sesion de un corredor, mostrando el menu y procesando las opciones.
     */
    private static void manejarSesionCorredor(EventoService eventoService,
                                              InscripcionService inscripcionService,
                                              ComunicacionService comunicacionService,
                                              Map<String, Administrador> administradores,
                                              Map<String, Corredor> corredores) {
        Corredor corredor = autenticarCorredor(corredores);
        if (corredor == null) {
            return;
        }
        Administrador administrador = administradores.values().stream().findFirst().orElse(null);
        if (administrador == null) {
            System.out.println("No hay administradores registrados en el sistema.");
            return;
        }
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menu Corredor ---");
            System.out.println("1. Listar eventos y carreras");
            System.out.println("2. Inscribirse en carrera");
            System.out.println("3. Registrar pago de inscripcion");
            System.out.println("4. Ver mis inscripciones");
            System.out.println("5. Ver mis resultados");
            System.out.println("6. Ver mis datos personales");
            System.out.println("7. Ver mensajes recibidos");
            System.out.println("8. Ver chat general");
            System.out.println("9. Enviar mensaje al administrador");
            System.out.println("0. Cerrar sesion");
            int opcion = leerEntero("Seleccione una opcion: ");
            switch (opcion) {
                case 1:
                    listarEventos(eventoService);
                    break;
                case 2:
                    inscribirseEnCarrera(eventoService, inscripcionService, administrador, corredor);
                    break;
                case 3:
                    registrarPagoCorredor(inscripcionService, corredor);
                    break;
                case 4:
                    mostrarInscripcionesCorredor(corredor);
                    break;
                case 5:
                    mostrarResultadosCorredor(corredor);
                    break;
                case 6:
                    mostrarDatosPersonales(corredor);
                    break;
                case 7:
                    mostrarMensajes(corredor);
                    break;
                case 8:
                    mostrarChatGeneral(comunicacionService);
                    break;
                case 9:
                    enviarMensajeAdministrador(comunicacionService, corredor, administrador);
                    break;
                case 0:
                    corredor.cerrarSesion();
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }

    /** Autentica a un administrador usando correo y contrasena.
     * @param administradores Mapa de administradores registrados.
     * @return El administrador autenticado o null si falla la autenticacion.
     */
    private static Administrador autenticarAdministrador(Map<String, Administrador> administradores) {
        System.out.println("\n--- Inicio de sesion Administrador ---");
        System.out.print("Correo: ");
        String correo = SCANNER.nextLine().trim();
        System.out.print("Contrasena: ");
        String contrasena = SCANNER.nextLine().trim();
        Administrador administrador = administradores.get(correo);
        if (administrador == null || !administrador.iniciarSesion(correo, contrasena)) {
            System.out.println("Credenciales invalidas.");
            return null;
        }
        return administrador;
    }

    /** Autentica a un corredor usando correo y contrasena.
     * @param corredores Mapa de corredores registrados.
     * @return El corredor autenticado o null si falla la autenticacion.
     */
    private static Corredor autenticarCorredor(Map<String, Corredor> corredores) {
        System.out.println("\n--- Inicio de sesion Corredor ---");
        System.out.print("Correo: ");
        String correo = SCANNER.nextLine().trim();
        System.out.print("Contrasena: ");
        String contrasena = SCANNER.nextLine().trim();
        Corredor corredor = corredores.get(correo);
        if (corredor == null || !corredor.iniciarSesion(correo, contrasena)) {
            System.out.println("Credenciales invalidas.");
            return null;
        }
        return corredor;
    }

    private static void listarEventos(EventoService eventoService) {
        System.out.println("\nEventos registrados:");
        List<Evento> eventos = eventoService.listarEventos();
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos cargados.");
            return;
        }
        eventos.forEach(evento -> {
            System.out.println("- " + evento.getIdEvento() + " | " + evento.getNombre() +
                    " | Fecha: " + evento.getFecha() + " " + evento.getHora() +
                    " | Tipo: " + evento.getTipoActividad() +
                    " | Estado: " + evento.getEstado());
            evento.getCarreras().forEach(carrera -> {
                String categorias = carrera.getCategorias().stream()
                        .map(Categoria::getNombre)
                        .collect(java.util.stream.Collectors.joining(", "));
                System.out.println("    * " + carrera.getIdCarrera() + " - " + carrera.getNombre() +
                        " | Distancia: " + carrera.getDistanciaKm() + " km | Inscripcion abierta: " +
                        (carrera.isInscripcionAbierta() ? "Si" : "No") +
                        " | Categorias: " + categorias);
            });
            if (!evento.getMultimedia().isEmpty()) {
                System.out.println("    Recursos multimedia:");
                evento.getMultimedia().forEach(multimedia ->
                        System.out.println("       - " + multimedia.getTipo() + ": " + multimedia.getDescripcion() +
                                " | URL: " + multimedia.getUrl()));
            }
        });
    }

    /** Crea un nuevo evento solicitando los datos al usuario.
     * @param eventoService Servicio para gestionar eventos.
     */
    private static void crearEvento(EventoService eventoService) {
        try {
            System.out.println("\n--- Crear Evento ---");
            System.out.print("ID del evento: ");
            String id = SCANNER.nextLine().trim();
            System.out.print("Nombre: ");
            String nombre = SCANNER.nextLine().trim();
            LocalDate fecha = leerFecha("Fecha (yyyy-MM-dd): ");
            LocalTime hora = leerHora("Hora (HH:mm): ");
            System.out.print("Descripcion: ");
            String descripcion = SCANNER.nextLine().trim();
            System.out.print("Ubicacion: ");
            String ubicacion = SCANNER.nextLine().trim();
            System.out.println("Tipos disponibles: " + Arrays.toString(TipoActividad.values()));
            System.out.print("Tipo de actividad: ");
            TipoActividad tipoActividad = TipoActividad.valueOf(SCANNER.nextLine().trim().toUpperCase());
            Evento evento = new Evento(id, nombre, fecha, hora, descripcion, ubicacion, tipoActividad);
            eventoService.registrarEvento(evento);
            System.out.println("Evento creado correctamente.");
        } catch (IllegalArgumentException ex) {
            System.out.println("No se pudo crear el evento: " + ex.getMessage());
        }
    }

    /** Agrega una nueva carrera a un evento existente.
     * @param eventoService Servicio para gestionar eventos.
     */
    private static void agregarCarrera(EventoService eventoService) {
        System.out.println("\n--- Agregar Carrera ---");
        System.out.print("ID del evento: ");
        String idEvento = SCANNER.nextLine().trim();
        Optional<Evento> eventoOpt = eventoService.buscarPorId(idEvento);
        if (!eventoOpt.isPresent()) {
            System.out.println("Evento no encontrado.");
            return;
        }
        Evento evento = eventoOpt.get();
        try {
            System.out.print("ID de la carrera: ");
            String idCarrera = SCANNER.nextLine().trim();
            System.out.print("Nombre de la carrera: ");
            String nombre = SCANNER.nextLine().trim();
            double distancia = leerDouble("Distancia (km): ");
            LocalDate fecha = leerFecha("Fecha de la carrera (yyyy-MM-dd): ");
            Carrera carrera = new Carrera(idCarrera, nombre, distancia, fecha);
            boolean agregarMasCategorias = true;
            while (agregarMasCategorias) {
                System.out.print("Nombre de categoria (vacio para terminar): ");
                String nombreCategoria = SCANNER.nextLine().trim();
                if (nombreCategoria.isEmpty()) {
                    agregarMasCategorias = false;
                } else {
                    int edadMinima = leerEntero("Edad minima: ");
                    int edadMaxima = leerEntero("Edad maxima: ");
                    carrera.agregarCategoria(new Categoria(nombreCategoria, edadMinima, edadMaxima));
                }
            }
            evento.agregarCarrera(carrera);
            System.out.println("Carrera agregada exitosamente.");
        } catch (IllegalArgumentException ex) {
            System.out.println("No se pudo agregar la carrera: " + ex.getMessage());
        }
    }

    /** Abre o cierra la inscripcion de una carrera dentro de un evento.
     * @param eventoService Servicio para gestionar eventos.
     */
    private static void cambiarEstadoInscripcion(EventoService eventoService) {
        System.out.println("\n--- Abrir/Cerrar Inscripcion ---");
        System.out.print("ID del evento: ");
        String idEvento = SCANNER.nextLine().trim();
        Optional<Evento> eventoOpt = eventoService.buscarPorId(idEvento);
        if (!eventoOpt.isPresent()) {
            System.out.println("Evento no encontrado.");
            return;
        }
        Evento evento = eventoOpt.get();
        System.out.print("ID de la carrera: ");
        String idCarrera = SCANNER.nextLine().trim();
        Optional<Carrera> carreraOpt = evento.getCarreras().stream()
                .filter(c -> c.getIdCarrera().equals(idCarrera))
                .findFirst();
        if (!carreraOpt.isPresent()) {
            System.out.println("Carrera no encontrada.");
            return;
        }
        Carrera carrera = carreraOpt.get();
        if (carrera.isInscripcionAbierta()) {
            carrera.cerrarInscripcion();
            System.out.println("Inscripcion cerrada.");
        } else {
            carrera.abrirInscripcion();
            System.out.println("Inscripcion abierta.");
        }
    }

    /** Registra un nuevo corredor solicitando los datos al usuario.
     * @param corredores Mapa para almacenar los corredores registrados.
     */
    private static void registrarCorredor(Map<String, Corredor> corredores) {
        try {
            System.out.println("\n--- Registrar Corredor ---");
            System.out.print("Correo: ");
            String correo = SCANNER.nextLine().trim();
            if (corredores.containsKey(correo)) {
                System.out.println("Ya existe un corredor con ese correo.");
                return;
            }
            System.out.print("Contrasena: ");
            String contrasena = SCANNER.nextLine().trim();
            System.out.print("Nombre completo: ");
            String nombre = SCANNER.nextLine().trim();
            System.out.print("Telefono: ");
            String telefono = SCANNER.nextLine().trim();
            LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (yyyy-MM-dd): ");
            System.out.println("Generos disponibles: " + Arrays.toString(Genero.values()));
            System.out.print("Genero: ");
            Genero genero = Genero.valueOf(SCANNER.nextLine().trim().toUpperCase());
            System.out.println("Tipos de sangre disponibles: " + Arrays.toString(TipoSangre.values()));
            System.out.print("Tipo de sangre: ");
            TipoSangre tipoSangre = TipoSangre.valueOf(SCANNER.nextLine().trim().toUpperCase());

            List<ContactoEmergencia> contactos = new ArrayList<>();
            while (true) {
                System.out.print("Nombre de contacto de emergencia (vacio para terminar): ");
                String nombreContacto = SCANNER.nextLine().trim();
                if (nombreContacto.isEmpty()) {
                    if (contactos.isEmpty()) {
                        System.out.println("Registro cancelado. Debe registrar al menos un contacto de emergencia.");
                        return;
                    }
                    break;
                }
                System.out.print("Telefono del contacto: ");
                String telefonoContacto = SCANNER.nextLine().trim();
                if (telefonoContacto.isEmpty()) {
                    System.out.println("El telefono del contacto no puede estar vacio.");
                    continue;
                }
                System.out.print("Relacion con el contacto: ");
                String relacion = SCANNER.nextLine().trim();
                if (relacion.isEmpty()) {
                    System.out.println("La relacion con el contacto no puede estar vacia.");
                    continue;
                }
                try {
                    contactos.add(new ContactoEmergencia(nombreContacto, telefonoContacto, relacion));
                    System.out.println("Contacto agregado.");
                } catch (IllegalArgumentException ex) {
                    System.out.println("No se pudo registrar el contacto: " + ex.getMessage());
                    continue;
                }
            }

            String idCorredor = "COR-" + (corredores.size() + 1);
            Corredor corredor = new Corredor(idCorredor, correo, contrasena, idCorredor, nombre,
                    telefono, fechaNacimiento, genero, tipoSangre);
            contactos.forEach(corredor::agregarContacto);
            corredores.put(correo, corredor);
            System.out.println("Corredor registrado correctamente.");
        } catch (IllegalArgumentException ex) {
            System.out.println("No se pudo registrar el corredor: " + ex.getMessage());
        }
    }

    /** Registra una inscripcion de un corredor a una carrera.
     * @param inscripcionService Servicio para gestionar inscripciones.
     * @param eventoService Servicio para gestionar eventos.
     * @param administrador Administrador que realiza la inscripcion.
     * @param corredores Mapa de corredores registrados.
     */
    private static void registrarInscripcion(InscripcionService inscripcionService, EventoService eventoService,
                                             Administrador administrador, Map<String, Corredor> corredores) {
        System.out.println("\n--- Registrar Inscripcion ---");
        System.out.print("Correo del corredor: ");
        String correo = SCANNER.nextLine().trim();
        Corredor corredor = corredores.get(correo);
        if (corredor == null) {
            System.out.println("Corredor no encontrado.");
            return;
        }
        Carrera carrera = seleccionarCarrera(eventoService);
        if (carrera == null) {
            return;
        }
        if (!carrera.isInscripcionAbierta()) {
            System.out.println("La inscripcion para esta carrera no esta abierta.");
            return;
        }
        Categoria categoria = seleccionarCategoria(carrera);
        if (categoria == null) {
            System.out.println("Debe seleccionar una categoria valida.");
            return;
        }
        try {
            System.out.println("Tallas disponibles: " + Arrays.toString(TallaCamiseta.values()));
            System.out.print("Seleccione talla: ");
            TallaCamiseta talla = TallaCamiseta.valueOf(SCANNER.nextLine().trim().toUpperCase());
            Inscripcion inscripcion = inscripcionService.registrarInscripcion(
                    carrera,
                    corredor,
                    categoria,
                    talla,
                    administrador
            );
            System.out.println("Inscripcion registrada con ID: " + inscripcion.getIdInscripcion());
        } catch (IllegalArgumentException ex) {
            System.out.println("Error al registrar la inscripcion: " + ex.getMessage());
        }
    }

    /** Registra un pago para una inscripcion y confirma la inscripcion.
     * @param inscripcionService Servicio para gestionar inscripciones.
     */
    private static void registrarPago(InscripcionService inscripcionService) {
        System.out.println("\n--- Registrar Pago ---");
        System.out.print("ID de la inscripcion: ");
        String idInscripcion = SCANNER.nextLine().trim();
        BigDecimal monto = leerMonto("Monto del pago: ");
        System.out.print("Descripcion: ");
        String descripcion = SCANNER.nextLine().trim();
        try {
            Pago pago = new Pago("PAY-" + idInscripcion, monto, descripcion, LocalDateTime.now());
            inscripcionService.registrarPago(idInscripcion, pago);
            inscripcionService.confirmarInscripcion(idInscripcion);
            System.out.println("Pago registrado e inscripcion confirmada.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.println("No se pudo registrar el pago: " + ex.getMessage());
        }
    }

    /** Registra el resultado de un corredor en una inscripcion confirmada.
     * @param tiempoService Servicio para gestionar tiempos y resultados.
     * @param inscripcionService Servicio para gestionar inscripciones.
     * @param administrador Administrador que registra el resultado.
     */
    private static void registrarResultado(TiempoService tiempoService, InscripcionService inscripcionService,
                                           Administrador administrador) {
        System.out.println("\n--- Registrar Resultado ---");
        System.out.print("ID de la inscripcion: ");
        String idInscripcion = SCANNER.nextLine().trim();
        Optional<Inscripcion> inscripcionOpt = inscripcionService.buscarPorId(idInscripcion);
        if (!inscripcionOpt.isPresent()) {
            System.out.println("Inscripcion no encontrada.");
            return;
        }
        Inscripcion inscripcion = inscripcionOpt.get();
        if (inscripcion.getEstado() != EstadoInscripcion.Confirmada) {
            System.out.println("La inscripcion debe estar confirmada antes de registrar un resultado.");
            return;
        }
        double tiempo = leerDouble("Tiempo en segundos: ");
        int posicionGeneral = leerEntero("Posicion general: ");
        int posicionCategoria = leerEntero("Posicion por categoria: ");
        try {
            tiempoService.ingresarResultado(administrador, inscripcion, tiempo, posicionGeneral, posicionCategoria);
            System.out.println("Resultado registrado correctamente.");
        } catch (IllegalArgumentException ex) {
            System.out.println("No se pudo registrar el resultado: " + ex.getMessage());
        }
    }

    /** Actualiza el estado de un evento.
     * @param eventoService Servicio para gestionar eventos.
     */
    private static void actualizarEstadoEvento(EventoService eventoService) {
        System.out.println("\n--- Actualizar Estado de Evento ---");
        listarEventos(eventoService);
        System.out.print("ID del evento a actualizar: ");
        String idEvento = SCANNER.nextLine().trim();
        Optional<Evento> eventoOpt = eventoService.buscarPorId(idEvento);
        if (!eventoOpt.isPresent()) {
            System.out.println("Evento no encontrado.");
            return;
        }
        System.out.println("Estados disponibles: " + Arrays.toString(EstadoEvento.values()));
        System.out.print("Nuevo estado: ");
        String nuevoEstadoTexto = SCANNER.nextLine().trim();
        Optional<EstadoEvento> estadoSeleccionado = Arrays.stream(EstadoEvento.values())
                .filter(estado -> estado.name().equalsIgnoreCase(nuevoEstadoTexto))
                .findFirst();
        if (!estadoSeleccionado.isPresent()) {
            System.out.println("Estado invalido.");
            return;
        }
        try {
            eventoService.actualizarEstado(idEvento, estadoSeleccionado.get());
            System.out.println("Estado del evento actualizado a " + estadoSeleccionado.get() + ".");
            listarEventos(eventoService);
        } catch (IllegalArgumentException ex) {
            System.out.println("No se pudo actualizar el estado del evento: " + ex.getMessage());
        }
    }

    /** Publica un mensaje en el chat general.
     * @param comunicacionService Servicio para gestionar comunicaciones.
     * @param administrador Administrador que publica el mensaje.
     * @param administradores Mapa de administradores registrados.
     * @param corredores Mapa de corredores registrados.
     */
    private static void publicarMensajeGeneral(ComunicacionService comunicacionService, Administrador administrador,
                                               Map<String, Administrador> administradores,
                                               Map<String, Corredor> corredores) {
        System.out.println("\n--- Publicar Mensaje General ---");
        System.out.print("Contenido del mensaje: ");
        String contenido = SCANNER.nextLine().trim();
        if (contenido.isEmpty()) {
            System.out.println("El contenido no puede estar vacio.");
            return;
        }
        Set<Usuario> destinatarios = new LinkedHashSet<>();
        destinatarios.add(administrador);
        destinatarios.addAll(administradores.values());
        destinatarios.addAll(corredores.values());
        comunicacionService.publicarMensajeGeneral(
                "MSG-" + System.currentTimeMillis(),
                contenido,
                administrador,
                destinatarios
        );
        System.out.println("Mensaje publicado en el chat general.");
    }

    /** Muestra el chat general con todos los mensajes publicados.
     * @param comunicacionService Servicio para gestionar comunicaciones.
     */
    private static void enviarMensajeACorredor(ComunicacionService comunicacionService, Administrador administrador,
                                               Map<String, Corredor> corredores) {
        System.out.println("\n--- Enviar Mensaje a Corredor ---");
        System.out.print("Correo del corredor: ");
        String correo = SCANNER.nextLine().trim();
        Corredor corredor = corredores.get(correo);
        if (corredor == null) {
            System.out.println("Corredor no encontrado.");
            return;
        }
        System.out.print("Contenido del mensaje: ");
        String contenido = SCANNER.nextLine().trim();
        if (contenido.isEmpty()) {
            System.out.println("El contenido no puede estar vacio.");
            return;
        }
        comunicacionService.enviarMensajePrivado(
                "MSG-" + System.currentTimeMillis(),
                contenido,
                administrador,
                corredor
        );
        System.out.println("Mensaje enviado.");
    }

    /**
     * Permite a un corredor inscribirse en una carrera.
     * @param eventoService Servicio para gestionar eventos.
     * @param inscripcionService Servicio para gestionar inscripciones.
     * @param administrador Administrador que gestiona la inscripcion.
     * @param corredor Corredor que se inscribe.
     */
    private static void inscribirseEnCarrera(EventoService eventoService, InscripcionService inscripcionService,
                                             Administrador administrador, Corredor corredor) {
        Carrera carrera = seleccionarCarrera(eventoService);
        if (carrera == null) {
            return;
        }
        if (!carrera.isInscripcionAbierta()) {
            System.out.println("La inscripcion para esta carrera no esta abierta.");
            return;
        }
        Categoria categoria = seleccionarCategoria(carrera);
        if (categoria == null) {
            System.out.println("Debe seleccionar una categoria valida.");
            return;
        }
        try {
            System.out.println("Tallas disponibles: " + Arrays.toString(TallaCamiseta.values()));
            System.out.print("Seleccione talla: ");
            TallaCamiseta talla = TallaCamiseta.valueOf(SCANNER.nextLine().trim().toUpperCase());
            Inscripcion inscripcion = inscripcionService.registrarInscripcion(
                    carrera,
                    corredor,
                    categoria,
                    talla,
                    administrador
            );
            System.out.println("Inscripcion creada con ID: " + inscripcion.getIdInscripcion());
        } catch (IllegalArgumentException ex) {
            System.out.println("No se pudo completar la inscripcion: " + ex.getMessage());
        }
    }

    /** Registra un pago para una inscripcion de un corredor y confirma la inscripcion.
     * @param inscripcionService Servicio para gestionar inscripciones.
     * @param corredor Corredor que realiza el pago.
     */
    private static void registrarPagoCorredor(InscripcionService inscripcionService, Corredor corredor) {
        mostrarInscripcionesCorredor(corredor);
        System.out.print("ID de la inscripcion a pagar: ");
        String idInscripcion = SCANNER.nextLine().trim();
        Optional<Inscripcion> inscripcionOpt = corredor.getInscripciones().stream()
                .filter(inscripcion -> inscripcion.getIdInscripcion().equals(idInscripcion))
                .findFirst();
        if (!inscripcionOpt.isPresent()) {
            System.out.println("No tienes una inscripcion con ese ID.");
            return;
        }
        BigDecimal monto = leerMonto("Monto del pago: ");
        System.out.print("Descripcion: ");
        String descripcion = SCANNER.nextLine().trim();
        try {
            Pago pago = new Pago("PAY-" + idInscripcion, monto, descripcion, LocalDateTime.now());
            inscripcionService.registrarPago(idInscripcion, pago);
            inscripcionService.confirmarInscripcion(idInscripcion);
            System.out.println("Pago registrado e inscripcion confirmada.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.println("No se pudo registrar el pago: " + ex.getMessage());
        }
    }

    /** Muestra las inscripciones del corredor.
     * @param corredor Corredor cuyo inscripciones se van a mostrar.
     */
    private static void mostrarInscripcionesCorredor(Corredor corredor) {
        System.out.println("\nTus inscripciones:");
        if (corredor.getInscripciones().isEmpty()) {
            System.out.println("No tienes inscripciones registradas.");
            return;
        }
        corredor.getInscripciones().forEach(inscripcion -> {
            System.out.println("- " + inscripcion.getIdInscripcion() + " | Carrera: " + inscripcion.getCarrera().getNombre()
                    + " | Categoria: " + inscripcion.getCategoriaSeleccionada().getNombre()
                    + " | Estado: " + inscripcion.getEstado()
                    + " | Pago: " + (inscripcion.getPago() != null ? inscripcion.getPago().getMonto() : "Pendiente"));
        });
    }

    /** Muestra los resultados del corredor.
     * @param corredor Corredor cuyos resultados se van a mostrar.
     */
    private static void mostrarResultadosCorredor(Corredor corredor) {
        System.out.println("\nResultados registrados:");
        List<Resultado> resultados = corredor.verMisResultados();
        if (resultados.isEmpty()) {
            System.out.println("AUn no tienes resultados.");
            return;
        }
        resultados.forEach(resultado -> System.out.println(
                "- " + resultado.getIdResultado() + " | Tiempo: " + resultado.getTiempoSegundos() +
                        " s | Posicion general: " + resultado.getPosicionGeneral() +
                        " | Posicion categoria: " + resultado.getPosicionCategoria()
        ));
    }

    /** Muestra los datos personales del corredor.
     * @param corredor Corredor cuyos datos se van a mostrar.
     */
    private static void mostrarDatosPersonales(Corredor corredor) {
        System.out.println("\n--- Datos personales ---");
        System.out.println("Nombre: " + corredor.getNombreCompleto());
        System.out.println("Correo: " + corredor.getCorreo());
        System.out.println("Telefono: " + corredor.getTelefono());
        System.out.println("Fecha de nacimiento: " + corredor.getFechaNacimiento());
        System.out.println("Genero: " + corredor.getGenero());
        System.out.println("Tipo de sangre: " + corredor.getTipoSangre());
        System.out.println("Contactos de emergencia:");
        if (corredor.getContactos().isEmpty()) {
            System.out.println("  No hay contactos de emergencia registrados.");
            return;
        }
        corredor.getContactos().forEach(contacto -> System.out.println(
                "  - " + contacto.getNombre() + " (" + contacto.getRelacion() + ") - Tel: " + contacto.getTelefono()
        ));
    }

    /** Muestra los mensajes privados del usuario.
     * @param usuario Usuario cuyos mensajes se van a mostrar.
     */
    private static void mostrarMensajes(Usuario usuario) {
        System.out.println("\nMensajes privados:");
        if (usuario.getMensajes().isEmpty()) {
            System.out.println("No tienes mensajes privados.");
            return;
        }
        boolean hayPrivados = false;
        for (Mensaje mensaje : usuario.getMensajes()) {
            if (mensaje.esGeneral()) {
                continue;
            }
            hayPrivados = true;
            String cabecera;
            if (usuario.equals(mensaje.getRemitente())) {
                cabecera = "Para " + obtenerNombreUsuario(mensaje.getDestinatario());
            } else {
                cabecera = "De " + obtenerNombreUsuario(mensaje.getRemitente());
            }
            System.out.println("- [" + mensaje.getEnviadoEn() + "] " + cabecera + ": " + mensaje.getContenido());
        }
        if (!hayPrivados) {
            System.out.println("No tienes mensajes privados.");
        }
    }

    /** Muestra los mensajes del chat general en la consola.
     * @param comunicacionService Servicio para gestionar comunicaciones.
     */
    private static void mostrarChatGeneral(ComunicacionService comunicacionService) {
        System.out.println("\n--- Chat General ---");
        List<Mensaje> mensajes = comunicacionService.obtenerChatGeneral();
        if (mensajes.isEmpty()) {
            System.out.println("No hay mensajes en el chat general.");
            return;
        }
        for (Mensaje mensaje : mensajes) {
            if (!mensaje.esGeneral()) {
                continue;
            }
            String autor = obtenerNombreUsuario(mensaje.getRemitente());
            System.out.println("- [" + mensaje.getEnviadoEn() + "] " + autor + ": " + mensaje.getContenido());
        }
    }

    /** Envía un mensaje privado del corredor al administrador.
     * @param comunicacionService Servicio para gestionar comunicaciones.
     * @param corredor Corredor que envia el mensaje.
     * @param administrador Administrador que recibe el mensaje.
     */
    private static void enviarMensajeAdministrador(ComunicacionService comunicacionService, Corredor corredor,
                                                   Administrador administrador) {
        System.out.print("Contenido del mensaje: ");
        String contenido = SCANNER.nextLine().trim();
        if (contenido.isEmpty()) {
            System.out.println("El contenido no puede estar vacio.");
            return;
        }
        comunicacionService.enviarMensajePrivado(
                "MSG-" + System.currentTimeMillis(),
                contenido,
                corredor,
                administrador
        );
        System.out.println("Mensaje enviado al administrador.");
    }

    /** Agrega un recurso multimedia a un evento existente.
     * @param eventoService Servicio para gestionar eventos.
     */
    private static void agregarMultimediaAEvento(EventoService eventoService) {
        System.out.println("\n--- Agregar Multimedia a Evento ---");
        System.out.print("ID del evento: ");
        String idEvento = SCANNER.nextLine().trim();
        Optional<Evento> eventoOpt = eventoService.buscarPorId(idEvento);
        if (!eventoOpt.isPresent()) {
            System.out.println("Evento no encontrado.");
            return;
        }
        Evento evento = eventoOpt.get();
        System.out.println("Tipos de multimedia: " + Arrays.toString(TipoMultimedia.values()));
        System.out.print("Tipo: ");
        TipoMultimedia tipo = TipoMultimedia.valueOf(SCANNER.nextLine().trim().toUpperCase());
        System.out.print("URL: ");
        String url = SCANNER.nextLine().trim();
        System.out.print("Descripcion: ");
        String descripcion = SCANNER.nextLine().trim();
        Multimedia multimedia = new Multimedia("MM-" + System.currentTimeMillis(), tipo, url, descripcion);
        evento.agregarMultimedia(multimedia);
        System.out.println("Recurso agregado al evento.");
    }

    /** Permite al usuario seleccionar una carrera de un evento.
     * @param eventoService Servicio para gestionar eventos.
     * @return La carrera seleccionada o null si no se encuentra.
     */
    private static Carrera seleccionarCarrera(EventoService eventoService) {
        listarEventos(eventoService);
        System.out.print("ID del evento: ");
        String idEvento = SCANNER.nextLine().trim();
        Optional<Evento> eventoOpt = eventoService.buscarPorId(idEvento);
        if (!eventoOpt.isPresent()) {
            System.out.println("Evento no encontrado.");
            return null;
        }
        Evento evento = eventoOpt.get();
        System.out.print("ID de la carrera: ");
        String idCarrera = SCANNER.nextLine().trim();
        Optional<Carrera> carreraOpt = evento.getCarreras().stream()
                .filter(carrera -> carrera.getIdCarrera().equals(idCarrera))
                .findFirst();
        if (!carreraOpt.isPresent()) {
            System.out.println("Carrera no encontrada.");
            return null;
        }
        return carreraOpt.get();
    }

    /** Permite al usuario seleccionar una categoria de una carrera.
     * @param carrera Carrera de la cual seleccionar la categoria.
     * @return La categoria seleccionada o null si no se encuentra.
     */
    private static Categoria seleccionarCategoria(Carrera carrera) {
        if (carrera.getCategorias().isEmpty()) {
            System.out.println("La carrera no tiene categorias registradas.");
            return null;
        }
        System.out.println("Categorias disponibles: ");
        for (int i = 0; i < carrera.getCategorias().size(); i++) {
            Categoria categoria = carrera.getCategorias().get(i);
            System.out.println((i + 1) + ". " + categoria.getNombre() + " (" + categoria.getEdadMinima() +
                    "-" + categoria.getEdadMaxima() + " anos)");
        }
        int opcion = leerEntero("Seleccione una categoria: ");
        if (opcion < 1 || opcion > carrera.getCategorias().size()) {
            System.out.println("Opcion invalida.");
            return null;
        }
        return carrera.getCategorias().get(opcion - 1);
    }

    /** Lee un entero desde la entrada estandar con manejo de errores.
     * @param mensaje Mensaje a mostrar al usuario.
     * @return El entero ingresado por el usuario.
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    /** Lee un double desde la entrada estandar con manejo de errores.
     * @param mensaje Mensaje a mostrar al usuario.
     * @return El double ingresado por el usuario.
     */
    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine().trim();
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero decimal valido.");
            }
        }
    }

    /** Lee un BigDecimal desde la entrada estandar con manejo de errores.
     * @param mensaje Mensaje a mostrar al usuario.
     * @return El BigDecimal ingresado por el usuario.
     */
    private static BigDecimal leerMonto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine().trim();
            try {
                return new BigDecimal(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un monto valido.");
            }
        }
    }

    /** Lee una fecha desde la entrada estandar con manejo de errores.
     * @param mensaje Mensaje a mostrar al usuario.
     * @return La fecha ingresada por el usuario.
     */
    private static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine().trim();
            try {
                return LocalDate.parse(entrada);
            } catch (DateTimeParseException ex) {
                System.out.println("Formato de fecha invalido. Use yyyy-MM-dd.");
            }
        }
    }

    /** Lee una hora desde la entrada estandar con manejo de errores.
     * @param mensaje Mensaje a mostrar al usuario.
     * @return La hora ingresada por el usuario.
     */
    private static LocalTime leerHora(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine().trim();
            try {
                return LocalTime.parse(entrada);
            } catch (DateTimeParseException ex) {
                System.out.println("Formato de hora invalido. Use HH:mm.");
            }
        }
    }

    /** Obtiene una representacion legible del nombre de un usuario.
     * @param usuario Usuario del cual obtener el nombre.
     * @return Nombre legible del usuario.
     */
    private static String obtenerNombreUsuario(Usuario usuario) {
        if (usuario == null) {
            return "Desconocido";
        }
        if (usuario instanceof Corredor) {
            Corredor corredor = (Corredor) usuario;
            return corredor.getNombreCompleto() + " (" + corredor.getCorreo() + ")";
        }
        return usuario.getCorreo();
    }
}
