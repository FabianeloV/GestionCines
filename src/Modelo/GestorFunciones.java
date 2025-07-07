package Modelo;

import java.util.ArrayList;
import java.util.List;

public class GestorFunciones {
    private List<Funcion> funciones = new ArrayList<>();

    public void agregarFuncion(Funcion funcion) {
        funciones.add(funcion);
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }

    public Funcion buscarFuncion(String pelicula, String fecha, String hora, String salaNombre) {
        for (Funcion f : funciones) {
            if (f.getPelicula().equals(pelicula) &&
                    f.getFecha().equals(fecha) &&
                    f.getHora().equals(hora) &&
                    f.getSala().getNombre().equals(salaNombre)) {
                return f;
            }
        }
        return null;
    }
}
