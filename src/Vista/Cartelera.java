package Vista;

import javax.swing.*;

public class Cartelera extends JFrame {
    private JPanel panel1;
    private JScrollPane PanelMain;
    private JLabel Poster1;
    private JLabel Poster2;
    private JLabel Poster3;
    private JLabel Poster4;
    private JLabel Poster5;
    private JLabel Poster6;
    private JList list1;

    public Cartelera() {
        this.setTitle("Cartelera");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setVisible(true);
        this.setSize(600,800);
    }
}
