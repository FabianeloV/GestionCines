package Modelo;

import java.util.Date;

public class Funcion {
    private Sala sala;
    private String pelicula;
    private String fecha;
    private String hora;

    public Funcion(String pelicula, Sala sala, String fecha, String hora) {
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Sala getSala() {
        return sala;
    }

    public String getPelicula() {
        return pelicula;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }
}

