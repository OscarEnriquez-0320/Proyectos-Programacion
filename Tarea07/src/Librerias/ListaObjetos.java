package Librerias;

import java.util.ArrayList;

public class ListaObjetos {
    private ArrayList lista;


    public ListaObjetos(){
    	 lista= new ArrayList();
   
    }

    public boolean Insertar(Object nodo){
       if (this.buscar(nodo)==-1)
        	{this.lista.add(nodo);
        	return true;
        	}
    	return false;
    }

    public boolean Eliminar(Object nodo){
    	int pos =this.buscar(nodo);
    	if (pos>-1)
        {
        	this.lista.remove(pos);
        	return true;
        }
            return false;
    }

    public boolean Eliminar(int pos){
    	this.lista.remove(pos);
        
        return false;
    }

    public void Modificar(int pos, Object nodo){
    	this.lista.set(pos, nodo);
        
    }

    public int getultimapos(){
        return this.lista.size()-1;
    }

    // debera recibir un objeto con el dato id a buscar
    public int buscar(Object nodoid){
       int pos=-1;
       int enc=-1;
    	for(Object nodo:this.lista)
    	{ pos++;
    		if( nodo.equals(nodoid))
    			enc=pos;
    	}
    		   
    	return enc;
    }

    public ArrayList getLista(){
        return this.lista;
    }

    public Object getPos(int pos){
        return this.lista.get(pos);
    }

    

}
