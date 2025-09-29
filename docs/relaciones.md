# Modelo alineado al diagrama propuesto

Este prototipo implementa el modelo solicitado para la gestion de eventos deportivos tomando como referencia la Carrera del Informatico. Las clases siguen la estructura del diagrama PlantUML incluido y se complementan con ajustes recientes para extender los metadatos de eventos, la seleccion de categorias y la visibilidad de recursos multimedia.

## Clases principales
- **Usuario / Administrador / Corredor**: Usuario provee autenticacion basica y registro de mensajes. Administrador implementa IRegistroTiempo para confirmar resultados y ahora puede registrar nuevos corredores desde la consola. Corredor encapsula datos personales, contactos de emergencia, historial de inscripciones y la categoria elegida en cada carrera.
- **Evento / Carrera / Categoria**: un evento contiene carreras (composicion) y ahora almacena la hora de realizacion y el TipoActividad. Cada carrera agrega categorias y administra el ciclo de vida de sus inscripciones; durante una inscripcion se valida que la categoria seleccionada pertenezca a la carrera.
- **Inscripcion / Pago / Resultado**: la inscripcion guarda el dorsal, la talla, el estado (pendiente/pagada/confirmada), el pago asociado, el resultado y la categoria seleccionada. Para registrar tiempos la inscripcion debe encontrarse confirmada.
- **Mensajeria y multimedia**: Mensaje modela la comunicacion entre usuarios, mientras que Multimedia almacena material de apoyo clasificado por TipoMultimedia. La consola permite al administrador adjuntar nuevos recursos a un evento y a ambos perfiles consultarlos.

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
  - Inscripcion --> Categoria: toda inscripcion referencia la categoria que el corredor eligio.
  - Inscripcion --> Pago: el pago es opcional y puede existir de forma independiente.
  - Usuario --> Mensaje: los usuarios envian o reciben mensajes sin dependencia de ciclo de vida.

## Cobertura funcional
- Registro y administracion de eventos con fecha, hora, ubicacion, tipo de actividad y recursos multimedia.
- Gestion de corredores con datos personales completos, contactos de emergencia, registro via consola y seleccion de categorias durante la inscripcion.
- Flujo de inscripcion: registro, pago (Pago con BigDecimal y LocalDateTime), confirmacion y asignacion de resultados vinculados a posiciones general y por categoria.
- Registro de tiempos por parte del administrador a traves de ingresarResultado/egistrarTiempo.
- Comunicacion interna mediante chat general y mensajes privados gestionados por ComunicacionService, con opciones visibles en los menues de ambos perfiles.
