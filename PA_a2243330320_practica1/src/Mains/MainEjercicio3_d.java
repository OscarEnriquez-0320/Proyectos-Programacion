package Mains;

import java.awt.EventQueue;

import Parte2.Vista.Practica03_d;
public class MainEjercicio3_d   {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Practica03_d frame = new Practica03_d();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
}

}