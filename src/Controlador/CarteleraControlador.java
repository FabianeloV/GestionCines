package Controlador;

import Modelo.GestorPelicula;
import Modelo.Pelicula;
import Vista.CarteleraEditable;
import Vista.SeleccionPelicula;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarteleraControlador implements ActionListener {

    private final GestorPelicula gestorPeliculas;
    private final CarteleraEditable vistaCartelera;
    private final SeleccionPelicula elegirPelicula;

    public CarteleraControlador() {
        gestorPeliculas = new GestorPelicula();
        vistaCartelera = new CarteleraEditable();
        elegirPelicula = new SeleccionPelicula();

        // Enlazar eventos
        vistaCartelera.getAñadirPeliculaButton().addActionListener(this);
        vistaCartelera.getEditarButton().addActionListener(this);
        vistaCartelera.getBorrarPeliculaButton().addActionListener(this);

        elegirPelicula.getBtnBack().addActionListener(this);

        actualizarCartelera();
    }


    private void actualizarCartelera() {
        vistaCartelera.mostrarPeliculas(gestorPeliculas.leerPeliculasDesdeArchivo("peliculas.dat"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vistaCartelera.getEditarButton()) {
            Integer indice = gestorPeliculas.solicitarIndicePelicula("Ingrese el nombre de la película a editar");
            if (indice != null) {
                gestorPeliculas.editarPelicula(indice);
                actualizarCartelera();
            }

        } else if (src == vistaCartelera.getAñadirPeliculaButton()) {
            gestorPeliculas.añadirPelicula();
            actualizarCartelera();

        } else if (src == vistaCartelera.getBorrarPeliculaButton()) {
            Integer indice = gestorPeliculas.solicitarIndicePelicula("Ingrese el nombre de la película a eliminar");
            if (indice != null) {
                gestorPeliculas.eliminarPelicula(indice);
                actualizarCartelera();
            }

        }
    }

    public CarteleraEditable getVistaCartelera() {
        return vistaCartelera;
    }


}
