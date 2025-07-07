package Modelo;

import java.time.LocalDate;

public class Reporte {

    private String nombreReporte;
    private String categoriaReporte;
    private double valor;
    private LocalDate fecha;

    public Reporte(String nombreReporte, String categoriaReporte, double valor, LocalDate fecha) {
        this.nombreReporte = nombreReporte;
        this.categoriaReporte = categoriaReporte;
        this.valor = valor;
        this.fecha = fecha;
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    public String getCategoriaReporte() {
        return categoriaReporte;
    }

    public void setCategoriaReporte(String categoriaReporte) {
        this.categoriaReporte = categoriaReporte;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
