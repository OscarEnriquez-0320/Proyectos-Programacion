package Controlador;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import Modelo.DetalleVenta;
import Modelo.Venta;

public class ControladorTicket {
    
    private String nombreEmpresa;
    private String telefonoEmpresa;
    private String direccionEmpresa;
    private String mensajeTicket;
    
    public ControladorTicket() {
        ControladorConfiguracion config = new ControladorConfiguracion();
        nombreEmpresa = config.getConfiguracion("empresa_nombre");
        if (nombreEmpresa == null || nombreEmpresa.isEmpty()) nombreEmpresa = "Abarrotes Felipe";
        
        telefonoEmpresa = config.getConfiguracion("empresa_telefono");
        direccionEmpresa = config.getConfiguracion("empresa_direccion");
        mensajeTicket = config.getConfiguracion("ticket_mensaje");
        if (mensajeTicket == null || mensajeTicket.isEmpty()) mensajeTicket = "Gracias por su compra";
    }
    
    public String getNombreEmpresa() { return nombreEmpresa; }
    public String getTelefonoEmpresa() { return telefonoEmpresa; }
    public String getDireccionEmpresa() { return direccionEmpresa; }
    public String getMensajeTicket() { return mensajeTicket; }
    
    public boolean generarTicketPDF(Venta venta, List<DetalleVenta> detalles, String rutaArchivo) {
        try {
            Document document = new Document(PageSize.A5);
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();
            
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            
            Paragraph titulo = new Paragraph(nombreEmpresa, titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            
            if (direccionEmpresa != null && !direccionEmpresa.isEmpty()) {
                Paragraph direccion = new Paragraph(direccionEmpresa, normalFont);
                direccion.setAlignment(Element.ALIGN_CENTER);
                document.add(direccion);
            }
            
            if (telefonoEmpresa != null && !telefonoEmpresa.isEmpty()) {
                Paragraph telefono = new Paragraph("Tel: " + telefonoEmpresa, normalFont);
                telefono.setAlignment(Element.ALIGN_CENTER);
                document.add(telefono);
            }
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph("----------------------------------------", normalFont));
            document.add(new Paragraph("Vendedor: " + (venta.getNombreUsuario() != null ? venta.getNombreUsuario() : "N/A"), normalFont));
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            document.add(new Paragraph("Fecha: " + sdf.format(venta.getFecha()), normalFont));
            document.add(new Paragraph("Folio: " + venta.getFolio(), boldFont));
            document.add(new Paragraph("Vendedor: " + venta.getNombreUsuario(), normalFont));
            document.add(new Paragraph("Metodo Pago: " + venta.getMetodoPago(), normalFont));
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph("----------------------------------------", normalFont));
            
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{40, 15, 25});
            
            PdfPCell cell1 = new PdfPCell(new Phrase("Producto", headerFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell1);
            
            PdfPCell cell2 = new PdfPCell(new Phrase("Cant", headerFont));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell2);
            
            PdfPCell cell3 = new PdfPCell(new Phrase("Subtotal", headerFont));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell3);
            
            for (DetalleVenta detalle : detalles) {
                table.addCell(new Phrase(detalle.getNombreProducto(), normalFont));
                table.addCell(new Phrase(String.valueOf(detalle.getCantidad()), normalFont));
                table.addCell(new Phrase("$" + detalle.getSubtotal().toString(), normalFont));
            }
            
            document.add(table);
            document.add(new Paragraph("----------------------------------------", normalFont));
            document.add(new Paragraph("Total: $" + venta.getTotal().toString(), boldFont));
            
            if (venta.getMetodoPago().equals("Efectivo")) {
                BigDecimal efectivo = venta.getEfectivoRecibido() != null ? venta.getEfectivoRecibido() : BigDecimal.ZERO;
                BigDecimal cambioVenta = venta.getCambio() != null ? venta.getCambio() : BigDecimal.ZERO;
                document.add(new Paragraph("Efectivo Recibido: $" + efectivo.toString(), normalFont));
                document.add(new Paragraph("Cambio: $" + cambioVenta.toString(), normalFont));
            }
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph("----------------------------------------", normalFont));
            
            Paragraph mensaje = new Paragraph(mensajeTicket, normalFont);
            mensaje.setAlignment(Element.ALIGN_CENTER);
            document.add(mensaje);
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph("¡Vuelva pronto!", boldFont));
            
            document.close();
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public String generarTextoTicket(Venta venta, List<DetalleVenta> detalles) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=".repeat(40)).append("\n");
        sb.append(String.format("%40s\n", "TICKET DE VENTA"));
        sb.append("=".repeat(40)).append("\n\n");
        
        sb.append(nombreEmpresa).append("\n");
        if (direccionEmpresa != null && !direccionEmpresa.isEmpty()) sb.append(direccionEmpresa).append("\n");
        if (telefonoEmpresa != null && !telefonoEmpresa.isEmpty()) sb.append("Tel: ").append(telefonoEmpresa).append("\n");
        
        sb.append("\n");
        sb.append("-".repeat(40)).append("\n");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sb.append("Folio: ").append(venta.getFolio()).append("\n");
        sb.append("Fecha: ").append(sdf.format(venta.getFecha())).append("\n");
        sb.append("Vendedor: ").append(venta.getNombreUsuario()).append("\n");
        sb.append("Metodo: ").append(venta.getMetodoPago()).append("\n");
        
        sb.append("\n");
        sb.append("-".repeat(40)).append("\n");
        sb.append(String.format("%-25s %8s %10s\n", "Producto", "Cant", "Subtotal"));
        sb.append("-".repeat(40)).append("\n");
        
        for (DetalleVenta d : detalles) {
            String nombre = d.getNombreProducto();
            if (nombre.length() > 25) nombre = nombre.substring(0, 22) + "...";
            sb.append(String.format("%-25s %8s %10s\n", 
                nombre, 
                d.getCantidad(), 
                "$" + d.getSubtotal().toString()));
        }
        
        sb.append("-".repeat(40)).append("\n");
        sb.append(String.format("%-35s %10s\n", "TOTAL:", "$" + venta.getTotal().toString()));
        

        if (venta.getMetodoPago().equals("Efectivo")) {
            if (venta.getEfectivoRecibido() != null) {
                sb.append(String.format("%-35s %10s\n", "Efectivo Recibido:", "$" + venta.getEfectivoRecibido().toString()));
            } else {
                sb.append(String.format("%-35s %10s\n", "Efectivo Recibido:", "$0.00"));
            }
            if (venta.getCambio() != null) {
                sb.append(String.format("%-35s %10s\n", "Cambio:", "$" + venta.getCambio().toString()));
            } else {
                sb.append(String.format("%-35s %10s\n", "Cambio:", "$0.00"));
            }
        }
        
        sb.append("\n");
        sb.append("=".repeat(40)).append("\n");
        sb.append(mensajeTicket).append("\n");
        sb.append("=".repeat(40)).append("\n");
        sb.append("¡Vuelva pronto!\n");
        
        return sb.toString();
    }
}