package Modelo;

import java.io.*;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;

public class GestorCliente {
    private static final String ARCHIVO_CLIENTES = "clientes.dat";
    private LinkedList<Cliente> clientes;

    public GestorCliente() {
        clientes = new LinkedList<>();
        cargarClientes();
    }

    // Cargar clientes desde archivo
    @SuppressWarnings("unchecked")
    private void cargarClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_CLIENTES))) {
            clientes = (LinkedList<Cliente>) ois.readObject();
            System.out.println("Clientes cargados correctamente: " + clientes.size());
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de clientes no encontrado. Se creará uno nuevo.");
            clientes = new LinkedList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
            clientes = new LinkedList<>();
        }
    }

    // Guardar clientes en archivo
    public void guardarClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CLIENTES))) {
            oos.writeObject(clientes);
            System.out.println("Clientes guardados correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar clientes: " + e.getMessage());
        }
    }

    // Buscar cliente por cédula
    public Cliente buscarClientePorCedula(String cedula) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                return cliente;
            }
        }
        return null;
    }

    // Agregar nuevo cliente
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        guardarClientes();
    }

    // Actualizar cliente existente
    public void actualizarCliente(Cliente clienteActualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCedula().equals(clienteActualizado.getCedula())) {
                clientes.set(i, clienteActualizado);
                guardarClientes();
                return;
            }
        }
    }

    // Procesar compra (crear cliente o actualizar existente)
    public void procesarCompra(String nombre, String cedula, String correo, Float montoCompra) {
        Cliente clienteExistente = buscarClientePorCedula(cedula);

        if (clienteExistente != null) {
            // Cliente existe, actualizar su total de compras
            clienteExistente.agregarCompra(montoCompra);
            // Actualizar también nombre y correo en caso de que hayan cambiado
            clienteExistente.setNombre(nombre);
            clienteExistente.setCorreo(correo);
            clienteExistente.setUltimaCompra(new Date(2025,07,16));
            actualizarCliente(clienteExistente);
            System.out.println("Cliente actualizado: " + clienteExistente);
        } else {
            // Cliente no existe, crear nuevo
            Cliente nuevoCliente = new Cliente(nombre, cedula,new Date(2025,07,16), montoCompra);
            agregarCliente(nuevoCliente);
            System.out.println("Nuevo cliente creado: " + nuevoCliente);
        }
    }

    // Obtener todos los clientes
    public LinkedList<Cliente> obtenerTodosLosClientes() {
        return new LinkedList<>(clientes);
    }

    // Obtener información de un cliente específico
    public String obtenerInfoCliente(String cedula) {
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente != null) {
            return cliente.toString();
        }
        return "Cliente no encontrado";
    }
}
