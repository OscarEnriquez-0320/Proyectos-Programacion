package Librerias;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;



public class Libreria  {


	/**
	 * Verifica si un archivo existe en el sistema.
	 *
	 * @param narchivo El nombre del archivo que se desea verificar.
	 * @return true si el archivo existe, false en caso contrario.
	 */
	public static boolean ExisteArchivo(String narchivo) {
		File archivo = new File(narchivo);
		return archivo.exists();
	}

	
	public static ArrayList<String[]> LeerDatosCSV(String narchivo) {
		ArrayList<String[]> lista= new ArrayList<String[]>();
		try (BufferedReader lector = new BufferedReader(new FileReader(narchivo))) {
			String linea;
			while ((linea = lector.readLine()) != null) {
				linea = linea.trim(); // Elimina espacios en blanco
				if (!linea.isEmpty()) { // Ignorar filas vacías
					String[] datos = linea.split(",");
					lista.add(datos); // agrego la string arreglo 
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
		return lista;
	}
	/**
	 * Escribe contenido en un archivo CSV. Si el archivo ya existe, agrega el
	 * contenido al final del archivo.
	 *
	 * @param archivo   El nombre del archivo CSV donde se escribirá el contenido.
	 * @param contenido El contenido que se escribirá en el archivo.
	 */
	public static void EscribirArchivoCSV(String narchivo, String contenido,boolean accion) {
		try (BufferedWriter escritor = new BufferedWriter(
				new FileWriter(narchivo, accion))) {
			escritor.write(contenido);
		} catch (IOException e) {
			System.out.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
		}
	}

	
	public static String lineacvs(Object[] dato)
	{
	String linea="";	
		for(Object nodo:dato)
		{
		linea=linea+String.valueOf(nodo)+",";	
		}
		return 		linea.substring(0, linea.length()-1);
	}
	
	

	
	  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    
	    public static String crearNodo(String[] atributos, Object[] datos) {
	        if (atributos.length != datos.length) {
	            throw new IllegalArgumentException("Los arreglos de atributos y datos deben tener la misma longitud");
	        }
	        
	        JsonObject nodo = new JsonObject();
	        
	        for (int i = 0; i < atributos.length; i++) {
	            if (datos[i] instanceof String) {
	                nodo.addProperty(atributos[i], (String) datos[i]);
	            } else if (datos[i] instanceof Number) {
	                nodo.addProperty(atributos[i], (Number) datos[i]);
	            } else if (datos[i] instanceof Boolean) {
	                nodo.addProperty(atributos[i], (Boolean) datos[i]);
	            } else {
	                nodo.addProperty(atributos[i], String.valueOf(datos[i]));
	            }
	        }
	        
	        return gson.toJson(nodo);
	    }
	    
	    public static String agregarNodoALista(String nombreLista, String nodoJson, String archivoActual) {
	        JsonObject jsonPrincipal;
	        
	        // Manejar archivo vacío
	        if (archivoActual == null || archivoActual.trim().isEmpty() || archivoActual.trim().equals("{}")) {
	            jsonPrincipal = new JsonObject();
	            jsonPrincipal.add(nombreLista, new JsonArray());
	        } else {
	            jsonPrincipal = JsonParser.parseString(archivoActual).getAsJsonObject();
	        }
	        
	        // Verificar si existe la lista, si no crearla
	        JsonArray lista;
	        if (jsonPrincipal.has(nombreLista)) {
	            lista = jsonPrincipal.getAsJsonArray(nombreLista);
	        } else {
	            lista = new JsonArray();
	            jsonPrincipal.add(nombreLista, lista);
	        }
	        
	        // Agregar el nuevo nodo
	        JsonObject nuevoNodo = JsonParser.parseString(nodoJson).getAsJsonObject();
	        lista.add(nuevoNodo);
	        
	        return gson.toJson(jsonPrincipal);
	    }
	    
	    // Método para inicializar un archivo JSON vacío con la estructura correcta
	    public static String inicializarJsonConLista(String nombreLista) {
	        JsonObject jsonPrincipal = new JsonObject();
	        jsonPrincipal.add(nombreLista, new JsonArray());
	        return gson.toJson(jsonPrincipal);
	    }
	
	public static ArrayList<String[]> leerArchivoJSON(String archivo) {
	    ArrayList<String[]> datos = new ArrayList<>();
	    FileReader fileReader = null;
	    JsonReader jsonReader = null;
	    
	    try {
	        fileReader = new FileReader(archivo);
	        jsonReader = new JsonReader(fileReader);
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        
	        JsonElement jsonElement = gson.fromJson(jsonReader, JsonElement.class);
	        
	        if (jsonElement.isJsonObject()) {
	            procesarObjetoJSON(jsonElement.getAsJsonObject(), "", datos);
	        } else if (jsonElement.isJsonArray()) {
	            procesarArrayJSON(jsonElement.getAsJsonArray(), "", datos);
	        } else if (jsonElement.isJsonPrimitive()) {
	            String[] fila = {"valor", jsonElement.getAsString()};
	            datos.add(fila);
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error al leer el archivo JSON: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (jsonReader != null) {
	                jsonReader.close();
	            }
	            if (fileReader != null) {
	                fileReader.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return datos;
	}
	
	
	private static void procesarObjetoJSON(JsonObject jsonObject, String rutaPadre, ArrayList<String[]> datos) {
	    Set<Map.Entry<String, JsonElement>> entradas = jsonObject.entrySet();
	    
	    for (Map.Entry<String, JsonElement> entrada : entradas) {
	        String clave = entrada.getKey();
	        JsonElement valor = entrada.getValue();
	        String rutaActual = rutaPadre.isEmpty() ? clave : rutaPadre + "." + clave;
	        
	        if (valor.isJsonObject()) {
	            procesarObjetoJSON(valor.getAsJsonObject(), rutaActual, datos);
	        } else if (valor.isJsonArray()) {
	            procesarArrayJSON(valor.getAsJsonArray(), rutaActual, datos);
	        } else if (valor.isJsonPrimitive()) {
	            String[] fila = {rutaActual, obtenerValorPrimitivo(valor)};
	            datos.add(fila);
	        } else if (valor.isJsonNull()) {
	            String[] fila = {rutaActual, "null"};
	            datos.add(fila);
	        }
	    }
	}
	
	private static void procesarArrayJSON(JsonArray jsonArray, String rutaPadre, ArrayList<String[]> datos) {
	    for (int i = 0; i < jsonArray.size(); i++) {
	        JsonElement elemento = jsonArray.get(i);
	        String rutaActual = rutaPadre + "[" + i + "]";
	        
	        if (elemento.isJsonObject()) {
	            procesarObjetoJSON(elemento.getAsJsonObject(), rutaActual, datos);
	        } else if (elemento.isJsonArray()) {
	            procesarArrayJSON(elemento.getAsJsonArray(), rutaActual, datos);
	        } else if (elemento.isJsonPrimitive()) {
	            String[] fila = {rutaActual, obtenerValorPrimitivo(elemento)};
	            datos.add(fila);
	        } else if (elemento.isJsonNull()) {
	            String[] fila = {rutaActual, "null"};
	            datos.add(fila);
	        }
	    }
	}

	
	
	
	
	private static String obtenerValorPrimitivo(JsonElement elemento) {
	    JsonPrimitive primitivo = elemento.getAsJsonPrimitive();
	    
	    if (primitivo.isBoolean()) {
	        return String.valueOf(primitivo.getAsBoolean());
	    } else if (primitivo.isNumber()) {
	        return String.valueOf(primitivo.getAsNumber());
	    } else if (primitivo.isString()) {
	        return primitivo.getAsString();
	    }
	    
	    return "desconocido";
	}
	
	
}
