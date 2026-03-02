package Ejercicio2;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class Practica02_b2 extends JFrame implements ActionListener {
    private JPanel PanelPrincipal;
    private JButton Bsalir;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Practica02_b frame = new Practica02_b();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Practica02_b2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 348, 233);
        setTitle("Frame Practica02_b2");
        PanelPrincipal = new JPanel();
        PanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(PanelPrincipal);
        PanelPrincipal.setLayout(null);

        Bsalir = new JButton("Salir");
        Bsalir.setBounds(104, 87, 89, 23);
        Bsalir.setMnemonic(KeyEvent.VK_S);
        Bsalir.setDisplayedMnemonicIndex(0);
        Bsalir.addActionListener(this);
        PanelPrincipal.add(Bsalir);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.Bsalir) 
        {
           JOptionPane.showMessageDialog(this,"Hasta Luego");
           this.dispose();
           
        }
    }
     
    
}
