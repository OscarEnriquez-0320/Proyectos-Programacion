package Parte2.Vista;

import Parte2.Controlador.Controlador;
import Parte2.Modelo.Categoria;
import Parte2.Modelo.ListaCategorias;
import Parte2.Modelo.ListaInsumos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Practica03_a extends JFrame implements ActionListener {

    private ListaInsumos listaInsumos;
    private ListaCategorias listaCategorias;
    private Controlador controlador;

    private JPanel panelFormulario;
    private JTextField Tid;
    private JTextField Tinsumo;
    private JComboBox<Categoria> ComboCategoria;
    private JTextArea areaProductos;
    private JButton Bagregar, Beliminar, Bsalir;

    public Practica03_a() {
        super("Administración de Productos");

        // Inicializamos listas
        inicializarCategorias();
        listaInsumos = new ListaInsumos();

        // Creamos controlador
        controlador = new Controlador(this, listaInsumos);

        setBounds(0, 0, 500, 400);

        // Panel principal
        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        getContentPane().add(panelFormulario, BorderLayout.CENTER);

        JLabel lid = new JLabel("ID:");
        lid.setBounds(20, 20, 100, 25);
        panelFormulario.add(lid);

        Tid = new JTextField();
        Tid.setBounds(130, 20, 200, 25);
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
        ComboCategoria.setEditable(false);
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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // --- Métodos auxiliares para el controlador ---
    public JButton getBagregar() { return Bagregar; }
    public JButton getBeliminar() { return Beliminar; }
    public JButton getBsalir() { return Bsalir; }
    public JTextField getTid() { return Tid; }
    public JTextField getTinsumo() { return Tinsumo; }
    public JComboBox<Categoria> getComboCategoria() { return ComboCategoria; }
    public JTextArea getAreaProductos() { return areaProductos; }

    public void actualizarArea(String texto) {
        areaProductos.setText(texto);
    }

    public void prepararAlta() {
        Bagregar.setText("Salvar");
        Bsalir.setText("Cancelar");
        Beliminar.setEnabled(false);
        Tid.setEditable(false);
        Tinsumo.setEditable(true);
        ComboCategoria.setEditable(true);
        ComboCategoria.setFocusable(true);
    }

    public void volverAlInicio() {
        Bagregar.setText("Agregar");
        Bsalir.setText("Salir");
        Beliminar.setEnabled(true);
        Tid.setEditable(false);
        Tinsumo.setEditable(false);
        ComboCategoria.setEditable(false);
        Tid.setText("");
        Tinsumo.setText("");
        ComboCategoria.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Bagregar) {
            controlador.altas();
        } else if (e.getSource() == Beliminar) {
            controlador.eliminar();
        } else if (e.getSource() == Bsalir) {
            if (Bsalir.getText().equals("Cancelar"))
                volverAlInicio();
            else
                dispose();
        }
    }

    public void inicializarCategorias() {
        listaCategorias = new ListaCategorias();
        Categoria nodo1 = new Categoria("01", "Materiales");
        Categoria nodo2 = new Categoria("02", "Mano de Obra");
        Categoria nodo3 = new Categoria("03", "Maquinaria y Equipo");
        Categoria nodo4 = new Categoria("04", "Servicios");

        listaCategorias.agregarCategoria(nodo1);
        listaCategorias.agregarCategoria(nodo2);
        listaCategorias.agregarCategoria(nodo3);
        listaCategorias.agregarCategoria(nodo4);
    }

    
}