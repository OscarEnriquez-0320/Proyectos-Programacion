package Controlador;


import Vista.Vejerciociclase01;
import Modelo.Lista;
import Modelo.Cpersona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cejercioclase01 {
    private Vejerciociclase01 vista;
    private Lista lista;
    
    public Cejercioclase01() {
        lista = new Lista();
        vista = new Vejerciociclase01();
        vista.setVisible(true);
        
        vista.bagregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cpersona nodo = new Cpersona(vista.getT1(), vista.getT2());
                lista.insertar(nodo);
                vista.limparText();
                vista.setTr(lista.info() + "\n......\n");
            }
        });
        
        vista.bsalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
            }
        });
    }
}