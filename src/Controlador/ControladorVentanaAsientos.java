package Controlador;

import Vista.ElegirAsientosVentana;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorVentanaAsientos implements ActionListener {

    private ElegirAsientosVentana ventanaAsientos;
    private ControladorConfirmacionPagos confirmacionPagos;

    public ControladorVentanaAsientos() {
        ventanaAsientos = new ElegirAsientosVentana();
        confirmacionPagos = new ControladorConfirmacionPagos();

        ventanaAsientos.getBtnConfirmar().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaAsientos.getBtnConfirmar()) {
            confirmacionPagos.getVentana().setVisible(true);
            ventanaAsientos.setVisible(false);
        }
    }

    public ElegirAsientosVentana getVentanaAsientos() {
        return ventanaAsientos;
    }

    public ControladorConfirmacionPagos getConfirmacionPagos() {
        return confirmacionPagos;
    }
}
