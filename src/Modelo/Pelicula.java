package Modelo;

import java.io.Serializable;

public class Pelicula implements Serializable {
    private String Nombre;
    private String Descripcion;
    private String Duracion;
    private String Genero;
    private String rutaPoster;


    public Pelicula(String nombre, String descripcion, String duracion, String genero, String rutaPoster) {
        Nombre = nombre;
        Descripcion = descripcion;
        Duracion = duracion;
        Genero = genero;
        this.rutaPoster = rutaPoster;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getDuracion() {
        return Duracion;
    }

    public void setDuracion(String duracion) {
        Duracion = duracion;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getRutaPoster() {
        return rutaPoster;
    }

    public void setRutaPoster(String rutaPoster) {
        this.rutaPoster = rutaPoster;
    }
}
