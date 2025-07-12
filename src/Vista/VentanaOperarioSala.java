package Vista;

import javax.swing.*;
import java.awt.*;

public class VentanaOperarioSala extends JFrame {

    private JComboBox<String> comboCiudad;
    private JComboBox<String> comboSala;
    private JTextArea areaInfoSala;
    private JRadioButton radioMantenimiento, radioDisponible;
    private JButton btnAceptar;
    private JButton btnBack;
    private PanelConFondo panelFondo;

    public VentanaOperarioSala() {
        setTitle("Gestión de Salas - Operario");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Fondo degradado
        panelFondo = new PanelConFondo("/Sources/Crea un hermoso degradado para tu diseño, generador de degradados CSS y PNG _ Gradients_app.jpeg");
        panelFondo.setLayout(null);
        setContentPane(panelFondo);

        // Panel Izquierdo
        JPanel panelIzquierdo = new JPanel(null);
        panelIzquierdo.setBounds(30, 50, 300, 300);
        panelIzquierdo.setOpaque(false);

        JLabel lblSeleccion = new JLabel("Seleccione ciudad y sala:");
        lblSeleccion.setBounds(10, 10, 300, 30);
        lblSeleccion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSeleccion.setForeground(Color.WHITE);
        panelIzquierdo.add(lblSeleccion);

        comboCiudad = new JComboBox<>(new String[]{"Seleccionar", "Cuenca", "Azogues", "Gualaceo"});
        comboCiudad.setBounds(10, 60, 260, 30);
        panelIzquierdo.add(comboCiudad);

        comboSala = new JComboBox<>(new String[]{"Seleccionar sala", "Sala 1", "Sala 2", "Sala 3", "Sala 4", "Sala 5"});
        comboSala.setBounds(10, 110, 260, 30);
        panelIzquierdo.add(comboSala);

        panelFondo.add(panelIzquierdo);

        // Botón Back
        btnBack = crearBotonPanel("", "/Sources/contorno-fino-de-punta-de-flecha-a-la-izquierda.png", 10, 10);
        btnBack.setBounds(10, 10, 50, 50);
        btnBack.setOpaque(false);
        panelFondo.add(btnBack);

        // Panel Derecho
        JPanel panelDerecho = new JPanel(null);
        panelDerecho.setBounds(370, 50, 420, 350);
        panelDerecho.setOpaque(false);

        JLabel lblTitulo = new JLabel("Información de la Sala");
        lblTitulo.setBounds(10, 10, 400, 30);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelDerecho.add(lblTitulo);

        areaInfoSala = new JTextArea();
        areaInfoSala.setBounds(10, 50, 390, 100);
        areaInfoSala.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaInfoSala.setEditable(false);
        areaInfoSala.setLineWrap(true);
        areaInfoSala.setWrapStyleWord(true);
        areaInfoSala.setBackground(new Color(255, 255, 255, 200));
        areaInfoSala.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelDerecho.add(areaInfoSala);

        radioMantenimiento = new JRadioButton("En Mantenimiento");
        radioDisponible = new JRadioButton("Disponible");

        radioMantenimiento.setBounds(10, 170, 180, 30);
        radioDisponible.setBounds(200, 170, 150, 30);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radioMantenimiento);
        grupo.add(radioDisponible);

        radioMantenimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        radioDisponible.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        radioMantenimiento.setOpaque(false);
        radioDisponible.setOpaque(false);

        radioMantenimiento.setForeground(Color.WHITE);
        radioDisponible.setForeground(Color.WHITE);

        panelDerecho.add(radioMantenimiento);
        panelDerecho.add(radioDisponible);

        btnAceptar = new JButton("Actualizar Sala");
        btnAceptar.setBounds(130, 230, 160, 35);
        btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAceptar.setBackground(new Color(100, 200, 200));
        panelDerecho.add(btnAceptar);

        panelFondo.add(panelDerecho);
    }

    // Getters
    public JComboBox<String> getComboCiudad() {
        return comboCiudad; }

    public JComboBox<String> getComboSala() {
        return comboSala; }

    public JRadioButton getRadioMantenimiento() {
        return radioMantenimiento; }

    public JRadioButton getRadioDisponible() {
        return radioDisponible; }

    public JButton getBtnAceptar() {
        return btnAceptar; }

    public JButton getBtnBack(){
        return btnBack;}

    public void setInfoSala(String nombre, int capacidad, boolean mantenimiento) {
        String estado = mantenimiento ? "En mantenimiento" : "Disponible";
        areaInfoSala.setText(
                "Nombre: " + nombre + "\n" +
                        "Capacidad: " + capacidad + "\n" +
                        "Estado: " + estado
        );
        radioMantenimiento.setSelected(mantenimiento);
        radioDisponible.setSelected(!mantenimiento);
    }

    private JButton crearBotonPanel(String texto, String iconoRuta, int x, int y) {
        JButton boton = new JButton("<html><center>" + texto + "</center></html>",
                new ImageIcon(getClass().getResource(iconoRuta)));
        boton.setBounds(x, y, 50, 50);
        boton.setHorizontalTextPosition(JButton.CENTER);
        boton.setVerticalTextPosition(JButton.BOTTOM);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(204, 204, 204));
        return boton;
    }

}
