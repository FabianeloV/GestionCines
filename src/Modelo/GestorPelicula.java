package Modelo;

import javax.swing.*;
import java.util.ArrayList;

public class GestorPelicula {

    ArrayList<Pelicula> peliculas;

    public GestorPelicula() {
        peliculas = new ArrayList<>();
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void cargarPeliculas() {
        String nombre="Lilo y Stitch";
        String descripcion="Lilo es una niña hawaiana solitaria que adopta a un perro que en realidad es un extraterrestre travieso que se esconde de unos cazadores intergalácticos.";
        String duracion="1h:45min";
        String genero="Infantil";
        String rutaPoster="C:\\Users\\Pablo Jaiba\\IdeaProjects\\GestionCines\\src\\Peliculas\\liloSticth.png";

        String nombre1="Django";
        String descripcion1="Un antiguo esclavo une sus fuerzas con un cazador de recompensas alemán que lo libera y ayuda a cazar a los criminales más buscados del Sur, todo ello con la esperanza de encontrar a su esposa perdida hace mucho tiempo.";
        String duracion1="2h 45m";
        String genero1="Wéstern/Acción";
        String rutaPoster1="C:\\Users\\Pablo Jaiba\\IdeaProjects\\GestionCines\\src\\Peliculas\\Django.jpg";

        Pelicula p=new Pelicula(nombre,descripcion,duracion,genero,rutaPoster);
        Pelicula p2=new Pelicula(nombre1,descripcion1,duracion1,genero1,rutaPoster1);
        peliculas.add(p);
        peliculas.add(p2);
    }



    public void añadirPelicula() {

        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre de la película:");
        String duracion = JOptionPane.showInputDialog(null, "Ingrese la duración de la película:");
        String descripcion = JOptionPane.showInputDialog(null, "Ingrese la descripción de la película:");
        String genero = JOptionPane.showInputDialog(null, "Ingrese el género de la película:");

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Selecciona el nuevo póster");
        selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));

        String rutaPoster;
        int resultado = selector.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            rutaPoster = selector.getSelectedFile().getAbsolutePath();
        } else {
            JOptionPane.showMessageDialog(null, "No se seleccionó ningún archivo. No se añadió la película.");
            return; // sale del método sin añadir nada
        }

        Pelicula peliculaNueva = new Pelicula(nombre, descripcion, duracion, genero, rutaPoster);

        // Asegurarse de que la lista esté inicializada
        if (peliculas == null) {
            peliculas = new ArrayList<>();
        }

        peliculas.add(peliculaNueva);
        JOptionPane.showMessageDialog(null, "Película añadida correctamente.");
    }


    public void eliminarPelicula(int indice) {
        if (indice >= 0 && indice < peliculas.size()) {
            peliculas.remove(indice);
        }
    }

    public void editarPelicula(int indice) {
        if (indice < 0 || indice >= peliculas.size()) {
            JOptionPane.showMessageDialog(null, "Índice inválido");
            return;
        }

        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre de la película:");
        String duracion = JOptionPane.showInputDialog(null, "Ingrese la duración de la película:");
        String descripcion = JOptionPane.showInputDialog(null, "Ingrese la descripción de la película:");
        String genero = JOptionPane.showInputDialog(null, "Ingrese el género de la película:");

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Selecciona el nuevo póster");
        selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));

        String rutaPoster = peliculas.get(indice).getRutaPoster();

        int resultado = selector.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            rutaPoster = selector.getSelectedFile().getAbsolutePath();
        }

        Pelicula peliculaEditada = new Pelicula(nombre, descripcion, duracion, genero, rutaPoster);
        peliculas.set(indice, peliculaEditada);

        JOptionPane.showMessageDialog(null, "Película actualizada correctamente.");
    }

    public int buscarPelicula(String nombre) {
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getNombre().equals(nombre)) {
                return i;
            }
        }

        return -1;
    }

    public Integer solicitarIndicePelicula(String mensaje) {
        int indice = -1;
        while(indice < 0) {
            String nombre = JOptionPane.showInputDialog(null, mensaje);
            if(nombre == null) return null;

            nombre = nombre.trim();
            if(nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre inválido");
                continue;
            }

            indice =buscarPelicula(nombre);
            if(indice == -1) {
                JOptionPane.showMessageDialog(null, "Película no encontrada");
            }
        }
        return indice;
    }

}
