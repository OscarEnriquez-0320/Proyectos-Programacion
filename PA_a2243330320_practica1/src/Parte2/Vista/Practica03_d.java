package Parte2.Vista;

import Parte2.Modelo.Categoria;
import Parte2.Modelo.ListaCategorias;
import Parte2.Modelo.ListaInsumos;
import Parte2.Modelo.Insumo;
import Libreria.Archivotxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Practica03_d extends JFrame implements ActionListener {

    private ListaInsumos listaInsumos;
    private ListaCategorias listaCategorias;
    private Archivotxt archivo;

    private JPanel panelFormulario;
    private JTextField Tid;
    private JTextField Tinsumo;
    private JComboBox<Categoria> ComboCategoria;
    private JTextArea areaProductos;
    private JButton Bagregar, Beliminar, Bsalir;

    public Practica03_d() {
        super("Administración de Insumos con Archivo");

        listaCategorias = new ListaCategorias();
        listaInsumos = new ListaInsumos();
        archivo = new Archivotxt("insumos.txt");

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

        JLabel linsumo = new JLabel("Insumo:");
        linsumo.setBounds(20, 60, 100, 25);
        panelFormulario.add(linsumo);

        Tinsumo = new JTextField();
        Tinsumo.setBounds(130, 60, 200, 25);
        Tinsumo.setEditable(false);
        panelFormulario.add(Tinsumo);

        JLabel lcategoria = new JLabel("Categoría:");
        lcategoria.setBounds(20, 100, 100, 25);
        panelFormulario.add(lcategoria);

        ComboCategoria = new JComboBox<>();
        ComboCategoria.setBounds(130, 100, 200, 25);
        ComboCategoria.setEnabled(true); // habilitado
        listaCategorias.agregarCategoriasAsComboBox(ComboCategoria);
        panelFormulario.add(ComboCategoria);

        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(20, 150, 100, 30);
        Bagregar.addActionListener(this);
        panelFormulario.add(Bagregar);

        Beliminar = new JButton("Eliminar");
        Beliminar.setBounds(130, 150, 100, 30);
        Beliminar.addActionListener(this);
        panelFormulario.add(Beliminar);

        Bsalir = new JButton("Salir");
        Bsalir.setBounds(240, 150, 100, 30);
        Bsalir.addActionListener(this);
        panelFormulario.add(Bsalir);

        areaProductos = new JTextArea();
        areaProductos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaProductos);
        scrollPane.setBounds(20, 200, 440, 140);
        panelFormulario.add(scrollPane);

        // Cargar insumos desde archivo al iniciar
        ArrayList<String> datos = archivo.cargar();
        cargarInsumo(convertirDatos(datos));
        actualizarArea();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            String id = String.valueOf(listaInsumos.size() + 1);
            String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre del insumo:");
            Categoria categoria = (Categoria) ComboCategoria.getSelectedItem();
            if (nombre != null && !nombre.trim().isEmpty() && categoria != null) {
                Insumo insumo = new Insumo(id, nombre.trim(), categoria.getIdcategoria());
                listaInsumos.agregarInsumo(insumo);
                actualizarArea();
            }
        } else if (e.getSource() == Beliminar) {
            String id = JOptionPane.showInputDialog(this, "Ingrese ID del insumo a eliminar:");
            if (id != null && !id.trim().isEmpty()) {
                if (!listaInsumos.eliminarInsumoPorId(id)) {
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
        areaProductos.setText(listaInsumos.toString());
        archivo.guardar(listaInsumos.toString()); // guardar automáticamente
    }

    private ArrayList<String[]> convertirDatos(ArrayList<String> datos) {
        ArrayList<String[]> lista = new ArrayList<>();
        for (String linea : datos) {
            String[] partes = linea.split(",");
            if (partes.length == 3) {
                lista.add(partes);
            }
        }
        return lista;
    }

    private void cargarInsumo(ArrayList<String[]> insumosString) {
        for (String[] insumoString : insumosString) {
            String id = insumoString[0];
            String nombre = insumoString[1];
            String idCategoria = insumoString[2];
            Insumo nodo = new Insumo(id, nombre, idCategoria);
            listaInsumos.agregarInsumo(nodo);
        }
    }

    public static void main(String[] args) {
        new Practica03_d();
    }
}