package model;

import java.util.Objects;

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

    public Inscripcion(String idInscripcion, int numeroDorsal, Corredor corredor, Carrera carrera,
                       Categoria categoriaSeleccionada) {
        this.idInscripcion = Objects.requireNonNull(idInscripcion, "El identificador de la inscripcion no puede ser nulo");
        this.numeroDorsal = numeroDorsal;
        this.corredor = Objects.requireNonNull(corredor, "El corredor no puede ser nulo");
        this.carrera = Objects.requireNonNull(carrera, "La carrera no puede ser nula");
        this.categoriaSeleccionada = Objects.requireNonNull(categoriaSeleccionada, "La categoria seleccionada no puede ser nula");
        this.estado = EstadoInscripcion.Pendiente;
    }

    public String getIdInscripcion() {
        return idInscripcion;
    }

    public int getNumeroDorsal() {
        return numeroDorsal;
    }

    public TallaCamiseta getTallaCamiseta() {
        return tallaCamiseta;
    }

    public void asignarTallaCamiseta(TallaCamiseta tallaCamiseta) {
        this.tallaCamiseta = Objects.requireNonNull(tallaCamiseta, "La talla de camiseta no puede ser nula");
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }

    public Corredor getCorredor() {
        return corredor;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public Categoria getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public Pago getPago() {
        return pago;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public boolean pagar(Pago pago) {
        if (this.pago != null) {
            return false;
        }
        this.pago = Objects.requireNonNull(pago, "El pago no puede ser nulo");
        this.estado = EstadoInscripcion.Pagada;
        return true;
    }

    public void confirmar() {
        if (pago == null) {
            throw new IllegalStateException("La inscripcion debe contar con un pago para ser confirmada");
        }
        this.estado = EstadoInscripcion.Confirmada;
    }

    public void establecerResultado(Resultado resultado) {
        if (estado != EstadoInscripcion.Confirmada) {
            throw new IllegalStateException("La inscripcion debe estar confirmada antes de registrar resultados");
        }
        this.resultado = Objects.requireNonNull(resultado, "El resultado no puede ser nulo");
    }
}
