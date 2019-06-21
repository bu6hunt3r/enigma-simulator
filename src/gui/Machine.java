package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import enigma.Enigma;

public class Machine extends JFrame {
	
	private Enigma enigma;
	
	public Machine() {
		super("Enigma Machine");
		
		// Create the enigma
		enigma = new Enigma(Enigma.I, Enigma.II, Enigma.III, Enigma.A);
		
		// Set frame layout
		setLayout(new BorderLayout());
		// Frame preference
		setVisible(true);
		Dimension dim = new Dimension(1150,700);
		setPreferredSize(dim);
		setMinimumSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
	}
}
