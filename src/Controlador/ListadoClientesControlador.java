package Controlador;

import Modelo.Cliente;
import Vista.ListadoClientes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class ListadoClientesControlador implements ActionListener {

    public ListadoClientes vistaClientes;

    private final ArrayList<Cliente> clientes = new ArrayList<>();

    public ListadoClientesControlador() {
        vistaClientes = new ListadoClientes();

        vistaClientes.avanzarButton.addActionListener(this);
        vistaClientes.retrocederButton.addActionListener(this);
        vistaClientes.filtrarButton.addActionListener(this);

        rellenar();
        mostrarClientes();
        mostrarListado();
    }

    public void rellenar(){
        clientes.add(new Cliente("Pablo Jaiba", "016344675", new Date(125, Calendar.JULY, 16), 12.50F));

        clientes.add(new Cliente("Fabian Verdesoto", "0152332524", new Date(125, Calendar.JULY, 17), 24.65F));

        clientes.add(new Cliente("Katerine Yumbla", "015657299", new Date(125, Calendar.JULY, 9), 8.75F));

        vistaClientes.clienteTextArea.setText(String.join("\n"));
    }

    public void mostrarClientes(){

        vistaClientes.clienteTextArea.setText("");

        clientes.forEach(it -> {
            vistaClientes.clienteTextArea.append(it.getNombre() + "\n");
        });

        vistaClientes.Documento.setText("");

        clientes.forEach(it -> {
            vistaClientes.Documento.append(it.getCedula() + "\n");
        });

        vistaClientes.Fecha.setText("");

        clientes.forEach(it -> {
            vistaClientes.Fecha.append(it.getUltimaCompra() + "\n");
        });

        vistaClientes.Compras.setText("");

        clientes.forEach(it -> {
            vistaClientes.Compras.append(it.getTotalCompras() + " $\n");
        });

    }

    public void ordenarClientes(){
        if (vistaClientes.NombresRadio.isSelected()){
            clientes.sort(Comparator.comparing(Cliente::getNombre));
        } else if (vistaClientes.FechaRadio.isSelected()){
            clientes.sort(Comparator.comparing(Cliente::getUltimaCompra));
        } else {
            clientes.sort(Comparator.comparing(Cliente::getTotalCompras));
        }

    }

    public void mostrarListado(){
        vistaClientes.setTitle("Listado clientes");
        vistaClientes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vistaClientes.setSize(800, 500);

        vistaClientes.setContentPane(vistaClientes.MainPanel);

        vistaClientes.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaClientes.filtrarButton){
            ordenarClientes();
            mostrarClientes();
        }
    }
}
