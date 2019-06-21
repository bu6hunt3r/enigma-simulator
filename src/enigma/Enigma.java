package enigma;

import javax.management.RuntimeErrorException;

public class Enigma {
	// Machine configuration
	private Rotor rightRotor;
	private Rotor centerRotor;
	private Rotor leftRotor;
	private Reflector reflector;
	private int[] plugboard;
	
	// Available rotors to choose from
	public static final String[] I = {"EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q"};
	public static final String[] II = {"AJDKSIRUXBLHWTMCQGZNPYFVOE", "E"};
	public static final String[] III = {"BDFHJLCPRTXVZNYEIWGAKMUSQO", "V"};
	public static final String[] IV = {"ESOVPZJAYQUIRHXLNFTGKDCMWB", "J"};
	public static final String[] V = {"VZBRGITYUPSDNHLXAWMJQOFECK", "Z"};
		
	// Available reflectors to choose from
	public static final String A = "EJMZALYXVBWFCRQUONTSPIKHGD";
	public static final String B = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
	public static final String C = "FVPJIAOYEDRZXWGCTKUQSBNMH";
	
	/*
	 * Construct the machine
	 * 
	 * @param left
	 * 
	 * @param center
	 * 
	 * @param right
	 * 
	 * @param ref
	 */
	public Enigma(String[] left, String[] center, String[] right, String ref) {
		// Check for correct rotor	
		if(!correctRotor(left) || !correctRotor(center) || !correctRotor(right))
			throw new RuntimeException("Please choose a correct Rotor");
		
		// Check for correct reflector
		if(!correctReflector(ref))
			throw new RuntimeException("Please choose a correct Reflector");
	
		// Assign rotors and reflector
		this.leftRotor = new Rotor(left[0], left[1].charAt(0));
		this.centerRotor = new Rotor(center[0], center[1].charAt(0));
		this.rightRotor = new Rotor(right[0], right[1].charAt(0));
		this.reflector = new Reflector(ref);
		
		// Create plugboard
		plugboard = new int[26];
		
		// Reset plugboard
		resetPlugboard();
		
		
	}
	
	/**
	* Type text to encode
	* @param text
	* @return encoded text
	*/
	public String type(String text) {
		String output = "";
		for (int i = 0; i < text.length(); i++) {
			// Allow upper case letter
			if(text.charAt(i) >= 'A' && text.charAt(i) <= 'Z')
				output += rotorsEncryption(text.charAt(i));
			
			// Allow white space and newline
			else if (text.charAt(i) == ' ' || text.charAt(i) == '\n')
				output += text.charAt(i);
			
			// If other chars
			else
				throw new RuntimeException("Only upper case letters allowed!");
		}
		return output;
	}
	
	/** 
	* Rotor encryption
	* @param inputC
	* @return encoded input
	*/
	private char rotorsEncryption(char inputC) {
		
		// rotate Rotor left & center
		if(centerRotor.getNotch() == centerRotor.getRotorHead()) {
			leftRotor.rotate();
			centerRotor.rotate();
		}
		
		if(rightRotor.getNotch() == rightRotor.getRotorHead()) {
			centerRotor.rotate();
		}
		
		// Rotate rotor right
		rightRotor.rotate();
		
		// Static wheel
		int input = inputC - 'A';
		
		// Pass by the plugboard
		if(plugboard[input] != -1)
			input = plugboard[input];
		
		// Start  processing from the right wheel to left wheel
		int outOfRightRotor = rightRotor.getOutputOf(input);
		int outOfCenterRotor = centerRotor.getOutputOf(outOfRightRotor);
		int outOfLeftRotor = centerRotor.getOutputOf(outOfCenterRotor);
		
		// Enter and exit reflector
		int outOfReflector = reflector.getOutputOf(outOfLeftRotor);
		
		// Start processing from left to right wheel
		int inOfLeftRotor = leftRotor.getInputOf(outOfReflector);
		int inOfCenterRotor = centerRotor.getInputOf(inOfLeftRotor);
		int inOfRightRotor = rightRotor.getInputOf(inOfCenterRotor);
		
		// Pass by plugboard
		if(plugboard[inOfRightRotor] != -1)
			inOfRightRotor = plugboard[inOfRightRotor];
		
		// Static wheel
		return (char) (inOfRightRotor + 'A');
		
				
	}
	
	/**
	* Check if the chosen Rotor is correct
	* @param rotor
	* @return boolean
	*/
	private boolean correctRotor(String[] rotor) {
		return rotor == I || rotor == II || rotor == III || rotor == IV || rotor == V; 
	}
	
	/**
	* Check if the chosen Reflector is correct
	* @param reflector
	* @return boolean
	*/
	private boolean correctReflector(String reflector) {
		return reflector == A || reflector == B || reflector == C;  
	}

	/**
	 * Set the plugboard
	 * @param plugboard
	 */
	public void insertPlugboardWire(char a, char b) {
		this.plugboard[a - 'A'] = b - 'A';
		this.plugboard[b - 'A'] = a - 'A';
	}
	
	/**
	 * Remove a wire from the plugboard
	 * @param wire
	 */
	public void removePlugboardWire(char a) {
		this.plugboard[a - 'A'] = -1;
		this.plugboard[this.plugboard[a - 'A']] = -1;
	}
	
	/**
	 * Checks if a letter is occupied
	 * @param c
	 * @return boolean
	 */
	public boolean isPlugged(char c) {
		return this.plugboard[c - 'A'] != -1;
	}
	
	/**
	 * Get linked char to `a`
	 * @param a
	 * @return 
	 * @return int
	 */
	public int getPlugboardOf(int a) {
		return this.plugboard[a];
	}
	
	/**
	 * Reset plugboard
	 */
	private void resetPlugboard() {
		for (int wire = 0; wire < 26; wire++) {
			this.plugboard[wire] = -1;
		}
	}

	/** Get left rotor
	 * @return left rotor
	 */
	public Rotor getLeftRotor() {
		return leftRotor;
	}
	
	/** Get center rotor
	 * @return center rotor
	 */
	public Rotor getCenterRotor() {
		return centerRotor;
	}
	
	/** Get right rotor
	 * @return right rotor
	 */
	public Rotor getrightRotor() {
		return rightRotor;
	}
	
	/**
	 * Get reflector
	 * @return reflector
	 */
	public Reflector getReflector() {
		return reflector;
	}

	public void resetRotation() {
		leftRotor.reset();
		centerRotor.reset();
		rightRotor.reset();
	}
}
