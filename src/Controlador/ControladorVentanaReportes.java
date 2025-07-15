package Controlador;

import Modelo.ExportadorExcel;
import Modelo.ExportadorPDF;
import Modelo.GestorPelicula;
import Modelo.Pelicula;
import Vista.VentanaReportes;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControladorVentanaReportes implements ActionListener {

    VentanaReportes ventanaReportes;
    GestorPelicula gestorPelicula;
    ExportadorPDF exportadorPDF;
    ExportadorExcel exportadorExcel;

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
            if (formato.equals("PDF")) {
                exportadorPDF.exportar("Reporte Cartelera", encabezados, datosReporte);
                JOptionPane.showMessageDialog(null, "Reporte PDF generado correctamente.");
            } else if (formato.equals("Excel")) {
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
}
