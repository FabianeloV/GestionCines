package Controlador;

import Modelo.ExportadorExcel;
import Modelo.ExportadorPDF;
import Vista.VentanaReportes;
import java.text.SimpleDateFormat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

public class ControladorVentanaReportes implements ActionListener {


    VentanaReportes ventanaReportes;
    ExportadorExcel excel;
    ExportadorPDF PDF;

    public ControladorVentanaReportes() {
        ventanaReportes = new VentanaReportes();
        excel = new ExportadorExcel();
        PDF = new ExportadorPDF();


        ventanaReportes.getBtnGenerar().addActionListener(this);
        ventanaReportes.getBotonBack().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaReportes.getBtnGenerar()) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

            Date fechaInicio = ventanaReportes.getFechaInicio().getDate();
            Date fechaFin = ventanaReportes.getFechaFin().getDate();

            String inicio = (fechaInicio != null) ? formatoFecha.format(fechaInicio) : "";
            String fin = (fechaFin != null) ? formatoFecha.format(fechaFin) : "";

            String formato = ventanaReportes.getComboFormatoReporte().getSelectedItem().toString();

            if (inicio.isEmpty() || fin.isEmpty()) {
                mostrarError("Ambas fechas son obligatorias.");
                return;
            }

            if (fechaInicio.after(fechaFin)) {
                mostrarError("La fecha de inicio debe ser anterior a la fecha fin.");
                return;
            }

            //si todo esta bien se llama a logica de generar reportes

            if (formato.equalsIgnoreCase("Excel")) {
                //excel.exportar();
            }

            if (formato.equalsIgnoreCase("PDF")) {
                //PDF.exportar();
            }
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
