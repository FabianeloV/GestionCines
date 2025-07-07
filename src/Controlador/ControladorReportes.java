package Controlador;

import Vista.VentanaReportes;
import Modelo.Reporte;
import utils.LectorArchivos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ControladorReportes implements ActionListener, Serializable {

    private VentanaReportes vista;

    public ControladorReportes() {
        vista = new VentanaReportes();

        // Asignar acciones a botones
        this.vista.getBtnGenerar().addActionListener(this);
        this.vista.getBotonBack().addActionListener(this);

        this.vista.setVisible(true);
    }

    private void cargarReporte() {
        String tipoReporte = (String) vista.getComboTipoReporte().getSelectedItem();

        if (tipoReporte.equals("Seleccionar")) {
            JOptionPane.showMessageDialog(vista, "Seleccione un tipo de reporte", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fechaInicioStr = vista.getTxtFechaInicio().getText().trim();
        String fechaFinStr = vista.getTxtFechaFin().getText().trim();

        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaInicio = LocalDate.parse(fechaInicioStr, formato);
            fechaFin = LocalDate.parse(fechaFinStr, formato);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(vista, "Formato de fecha incorrecto. Usa dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Reporte> todosReportes = LectorArchivos.leerReportesArchivo("reportes.txt");

        LocalDate finalFechaInicio = fechaInicio;
        LocalDate finalFechaFin = fechaFin;

        List<Reporte> filtrados = todosReportes.stream()
                .filter(r -> r.getCategoriaReporte().equalsIgnoreCase(tipoReporte))
                .filter(r -> !r.getFecha().isBefore(finalFechaInicio) && !r.getFecha().isAfter(finalFechaFin))
                .toList();

        if (filtrados.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No hay reportes en el rango de fechas para el tipo seleccionado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder mensaje = new StringBuilder("Reportes encontrados:\n\n");
            for (Reporte r : filtrados) {
                mensaje.append("Nombre: ").append(r.getNombreReporte()).append("\n")
                        .append("Fecha: ").append(r.getFecha()).append("\n")
                        .append("Valor: ").append(r.getValor()).append("\n")
                        .append("---------------------------\n");
            }
            JOptionPane.showMessageDialog(vista, mensaje.toString(), "Reportes Filtrados", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiarCampos() {
        vista.getComboTipoReporte().setSelectedIndex(0);
        vista.getComboFormatoReporte().setSelectedIndex(0);
        vista.getTxtFechaInicio().setText("");
        vista.getTxtFechaFin().setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == vista.getBtnGenerar()) {
            cargarReporte();
        } else if (source == vista.getBotonBack()) {
            limpiarCampos();
            vista.dispose(); // Cierra la ventana, puedes cambiarlo si deseas volver al menú principal
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ControladorReportes::new);
    }
}
