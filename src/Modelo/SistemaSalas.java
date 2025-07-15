package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaSalas {
    private Map<String, List<Sala>> salasPorCiudad;

    public SistemaSalas() {
        salasPorCiudad = new HashMap<>();
        inicializarSalas();
    }

    private List<Sala> crearSalasBase(String ciudad) {
        List<Sala> salas = new ArrayList<>();
        salas.add(new Sala("Sala 1", ciudad, 8, 5));
        salas.add(new Sala("Sala 2", ciudad, 10, 6));
        salas.add(new Sala("Sala 3", ciudad, 12, 8));
        salas.add(new Sala("Sala 4", ciudad, 6, 4));
        salas.add(new Sala("Sala 5", ciudad, 15, 10));
        return salas;
    }

    private void inicializarSalas() {
        salasPorCiudad.put("Cuenca", crearSalasBase("Cuenca"));
        salasPorCiudad.put("Azogues", crearSalasBase("Azogues"));
        salasPorCiudad.put("Gualaceo", crearSalasBase("Gualaceo"));
    }


    public List<String> getCiudades() {
        return new ArrayList<>(salasPorCiudad.keySet());
    }

    public List<Sala> getSalasPorCiudad(String ciudad) {
        return salasPorCiudad.getOrDefault(ciudad, new ArrayList<>());
    }

    public Sala getSala(String ciudad, String nombreSala) {
        List<Sala> salas = getSalasPorCiudad(ciudad);
        return salas.stream()
                .filter(sala -> sala.getNombre().equals(nombreSala))
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarEstadoSala(String ciudad, String nombreSala, boolean enMantenimiento) {
        Sala sala = getSala(ciudad, nombreSala);
        if (sala != null) {
            sala.setEnMantenimiento(enMantenimiento);
            return true;
        }
        return false;
    }

    public List<Sala> getSalasDisponibles(String ciudad) {
        return getSalasPorCiudad(ciudad).stream()
                .filter(sala -> !sala.isEnMantenimiento())
                .toList();
    }

    public List<Sala> getSalasEnMantenimiento(String ciudad) {
        return getSalasPorCiudad(ciudad).stream()
                .filter(Sala::isEnMantenimiento)
                .toList();
    }
}