package Controlador;

import Modelo.GestorPelicula;
import Modelo.Rol;
import Modelo.Usuario;
import Vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorPrincipal implements ActionListener {

    private Usuario usuarioActual = new Usuario("", "", Rol.Admin);

    private GestorPelicula gestorPeliculas;
    private InicioSesionVentana ventanaInicio;
    private VentanaDashboard ventanaDashboard;
    private CarteleraControlador controladorCartelera;
    private CarteleraNoEditControlador controladorCarteleraNoEdit;
    private ControladorCrearUsuarios controladorCrearUsuarios;
    private VentanaNuevosUsuarios ventanaNuevosUsuarios;
    private ControladorVentanaReportes ventanaReportes;
    private ControladorVentanaAsientos ventanaAsientos;
    private ControladorInicioSesion controladorInicioSesion;
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
        VentanaReportes vistaReportes = new VentanaReportes();
        ventanaReportes = new ControladorVentanaReportes(vistaReportes);
        ventanaAsientos = new ControladorVentanaAsientos();
        controladorInicioSesion = new ControladorInicioSesion(ventanaInicio);
        ventanaNuevosUsuarios = new VentanaNuevosUsuarios();
        controladorCrearUsuarios = new ControladorCrearUsuarios(ventanaNuevosUsuarios);
        controladorSalas = new ControladorOperarioSala();

        gestorPeliculas.actualizarLista();

        vistaReportes.getBotonBack().addActionListener(this);
        ventanaInicio.getBotonAceptar().addActionListener(this);
        ventanaDashboard.getBotonAgregarUsuario().addActionListener(this);
        ventanaDashboard.getBotonCartelera().addActionListener(this);
        ventanaDashboard.getBotonControlSalas().addActionListener(this);
        ventanaDashboard.getBotonLogout().addActionListener(this);
        ventanaDashboard.getBotonTickets().addActionListener(this);
        ventanaDashboard.getBotonReportes().addActionListener(this);
        ventanaNuevosUsuarios.getBtnCrearUsuario().addActionListener(this);


        controladorCarteleraNoEdit.getElegirPelicula().getVista().getBtnBack().addActionListener(this);
        controladorCarteleraNoEdit.getVistaCarteleraNoEditable().getBtnBack().addActionListener(this);

        if (controladorCarteleraNoEdit.getElegirPelicula() != null) {
            controladorCarteleraNoEdit.getElegirPelicula().setControladorPrincipal(this);
            controladorCarteleraNoEdit.getElegirPelicula().setControladorAsientos(ventanaAsientos);
        }


        if (ventanaAsientos != null) {
            ventanaAsientos.setControladorPrincipal(this);
        }

        controladorCartelera.getVistaCartelera().getBtnBack().addActionListener(this);

        ventanaNuevosUsuarios.getBotonBack().addActionListener(this);

        controladorSalas.getVista().getBtnBack().addActionListener(this);

        if (ventanaAsientos != null && ventanaAsientos.getVentanaAsientos() != null) {
            ventanaAsientos.getVentanaAsientos().getBtnBack().addActionListener(this);
        }

        if (ventanaAsientos != null && ventanaAsientos.getConfirmacionPagos() != null
                && ventanaAsientos.getConfirmacionPagos().getVentana() != null) {
            ventanaAsientos.getConfirmacionPagos().getVentana().getBtnBack().addActionListener(this);
        }

        ventanaReportes.getVentanaReportes().getBotonBack().addActionListener(this);
        ventanaInicio.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaInicio.getBotonAceptar()) {
            if (controladorInicioSesion.validarLogin(usuarioActual)) {
                ventanaDashboard.setVisible(true);
                JOptionPane.showMessageDialog(null, "Iniciando Sesion como " + usuarioActual.getRol().toString());
            }
        }

        if (e.getSource() == ventanaDashboard.getBotonCartelera()) {
            ventanaDashboard.setVisible(false);
            controladorCartelera.getVistaCartelera().setVisible(true);
        }

        if (e.getSource() == ventanaDashboard.getBotonTickets()) {
            if (usuarioActual.getRol() == Rol.Admin || usuarioActual.getRol() == Rol.Taquilla) {
                gestorPeliculas.actualizarLista();
                controladorCarteleraNoEdit.getVistaCarteleraNoEditable()
                        .mostrarPeliculas(gestorPeliculas.getPeliculas());
                controladorCarteleraNoEdit.getVistaCarteleraNoEditable().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Rol no autorizado, rol actual: " + usuarioActual.getRol().toString());
            }
        }

        if (e.getSource() == ventanaNuevosUsuarios.getBtnCrearUsuario()) {
            controladorCrearUsuarios.registrarUsuario();
        }

        if (e.getSource() == ventanaDashboard.getBotonAgregarUsuario()) {
            if (usuarioActual.getRol() == Rol.Admin) {
                ventanaDashboard.setVisible(false);
                ventanaNuevosUsuarios.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Rol no autorizado, rol actual: " + usuarioActual.getRol().toString());
            }

        }

        if (e.getSource() == ventanaDashboard.getBotonReportes()) {
            if (usuarioActual.getRol() == Rol.Admin ||
                    usuarioActual.getRol() == Rol.Operador) {
                ventanaDashboard.setVisible(false);
                ventanaReportes.getVentanaReportes().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Rol no autorizado, rol actual: " +
                                usuarioActual.getRol().toString());
            }

        }

        if (e.getSource() == ventanaDashboard.getBotonControlSalas()) {
            if (usuarioActual.getRol() == Rol.Operador || usuarioActual.getRol() == Rol.Admin) {
                ventanaDashboard.setVisible(false);
                controladorSalas.getVista().setVisible(true);
            }
        }

        //botones de back
        if (e.getSource() == controladorCarteleraNoEdit.getElegirPelicula().getVista().getBtnBack()) {
            volveraDashboard(controladorCarteleraNoEdit.getElegirPelicula().getVista());
        }

        if (e.getSource() == controladorCarteleraNoEdit.getVistaCarteleraNoEditable().getBtnBack()) {
            volveraDashboard(controladorCarteleraNoEdit.getVistaCarteleraNoEditable());
        }

        if (e.getSource() == controladorCartelera.getVistaCartelera().getBtnBack()) {
            volveraDashboard(controladorCartelera.getVistaCartelera());
        }

        if (e.getSource() == ventanaNuevosUsuarios.getBotonBack()) {
            volveraDashboard(ventanaNuevosUsuarios);
        }

        if (e.getSource() == ventanaReportes.getVentanaReportes().getBotonBack()){
            volveraDashboard(ventanaReportes.getVentanaReportes());
        }


        if (e.getSource() == controladorSalas.getVista().getBtnBack()) {
            volveraDashboard(controladorSalas.getVista());
        }
      
        if (e.getSource() == controladorCarteleraNoEdit.getVistaCarteleraNoEditable().getBtnBack()) {
            volveraDashboard(controladorCarteleraNoEdit.getVistaCarteleraNoEditable()); }

        if (e.getSource() == controladorCarteleraNoEdit.getElegirPelicula().getVista().getBtnBack()) {
            volveraDashboard(controladorCarteleraNoEdit.getElegirPelicula().getVista());
        }

        if (e.getSource() == controladorCarteleraNoEdit.getElegirPelicula().getVista().getBtnBack()) {
            volveraDashboard(controladorCarteleraNoEdit.getElegirPelicula().getVista());
        }

        if (e.getSource() == controladorCarteleraNoEdit.getVistaCarteleraNoEditable().getBtnBack()) {
            volveraDashboard(controladorCarteleraNoEdit.getVistaCarteleraNoEditable());
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
      

        // LogOut
        if (e.getSource() == ventanaDashboard.getBotonLogout()) {
            String[] opciones = {"OK","Cerrar sesión"};

            int seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Nombre de usuario: " + usuarioActual.getNombre() + "\n" + "Rol actual: " + usuarioActual.getRol().toString(),
                    "Usuario",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (seleccion) {
                case 0:
                    break;
                case 1: // Botón "Ejecutar Función"
                    System.out.println("Cerrando sesion...");
                    ventanaDashboard.setVisible(false);
                    ventanaInicio.setVisible(true);
                    usuarioActual.setRol(Rol.Admin);
                    usuarioActual.setNombre("");
                    usuarioActual.setContrasena("");
                    break;

                case JOptionPane.CLOSED_OPTION: // Usuario cerró la ventana
                    break;
                default:
                    System.out.println("Opción no reconocida");
            }
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