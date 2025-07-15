package Modelo;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class GestorPelicula implements Serializable {

    ArrayList<Pelicula> peliculas;

    public GestorPelicula() {
        peliculas = new ArrayList<>();
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }


    public void añadirPelicula() {

        peliculas=leerPeliculasDesdeArchivo("peliculas.dat");

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
        guardarEstadoActual("peliculas.dat");
    }




    public void eliminarPelicula(int indice) {
        peliculas=leerPeliculasDesdeArchivo("peliculas.dat");
        if (indice >= 0 && indice < peliculas.size()) {
            peliculas.remove(indice);
        }
        guardarEstadoActual("peliculas.dat");
    }

    public void editarPelicula(int indice) {
        peliculas = leerPeliculasDesdeArchivo("peliculas.dat");

        if (indice < 0 || indice >= peliculas.size()) {
            JOptionPane.showMessageDialog(null, "Índice inválido");
            return;
        }

        Pelicula original = peliculas.get(indice);

        String[] opciones = {"Nombre", "Duración", "Descripción", "Género", "Póster"};
        String seleccion = (String) JOptionPane.showInputDialog(
                null,
                "¿Qué deseas editar?",
                "Editar Película",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == null) return; // Cancelado

        String nuevoNombre = original.getNombre();
        String nuevaDuracion = original.getDuracion();
        String nuevaDescripcion = original.getDescripcion();
        String nuevoGenero = original.getGenero();
        String nuevaRutaPoster = original.getRutaPoster();

        switch (seleccion) {
            case "Nombre":
                String nombre = JOptionPane.showInputDialog(null, "Nuevo nombre:", original.getNombre());
                if (nombre != null && !nombre.trim().isEmpty()) {
                    nuevoNombre = nombre.trim();
                }
                break;

            case "Duración":
                String duracion = JOptionPane.showInputDialog(null, "Nueva duración:", original.getDuracion());
                if (duracion != null && !duracion.trim().isEmpty()) {
                    nuevaDuracion = duracion.trim();
                }
                break;

            case "Descripción":
                String descripcion = JOptionPane.showInputDialog(null, "Nueva descripción:", original.getDescripcion());
                if (descripcion != null && !descripcion.trim().isEmpty()) {
                    nuevaDescripcion = descripcion.trim();
                }
                break;

            case "Género":
                String genero = JOptionPane.showInputDialog(null, "Nuevo género:", original.getGenero());
                if (genero != null && !genero.trim().isEmpty()) {
                    nuevoGenero = genero.trim();
                }
                break;

            case "Póster":
                JFileChooser selector = new JFileChooser();
                selector.setDialogTitle("Selecciona el nuevo póster");
                selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));
                int resultado = selector.showOpenDialog(null);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    nuevaRutaPoster = selector.getSelectedFile().getAbsolutePath();
                }
                break;
        }

        Pelicula editada = new Pelicula(nuevoNombre, nuevaDescripcion, nuevaDuracion, nuevoGenero, nuevaRutaPoster);
        peliculas.set(indice, editada);

        JOptionPane.showMessageDialog(null, "Película actualizada correctamente.");
        guardarEstadoActual("peliculas.dat");
    }


    public int buscarPelicula(String nombre) {
        actualizarLista();

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

    public void guardarPeliculasDeEjemplo(String rutaArchivo) {

        Pelicula p1 = new Pelicula(
                "Lilo y Stitch",
                "Lilo es una niña hawaiana solitaria que adopta a un perro que en realidad es un extraterrestre travieso que se esconde de unos cazadores intergalácticos.",
                "1h:45min",
                "Infantil",
                "C:\\Users\\Pablo Jaiba\\IdeaProjects\\GestionCines\\src\\Peliculas\\liloSticth.png"
        );

        Pelicula p2 = new Pelicula(
                "Django",
                "Un antiguo esclavo une sus fuerzas con un cazador de recompensas alemán que lo libera y ayuda a cazar a los criminales más buscados del Sur, todo ello con la esperanza de encontrar a su esposa perdida hace mucho tiempo.",
                "2h 45m",
                "Wéstern/Acción",
                "C:\\Users\\Pablo Jaiba\\IdeaProjects\\GestionCines\\src\\Peliculas\\Django.jpg"
        );

        peliculas.add(p1);
        peliculas.add(p2);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(peliculas);
            out.flush();
            out.close();
            System.out.println("Películas de ejemplo guardadas correctamente.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo.");
        }
    }

    public ArrayList<Pelicula> leerPeliculasDesdeArchivo(String rutaArchivo) {
        ArrayList<Pelicula> leidas = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            leidas = (ArrayList<Pelicula>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return leidas;
    }

    public void guardarEstadoActual(String NombreArchivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(NombreArchivo))) {
            out.writeObject(peliculas);
        } catch (IOException e) {
            System.err.println("Error al guardar el estado actual:");
            e.printStackTrace();
        }
    }
     public void actualizarLista(){
        peliculas = leerPeliculasDesdeArchivo("peliculas.dat");
     }

}
