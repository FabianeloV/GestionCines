package Modelo;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class GestorVentas implements Serializable {
    private ArrayList<Venta> ventas;
    private final String archivoVentas = "ventas.dat";

    public GestorVentas() {
        ventas = new ArrayList<>();
    }

    public void añadirVenta(Venta v) {
        ventas.add(v);
        guardarVentasEnArchivo();
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public void guardarVentasEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoVentas))) {
            out.writeObject(ventas);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error guardando ventas: " + e.getMessage());
        }
    }

    public void leerVentasDesdeArchivo() {
        File file = new File(archivoVentas);
        if (!file.exists() || file.length() == 0) {
            ventas = new ArrayList<>();
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivoVentas))) {
            ventas = (ArrayList<Venta>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error leyendo ventas: " + e.getMessage());
            ventas = new ArrayList<>();
        }
    }
}
