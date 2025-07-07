package Modelo;

public class Asiento {
    private String id; // Ej. "A1", "B5"
    private boolean ocupado;

    public Asiento(String id) {
        this.id = id;
        this.ocupado = false;
    }

    public String getId() {
        return id;
    }

    public boolean estaOcupado() {
        return ocupado;
    }

    public void ocupar() {
        this.ocupado = true;
    }
}
