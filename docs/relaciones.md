# Modelo alineado al diagrama propuesto

Este prototipo implementa el modelo solicitado para la gestion de eventos deportivos tomando como referencia la Carrera del Informatico. Las clases siguen la estructura del diagrama PlantUML incluido y se complementan con pequenos ajustes para sostener la logica de negocio.

## Clases principales
- **Usuario / Administrador / Corredor**: Usuario provee autenticacion basica y registro de mensajes. Administrador implementa IRegistroTiempo para generar y confirmar resultados sobre inscripciones, mientras que Corredor encapsula datos personales, contactos de emergencia y el historial de inscripciones.
- **Evento / Carrera / Categoria**: un evento contiene carreras (composicion) y puede asociar material multimedia. Cada carrera agrega categorias y administra el ciclo de vida de sus inscripciones; solo puede generar nuevas inscripciones cuando la fase esta abierta.
- **Inscripcion / Pago / Resultado**: la inscripcion conoce su pago (0..1) y compone el resultado (0..1). Para registrar tiempos debe encontrarse en estado Confirmada y el administrador responsable adjunta el resultado.
- **Mensajeria y multimedia**: Mensaje modela la comunicacion entre usuarios, mientras que Multimedia almacena material de apoyo clasificado por TipoMultimedia.

## Relaciones POO destacadas
- **Herencia**: Administrador y Corredor extienden Usuario, reutilizando autenticacion y mensajeria.
- **Interfaz**: Administrador implementa IRegistroTiempo para exponer la operacion egistrarTiempo sobre inscripciones.
- **Composicion**:
  - Evento *-- Carrera: las carreras solo existen dentro del evento que las programa.
  - Carrera *-- Inscripcion: las inscripciones dependen del ciclo de vida de su carrera.
  - Corredor *-- ContactoEmergencia: los contactos de emergencia se destruyen con el corredor.
  - Inscripcion *-- Resultado: el resultado deja de existir al eliminar la inscripcion.
- **Agregacion**:
  - Carrera o-- Categoria: las categorias pueden compartirse entre carreras.
  - Evento o-- Multimedia: el material multimedia puede reutilizarse fuera del evento.
- **Asociaciones**:
  - Corredor --> Inscripcion: un corredor mantiene un historial de inscripciones (0..*).
  - Inscripcion --> Pago: el pago es opcional y puede existir de forma independiente.
  - Usuario --> Mensaje: los usuarios envian o reciben mensajes sin dependencia de ciclo de vida.

## Cobertura funcional
- Registro y administracion de eventos y carreras (estados, categorias, material multimedia).
- Gestion de corredores con datos personales, contactos de emergencia y tallas de camiseta.
- Flujo de inscripcion: registro, pago (Pago con BigDecimal y LocalDateTime), confirmacion y asignacion de resultados.
- Registro de tiempos por parte del administrador a traves de ingresarResultado/egistrarTiempo.
- Comunicacion interna mediante chat general y mensajes privados gestionados por ComunicacionService.

Los servicios (EventoService, InscripcionService, TiempoService, ComunicacionService) coordinan las entidades sin romper el encapsulamiento del dominio y permiten expandir el prototipo hacia persistencia o interfaces de usuario futuras.
