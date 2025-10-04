package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * La clase Pago representa el pago realizado por una inscripción, incluyendo monto, descripción y fecha.
 */
public class Pago {
    private final String idPago;
    private final BigDecimal monto;
    private final String descripcion;
    private final LocalDateTime fecha;

    /**
     * Crea un nuevo pago para una inscripción.
     * @param idPago Identificador único del pago.
     * @param monto Monto del pago.
     * @param descripcion Descripción del pago.
     * @param fecha Fecha y hora del pago.
     * @throws NullPointerException si algún parámetro es nulo.
     * @throws IllegalArgumentException si el monto es nulo o no es positivo.
     */
    public Pago(String idPago, BigDecimal monto, String descripcion, LocalDateTime fecha) {
        this.idPago = Objects.requireNonNull(idPago, "El identificador del pago no puede ser nulo");
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser positivo");
        }
        this.monto = monto;
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.fecha = Objects.requireNonNull(fecha, "La fecha del pago no puede ser nula");
    }

    /**
     * Obtiene el identificador único del pago.
     * @return idPago
     */
    public String getIdPago() {
        return idPago;
    }

    /**
     * Obtiene el monto del pago.
     * @return monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * Obtiene la descripción del pago.
     * @return descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene la fecha y hora en que se realizó el pago.
     * @return fecha y hora del pago
     */
    public LocalDateTime getFecha() {
        return fecha;
    }
}
