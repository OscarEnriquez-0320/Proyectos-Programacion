package Vista;

import javax.swing.*;
import java.awt.*;

public class Vejerciociclase01 extends JFrame {
    private JPanel contentPane;
    private JTextField T1, T2, Tr;
    private JButton Bagregar, Bsalir;
    private JScrollPane scrollPane;
    
    public Vejerciociclase01() {
        setTitle("Ventana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 60, 20);
        contentPane.add(lblNombre);
        
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(30, 60, 60, 20);
        contentPane.add(lblApellido);
        
        T1 = new JTextField();
        T1.setBounds(100, 30, 150, 20);
        contentPane.add(T1);
        
        T2 = new JTextField();
        T2.setBounds(100, 60, 150, 20);
        contentPane.add(T2);
        
        Tr = new JTextField();
        Tr.setBounds(30, 100, 320, 100);
        contentPane.add(Tr);
        
        Bagregar = new JButton("Agregar");
        Bagregar.setBounds(80, 220, 80, 25);
        contentPane.add(Bagregar);
        
        Bsalir = new JButton("Salir");
        Bsalir.setBounds(200, 220, 80, 25);
        contentPane.add(Bsalir);
    }
    
    public String getT1() {
        return T1.getText();
    }
    
    public String getT2() {
        return T2.getText();
    }
    
    public void setT1(String texto) {
        T1.setText(texto);
    }
    
    public void setT2(String texto) {
        T2.setText(texto);
    }
    
    public void setTr(String texto) {
        Tr.setText(texto);
    }
    
    public JButton bagregar() {
        return Bagregar;
    }
    
    public JButton bsalir() {
        return Bsalir;
    }
    
    public void limparText() {
        T1.setText("");
        T2.setText("");
    }
}