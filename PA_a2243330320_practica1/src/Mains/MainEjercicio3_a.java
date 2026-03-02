package Mains;

import java.awt.EventQueue;

import Parte2.Vista.Practica03_a;
public class MainEjercicio3_a   {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Practica03_a frame = new Practica03_a();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
}

}
