package Controlador;

import Vista.Ventana_Reportes;
import Modelo.Reporte;
import utils.LectorArchivos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ControladorReportes implements ActionListener, Serializable {

    private Ventana_Reportes vista;
    private Reporte modelo;

    public ControladorReportes() {
        vista = new Ventana_Reportes();


        this.vista.aceptarButton.addActionListener(this);
        this.vista.cancelarButton.addActionListener(this);
        this.vista.setTitle("Reportes Cine");
        //centrar la ventana
        this.vista.setLocationRelativeTo(null);
        this.vista.setContentPane(this.vista.PrincipalReportes);
        this.vista.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.vista.setSize(800, 600);
        this.vista.setVisible(true);
    }

    private void cargarReporte() {

        String tipoReporte= (String) vista.comboBox1.getSelectedItem();

        if(tipoReporte.equals("Seleccionar")){
            JOptionPane.showMessageDialog(vista, "Seleccione un tipo de reporte");
            return;
        }
        String fechaInicioStr = vista.textField1.getText();
        String fechaFinStr = vista.textField2.getText();

        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;

        try{
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaInicio = LocalDate.parse(fechaInicioStr,formato);
            fechaFin = LocalDate.parse(fechaFinStr,formato);
        }catch (DateTimeParseException ex){
            JOptionPane.showMessageDialog(vista, "Formato de fecha incorrecto. Usa dd/MM/yyyy.");
        }

        //cargamos todos lo reportes desde el archivo
        List<Reporte> todosReportes = LectorArchivos.leerReportesArchivo("reportes.txt");

        //filtar por el tipo de reporte y fecha
        LocalDate finalFechaInicio = fechaInicio;
        LocalDate finalFechaFin = fechaFin;
        List<Reporte> filtrados = todosReportes.stream()
                .filter(r -> r.getCategoriaReporte().equalsIgnoreCase(modelo.getCategoriaReporte()))
                .filter(r -> !r.getFecha().isBefore(finalFechaInicio) && !r.getFecha().isAfter(finalFechaFin))
                .toList();

        if(filtrados.isEmpty()){
            JOptionPane.showMessageDialog(vista, "No hay reportes en el rango de fechas");
            limpiarTabla();
        }else{
            llenarTabla(filtrados);
        }
    }





    public void llenarTabla(List<Reporte> lista){
        String[] colunmas ={"Categoria","Nombre","Fecha ","Valor"};
        DefaultTableModel modeloTabla = new DefaultTableModel(colunmas,0);

        for(Reporte r: lista){
            Object[] fila = {r.getCategoriaReporte(),r.getNombreReporte(),r.getFecha(),r.getValor()};
            modeloTabla.addRow(fila);
        }

        vista.tableReportes.setModel(modeloTabla);
    }

    public void limpiarCampos(){
        vista.comboBox1.setSelectedIndex(0);
        vista.textField1.setText("");
        vista.textField2.setText("");
        vista.PDFRadioButton.setSelected(false);
        vista.exelRadioButton.setSelected(false);
        limpiarTabla();
    }


    public void limpiarTabla(){
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tableReportes.getModel();
        modeloTabla.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.aceptarButton) {
            cargarReporte(); // Metodo que crearemos enseguida
        } else if (e.getSource() == vista.cancelarButton) {
            limpiarCampos(); // Otro metodo auxiliar
        }
    }


    public static void main(String[] args) {
            ControladorReportes controlador = new ControladorReportes();
            controlador.cargarReporte();
        }

}
