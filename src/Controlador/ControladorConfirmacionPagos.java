package Controlador;

import Vista.ConfirmacionVentaVentana;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorConfirmacionPagos implements ActionListener {

    ConfirmacionVentaVentana ventana;

    public ControladorConfirmacionPagos() {
        ventana = new ConfirmacionVentaVentana();


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public ConfirmacionVentaVentana getVentana() {
        return ventana;
    }
}
