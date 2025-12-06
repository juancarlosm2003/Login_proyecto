/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_login;

/**
 *
 * @author Admin
 */
import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Compra {
    private final IntegerProperty idCompra;
    private final ObjectProperty<LocalDateTime> fecha;
    private final StringProperty estado;
    private final StringProperty metodoPago;
    private final DoubleProperty impuestos;
    private final DoubleProperty total;
    private final DoubleProperty subtotal;

    // Constructor
    public Compra(int idCompra, LocalDateTime fecha, String estado, 
                  String metodoPago, double impuestos, double total, double subtotal) {
        this.idCompra = new SimpleIntegerProperty(idCompra);
        this.fecha = new SimpleObjectProperty<>(fecha);
        this.estado = new SimpleStringProperty(estado);
        this.metodoPago = new SimpleStringProperty(metodoPago);
        this.impuestos = new SimpleDoubleProperty(impuestos);
        this.total = new SimpleDoubleProperty(total);
        this.subtotal = new SimpleDoubleProperty(subtotal);
    }

    // Getters
    public int getIdCompra() { return idCompra.get(); }
    public LocalDateTime getFecha() { return fecha.get(); }
    public String getEstado() { return estado.get(); }
    public String getMetodoPago() { return metodoPago.get(); }
    public double getImpuestos() { return impuestos.get(); }
    public double getTotal() { return total.get(); }
    public double getSubtotal() { return subtotal.get(); }

    // Properties (necesarias para TableView)
    public IntegerProperty idCompraProperty() { return idCompra; }
    public ObjectProperty<LocalDateTime> fechaProperty() { return fecha; }
    public StringProperty estadoProperty() { return estado; }
    public StringProperty metodoPagoProperty() { return metodoPago; }
    public DoubleProperty impuestosProperty() { return impuestos; }
    public DoubleProperty totalProperty() { return total; }
    public DoubleProperty subtotalProperty() { return subtotal; }

    // Setters
    public void setEstado(String value) { estado.set(value); }
    public void setMetodoPago(String value) { metodoPago.set(value); }
    public void setImpuestos(double value) { impuestos.set(value); }
    public void setTotal(double value) { total.set(value); }
    public void setSubtotal(double value) { subtotal.set(value); }
}
