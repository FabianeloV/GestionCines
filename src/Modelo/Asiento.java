package Modelo;

public class Asiento {
    private String id;
    private boolean ocupado;
    private boolean seleccionado; // Nuevo campo para selección temporal

    public Asiento(String id) {
        this.id = id;
        this.ocupado = false;
        this.seleccionado = false;
    }

    public String getId() {
        return id;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    @Override
    public String toString() {
        return "Asiento{" +
                "id='" + id + '\'' +
                ", ocupado=" + ocupado +
                ", seleccionado=" + seleccionado +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Asiento asiento = (Asiento) obj;
        return id.equals(asiento.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}