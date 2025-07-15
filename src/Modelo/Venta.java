package Modelo;

import java.io.Serializable;
import java.util.Date;

public class Venta implements Serializable {
    private String numeroTicket;
    private Pelicula pelicula;
    private Cliente cliente;
    private Date fecha;
    private double monto;

    public Venta(String numeroTicket, Pelicula pelicula, Cliente cliente, Date fecha, double monto) {
        this.numeroTicket = numeroTicket;
        this.pelicula = pelicula;
        this.cliente = cliente;
        this.fecha = fecha;
        this.monto = monto;
    }

    public String getNumeroTicket() {
        return numeroTicket;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getMonto() {
        return monto;
    }
}
