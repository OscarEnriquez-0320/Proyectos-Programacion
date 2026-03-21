package principal.java.Reportes;

import principal.java.Modelo.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ReporteExcel {

    public static void generarReporteGeneral(GestionProducto modelo, String rutaArchivo) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Productos");

        Row header = sheet.createRow(0);
        String[] columnas = {"ID", "Nombre", "Descripción", "Categoría", "Precio Compra", "Precio Venta", "Stock", "Stock Mínimo", "Estado"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(estiloHeader(workbook));
        }

        int rowNum = 1;
        for (ProductoAbstract p : modelo.getLista()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getNombre());
            row.createCell(2).setCellValue(p.getDescripcion());
            row.createCell(3).setCellValue(p.getCategoria());
            row.createCell(4).setCellValue(p.getPrecioCompra());
            row.createCell(5).setCellValue(p.getPrecioVenta());
            row.createCell(6).setCellValue(p.getStock());
            row.createCell(7).setCellValue(p.getStockMinimo());
            row.createCell(8).setCellValue(p.isActivo() ? "Activo" : "Inactivo");
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream(rutaArchivo)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }

    public static void generarReportePorCategoria(GestionProducto modelo, String categoria, String rutaArchivo) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(categoria);

        Row header = sheet.createRow(0);
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio Venta", "Stock", "Estado"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(estiloHeader(workbook));
        }

        int rowNum = 1;
        for (ProductoAbstract p : modelo.getLista()) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getNombre());
                row.createCell(2).setCellValue(p.getDescripcion());
                row.createCell(3).setCellValue(p.getPrecioVenta());
                row.createCell(4).setCellValue(p.getStock());
                row.createCell(5).setCellValue(p.isActivo() ? "Activo" : "Inactivo");
            }
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream(rutaArchivo)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }

    private static CellStyle estiloHeader(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}