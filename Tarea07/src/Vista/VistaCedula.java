package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaCedula extends JFrame {
    
    private JTable tablaCedula;
    private JScrollPane scrollPane;
    private JButton btnCerrar;
    private JButton btnExportar;
    private JPanel panelBotones;
    
    public VistaCedula() {
        // Configuración básica
        setTitle("Resultados - Cédula 3.3.2");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Crear tabla (sin modelo inicialmente)
        tablaCedula = new JTable();
        tablaCedula.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaCedula.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaCedula.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        
        // ScrollPane para la tabla
        scrollPane = new JScrollPane(tablaCedula);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Panel inferior con botones
        panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnExportar = new JButton("Exportar a CSV");
        btnExportar.setBackground(new Color(60, 179, 113));
        btnExportar.setForeground(Color.WHITE);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(220, 20, 60));
        btnCerrar.setForeground(Color.WHITE);
        
        panelBotones.add(btnExportar);
        panelBotones.add(btnCerrar);
        
        // Agregar componentes
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(panelBotones, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    public void setModelo(DefaultTableModel modelo) {
        tablaCedula.setModel(modelo);
        
        // Ajustar ancho de columnas
        tablaCedula.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] anchos = {150, 200, 100, 100, 150, 150, 250};
        for (int i = 0; i < anchos.length && i < tablaCedula.getColumnCount(); i++) {
            tablaCedula.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
    
    public void addCerrarListener(java.awt.event.ActionListener listener) {
        btnCerrar.addActionListener(listener);
    }
    
    public void addExportarListener(java.awt.event.ActionListener listener) {
        btnExportar.addActionListener(listener);
    }
    
    public JTable getTablaCedula() {
        return tablaCedula;
    }
}