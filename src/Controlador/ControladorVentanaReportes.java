package Controlador;

import Vista.VentanaReportes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorVentanaReportes implements ActionListener {


    VentanaReportes ventanaReportes;

    public ControladorVentanaReportes() {
        ventanaReportes = new VentanaReportes();

        ventanaReportes.getBtnGenerar().addActionListener(this);
        ventanaReportes.getBotonBack().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaReportes.getBtnGenerar()) {
            String inicio = ventanaReportes.getTxtFechaInicio().getText().trim();
            String fin = ventanaReportes.getTxtFechaFin().getText().trim();

            if (inicio.isEmpty() || fin.isEmpty()) {
                mostrarError("Ambas fechas son obligatorias.");
                return;
            }

            if (!esFechaValida(inicio) || !esFechaValida(fin)) {
                mostrarError("Ingrese fechas válidas con el formato dd/MM/yyyy.");
                return;
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaInicio = sdf.parse(inicio);
                Date fechaFin = sdf.parse(fin);
                if (fechaInicio.after(fechaFin)) {
                    mostrarError("La fecha de inicio debe ser anterior a la fecha fin.");
                    return;
                }
            } catch (ParseException ex) {
                mostrarError("Error al interpretar las fechas.");
                return;
            }

            //si todo esta bien se llama a logica de generar reportes
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(ventanaReportes, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private boolean esFechaValida(String fechaTexto) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(fechaTexto);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    public VentanaReportes getVentanaReportes() {
        return ventanaReportes;
    }
}
