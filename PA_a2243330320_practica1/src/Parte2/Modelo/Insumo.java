package Parte2.Modelo;

public class Insumo {
private String idproducto;
private String producto;
private String idCategoria;


public Insumo(String idproducto, String producto, String idCategoria) {
	this.idproducto=idproducto;
	this.producto=producto;
	this.idCategoria=idCategoria;
	
}
public String getIdproducto() {
	return idproducto;
}


public void setIdproducto(String idproducto) {
	this.idproducto = idproducto;
}


public String getProducto() {
	return producto;
}


public void setProducto(String producto) {
	this.producto = producto;
}


public String getIdCategoria() {
	return idCategoria;
}


public void setIdCategoria(String idCategoria) {
	this.idCategoria = idCategoria;
}
@Override
public String toString() {
	return idproducto + "\t\t"+ producto + "\t\t"+ idCategoria;
}







}
