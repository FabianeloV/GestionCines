package Modelo;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class GestorAsistencia implements Serializable {
    private ArrayList<Asistencia> asistencias;
    private final String archivoAsistencias = "asistencias.dat";

    public GestorAsistencia() {
        asistencias = new ArrayList<>();
    }

    public void añadirAsistencia(Asistencia a) {
        asistencias.add(a);
        guardarAsistenciasEnArchivo();
    }

    public ArrayList<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void guardarAsistenciasEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoAsistencias))) {
            out.writeObject(asistencias);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error guardando asistencias: " + e.getMessage());
        }
    }

    public void leerAsistenciasDesdeArchivo() {
        File file = new File(archivoAsistencias);
        if (!file.exists() || file.length() == 0) {
            asistencias = new ArrayList<>();
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivoAsistencias))) {
            asistencias = (ArrayList<Asistencia>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error leyendo asistencias: " + e.getMessage());
            asistencias = new ArrayList<>();
        }
    }
}
