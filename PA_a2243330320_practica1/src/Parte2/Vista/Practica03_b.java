package Parte2.Vista;

import Parte2.Modelo.Categoria;
import Parte2.Modelo.ListaCategorias;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Practica03_b extends JFrame implements ActionListener {

    private ListaCategorias listaCategorias;

    private JPanel panelFormulario;
    private JTextField Tid;
    private JTextField Tcategoria;
    private JTextArea Tareacategoria;
    private JButton Bagregar, Beliminar, Bsalir;

    public Practica03_b() {
        super("Administración de Categorías");

        listaCategorias = new ListaCategorias(); // ✅ inicialización

        setBounds(0, 0, 500, 400);

        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        getContentPane().add(panelFormulario, BorderLayout.CENTER);

        JLabel lid = new JLabel("ID:");
        lid.setBounds(20, 20, 100, 25);
        panelFormulario.add(lid);

        Tid = new JTextField();
        Tid.setBounds(130, 20, 200, 25);
        Tid.setEditable(false);
        panelFormulario.add(Tid);

        JLabel lcat = new JLabel("Categoría:");
        lcat.setBounds(20, 60, 100, 25);
        panelFormulario.add(lcat);

        Tcategoria = new JTextField();
        Tcategoria.setBounds(130, 60, 200, 25);
        panelFormulario.setLayout(null);
        panelFormulario.add(Tcategoria);

        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(20, 100, 100, 30);
        Bagregar.addActionListener(this);
        panelFormulario.add(Bagregar);

        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(130, 100, 100, 30);
        Beliminar.addActionListener(this);
        panelFormulario.add(Beliminar);

        Bsalir = new JButton("Salir");
        Bsalir.setBounds(240, 100, 100, 30);
        Bsalir.addActionListener(this);
        panelFormulario.add(Bsalir);

        Tareacategoria = new JTextArea();
        Tareacategoria.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(Tareacategoria);
        scrollPane.setBounds(20, 150, 440, 180);
        panelFormulario.add(scrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            String id = String.valueOf(listaCategorias.size() + 1);
            String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre de la categoría:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                Categoria cat = new Categoria(id, nombre.trim());
                listaCategorias.agregarCategoria(cat);
                actualizarArea();
            }
        } else if (e.getSource() == Beliminar) {
            String id = JOptionPane.showInputDialog(this, "Ingrese ID de la categoría a eliminar:");
            if (id != null && !id.trim().isEmpty()) {
                if (!listaCategorias.eliminarCategoriaPorId(id)) {
                    JOptionPane.showMessageDialog(this, "No existe este ID");
                } else {
                    actualizarArea();
                }
            }
        } else if (e.getSource() == Bsalir) {
            dispose();
        }
    }

    private void actualizarArea() {
        Tareacategoria.setText(listaCategorias.toString());
    }

    public static void main(String[] args) {
        new Practica03_b();
    }
}