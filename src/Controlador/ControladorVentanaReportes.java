package Controlador;

import Modelo.ExportadorExcel;
import Modelo.ExportadorPDF;
import Modelo.GestorPelicula;
import Modelo.Pelicula;
import Vista.VentanaReportes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ControladorVentanaReportes implements ActionListener {

    public VentanaReportes ventanaReportes;
    private GestorPelicula gestorPelicula;
    private ExportadorPDF exportadorPDF;
    private ExportadorExcel exportadorExcel;

    public VentanaReportes getVentanaReportes() {
        return ventanaReportes;
    }

    public ControladorVentanaReportes(VentanaReportes ventanaReportes) {
        this.ventanaReportes = ventanaReportes;
        this.gestorPelicula = new GestorPelicula();
        this.exportadorPDF = new ExportadorPDF();
        this.exportadorExcel = new ExportadorExcel();

        this.ventanaReportes.getBtnGenerar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaReportes.getBtnGenerar()) {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

            Date fechaInicio = ventanaReportes.getFechaInicio().getDate();
            Date fechaFin = ventanaReportes.getFechaFin().getDate();

            String inicio = (fechaInicio != null) ? formatoFecha.format(fechaInicio) : "";
            String fin = (fechaFin != null) ? formatoFecha.format(fechaFin) : "";

            if (inicio.isEmpty() || fin.isEmpty()) {
                mostrarError("Ambas fechas son obligatorias.");
                return;
            }

            if (fechaInicio.after(fechaFin)) {
                mostrarError("La fecha de inicio debe ser anterior a la fecha fin.");
                return;
            }

            String tipoReporte = ventanaReportes.getComboTipoReporte().getSelectedItem().toString();
            String formato = ventanaReportes.getComboFormatoReporte().getSelectedItem().toString();

            if (tipoReporte.equals("Cartelera")) {
                generarReporteCartelera(formato);
            } else {
                JOptionPane.showMessageDialog(null, "Este tipo de reporte aún no está implementado en el sistema.");
            }
        }
    }

    private void generarReporteCartelera(String formato) {
        gestorPelicula.actualizarLista();
        ArrayList<Pelicula> peliculas = gestorPelicula.getPeliculas();

        if (peliculas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay películas registradas para generar el reporte.");
            return;
        }

        String[] encabezados = {"Nombre", "Descripción", "Duración", "Género"};

        List<Object[]> datosReporte = new ArrayList<>();
        for (Pelicula p : peliculas) {
            datosReporte.add(new Object[]{
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getDuracion(),
                    p.getGenero()
            });
        }

        try {
            if (formato.equalsIgnoreCase("PDF")) {
                exportadorPDF.exportar("Reporte Cartelera", encabezados, datosReporte);
                JOptionPane.showMessageDialog(null, "Reporte PDF generado correctamente.");
            } else if (formato.equalsIgnoreCase("Excel")) {
                exportadorExcel.exportar("Reporte Cartelera", encabezados, datosReporte);
                JOptionPane.showMessageDialog(null, "Reporte Excel generado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Formato no soportado.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(ventanaReportes, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
