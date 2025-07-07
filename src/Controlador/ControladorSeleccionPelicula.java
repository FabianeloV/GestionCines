package Controlador;

import Vista.SeleccionPelicula;

import javax.swing.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ControladorSeleccionPelicula implements ActionListener {

    private SeleccionPelicula vista;

    public ControladorSeleccionPelicula() {
        vista = new SeleccionPelicula();

        vista.getBtnContinuar().addActionListener(this);
        vista.getBtnBack().addActionListener(this);

        //validar al salir del campo fecha
        vista.getTfFecha().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String fecha = vista.getTfFecha().getText().trim();
                if (!fecha.isEmpty() && !esFechaValida(fecha)) {
                    mostrarError("Ingrese una fecha válida (dd/MM/yyyy)");
                    vista.getTfFecha().requestFocus();
                }
            }
        });

        // Validar al salir del campo hora
        vista.getTfHora().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String hora = vista.getTfHora().getText().trim();
                if (!hora.isEmpty() && !esHoraValida(hora)) {
                    mostrarError("Ingrese una hora válida (HH:mm)");
                    vista.getTfHora().requestFocus();
                }
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnContinuar()) {
            String fecha = vista.getTfFecha().getText().trim();
            String hora = vista.getTfHora().getText().trim();

            if (!esFechaValida(fecha) || !esHoraValida(hora)) {
                mostrarError("Verifique que la fecha y hora estén en formato correcto.");
                return;
            }

            JOptionPane.showMessageDialog(vista, "Fecha y hora válidas.");
        }

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

    private boolean esHoraValida(String horaTexto) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            sdf.setLenient(false);
            sdf.parse(horaTexto);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public SeleccionPelicula getVista() {
        return vista;
    }
}
