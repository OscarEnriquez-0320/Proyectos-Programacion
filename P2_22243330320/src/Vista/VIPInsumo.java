package Vista;

import javax.swing.*;
import java.awt.*;

public class VIPInsumo extends JInternalFrame {
    private JTextField txtId, txtInsumo;
    private JComboBox cmbCategoria;
    private JList listInsumos;
    private JScrollPane scrollPane;
    private JButton btnAgregar, btnEliminar, btnCerrar;  // ← AGREGADO btnCerrar
    
    public VIPInsumo() {
        setTitle("Gestión de Insumos");
        setBounds(50, 50, 550, 450);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(30, 30, 80, 25);
        contentPane.add(lblId);
        
        txtId = new JTextField();
        txtId.setBounds(120, 30, 150, 25);
        contentPane.add(txtId);
        
        JLabel lblInsumo = new JLabel("Insumo:");
        lblInsumo.setBounds(30, 70, 80, 25);
        contentPane.add(lblInsumo);
        
        txtInsumo = new JTextField();
        txtInsumo.setBounds(120, 70, 150, 25);
        contentPane.add(txtInsumo);
        
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(30, 110, 80, 25);
        contentPane.add(lblCategoria);
        
        cmbCategoria = new JComboBox();
        cmbCategoria.setBounds(120, 110, 150, 25);
        contentPane.add(cmbCategoria);
        
        JLabel lblLista = new JLabel("Lista de Insumos:");
        lblLista.setBounds(30, 160, 150, 25);
        contentPane.add(lblLista);
        
        listInsumos = new JList();
        listInsumos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollPane = new JScrollPane(listInsumos);
        scrollPane.setBounds(30, 190, 480, 150);
        contentPane.add(scrollPane);
        
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(80, 360, 100, 30);
        contentPane.add(btnAgregar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(200, 360, 100, 30);
        contentPane.add(btnEliminar);
        
        btnCerrar = new JButton("Cerrar");              // ← AGREGADO
        btnCerrar.setBounds(320, 360, 100, 30);         // ← AGREGADO
        contentPane.add(btnCerrar);                     // ← AGREGADO
    }
    
    // Getters
    public String getTxtId() {
        return txtId.getText();
    }
    
    public String getTxtInsumo() {
        return txtInsumo.getText();
    }
    
    public JComboBox getCmbCategoria() {
        return cmbCategoria;
    }
    
    public JList getListInsumos() {
        return listInsumos;
    }
    
    public JButton getBtnAgregar() {
        return btnAgregar;
    }
    
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
    
    public JButton getBtnCerrar() {                     // ← AGREGADO
        return btnCerrar;
    }
    
    public void limpiarCampos() {
        txtId.setText("");
        txtInsumo.setText("");
        cmbCategoria.setSelectedIndex(0);
        txtId.requestFocus();
    }
    
    public void setModeloCombo(DefaultComboBoxModel modelo) {
        cmbCategoria.setModel(modelo);
    }
    
    public void setModeloLista(DefaultListModel modelo) {
        listInsumos.setModel(modelo);
    }
    
    public int getIndiceSeleccionado() {
        return listInsumos.getSelectedIndex();
    }
}