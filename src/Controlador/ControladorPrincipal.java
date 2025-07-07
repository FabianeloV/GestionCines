package Controlador;

import Modelo.GestorPelicula;
import Vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorPrincipal implements ActionListener {

    private GestorPelicula gestorPeliculas;
    private InicioSesionVentana ventanaInicio;
    private VentanaDashboard ventanaDashboard;
    private CarteleraControlador controladorCartelera;
    private CarteleraNoEditControlador controladorCarteleraNoEdit;
    private ControladorVentanaCreacionUsuario ventanaNuevosUsuarios;
    private ControladorVentanaReportes ventanaReportes;

    public ControladorPrincipal() {

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        ventanaInicio = new InicioSesionVentana();
        ventanaDashboard = new VentanaDashboard();
        controladorCartelera = new CarteleraControlador();
        controladorCarteleraNoEdit = new CarteleraNoEditControlador();
        gestorPeliculas = new GestorPelicula();
        ventanaNuevosUsuarios = new ControladorVentanaCreacionUsuario();
        ventanaReportes = new ControladorVentanaReportes();


        gestorPeliculas.actualizarLista();

        ventanaInicio.getBotonAceptar().addActionListener(this);
        ventanaDashboard.getBotonAgregarUsuario().addActionListener(this);
        ventanaDashboard.getBotonCartelera().addActionListener(this);
        ventanaDashboard.getBotonControlSalas().addActionListener(this);
        ventanaDashboard.getBotonLogout().addActionListener(this);
        ventanaDashboard.getBotonTickets().addActionListener(this);
        ventanaDashboard.getBotonReportes().addActionListener(this);


        controladorCarteleraNoEdit.getElegirPelicula().getVista().getBtnBack().addActionListener(this);
        controladorCarteleraNoEdit.getVistaCarteleraNoEditable().getBtnBack().addActionListener(this);

        controladorCartelera.getVistaCartelera().getBtnBack().addActionListener(this);

        ventanaNuevosUsuarios.getVentanaNuevosUsuarios().getBotonBack().addActionListener(this);

        ventanaReportes.getVentanaReportes().getBotonBack().addActionListener(this);
        ventanaInicio.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaInicio.getBotonAceptar()) {
            ventanaInicio.setVisible(false);
            ventanaDashboard.setVisible(true);
        }

        if (e.getSource() == ventanaDashboard.getBotonCartelera()){
            ventanaDashboard.setVisible(false);
            controladorCartelera.getVistaCartelera().setVisible(true);
        }

        if (e.getSource() == ventanaDashboard.getBotonTickets()) {
            gestorPeliculas.actualizarLista();
            controladorCarteleraNoEdit.getVistaCarteleraNoEditable()
                    .mostrarPeliculas(gestorPeliculas.getPeliculas());
            controladorCarteleraNoEdit.getVistaCarteleraNoEditable().setVisible(true);
        }


        if(e.getSource() == ventanaDashboard.getBotonAgregarUsuario()){
            ventanaDashboard.setVisible(false);
            ventanaNuevosUsuarios.getVentanaNuevosUsuarios().setVisible(true);
        }

        if(e.getSource() == ventanaDashboard.getBotonReportes()){
            ventanaDashboard.setVisible(false);
            ventanaReportes.getVentanaReportes().setVisible(true);
        }

        //botones de back
        if (e.getSource() == controladorCarteleraNoEdit.getElegirPelicula().getVista().getBtnBack()){
            volveraDashboard(controladorCarteleraNoEdit.getElegirPelicula().getVista());
        }

        if (e.getSource() == controladorCarteleraNoEdit.getVistaCarteleraNoEditable().getBtnBack()) {
            volveraDashboard(controladorCarteleraNoEdit.getVistaCarteleraNoEditable());
        }

        if (e.getSource() == controladorCartelera.getVistaCartelera().getBtnBack()) {
            volveraDashboard(controladorCartelera.getVistaCartelera());
        }

        if (e.getSource() == ventanaNuevosUsuarios.getVentanaNuevosUsuarios().getBotonBack()){
            volveraDashboard(ventanaNuevosUsuarios.getVentanaNuevosUsuarios());
        }

        if (e.getSource() == ventanaReportes.getVentanaReportes().getBotonBack()){
            volveraDashboard(ventanaReportes.getVentanaReportes());
        }

    }

    public void volveraDashboard(JFrame ventanaActual) {
        gestorPeliculas.actualizarLista();
        ventanaActual.setVisible(false);
        ventanaDashboard.setVisible(true);
    }

    public static void main(String[] args) {
        new ControladorPrincipal();
    }
}

