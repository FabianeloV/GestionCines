package Modelo;

import java.util.Date;

public class Cliente {
    String nombre;
    String cedula;
    Date ultimaCompra;
    Float acumuladoVentas = 0F;

    public Cliente(String nombre, String cedula, Date ultimaCompra, Float acumuladoVentas) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.ultimaCompra = ultimaCompra;
        this.acumuladoVentas = acumuladoVentas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getUltimaCompra() {
        return ultimaCompra;
    }

    public void setUltimaCompra(Date ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    public Float getAcumuladoVentas() {
        return acumuladoVentas;
    }

    public void setAcumuladoVentas(Float acumuladoVentas) {
        this.acumuladoVentas = acumuladoVentas;
    }
}
