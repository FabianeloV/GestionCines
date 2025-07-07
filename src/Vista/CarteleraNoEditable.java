package Vista;

import Modelo.Pelicula;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CarteleraNoEditable extends JFrame {
    private PanelConFondo panelPrincipal;
    private JScrollPane panelScroll;
    private JPanel panelMain;
    private JPanel panelTitulo;
    private JPanel barraInferior;
    private JButton procederAlPagoButton;
    private JButton btnBack;

    public CarteleraNoEditable() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Cartelera de Cine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
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

        procederAlPagoButton = new JButton("Proceder al Pago");

        panelTitulo.setOpaque(false);
        barraInferior.setOpaque(false);
        barraInferior.setLayout(new BorderLayout());

        btnBack = crearBotonPanel("", "/Sources/contorno-fino-de-punta-de-flecha-a-la-izquierda.png",20,30);
        btnBack.setOpaque(false);
        barraInferior.add(btnBack, BorderLayout.WEST); // A la izquierda
        barraInferior.add(procederAlPagoButton, BorderLayout.EAST); // A la derecha

        panelPrincipal.add(panelTitulo);
        panelPrincipal.add(panelScroll,BorderLayout.CENTER);
        panelPrincipal.add(barraInferior, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    public JButton getProcederAlPagoButton() {
        return procederAlPagoButton;
    }

    public void mostrarPeliculas(List<Pelicula> peliculas) {
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

