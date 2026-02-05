package Vista;

import Controlador.ControladorPrincipal;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {
    private TextPanel textPanel;
    private JButton btn;
    private Toolbar toolbar;
    private FormPanel formPanel;
    
    public MainFrame(ControladorPrincipal controlador) {
        super("Hola Mundo");
        
        setLayout(new BorderLayout());
        toolbar = new Toolbar();
        btn = new JButton("Dame Click");
        textPanel = new TextPanel();
        formPanel = new FormPanel();
        
        setJMenuBar(createMenuBar());
        
        // Conectar vistas con controlador
        controlador.setVistas(textPanel, formPanel, toolbar);
        
        // Listener para el botón "Dame Click"
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                textPanel.appendText("hola\n");
            }
        });
        
        // Agregar componentes (IMPORTANTE: mantener el orden original)
        add(formPanel, BorderLayout.WEST);
        add(toolbar, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(btn, BorderLayout.SOUTH);
        
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        JMenu windowMenu = new JMenu("Window");
        JMenu showMenu = new JMenu("Show");
        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(true);
        
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        menuBar.add(fileMenu);
        menuBar.add(windowMenu);
        
        // Listener para mostrar/ocultar formulario
        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) ev.getSource();
                formPanel.setVisible(menuItem.isSelected());
            }
        });
        
        return menuBar;
    }
}