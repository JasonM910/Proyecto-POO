package model;

import java.util.Objects;

public class Pago {
    private final String idPago;
    private double monto;
    private String fecha;
    private String estado;

    public Pago(String idPago, double monto, String fecha) {
        this.idPago = Objects.requireNonNull(idPago, "El identificador del pago no puede ser nulo");
        setMonto(monto);
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
        this.estado = "pendiente";
    }

    public String getIdPago() {
        return idPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        if (monto < 0) {
            throw new IllegalArgumentException("El monto del pago no puede ser negativo");
        }
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo");
    }

    public boolean confirmarPago() {
        this.estado = "pagado";
        return true;
    }
}
