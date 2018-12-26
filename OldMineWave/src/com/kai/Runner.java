package com.kai;


import javax.swing.JFrame;
public class Runner {
	public static void main(String args[]) {
		JFrame frame = new JFrame("Mine Wave 2.0");
		Screen s1 = new Screen();
		frame.add(s1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		s1.animate();
	}

}