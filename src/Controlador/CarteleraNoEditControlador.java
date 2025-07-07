package Controlador;

import Modelo.GestorPelicula;
import Modelo.Pelicula;
import Vista.CarteleraNoEditable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarteleraNoEditControlador implements ActionListener {

    private CarteleraNoEditable carteleraNoEditable;
    private GestorPelicula  gestorPeliculas;
    private ControladorSeleccionPelicula controladorSeleccionPelicula;

    public CarteleraNoEditControlador() {
        carteleraNoEditable = new CarteleraNoEditable();
        gestorPeliculas = new GestorPelicula();
        controladorSeleccionPelicula =  new ControladorSeleccionPelicula();

        carteleraNoEditable.getProcederAlPagoButton().addActionListener(this);

        actualizarCartelera();
    }

    private void actualizarCartelera() {
        carteleraNoEditable.mostrarPeliculas(gestorPeliculas.leerPeliculasDesdeArchivo("peliculas.dat"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == carteleraNoEditable.getProcederAlPagoButton()) {

            Integer indice = gestorPeliculas.solicitarIndicePelicula("Ingrese el nombre de la película para proceder al pago:");

            if (indice != null && indice >= 0 && indice < gestorPeliculas.getPeliculas().size()) {
                Pelicula seleccionada = gestorPeliculas.getPeliculas().get(indice);

                controladorSeleccionPelicula.getVista().mostrarPelicula(seleccionada);
                controladorSeleccionPelicula.getVista().setVisible(true);
                carteleraNoEditable.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(null, "Película no encontrada.");
            }

        }
    }

    public CarteleraNoEditable getVistaCarteleraNoEditable() {
        return carteleraNoEditable;
    }

    public ControladorSeleccionPelicula getElegirPelicula() {
        return controladorSeleccionPelicula;
    }
}
