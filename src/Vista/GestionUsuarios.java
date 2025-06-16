package Vista;

import javax.swing.*;

public class GestionUsuarios extends JFrame {
    private JPanel Main;
    private JButton seleccionarButton;
    private JLabel icon1;
    private JLabel icon2;
    private JLabel icon3;

    public GestionUsuarios() {
        this.setTitle("Roles");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(Main);
        this.setVisible(true);
        this.setSize(500,300);
    }
}
