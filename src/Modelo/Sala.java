package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sala {
    private String nombre;
    private int filas;
    private int columnas;
    private String ciudad;
    private boolean enMantenimiento;
    private Map<String, Asiento> asientos; // id → Asiento

    public Sala(String nombre, String ciudad, int filas, int columnas) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.filas = filas;
        this.columnas = columnas;
        this.enMantenimiento = false; // Por defecto disponible
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

    // Getters
    public List<Asiento> getAsientos() {
        return new ArrayList<>(asientos.values());
    }


    public Asiento getAsiento(String id) {
        return asientos.get(id);
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public boolean isEnMantenimiento() {
        return enMantenimiento;
    }

    public int getCapacidad() {
        return filas * columnas;
    }

    // Setters
    public void setEnMantenimiento(boolean enMantenimiento) {
        this.enMantenimiento = enMantenimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public boolean reservarAsiento(String id) {
        Asiento asiento = asientos.get(id);
        if (asiento != null && !asiento.isOcupado() && !enMantenimiento) {
            asiento.setOcupado(true);
            return true;
        }
        return false;
    }

    public boolean liberarAsiento(String id) {
        Asiento asiento = asientos.get(id);
        if (asiento != null && asiento.isOcupado()) {
            asiento.setOcupado(false);
            return true;
        }
        return false;
    }

    public int getAsientosDisponibles() {
        if (enMantenimiento) return 0;
        return (int) asientos.values().stream().filter(a -> !a.isOcupado()).count();
    }

    public int getAsientosOcupados() {
        return (int) asientos.values().stream().filter(Asiento::isOcupado).count();
    }

    public void limpiarAsientos() {
        asientos.values().forEach(asiento -> asiento.setOcupado(false));
    }

    @Override
    public String toString() {
        return "Sala{" +
                "nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", capacidad=" + getCapacidad() +
                ", enMantenimiento=" + enMantenimiento +
                ", disponibles=" + getAsientosDisponibles() +
                '}';
    }

}