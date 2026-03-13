package Modelo;

import java.util.Objects;

public class Materias {
private String materia;
private String carrera;

@Override
public String toString() {
	return this.getMateria();
}

public Materias(String materia, String carrera) {
    super();
    this.materia = materia;
    this.carrera = carrera;
}


@Override
public int hashCode() {
	return Objects.hash(materia);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Materias other = (Materias) obj;
	return Objects.equals(materia, other.materia);
}

public String getMateria() {
	return materia;
}
public void setMateria(String materia) {
	this.materia = materia;
}
public String getCarrera() {
	return carrera;
}
public void setCarrera(String carrera) {
	this.carrera = carrera;
}


}
