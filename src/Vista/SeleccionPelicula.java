package Vista;

import Modelo.Pelicula;
import com.toedter.calendar.JDateChooser;

import java.text.SimpleDateFormat;

import javax.swing.*;
import java.awt.*;

public class SeleccionPelicula extends JFrame {
    private JTextArea infoPelicula;
    private JSpinner spinnerCantidad;
    private JButton btnContinuar, btnBack;
    private JComboBox<String> cbCiudad, cbTipoSala, cbFormato, cbIdiomas;
    private JTextField tfHora, tfFecha;
    private JDateChooser fecha;
    private JLabel lblHora, lblFecha, lblFunciones, lblCiudad,
            lblCantidad, lblTipoSala, lblFormato, lblIdiomas;
    private PanelConFondo panelFondo;

    public SeleccionPelicula() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Selección de Película");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        // 1) panelFondo con imagen
        panelFondo = new PanelConFondo("/Sources/Crea un hermoso degradado para tu diseño, generador de degradados CSS y PNG _ Gradients_app.jpeg");
        setContentPane(panelFondo);

        // 2) crear componentes
        infoPelicula = new JTextArea(5, 20);
        infoPelicula.setEditable(false);

        lblCantidad = new JLabel("Cantidad de Boletos:");
        lblCantidad.setBounds(310, 260, 150, 25);
        lblCantidad.setForeground(Color.white);
        panelFondo.add(lblCantidad);

        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spinnerCantidad.setFont(new Font("Segoe UI", Font.BOLD, 14));
        spinnerCantidad.setBounds(310, 290, 50, 30);
        panelFondo.add(spinnerCantidad);

        btnContinuar = new JButton("Continuar");

        // ComboBoxes - inicializados vacíos, el controlador los llenará
        cbCiudad = new JComboBox<>();
        cbTipoSala = new JComboBox<>();
        cbFormato = new JComboBox<>(new String[]{"Formatos", "2D", "3D", "4D"});
        cbIdiomas = new JComboBox<>(new String[]{"Idiomas", "Español", "Inglés"});

        tfHora = new JTextField();
        tfFecha = new JTextField();

        // Labels
        lblHora = new JLabel("Hora:");
        lblFecha = new JLabel("Fecha:");
        lblFunciones = new JLabel("Funciones:");
        lblCiudad = new JLabel("Ciudad:");
        lblTipoSala = new JLabel("Sala:");
        lblFormato = new JLabel("Formato:");
        lblIdiomas = new JLabel("Idioma:");

        // Botón Back
        btnBack = crearBotonPanel("", "/Sources/contorno-fino-de-punta-de-flecha-a-la-izquierda.png", 20, 30);
        btnBack.setBounds(0, 0, 50, 50);
        btnBack.setOpaque(false);
        panelFondo.add(btnBack);

        // Posicionamiento de componentes
        lblCiudad.setBounds(310, 20, 110, 25);
        lblCiudad.setForeground(Color.white);
        panelFondo.add(lblCiudad);

        cbCiudad.setBounds(310, 50, 110, 25);
        panelFondo.add(cbCiudad);

        lblTipoSala.setBounds(430, 20, 110, 25);
        lblTipoSala.setForeground(Color.white);
        panelFondo.add(lblTipoSala);

        cbTipoSala.setBounds(430, 50, 150, 25);
        panelFondo.add(cbTipoSala);

        lblFunciones.setBounds(310, 90, 100, 25);
        lblFunciones.setForeground(Color.white);
        panelFondo.add(lblFunciones);

        lblIdiomas.setBounds(310, 120, 100, 25);
        lblIdiomas.setForeground(Color.white);
        panelFondo.add(lblIdiomas);

        lblFormato.setBounds(430, 120, 100, 25);
        lblFormato.setForeground(Color.white);
        panelFondo.add(lblFormato);

        cbIdiomas.setBounds(310, 150, 110, 25);
        panelFondo.add(cbIdiomas);

        cbFormato.setBounds(430, 150, 110, 25);
        panelFondo.add(cbFormato);

        lblHora.setBounds(460, 190, 50, 25);
        lblHora.setForeground(Color.white);
        panelFondo.add(lblHora);

        tfHora.setBounds(460, 220, 120, 25);
        panelFondo.add(tfHora);

        lblFecha.setBounds(310, 190, 60, 25);
        lblFecha.setForeground(Color.white);
        panelFondo.add(lblFecha);

        fecha = new JDateChooser();
        fecha.setBounds(310, 220, 120, 25);
        panelFondo.add(fecha);

        infoPelicula.setBounds(40, 110, 230, 160);
        panelFondo.add(new JScrollPane(infoPelicula));

        btnContinuar.setBounds(510, 360, 120, 25);
        panelFondo.add(btnContinuar);

        // Configurar opacidad
        panelFondo.setOpaque(true);
        for (Component c : panelFondo.getComponents()) {
            if (c instanceof JPanel) ((JPanel) c).setOpaque(false);
        }

        for (Component c : panelFondo.getComponents()) {
            if (c instanceof JScrollPane sp) {
                sp.setBorder(null);
                sp.setOpaque(false);
                sp.getViewport().setOpaque(false);
            }
        }

        setLayout(null);
    }

    private JButton crearBotonPanel(String texto, String iconoRuta, int x, int y) {
        JButton boton = new JButton("<html><center>" + texto + "</center></html>",
                new ImageIcon(getClass().getResource(iconoRuta)));
        boton.setBounds(x, y, 160, 180);
        boton.setHorizontalTextPosition(JButton.CENTER);
        boton.setVerticalTextPosition(JButton.BOTTOM);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(204, 204, 204));
        return boton;
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

    public void mostrarPelicula(Pelicula pelicula) {
        Component[] components = panelFondo.getComponents();
        for (Component c : components) {
            if (c.getName() != null && c.getName().equals("tarjetaPelicula")) {
                panelFondo.remove(c);
            }
        }

        // Crea tarjeta nueva
        JPanel tarjeta = crearTarjetaPelicula(pelicula);
        tarjeta.setBounds(40, 90, 230, 300);
        tarjeta.setName("tarjetaPelicula");
        panelFondo.add(tarjeta);

        panelFondo.revalidate();
        panelFondo.repaint();
    }

    // Método para que el controlador actualice el máximo de boletos
    public void actualizarMaximoBoletos(int maximo) {
        SpinnerNumberModel model = (SpinnerNumberModel) spinnerCantidad.getModel();
        model.setMaximum(maximo);

        // Si el valor actual es mayor al máximo, ajustarlo
        if ((Integer) spinnerCantidad.getValue() > maximo) {
            spinnerCantidad.setValue(maximo);
        }
    }

    public JButton getBtnContinuar() {
        return btnContinuar;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    public JTextField getTfHora() {
        return tfHora;
    }

    public JTextField getTfFecha() {
        return tfFecha;
    }

    public JComboBox<String> getCbCiudad() {
        return cbCiudad;
    }

    public JComboBox<String> getCbTipoSala() {
        return cbTipoSala;
    }

    public JComboBox<String> getCbFormato() {
        return cbFormato;
    }

    public JComboBox<String> getCbIdiomas() {
        return cbIdiomas;
    }

    public int getCantidadBoletos() {
        return (int) spinnerCantidad.getValue();
    }

}