package Mains;

import java.awt.EventQueue;

import Parte2.Vista.Practica03_b;
public class MainEjercicio3_b   {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Practica03_b frame = new Practica03_b();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
}

}
