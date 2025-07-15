package Vista;

import Modelo.Pelicula;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CarteleraEditable extends JFrame {
    private PanelConFondo panelPrincipal;
    private JScrollPane panelScroll;
    private JPanel panelMain;
    private JPanel panelTitulo;
    private JButton añadirPeliculaButton;
    private JButton borrarPeliculaButton;
    private JButton editarButton;
    private JButton btnBack;
    private JPanel barraInferior;


    public CarteleraEditable() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Cartelera de Cine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        panelPrincipal = new PanelConFondo("/Sources/Crea un hermoso degradado para tu diseño, generador de degradados CSS y PNG _ Gradients_app.jpeg");
        panelPrincipal.setLayout(new BorderLayout());

        panelTitulo = new JPanel();
        barraInferior = new JPanel();
        panelMain = new JPanel(new WrapLayout());
        panelMain.setOpaque(false);

        panelScroll = new JScrollPane(panelMain);
        panelScroll.setOpaque(false);
        panelScroll.getViewport().setOpaque(false);

        añadirPeliculaButton = new JButton("Añadir Película");
        editarButton = new JButton("Editar Película");
        borrarPeliculaButton = new JButton("Eliminar Película");

        panelTitulo.setOpaque(false);
        barraInferior.setOpaque(false);

        btnBack = crearBotonPanel("", "/Sources/contorno-fino-de-punta-de-flecha-a-la-izquierda.png",20,30);
        btnBack.setOpaque(false);
        barraInferior.add(btnBack, BorderLayout.WEST);

        panelTitulo.add(añadirPeliculaButton);
        panelTitulo.add(editarButton);
        panelTitulo.add(borrarPeliculaButton);


        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelScroll, BorderLayout.CENTER);
        panelPrincipal.add(barraInferior, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    public JButton getAñadirPeliculaButton() { return añadirPeliculaButton; }
    public JButton getBorrarPeliculaButton() { return borrarPeliculaButton; }
    public JButton getEditarButton() { return editarButton; }

    public void mostrarPeliculas(ArrayList<Pelicula> peliculas) {
        panelMain.removeAll();

        for (Pelicula pelicula : peliculas) {
            JPanel tarjeta = crearTarjetaPelicula(pelicula);
            panelMain.add(tarjeta);
        }

        panelMain.revalidate();
        panelMain.repaint();
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
        añadirJTextArea(tarjeta, pelicula.getDescripcion());

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

    private void añadirJTextArea(JPanel panel, String texto) {
        JTextArea textArea = new JTextArea("Sinopsis: " + texto);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setBackground(panel.getBackground());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(230, 100));
        scrollPane.setMaximumSize(new Dimension(230, 100));
        scrollPane.setBorder(null);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(scrollPane);
    }

    private JButton crearBotonPanel(String texto, String iconoRuta, int x, int y) {
        JButton boton = new JButton("<html><center>" + texto + "</center></html>", new ImageIcon(getClass().getResource(iconoRuta)));
        boton.setBounds(x, y, 160, 180);
        boton.setHorizontalTextPosition(JButton.CENTER);
        boton.setVerticalTextPosition(JButton.BOTTOM);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(204, 204, 204));
        return boton;
    }

    public JButton getBtnBack() {
        return btnBack;
    }
}

