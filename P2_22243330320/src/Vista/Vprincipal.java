package Vista;

import javax.swing.*;
import java.awt.*;

public class Vprincipal extends JFrame {
    private JDesktopPane escritorio;
    private JMenuBar menuBar;
    private JMenu mGestion, mSalida;
    private JMenuItem miInsumos, miObras, miSalida;
    
    public Vprincipal(String titulo) {
        setTitle(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        
        // Escritorio para ventanas internas
        escritorio = new JDesktopPane();
        setContentPane(escritorio);
        
        // Barra de menús
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        mGestion = new JMenu("Gestión");
        menuBar.add(mGestion);
        
        miInsumos = new JMenuItem("Gestión de Insumos");
        mGestion.add(miInsumos);
        
        miObras = new JMenuItem("Gestión de Obras");
        mGestion.add(miObras);
        
        mSalida = new JMenu("Salida");
        menuBar.add(mSalida);
        
        miSalida = new JMenuItem("Salir");
        mSalida.add(miSalida);
    }
    
    // ========== GETTERS ==========
    
    public JDesktopPane getEscritorio() {
        return escritorio;
    }
    
    public JMenuItem getMiInsumos() {
        return miInsumos;
    }
    
    public JMenuItem getMiObras() {  // ← ESTE FALTABA
        return miObras;
    }
    
    public JMenuItem getMiSalida() {
        return miSalida;
    }
    
    // ========== MÉTODO PARA HABILITAR/DESHABILITAR MENÚS ==========
    
    public void setEstadoMenus(boolean estado) {  // ← ESTE FALTABA
        mGestion.setEnabled(estado);
        miInsumos.setEnabled(estado);
        miObras.setEnabled(estado);
    }
}