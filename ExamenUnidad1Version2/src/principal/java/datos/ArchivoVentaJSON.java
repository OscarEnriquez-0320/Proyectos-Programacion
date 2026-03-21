package principal.java.datos;

import principal.java.Modelo.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;

public class ArchivoVentaJSON {
    private static final String CARPETA_TICKETS = "tickets/";
    private static final String CONTROL_TICKETS = "control_tickets.json";

    public static void guardarTicket(ArrayList<Venta> ventas, String folio) {
        JSONArray jsonArray = new JSONArray();
        
        for (Venta v : ventas) {
            JSONObject obj = new JSONObject();
            obj.put("idTicket", v.getIdTicket());
            obj.put("idProducto", v.getIdProducto());
            obj.put("nombreProducto", v.getNombreProducto());
            obj.put("precioUnitario", v.getPrecioUnitario());
            obj.put("cantidad", v.getCantidad());
            obj.put("subtotal", v.getSubtotal());
            obj.put("fecha", v.getFecha());
            obj.put("cajero", v.getCajero());
            jsonArray.add(obj);
        }

        File carpeta = new File(CARPETA_TICKETS);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        try (FileWriter file = new FileWriter(CARPETA_TICKETS + folio + ".json")) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        registrarTicketEnControl(folio);
    }

    private static void registrarTicketEnControl(String folio) {
        JSONArray controlArray = new JSONArray();
        File controlFile = new File(CONTROL_TICKETS);

        if (controlFile.exists()) {
            try (FileReader reader = new FileReader(controlFile)) {
                controlArray = (JSONArray) new JSONParser().parse(reader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONObject ticketInfo = new JSONObject();
        ticketInfo.put("folio", folio);
        ticketInfo.put("fecha", new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date()));
        controlArray.add(ticketInfo);

        try (FileWriter file = new FileWriter(CONTROL_TICKETS)) {
            file.write(controlArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> obtenerListaTickets() {
        ArrayList<String> tickets = new ArrayList<>();
        File controlFile = new File(CONTROL_TICKETS);

        if (!controlFile.exists()) {
            return tickets;
        }

        try (FileReader reader = new FileReader(controlFile)) {
            JSONArray controlArray = (JSONArray) new JSONParser().parse(reader);
            for (Object obj : controlArray) {
                JSONObject jsonObj = (JSONObject) obj;
                tickets.add((String) jsonObj.get("folio"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }
}