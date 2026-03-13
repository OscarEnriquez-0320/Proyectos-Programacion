package Modelo;

import java.util.Objects;

public class Academias {
    private String academia;
    private String descripcion; // Campo adicional opcional

    public Academias() {
        super();
        this.academia = "";
        this.descripcion = "";
    }

    public Academias(String academia) {
        this.academia = academia;
        this.descripcion = "";
    }

    public String getAcademia() {
        return academia;
    }

    public void setAcademia(String academia) {
        this.academia = academia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.getAcademia();
    }

    @Override
    public int hashCode() {
        return Objects.hash(academia);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Academias other = (Academias) obj;
        return Objects.equals(academia, other.academia);
    }
}
