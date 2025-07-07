package Controlador;

import Vista.VentanaNuevosUsuarios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorVentanaCreacionUsuario implements ActionListener {


    private VentanaNuevosUsuarios ventanaNuevosUsuarios;

    public ControladorVentanaCreacionUsuario() {
        ventanaNuevosUsuarios = new VentanaNuevosUsuarios();

        ventanaNuevosUsuarios.getBtnCrearUsuario().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public VentanaNuevosUsuarios getVentanaNuevosUsuarios() {
        return ventanaNuevosUsuarios;
    }
}
