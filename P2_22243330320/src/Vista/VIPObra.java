package Vista;

import javax.swing.*;
import java.awt.*;

public class VIPObra extends JInternalFrame {
    private JTextField txtId, txtNombre, txtFechaInicio, txtFechaFin;
    private JList listObras;
    private JScrollPane scrollPane;
    private JButton btnAgregar, btnEliminar, btnCerrar;  // ← AGREGADO btnCerrar
    
    public VIPObra() {
        setTitle("Gestión de Obras");
        setBounds(80, 80, 550, 450);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JLabel lblId = new JLabel("ID Obra:");
        lblId.setBounds(30, 30, 80, 25);
        contentPane.add(lblId);
        
        txtId = new JTextField();
        txtId.setBounds(120, 30, 150, 25);
        contentPane.add(txtId);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 70, 80, 25);
        contentPane.add(lblNombre);
        
        txtNombre = new JTextField();
        txtNombre.setBounds(120, 70, 150, 25);
        contentPane.add(txtNombre);
        
        JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
        lblFechaInicio.setBounds(30, 110, 80, 25);
        contentPane.add(lblFechaInicio);
        
        txtFechaInicio = new JTextField();
        txtFechaInicio.setBounds(120, 110, 150, 25);
        contentPane.add(txtFechaInicio);
        
        JLabel lblFechaFin = new JLabel("Fecha Fin:");
        lblFechaFin.setBounds(30, 150, 80, 25);
        contentPane.add(lblFechaFin);
        
        txtFechaFin = new JTextField();
        txtFechaFin.setBounds(120, 150, 150, 25);
        contentPane.add(txtFechaFin);
        
        JLabel lblLista = new JLabel("Lista de Obras:");
        lblLista.setBounds(30, 200, 150, 25);
        contentPane.add(lblLista);
        
        listObras = new JList();
        listObras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollPane = new JScrollPane(listObras);
        scrollPane.setBounds(30, 230, 480, 120);
        contentPane.add(scrollPane);
        
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(80, 370, 100, 30);
        contentPane.add(btnAgregar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(200, 370, 100, 30);
        contentPane.add(btnEliminar);
        
        btnCerrar = new JButton("Cerrar");              // ← AGREGADO
        btnCerrar.setBounds(320, 370, 100, 30);         // ← AGREGADO
        contentPane.add(btnCerrar);                     // ← AGREGADO
    }
    
    // Getters
    public String getTxtId() { return txtId.getText(); }
    public String getTxtNombre() { return txtNombre.getText(); }
    public String getTxtFechaInicio() { return txtFechaInicio.getText(); }
    public String getTxtFechaFin() { return txtFechaFin.getText(); }
    public JList getListObras() { return listObras; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnCerrar() { return btnCerrar; } // ← AGREGADO
    
    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtFechaInicio.setText("");
        txtFechaFin.setText("");
        txtId.requestFocus();
    }
    
    public void setModeloLista(DefaultListModel modelo) {
        listObras.setModel(modelo);
    }
    
    public int getIndiceSeleccionado() {
        return listObras.getSelectedIndex();
    }
}