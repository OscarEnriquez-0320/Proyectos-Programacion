package Controlador;

import Controlador.CPrincipal;

public class principal {
    public static void main(String[] args) {
        try {
            // Iniciar la aplicación con el nuevo controlador
            CPrincipal app = new CPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                null,
                "Error al iniciar la aplicación: " + e.getMessage(),
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
}