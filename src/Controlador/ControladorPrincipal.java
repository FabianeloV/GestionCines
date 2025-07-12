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
    private ControladorVentanaAsientos ventanaAsientos;
    private ControladorOperarioSala controladorSalas;

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
        ventanaAsientos = new ControladorVentanaAsientos();
        controladorSalas = new ControladorOperarioSala();

        gestorPeliculas.actualizarLista();

        
        configurarEventos();

        ventanaInicio.setVisible(true);
    }

    private void configurarEventos() {
        
        ventanaInicio.getBotonAceptar().addActionListener(this);
        ventanaDashboard.getBotonAgregarUsuario().addActionListener(this);
        ventanaDashboard.getBotonCartelera().addActionListener(this);
        ventanaDashboard.getBotonControlSalas().addActionListener(this);
        ventanaDashboard.getBotonLogout().addActionListener(this);
        ventanaDashboard.getBotonTickets().addActionListener(this);
        ventanaDashboard.getBotonReportes().addActionListener(this);

        configurarEventosBack();

        configurarEventosVentanasSecundarias();
    }

    private void configurarEventosBack() {
        controladorCarteleraNoEdit.getElegirPelicula().getVista().getBtnBack().addActionListener(this);
        controladorCarteleraNoEdit.getVistaCarteleraNoEditable().getBtnBack().addActionListener(this);
        controladorCartelera.getVistaCartelera().getBtnBack().addActionListener(this);
        ventanaNuevosUsuarios.getVentanaNuevosUsuarios().getBotonBack().addActionListener(this);
        ventanaReportes.getVentanaReportes().getBotonBack().addActionListener(this);
        controladorSalas.getVista().getBtnBack().addActionListener(this);

        if (ventanaAsientos != null && ventanaAsientos.getVentanaAsientos() != null) {
            ventanaAsientos.getVentanaAsientos().getBtnBack().addActionListener(this);
        }

        if (ventanaAsientos != null && ventanaAsientos.getConfirmacionPagos() != null
                && ventanaAsientos.getConfirmacionPagos().getVentana() != null) {
            ventanaAsientos.getConfirmacionPagos().getVentana().getBtnBack().addActionListener(this);
        }
    }

    private void configurarEventosVentanasSecundarias() {
        
        if (controladorCarteleraNoEdit.getElegirPelicula() != null) {
            controladorCarteleraNoEdit.getElegirPelicula().setControladorPrincipal(this);
            controladorCarteleraNoEdit.getElegirPelicula().setControladorAsientos(ventanaAsientos);
        }

        
        if (ventanaAsientos != null) {
            ventanaAsientos.setControladorPrincipal(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Navegación principal
        NavegacionPrincipal(e);

        // Botones de regreso
        NavegacionBotonesBack(e);
    }

    private void NavegacionPrincipal(ActionEvent e) {
        if (e.getSource() == ventanaInicio.getBotonAceptar()) {
            navegarA(ventanaInicio, ventanaDashboard);
        }

        if (e.getSource() == ventanaDashboard.getBotonCartelera()) {
            navegarA(ventanaDashboard, controladorCartelera.getVistaCartelera());
        }

        if (e.getSource() == ventanaDashboard.getBotonTickets()) {
            gestorPeliculas.actualizarLista();
            controladorCarteleraNoEdit.getVistaCarteleraNoEditable()
                    .mostrarPeliculas(gestorPeliculas.getPeliculas());
            navegarA(ventanaDashboard, controladorCarteleraNoEdit.getVistaCarteleraNoEditable());
        }

        if (e.getSource() == ventanaDashboard.getBotonAgregarUsuario()) {
            navegarA(ventanaDashboard, ventanaNuevosUsuarios.getVentanaNuevosUsuarios());
        }

        if (e.getSource() == ventanaDashboard.getBotonReportes()) {
            navegarA(ventanaDashboard, ventanaReportes.getVentanaReportes());
        }

        if (e.getSource() == ventanaDashboard.getBotonControlSalas()) {
            navegarA(ventanaDashboard, controladorSalas.getVista());
        }
    }

    private void NavegacionBotonesBack(ActionEvent e) {
        // todos los botones Back vuelven al dashboard
        if (e.getSource() == controladorCarteleraNoEdit.getElegirPelicula().getVista().getBtnBack()) {
            volveraDashboard(controladorCarteleraNoEdit.getElegirPelicula().getVista());
        }

        if (e.getSource() == controladorCarteleraNoEdit.getVistaCarteleraNoEditable().getBtnBack()) {
            volveraDashboard(controladorCarteleraNoEdit.getVistaCarteleraNoEditable());
        }

        if (e.getSource() == controladorCartelera.getVistaCartelera().getBtnBack()) {
            volveraDashboard(controladorCartelera.getVistaCartelera());
        }

        if (e.getSource() == ventanaNuevosUsuarios.getVentanaNuevosUsuarios().getBotonBack()) {
            volveraDashboard(ventanaNuevosUsuarios.getVentanaNuevosUsuarios());
        }

        if (e.getSource() == ventanaReportes.getVentanaReportes().getBotonBack()) {
            volveraDashboard(ventanaReportes.getVentanaReportes());
        }

        if (e.getSource() == controladorSalas.getVista().getBtnBack()) {
            volveraDashboard(controladorSalas.getVista());
        }

        if (ventanaAsientos != null && ventanaAsientos.getVentanaAsientos() != null
                && e.getSource() == ventanaAsientos.getVentanaAsientos().getBtnBack()) {
            volveraDashboard(ventanaAsientos.getVentanaAsientos());
        }

        if (ventanaAsientos != null && ventanaAsientos.getConfirmacionPagos() != null
                && ventanaAsientos.getConfirmacionPagos().getVentana() != null
                && e.getSource() == ventanaAsientos.getConfirmacionPagos().getVentana().getBtnBack()) {
            volveraDashboard(ventanaAsientos.getConfirmacionPagos().getVentana());
        }
    }

    //metodo para navegar entre otras ventanas
    private void navegarA(JFrame ventanaOrigen, JFrame ventanaDestino) {
        if (ventanaOrigen != null) {
            ventanaOrigen.setVisible(false);
        }
        if (ventanaDestino != null) {
            ventanaDestino.setVisible(true);
        }
    }

    //volver al dashboard
    public void volveraDashboard(JFrame ventanaActual) {
        gestorPeliculas.actualizarLista();
        navegarA(ventanaActual, ventanaDashboard);
    }

    public static void main(String[] args) {
        new ControladorPrincipal();
    }
}