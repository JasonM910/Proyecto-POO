package model;

import java.util.Objects;

/**
 * La clase Inscripcion representa la inscripción de un corredor en una carrera y categoría específica.
 * Permite gestionar el estado de la inscripción, el pago, la talla de camiseta y el resultado obtenido.
 */
public class Inscripcion {
    private final String idInscripcion;
    private final int numeroDorsal;
    private TallaCamiseta tallaCamiseta;
    private EstadoInscripcion estado;
    private final Corredor corredor;
    private final Carrera carrera;
    private final Categoria categoriaSeleccionada;
    private Pago pago;
    private Resultado resultado;

    /**
     * Crea una nueva inscripción para un corredor en una carrera y categoría específica.
     * @param idInscripcion Identificador único de la inscripción.
     * @param numeroDorsal Número de dorsal asignado.
     * @param corredor Corredor inscrito.
     * @param carrera Carrera en la que se inscribe.
     * @param categoriaSeleccionada Categoría seleccionada.
     * @throws NullPointerException si algún parámetro es nulo.
     */
    public Inscripcion(String idInscripcion, int numeroDorsal, Corredor corredor, Carrera carrera,
                       Categoria categoriaSeleccionada) {
        this.idInscripcion = Objects.requireNonNull(idInscripcion, "El identificador de la inscripcion no puede ser nulo");
        this.numeroDorsal = numeroDorsal;
        this.corredor = Objects.requireNonNull(corredor, "El corredor no puede ser nulo");
        this.carrera = Objects.requireNonNull(carrera, "La carrera no puede ser nula");
        this.categoriaSeleccionada = Objects.requireNonNull(categoriaSeleccionada, "La categoria seleccionada no puede ser nula");
        this.estado = EstadoInscripcion.Pendiente;
    }

    /**
     * Obtiene el identificador único de la inscripción.
     * @return idInscripcion
     */
    public String getIdInscripcion() {
        return idInscripcion;
    }

    /**
     * Obtiene el número de dorsal asignado a la inscripción.
     * @return número de dorsal
     */
    public int getNumeroDorsal() {
        return numeroDorsal;
    }

    /**
     * Obtiene la talla de camiseta seleccionada para la inscripción.
     * @return talla de camiseta
     */
    public TallaCamiseta getTallaCamiseta() {
        return tallaCamiseta;
    }

    /**
     * Asigna la talla de camiseta a la inscripción.
     * @param tallaCamiseta Talla de camiseta.
     * @throws NullPointerException si la talla es nula.
     */
    public void asignarTallaCamiseta(TallaCamiseta tallaCamiseta) {
        this.tallaCamiseta = Objects.requireNonNull(tallaCamiseta, "La talla de camiseta no puede ser nula");
    }

    /**
     * Obtiene el estado actual de la inscripción.
     * @return estado
     */
    public EstadoInscripcion getEstado() {
        return estado;
    }

    /**
     * Obtiene el corredor inscrito.
     * @return corredor
     */
    public Corredor getCorredor() {
        return corredor;
    }

    /**
     * Obtiene la carrera en la que se inscribió el corredor.
     * @return carrera
     */
    public Carrera getCarrera() {
        return carrera;
    }

    /**
     * Obtiene la categoría seleccionada por el corredor.
     * @return categoría seleccionada
     */
    public Categoria getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    /**
     * Obtiene el pago realizado para la inscripción.
     * @return pago
     */
    public Pago getPago() {
        return pago;
    }

    /**
     * Obtiene el resultado registrado para la inscripción.
     * @return resultado
     */
    public Resultado getResultado() {
        return resultado;
    }

    /**
     * Realiza el pago de la inscripción.
     * @param pago Pago a registrar.
     * @return true si el pago se realizó correctamente, false si ya existía un pago.
     * @throws NullPointerException si el pago es nulo.
     */
    public boolean pagar(Pago pago) {
        if (this.pago != null) {
            return false;
        }
        this.pago = Objects.requireNonNull(pago, "El pago no puede ser nulo");
        this.estado = EstadoInscripcion.Pagada;
        return true;
    }

    /**
     * Confirma la inscripción si el pago ha sido realizado.
     * @throws IllegalStateException si no se ha realizado el pago.
     */
    public void confirmar() {
        if (pago == null) {
            throw new IllegalStateException("La inscripcion debe contar con un pago para ser confirmada");
        }
        this.estado = EstadoInscripcion.Confirmada;
    }

    /**
     * Establece el resultado de la inscripción si está confirmada.
     * @param resultado Resultado a registrar.
     * @throws IllegalStateException si la inscripción no está confirmada.
     * @throws NullPointerException si el resultado es nulo.
     */
    public void establecerResultado(Resultado resultado) {
        if (estado != EstadoInscripcion.Confirmada) {
            throw new IllegalStateException("La inscripcion debe estar confirmada antes de registrar resultados");
        }
        this.resultado = Objects.requireNonNull(resultado, "El resultado no puede ser nulo");
    }
}
