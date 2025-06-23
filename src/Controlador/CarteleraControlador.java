package Controlador;

import Modelo.GestorPelicula;
import Modelo.Pelicula;
import Vista.CarteleraEditable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarteleraControlador implements ActionListener {

    private final GestorPelicula gestorPeliculas;
    private final CarteleraEditable vistaCartelera;

    public CarteleraControlador() {
        // Inicialización de componentes

        gestorPeliculas = new GestorPelicula();
        vistaCartelera = new CarteleraEditable();


        vistaCartelera.getAñadirPeliculaButton().addActionListener(this);
        vistaCartelera.getEditarButton().addActionListener(this);
        vistaCartelera.getBorrarPeliculaButton().addActionListener(this);

        configurarVistaCartelera();

        cargarDatosIniciales();
    }

    private void configurarVistaCartelera() {

        vistaCartelera.setTitle("Cartelera de Cine");
        vistaCartelera.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaCartelera.setSize(800, 600);

        vistaCartelera.getPanelMain().setLayout(new WrapLayout());

        vistaCartelera.getPanelMain().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        vistaCartelera.getPanelMain().setBackground(Color.WHITE);

        vistaCartelera.setVisible(true);
    }

    private void cargarDatosIniciales() {
        gestorPeliculas.cargarPeliculas();
        actualizarCartelera();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaCartelera.getEditarButton()) {
            Integer indice = gestorPeliculas.solicitarIndicePelicula("Ingrese el nombre de la película a editar");
            if(indice != null) {
                gestorPeliculas.editarPelicula(indice);
                actualizarCartelera();
            }
        }
        else if (e.getSource() == vistaCartelera.getAñadirPeliculaButton()) {
            gestorPeliculas.añadirPelicula();
            actualizarCartelera();
        }else if (e.getSource() == vistaCartelera.getBorrarPeliculaButton()) {
            Integer indice = gestorPeliculas.solicitarIndicePelicula("Ingrese el nombre de la película a eliminar");
            if(indice != null) {
                gestorPeliculas.eliminarPelicula(indice);
                actualizarCartelera();
            }
        }
    }

    private void actualizarCartelera() {
        JPanel panelContenido = vistaCartelera.getPanelMain();
        panelContenido.removeAll();

        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContenido.setBackground(Color.WHITE);

        for (Pelicula pelicula : gestorPeliculas.getPeliculas()) {
            JPanel tarjeta = crearTarjetaPelicula(pelicula);
            panelContenido.add(tarjeta);
        }

        panelContenido.revalidate();
        panelContenido.repaint();
    }

    private JPanel crearTarjetaPelicula(Pelicula pelicula) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setMaximumSize(new Dimension(200, 500));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

        añadirPoster(tarjeta, pelicula.getRutaPoster());
        añadirLabel(tarjeta, pelicula.getNombre(), Font.BOLD, 18);
        añadirLabel(tarjeta, "Género: " + pelicula.getGenero(), Font.PLAIN, 16);
        añadirLabel(tarjeta, "Duración: " + pelicula.getDuracion(), Font.PLAIN, 16);
        añadirJtextArea(tarjeta, "Sinopsis: " + pelicula.getDescripcion());

        return tarjeta;
    }

    private void añadirPoster(JPanel panel, String rutaPoster) {
        try {
            ImageIcon icon = new ImageIcon(rutaPoster);
            Image imagen = icon.getImage().getScaledInstance(70, 90, Image.SCALE_SMOOTH);
            JLabel lblPoster = new JLabel(new ImageIcon(imagen));
            lblPoster.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lblPoster);
        } catch (Exception e) {
            JLabel lblError = new JLabel("Poster no disponible");
            lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lblError);
        }
    }

    private void añadirLabel(JPanel panel, String texto, int estilo, int tamaño) {
        JLabel label = new JLabel(texto);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", estilo, tamaño));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(label);
    }

    private void añadirJtextArea(JPanel panel, String texto) {
        JTextArea textArea = new JTextArea(texto);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setBackground(panel.getBackground());

        // Configurar márgenes internos
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Sinopsis"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(230, 100));
        scrollPane.setMaximumSize(new Dimension(230, 100));
        scrollPane.setBorder(null);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(scrollPane);
    }


    static class WrapLayout extends FlowLayout {
        public WrapLayout() {
            super(FlowLayout.LEFT, 15, 15);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            return layoutSize(target, false);
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;

                int hgap = getHgap();
                int vgap = getVgap();
                Insets insets = target.getInsets();
                int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);

                Dimension dim = new Dimension(0, 0);
                int rowWidth = 0;
                int rowHeight = 0;

                for (Component comp : target.getComponents()) {
                    if (comp.isVisible()) {
                        Dimension d = preferred ? comp.getPreferredSize() : comp.getMinimumSize();
                        if (rowWidth + d.width > maxWidth) {
                            dim.width = Math.max(dim.width, rowWidth);
                            dim.height += rowHeight + vgap;
                            rowWidth = 0;
                            rowHeight = 0;
                        }
                        if (rowWidth != 0) rowWidth += hgap;
                        rowWidth += d.width;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }

                dim.width = Math.max(dim.width, rowWidth);
                dim.height += rowHeight;

                dim.width += insets.left + insets.right + hgap * 2;
                dim.height += insets.top + insets.bottom + vgap * 2;

                return dim;
            }
        }
    }
}