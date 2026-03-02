package Mains;

import java.awt.EventQueue;

import Parte2.Vista.Practica03_c;
public class MainEjercicio3_c   {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Practica03_c frame = new Practica03_c();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
}

}