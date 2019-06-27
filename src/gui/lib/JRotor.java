package gui.lib;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class JRotor  extends JPanel {
	private Dimension dim;
	private GridBagConstraints gc;
	private int thickOutLine = -1;
	private int thickInLine = -1;

	public JRotor() {
		// Create fixed dimension
		dim = new Dimension(210, 445);
		
		// Set layout
		setLayout(new GridBagLayout());
		
		// Default grid configuration
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 1;
		gc.weighty = 1;
		
		// Set size
		setPreferredSize(dim);
	}
}
