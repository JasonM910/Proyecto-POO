package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Pago {
    private final String idPago;
    private final BigDecimal monto;
    private final String descripcion;
    private final LocalDateTime fecha;

    public Pago(String idPago, BigDecimal monto, String descripcion, LocalDateTime fecha) {
        this.idPago = Objects.requireNonNull(idPago, "El identificador del pago no puede ser nulo");
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser positivo");
        }
        this.monto = monto;
        this.descripcion = Objects.requireNonNull(descripcion, "La descripcion no puede ser nula");
        this.fecha = Objects.requireNonNull(fecha, "La fecha del pago no puede ser nula");
    }

    public String getIdPago() {
        return idPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
