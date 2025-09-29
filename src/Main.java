import model.Administrador;
import model.Carrera;
import model.Categoria;
import model.ContactoEmergencia;
import model.Corredor;
import model.Evento;
import model.EstadoEvento;
import model.EstadoInscripcion;
import model.Inscripcion;
import model.Multimedia;
import model.Pago;
import model.Resultado;
import model.TallaCamiseta;
import model.TipoMultimedia;
import model.Usuario;
import model.Genero;
import model.TipoSangre;
import service.ComunicacionService;
import service.EventoService;
import service.InscripcionService;
import service.TiempoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            System.out.println("1. Iniciar sesi�n como administrador");
            System.out.println("2. Iniciar sesi�n como corredor");
            System.out.println("0. Salir");
            int opcion = leerEntero("Seleccione una opci�n: ");
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
                    System.out.println("Opci�n no v�lida. Intente nuevamente.");
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
                "Carrera del Inform�tico",
                LocalDate.of(2024, 10, 1),
                "Evento anual para la comunidad de tecnolog�a",
                "Campus San Carlos"
        );
        evento.actualizarEstado(EstadoEvento.Programada);

        Carrera carrera10K = new Carrera("CAR-10", "Carrera 10K", 10.0, evento.getFecha());
        carrera10K.agregarCategoria(new Categoria("General", 18, 65));
        carrera10K.agregarCategoria(new Categoria("M�ster", 36, 80));
        carrera10K.abrirInscripcion();
        evento.agregarCarrera(carrera10K);

        evento.agregarMultimedia(new Multimedia(
                "MM-1",
                TipoMultimedia.Video,
                "https://example.com/calentar",
                "Rutina de calentamiento previa a la carrera"
        ));

        eventoService.registrarEvento(evento);

        Corredor corredor = new Corredor(
                "USR-1",
                "ana@example.com",
                "contrasena",
                "COR-1",
                "Ana P�rez",
                "555-0101",
                LocalDate.of(1994, 3, 21),
                Genero.Femenino,
                TipoSangre.OPositivo
        );
        corredor.agregarContacto(new ContactoEmergencia("Luis P�rez", "555-1234", "Hermano"));
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
            System.out.println("\n--- Men� Administrador ---");
            System.out.println("1. Listar eventos");
            System.out.println("2. Crear evento");
            System.out.println("3. Agregar carrera a evento");
            System.out.println("4. Abrir/cerrar inscripci�n de carrera");
            System.out.println("5. Registrar inscripci�n de un corredor");
            System.out.println("6. Registrar pago y confirmar inscripci�n");
            System.out.println("7. Registrar resultado");
            System.out.println("8. Publicar mensaje general");
            System.out.println("9. Enviar mensaje a corredor");
            System.out.println("0. Cerrar sesi�n");
            int opcion = leerEntero("Seleccione una opci�n: ");
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
                    registrarInscripcion(inscripcionService, eventoService, administrador, corredores);
                    break;
                case 6:
                    registrarPago(inscripcionService);
                    break;
                case 7:
                    registrarResultado(tiempoService, inscripcionService, administrador);
                    break;
                case 8:
                    publicarMensajeGeneral(comunicacionService, administrador);
                    break;
                case 9:
                    enviarMensajeACorredor(comunicacionService, administrador, corredores);
                    break;
                case 0:
                    administrador.cerrarSesion();
                    salir = true;
                    break;
                default:
                    System.out.println("Opci�n no v�lida.");
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
            System.out.println("\n--- Men� Corredor ---");
            System.out.println("1. Ver eventos y carreras disponibles");
            System.out.println("2. Inscribirme en una carrera");
            System.out.println("3. Registrar pago de inscripci�n");
            System.out.println("4. Ver mis inscripciones");
            System.out.println("5. Ver mis resultados");
            System.out.println("6. Ver mis mensajes");
            System.out.println("7. Enviar mensaje al administrador");
            System.out.println("0. Cerrar sesi�n");
            int opcion = leerEntero("Seleccione una opci�n: ");
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
                    System.out.println("Opci�n no v�lida.");
                    break;
            }
        }
    }

    private static Administrador autenticarAdministrador(Map<String, Administrador> administradores) {
        System.out.println("\n--- Inicio de sesi�n Administrador ---");
        System.out.print("Correo: ");
        String correo = SCANNER.nextLine().trim();
        System.out.print("Contrase�a: ");
        String contrasena = SCANNER.nextLine().trim();

        Administrador administrador = administradores.get(correo);
        if (administrador == null || !administrador.iniciarSesion(correo, contrasena)) {
            System.out.println("Credenciales inv�lidas.");
            return null;
        }
        return administrador;
    }

    private static Corredor autenticarCorredor(Map<String, Corredor> corredores) {
        System.out.println("\n--- Inicio de sesi�n Corredor ---");
        System.out.print("Correo: ");
        String correo = SCANNER.nextLine().trim();
        System.out.print("Contrase�a: ");
        String contrasena = SCANNER.nextLine().trim();

        Corredor corredor = corredores.get(correo);
        if (corredor == null || !corredor.iniciarSesion(correo, contrasena)) {
            System.out.println("Credenciales inv�lidas.");
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
            System.out.println("- " + evento.getIdEvento() + " | " + evento.getNombre() + " | " + evento.getFecha());
            evento.getCarreras().forEach(carrera -> {
                System.out.println("    * " + carrera.getIdCarrera() + " - " + carrera.getNombre() +
                        " | Distancia: " + carrera.getDistanciaKm() + " km | Inscripci�n abierta: " +
                        (carrera.isInscripcionAbierta() ? "S�" : "No"));
            });
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
            System.out.print("Descripci�n: ");
            String descripcion = SCANNER.nextLine().trim();
            System.out.print("Ubicaci�n: ");
            String ubicacion = SCANNER.nextLine().trim();

            Evento evento = new Evento(id, nombre, fecha, descripcion, ubicacion);
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
                System.out.print("Nombre de categor�a (vac�o para terminar): ");
                String nombreCategoria = SCANNER.nextLine().trim();
                if (nombreCategoria.isEmpty()) {
                    agregarMasCategorias = false;
                } else {
                    int edadMinima = leerEntero("Edad m�nima: ");
                    int edadMaxima = leerEntero("Edad m�xima: ");
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
        System.out.println("\n--- Abrir/Cerrar Inscripci�n ---");
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
            System.out.println("Inscripci�n cerrada.");
        } else {
            carrera.abrirInscripcion();
            System.out.println("Inscripci�n abierta.");
        }
    }

    private static void registrarInscripcion(InscripcionService inscripcionService, EventoService eventoService,
                                             Administrador administrador, Map<String, Corredor> corredores) {
        System.out.println("\n--- Registrar Inscripci�n ---");
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
            System.out.println("La inscripci�n para esta carrera no est� abierta.");
            return;
        }
        try {
            System.out.println("Tallas disponibles: " + Arrays.toString(TallaCamiseta.values()));
            System.out.print("Seleccione talla: ");
            String talla = SCANNER.nextLine().trim();
            Inscripcion inscripcion = inscripcionService.registrarInscripcion(
                    carrera,
                    corredor,
                    TallaCamiseta.valueOf(talla),
                    administrador
            );
            System.out.println("Inscripci�n registrada con ID: " + inscripcion.getIdInscripcion());
        } catch (IllegalArgumentException ex) {
            System.out.println("Error al registrar la inscripci�n: " + ex.getMessage());
        }
    }

    private static void registrarPago(InscripcionService inscripcionService) {
        System.out.println("\n--- Registrar Pago ---");
        System.out.print("ID de la inscripci�n: ");
        String idInscripcion = SCANNER.nextLine().trim();
        BigDecimal monto = leerMonto("Monto del pago: ");
        System.out.print("Descripci�n: ");
        String descripcion = SCANNER.nextLine().trim();
        try {
            Pago pago = new Pago("PAY-" + idInscripcion, monto, descripcion, LocalDateTime.now());
            inscripcionService.registrarPago(idInscripcion, pago);
            inscripcionService.confirmarInscripcion(idInscripcion);
            System.out.println("Pago registrado e inscripci�n confirmada.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.println("No se pudo registrar el pago: " + ex.getMessage());
        }
    }

    private static void registrarResultado(TiempoService tiempoService, InscripcionService inscripcionService,
                                           Administrador administrador) {
        System.out.println("\n--- Registrar Resultado ---");
        System.out.print("ID de la inscripci�n: ");
        String idInscripcion = SCANNER.nextLine().trim();
        Optional<Inscripcion> inscripcionOpt = inscripcionService.buscarPorId(idInscripcion);
    if (!inscripcionOpt.isPresent()) {
            System.out.println("Inscripci�n no encontrada.");
            return;
        }
        Inscripcion inscripcion = inscripcionOpt.get();
        if (inscripcion.getEstado() != EstadoInscripcion.Confirmada) {
            System.out.println("La inscripci�n debe estar confirmada antes de registrar un resultado.");
            return;
        }
        double tiempo = leerDouble("Tiempo en segundos: ");
        int posicionGeneral = leerEntero("Posici�n general: ");
        int posicionCategoria = leerEntero("Posici�n por categor�a: ");
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
            System.out.println("El contenido no puede estar vac�o.");
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
            System.out.println("El contenido no puede estar vac�o.");
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
            System.out.println("La inscripci�n para esta carrera no est� abierta.");
            return;
        }
        try {
            System.out.println("Tallas disponibles: " + Arrays.toString(TallaCamiseta.values()));
            System.out.print("Seleccione talla: ");
            String talla = SCANNER.nextLine().trim();
            Inscripcion inscripcion = inscripcionService.registrarInscripcion(
                    carrera,
                    corredor,
                    TallaCamiseta.valueOf(talla),
                    administrador
            );
            System.out.println("Inscripci�n creada con ID: " + inscripcion.getIdInscripcion());
        } catch (IllegalArgumentException ex) {
            System.out.println("No se pudo completar la inscripci�n: " + ex.getMessage());
        }
    }

    private static void registrarPagoCorredor(InscripcionService inscripcionService, Corredor corredor) {
        mostrarInscripcionesCorredor(corredor);
        System.out.print("ID de la inscripci�n a pagar: ");
        String idInscripcion = SCANNER.nextLine().trim();
        Optional<Inscripcion> inscripcionOpt = corredor.getInscripciones().stream()
                .filter(inscripcion -> inscripcion.getIdInscripcion().equals(idInscripcion))
                .findFirst();
    if (!inscripcionOpt.isPresent()) {
            System.out.println("No tienes una inscripci�n con ese ID.");
            return;
        }
        BigDecimal monto = leerMonto("Monto del pago: ");
        System.out.print("Descripci�n: ");
        String descripcion = SCANNER.nextLine().trim();
        try {
            Pago pago = new Pago("PAY-" + idInscripcion, monto, descripcion, LocalDateTime.now());
            inscripcionService.registrarPago(idInscripcion, pago);
            inscripcionService.confirmarInscripcion(idInscripcion);
            System.out.println("Pago registrado e inscripci�n confirmada.");
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
                    + " | Estado: " + inscripcion.getEstado()
                    + " | Pago: " + (inscripcion.getPago() != null ? inscripcion.getPago().getMonto() : "Pendiente"));
        });
    }

    private static void mostrarResultadosCorredor(Corredor corredor) {
        System.out.println("\nResultados registrados:");
        List<Resultado> resultados = corredor.verMisResultados();
        if (resultados.isEmpty()) {
            System.out.println("A�n no tienes resultados.");
            return;
        }
        resultados.forEach(resultado -> System.out.println(
                "- " + resultado.getIdResultado() + " | Tiempo: " + resultado.getTiempoSegundos() +
                        " s | Posici�n general: " + resultado.getPosicionGeneral() +
                        " | Posici�n categor�a: " + resultado.getPosicionCategoria()
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
            System.out.println("El contenido no puede estar vac�o.");
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

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un n�mero v�lido.");
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
                System.out.println("Ingrese un n�mero decimal v�lido.");
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
                System.out.println("Ingrese un monto v�lido.");
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
                System.out.println("Formato de fecha inv�lido. Use yyyy-MM-dd.");
            }
        }
    }
}
