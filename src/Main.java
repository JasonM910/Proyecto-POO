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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        EventoService eventoService = new EventoService();
        InscripcionService inscripcionService = new InscripcionService();
        TiempoService tiempoService = new TiempoService();
        ComunicacionService comunicacionService = new ComunicacionService();

        Map<String, Administrador> administradores = new HashMap<>();
        Map<String, Corredor> corredores = new HashMap<>();

        inicializarDatos(eventoService, administradores, corredores);

        boolean salir = false;
        while (!salir) {
            System.out.println("=================================");
            System.out.println("  Sistema Carrera del Informatico");
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

    private static void inicializarDatos(EventoService eventoService,
                                         Map<String, Administrador> administradores,
                                         Map<String, Corredor> corredores) {
        Administrador administrador = new Administrador("ADM-1", "admin@evento.com", "segura");
        administradores.put(administrador.getCorreo(), administrador);
        Evento evento = new Evento(
                "EVT-1",
                "Carrera del Informatico",
                LocalDate.of(2024, 10, 1),
                LocalTime.of(8, 0),
                "Evento anual para la comunidad de tecnologia",
                "Campus San Carlos",
                TipoActividad.CARRERA
        );
        evento.actualizarEstado(EstadoEvento.Programada);
        Carrera carrera10K = new Carrera("CAR-10", "Carrera 10K", 10.0, evento.getFecha());
        carrera10K.agregarCategoria(new Categoria("General", 18, 65));
        carrera10K.agregarCategoria(new Categoria("MAster", 36, 80));
        carrera10K.abrirInscripcion();
        evento.agregarCarrera(carrera10K);
        evento.agregarMultimedia(new Multimedia(
                "MM-1",
                TipoMultimedia.Video,
                "https://example.com/calentar",
                "Rutina de calentamiento previa a la carrera"
        ));
        evento.agregarMultimedia(new Multimedia(
                "MM-2",
                TipoMultimedia.Documento,
                "https://example.com/reglamento.pdf",
                "Reglamento oficial del evento"
        ));
        eventoService.registrarEvento(evento);
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
        corredores.put(corredor.getCorreo(), corredor);
    }

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
            System.out.println("10. Enviar mensaje a corredor");
            System.out.println("11. Agregar recurso multimedia a evento");
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
                    publicarMensajeGeneral(comunicacionService, administrador);
                    break;
                case 10:
                    enviarMensajeACorredor(comunicacionService, administrador, corredores);
                    break;
                case 11:
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
            System.out.println("6. Ver mensajes recibidos");
            System.out.println("7. Enviar mensaje al administrador");
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
                    mostrarMensajes(corredor);
                    break;
                case 7:
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
            String idCorredor = "COR-" + (corredores.size() + 1);
            Corredor corredor = new Corredor(idCorredor, correo, contrasena, idCorredor, nombre,
                    telefono, fechaNacimiento, genero, tipoSangre);
            corredores.put(correo, corredor);
            System.out.println("Corredor registrado correctamente.");
        } catch (IllegalArgumentException ex) {
            System.out.println("No se pudo registrar el corredor: " + ex.getMessage());
        }
    }

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

    private static void publicarMensajeGeneral(ComunicacionService comunicacionService, Administrador administrador) {
        System.out.println("\n--- Publicar Mensaje General ---");
        System.out.print("Contenido del mensaje: ");
        String contenido = SCANNER.nextLine().trim();
        if (contenido.isEmpty()) {
            System.out.println("El contenido no puede estar vacio.");
            return;
        }
        comunicacionService.publicarMensajeGeneral("MSG-" + System.currentTimeMillis(), contenido, administrador);
        System.out.println("Mensaje publicado en el chat general.");
    }

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

    private static void mostrarMensajes(Usuario usuario) {
        System.out.println("\nMensajes recibidos:");
        if (usuario.getMensajes().isEmpty()) {
            System.out.println("No tienes mensajes.");
            return;
        }
        usuario.getMensajes().forEach(mensaje -> System.out.println(
                "- [" + mensaje.getEnviadoEn() + "] " + mensaje.getContenido()
        ));
    }

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
}
