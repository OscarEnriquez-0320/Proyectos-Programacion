package ParteFinal;

import Modelo.Obra;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JDialog para Agregar/Modificar Obras
 */
public class DialogoObra extends JDialog implements ActionListener {
    
    private JTextField Tid, Tnombre, Tdescripcion;
    private JButton Bguardar, Bcancelar;
    
    private boolean resultado = false;
    private Obra obra;
    private boolean esNuevo;
    
    public DialogoObra(JFrame parent, String titulo, Obra obra, boolean esNuevo) {
        super(parent, titulo, true);
        this.obra = obra;
        this.esNuevo = esNuevo;
        
        setLayout(null);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        
        // ID (solo visible en modificación)
        if (!esNuevo) {
            JLabel lblId = new JLabel("ID:");
            lblId.setBounds(30, 30, 50, 25);
            add(lblId);
            
            Tid = new JTextField(obra.getIdObra());
            Tid.setBounds(100, 30, 250, 25);
            Tid.setEditable(false);
            add(Tid);
        }
        
        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, esNuevo ? 30 : 70, 70, 25);
        add(lblNombre);
        
        Tnombre = new JTextField();
        Tnombre.setBounds(100, esNuevo ? 30 : 70, 250, 25);
        if (!esNuevo) {
            Tnombre.setText(obra.getNombre());
        }
        add(Tnombre);
        
        // Descripción
        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setBounds(30, esNuevo ? 70 : 110, 80, 25);
        add(lblDesc);
        
        Tdescripcion = new JTextField();
        Tdescripcion.setBounds(100, esNuevo ? 70 : 110, 250, 25);
        if (!esNuevo) {
            Tdescripcion.setText(obra.getDescripcion());
        }
        add(Tdescripcion);
        
        // Botones
        int yBotones = esNuevo ? 130 : 170;
        Bguardar = new JButton("Guardar");
        Bguardar.setBounds(100, yBotones, 90, 30);
        Bguardar.addActionListener(this);
        add(Bguardar);
        
        Bcancelar = new JButton("Cancelar");
        Bcancelar.setBounds(210, yBotones, 90, 30);
        Bcancelar.addActionListener(this);
        add(Bcancelar);
    }
    
    public boolean mostrar() {
        setVisible(true);
        return resultado;
    }
    
    public Obra getObra() {
        return obra;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bguardar) {
            String nombre = Tnombre.getText().trim();
            String desc = Tdescripcion.getText().trim();
            
            if (nombre.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }
            
            if (esNuevo) {
                // El ID se genera automáticamente en el InternalFrame
                obra = new Obra("", nombre, desc);
            } else {
                obra.setNombre(nombre);
                obra.setDescripcion(desc);
            }
            
            resultado = true;
            dispose();
            
        } else if (e.getSource() == Bcancelar) {
            resultado = false;
            dispose();
        }
    }
}
