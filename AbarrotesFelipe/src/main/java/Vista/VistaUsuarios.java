package Vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import Controlador.ControladorPrincipal;
import Modelo.Usuario;

public class VistaUsuarios extends JPanel {
    private VistaPrincipal vistaPrincipal;
    private ControladorPrincipal controladorPrincipal;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnRefrescar;
    
    private ArrayList<Usuario> listaUsuarios;
    
    public VistaUsuarios(VistaPrincipal vista) {
        this.vistaPrincipal = vista;
        this.controladorPrincipal = new ControladorPrincipal();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        listaUsuarios = new ArrayList<Usuario>();
        
        iniciarComponentes();
        cargarUsuarios();
    }
    
    private void iniciarComponentes() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(240, 248, 255));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(79, 70, 229));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        
        panelSuperior.add(panelBusqueda, BorderLayout.WEST);
        add(panelSuperior, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Imagen", "Usuario", "Nombre Completo", "Rol", "Permisos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Class<?> getColumnClass(int column) {
                if (column == 1) return ImageIcon.class;
                return String.class;
            }
        };
        
        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaUsuarios.setRowHeight(60);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(240, 248, 255));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnAgregar = new JButton("Agregar Usuario");
        btnAgregar.setBackground(new Color(34, 197, 94));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEditar = new JButton("Editar Usuario");
        btnEditar.setBackground(new Color(59, 130, 246));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEliminar = new JButton("Eliminar Usuario");
        btnEliminar.setBackground(new Color(220, 38, 38));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(new Color(107, 114, 128));
        btnRefrescar.setForeground(Color.BLACK);
        btnRefrescar.setFont(new Font("Arial", Font.BOLD, 12));
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelInferior.add(btnAgregar);
        panelInferior.add(btnEditar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnRefrescar);
        
        add(panelInferior, BorderLayout.SOUTH);
        
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarUsuarios();
            }
        });
        
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                buscarUsuarios();
            }
        });
        
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioUsuario(null);
            }
        });
        
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarUsuario();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });
        
        btnRefrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarUsuarios();
            }
        });
    }
    
    private void cargarUsuarios() {
        controladorPrincipal.cargarUsuarios(this);
    }
    
    private void buscarUsuarios() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            cargarUsuarios();
        } else {
            controladorPrincipal.buscarUsuarios(this, texto);
        }
    }
    
    private void mostrarFormularioUsuario(Usuario usuario) {
        VistaFormularioUsuario formulario = new VistaFormularioUsuario(vistaPrincipal, this, usuario);
        formulario.setVisible(true);
    }
    
    private void editarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para editar");
            return;
        }
        
        int idUsuario = (int) modeloTabla.getValueAt(fila, 0);
        Usuario usuarioEditar = null;
        
        for (int i = 0; i < listaUsuarios.size(); i++) {
            Usuario u = listaUsuarios.get(i);
            if (u.getId() == idUsuario) {
                usuarioEditar = u;
                break;
            }
        }
        
        if (usuarioEditar != null) {
            mostrarFormularioUsuario(usuarioEditar);
        }
    }
    
    private void eliminarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar");
            return;
        }
        
        int idUsuario = (int) modeloTabla.getValueAt(fila, 0);
        String nombreUsuario = (String) modeloTabla.getValueAt(fila, 2);
        
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar al usuario: " + nombreUsuario + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            controladorPrincipal.eliminarUsuario(idUsuario, this);
        }
    }
    
    public void actualizarTabla(ArrayList<Usuario> usuarios) {
        this.listaUsuarios = usuarios;
        modeloTabla.setRowCount(0);
        
        if (usuarios == null || usuarios.size() == 0) {
            modeloTabla.addRow(new Object[]{"-", "", "No hay datos", "", "", ""});
            return;
        }
        
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            
            ImageIcon imagen = null;
            if (u.getRutaImagen() != null && !u.getRutaImagen().isEmpty()) {
                try {
                    File archivo = new File(u.getRutaImagen());
                    if (archivo.exists()) {
                        ImageIcon original = new ImageIcon(u.getRutaImagen());
                        Image img = original.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        imagen = new ImageIcon(img);
                    }
                } catch (Exception e) {
                    imagen = null;
                }
            }
            
            String permisos = u.getPermisos() != null ? u.getPermisos() : "";
            if (permisos.equals("all")) {
                permisos = "Todos";
            }
            
            modeloTabla.addRow(new Object[]{
                u.getId(),
                imagen != null ? imagen : "",
                u.getNombreUsuario(),
                u.getNombreCompleto(),
                u.getRol(),
                permisos
            });
        }
    }
    
    public void solicitarGuardarUsuario(Usuario usuario, String password, int idExistente) {
        if (idExistente > 0) {
            usuario.setId(idExistente);
        }
        controladorPrincipal.guardarUsuario(usuario, password, this);
    }
    
    public void refrescar() {
        cargarUsuarios();
    }
}