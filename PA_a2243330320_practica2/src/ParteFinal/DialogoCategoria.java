package ParteFinal;

import Modelo.Categoria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JDialog para Agregar/Modificar Categorías
 */
public class DialogoCategoria extends JDialog implements ActionListener {
    
    private JTextField Tid, Tnombre;
    private JButton Bguardar, Bcancelar;
    
    private boolean resultado = false;
    private Categoria categoria;
    private boolean esNuevo;
    
    public DialogoCategoria(JFrame parent, String titulo, Categoria categoria, boolean esNuevo) {
        super(parent, titulo, true);
        this.categoria = categoria;
        this.esNuevo = esNuevo;
        
        setLayout(null);
        setSize(350, 200);
        setLocationRelativeTo(parent);
        
        // Componentes
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(30, 30, 50, 25);
        add(lblId);
        
        Tid = new JTextField();
        Tid.setBounds(90, 30, 200, 25);
        if (!esNuevo) {
            Tid.setEditable(false); // No se puede modificar ID existente
            Tid.setText(categoria.getIdCategoria());
        }
        add(Tid);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 70, 50, 25);
        add(lblNombre);
        
        Tnombre = new JTextField();
        Tnombre.setBounds(90, 70, 200, 25);
        if (!esNuevo) {
            Tnombre.setText(categoria.getNombreCategoria());
        }
        add(Tnombre);
        
        // Botones
        Bguardar = new JButton("Guardar");
        Bguardar.setBounds(80, 120, 90, 30);
        Bguardar.addActionListener(this);
        add(Bguardar);
        
        Bcancelar = new JButton("Cancelar");
        Bcancelar.setBounds(180, 120, 90, 30);
        Bcancelar.addActionListener(this);
        add(Bcancelar);
    }
    
    public boolean mostrar() {
        setVisible(true);
        return resultado;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bguardar) {
            String id = Tid.getText().trim();
            String nombre = Tnombre.getText().trim();
            
            if (id.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }
            
            if (esNuevo) {
                categoria = new Categoria(id, nombre);
            } else {
                categoria.setNombreCategoria(nombre);
            }
            
            resultado = true;
            dispose();
            
        } else if (e.getSource() == Bcancelar) {
            resultado = false;
            dispose();
        }
    }
}