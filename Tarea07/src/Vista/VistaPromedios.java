package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaPromedios extends JFrame {
    
    private JTable tablaPromedios;
    private JScrollPane scrollPane;
    private JButton btnCerrar;
    private JPanel panelBoton;
    
    public VistaPromedios() {
        // Configuración básica
        setTitle("Resultados - Promedios por Grupo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Crear tabla (sin modelo inicialmente)
        tablaPromedios = new JTable();
        tablaPromedios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaPromedios.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaPromedios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        
        // ScrollPane para la tabla
        scrollPane = new JScrollPane(tablaPromedios);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Panel inferior con botón
        panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(220, 20, 60));
        btnCerrar.setForeground(Color.WHITE);
        panelBoton.add(btnCerrar);
        
        // Agregar componentes
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(panelBoton, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    public void setModelo(DefaultTableModel modelo) {
        tablaPromedios.setModel(modelo);
        
        // Ajustar ancho de columnas
        tablaPromedios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] anchos = {150, 200, 80, 100, 80, 80, 80, 100, 100, 100};
        for (int i = 0; i < anchos.length && i < tablaPromedios.getColumnCount(); i++) {
            tablaPromedios.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
    
    public void addCerrarListener(java.awt.event.ActionListener listener) {
        btnCerrar.addActionListener(listener);
    }
    
    public JTable getTablaPromedios() {
        return tablaPromedios;
    }
}