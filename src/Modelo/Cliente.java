package Modelo;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    String nombre;
    String cedula;
    Date ultimaCompra;
    Float acumuladoVentas = 0F;
    private String correo;

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

    public Float getTotalCompras() {
        return acumuladoVentas;
    }

    public void setAcumuladoVentas(Float acumuladoVentas) {
        this.acumuladoVentas = acumuladoVentas;
    }


    public void setTotalCompras(Float totalCompras) {
        this.acumuladoVentas = totalCompras;
    }

    public void agregarCompra(Float montoCompra) {
        this.acumuladoVentas += montoCompra;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", correo='" + correo + '\'' +
                ", totalCompras=" + acumuladoVentas +
                '}';
    }
}
