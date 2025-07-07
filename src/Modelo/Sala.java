package Modelo;

import java.util.HashMap;
import java.util.Map;

public class Sala {
    private String nombre;
    private int filas;
    private int columnas;
    private Map<String, Asiento> asientos; // id → Asiento

    public Sala(String nombre, int filas, int columnas) {
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
        this.asientos = new HashMap<>();
        generarAsientos();
    }

    private void generarAsientos() {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                String id = (char) ('A' + f) + String.valueOf(c + 1);
                asientos.put(id, new Asiento(id));
            }
        }
    }

    public Map<String, Asiento> getAsientos() {
        return asientos;
    }

    public Asiento getAsiento(String id) {
        return asientos.get(id);
    }

    public String getNombre() {
        return nombre;
    }
}
