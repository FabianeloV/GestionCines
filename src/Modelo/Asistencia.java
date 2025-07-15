package Modelo;

import java.io.Serializable;
import java.util.Date;

public class Asistencia implements Serializable {
    private Cliente cliente;
    private Pelicula pelicula;
    private Date fecha;

    public Asistencia(Cliente cliente, Pelicula pelicula, Date fecha) {
        this.cliente = cliente;
        this.pelicula = pelicula;
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public Date getFecha() {
        return fecha;
    }
}
