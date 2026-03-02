package ParteFinal;

import Modelo.Categoria;
import Modelo.Insumo;
import Modelo.ListaCategorias;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JDialog para Agregar/Modificar Insumos
 */
public class DialogoInsumo extends JDialog implements ActionListener {
    
    private JTextField Tid, Tnombre;
    private JComboBox<Categoria> CbCategorias;
    private JButton Bguardar, Bcancelar;
    
    private boolean resultado = false;
    private Insumo insumo;
    private boolean esNuevo;
    private ListaCategorias listaCategorias;
    
    public DialogoInsumo(JFrame parent, String titulo, Insumo insumo, boolean esNuevo, ListaCategorias listaCategorias) {
        super(parent, titulo, true);
        this.insumo = insumo;
        this.esNuevo = esNuevo;
        this.listaCategorias = listaCategorias;
        
        setLayout(null);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        
        // Componentes
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(30, 30, 50, 25);
        add(lblId);
        
        Tid = new JTextField();
        Tid.setBounds(100, 30, 250, 25);
        if (!esNuevo) {
            Tid.setEditable(false);
            Tid.setText(insumo.getIdProducto());
        }
        add(Tid);
        
        JLabel lblNombre = new JLabel("Producto:");
        lblNombre.setBounds(30, 70, 70, 25);
        add(lblNombre);
        
        Tnombre = new JTextField();
        Tnombre.setBounds(100, 70, 250, 25);
        if (!esNuevo) {
            Tnombre.setText(insumo.getProducto());
        }
        add(Tnombre);
        
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(30, 110, 70, 25);
        add(lblCategoria);
        
        CbCategorias = new JComboBox<>();
        for (Categoria cat : listaCategorias.getCategorias()) {
            CbCategorias.addItem(cat);
        }
        CbCategorias.setBounds(100, 110, 250, 25);
        
        if (!esNuevo) {
            // Seleccionar la categoría correspondiente
            for (int i = 0; i < CbCategorias.getItemCount(); i++) {
                Categoria cat = CbCategorias.getItemAt(i);
                if (cat.getIdCategoria().equals(insumo.getIdCategoria())) {
                    CbCategorias.setSelectedIndex(i);
                    break;
                }
            }
        }
        add(CbCategorias);
        
        // Botones
        Bguardar = new JButton("Guardar");
        Bguardar.setBounds(100, 160, 90, 30);
        Bguardar.addActionListener(this);
        add(Bguardar);
        
        Bcancelar = new JButton("Cancelar");
        Bcancelar.setBounds(210, 160, 90, 30);
        Bcancelar.addActionListener(this);
        add(Bcancelar);
    }
    
    public boolean mostrar() {
        setVisible(true);
        return resultado;
    }
    
    public Insumo getInsumo() {
        return insumo;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bguardar) {
            String id = Tid.getText().trim();
            String nombre = Tnombre.getText().trim();
            Categoria catSeleccionada = (Categoria) CbCategorias.getSelectedItem();
            
            if (id.isEmpty() || nombre.isEmpty() || catSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }
            
            if (esNuevo) {
                insumo = new Insumo(id, nombre, catSeleccionada.getIdCategoria());
            } else {
                insumo.setProducto(nombre);
                insumo.setIdCategoria(catSeleccionada.getIdCategoria());
            }
            
            resultado = true;
            dispose();
            
        } else if (e.getSource() == Bcancelar) {
            resultado = false;
            dispose();
        }
    }
}