package Ejercicio1;

import java.awt.EventQueue;

public class MainP1B {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Practica01_01b frame = new Practica01_01b();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
