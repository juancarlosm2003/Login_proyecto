package com.mycompany.proyecto_login;


import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Abono {
    private final IntegerProperty idAbono;
    private final IntegerProperty idCompra;  
    private final ObjectProperty<LocalDateTime> fecha;
    private final DoubleProperty monto;
    private final StringProperty metodoPago;
    private final StringProperty comentario;

    public Abono(int idAbono, int idCompra, LocalDateTime fecha, 
                 double monto, String metodoPago, String comentario) {
        this.idAbono = new SimpleIntegerProperty(idAbono);
        this.idCompra = new SimpleIntegerProperty(idCompra);
        this.fecha = new SimpleObjectProperty<>(fecha);
        this.monto = new SimpleDoubleProperty(monto);
        this.metodoPago = new SimpleStringProperty(metodoPago);
        this.comentario = new SimpleStringProperty(comentario);
    }

    // Getters
    public int getIdAbono() { return idAbono.get(); }
    public int getIdCompra() { return idCompra.get(); }
    public LocalDateTime getFecha() { return fecha.get(); }
    public double getMonto() { return monto.get(); }
    public String getMetodoPago() { return metodoPago.get(); }
    public String getComentario() { return comentario.get(); }

    // Properties
    public IntegerProperty idAbonoProperty() { return idAbono; }
    public IntegerProperty idCompraProperty() { return idCompra; }
    public ObjectProperty<LocalDateTime> fechaProperty() { return fecha; }
    public DoubleProperty montoProperty() { return monto; }
    public StringProperty metodoPagoProperty() { return metodoPago; }
    public StringProperty comentarioProperty() { return comentario; }

    // Setters
    public void setMonto(double value) { monto.set(value); }
    public void setMetodoPago(String value) { metodoPago.set(value); }
    public void setComentario(String value) { comentario.set(value); }
}

