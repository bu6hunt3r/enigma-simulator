package enigma;

public class Rotor {
	// Rotor wires, arrays will store the jump distance to get to the end of the wire
	private int rotorOut[] = new int[26];
	private int rotorIn[] = new int[26];
	private int rotorHead;
	private int ringHead;
	private char notch;
	private int rotate;
	
	/*
	 * Construct the rotor
	 * 
	 * @param rotor
	 * 
	 * @param notch
	 */
	protected Rotor(String rotor, char notch) {
		setRotor(new String[]{rotor, notch+""});
	}

	/**
	 * Get the index of the output of the input wire
	 * @param pos
	 * @return index
	 */
	protected int getOutputOf(int pos) {
		int rotorRingDiff = rotorHead >= ringHead ? rotorHead - ringHead : 26 - (ringHead - rotorHead);
		return (pos + rotorOut[ (pos + rotate + rotorRingDiff) % 26 ]) % 26; // Pos + Jump to mirror with the next rotor
	}
	
	/**
	 * Get the index of the input of the output wire
	 * @param pos
	 * @return
	 */
	protected int getInputOf(int pos) {
		int rotorRingDiff = rotorHead >= ringHead ? rotorHead - ringHead : 26 - (ringHead - rotorHead);
		int posJump = pos - rotorIn[ (pos + rotate + rotorRingDiff) % 26 ];
		return posJump > 0 ? (posJump % 26) : (26 + posJump) % 26;
	}
	
	/**
	 * Get ring head
	 * @return ring head
	 */
	public char getRingHead() {
		return (char) ('A' + (ringHead + rotate) % 26);
	}
	
	/**
	 * @return rotor head
	 */
	public char getRotorHead() {
		return (char) ('A' + (rotorHead + rotate) % 26);
	}
	
	/**
	 * @return notch
	 */
	public char getNotch() {
		return notch;
	}
	
	/**
	 * Get an out wire connection (Used in GUI)
	 * @param pos
	 * @return
	 */
	public int getAnOutWire(int pos) {
		return getOutputOf(pos);
	}
	
	/**
	 * Get an in wire connection (Used in GUI)
	 * @param pos
	 * @return
	 */
	public int getAnInWire(int pos) {
		return getInputOf(pos);
	}
	
	
	/**
	 * Set the rotor position and reset rotations
	 * @param c
	 */
	public void setRotorHead(char c) {
		if (c < 'A' || c > 'Z')
			throw new RuntimeException("Only upper case letters allowed!");
		rotorHead = c - 'A';
		rotate = 0;
	}

	/**
	 * Set rotor
	 * @param rotor
	 */
	public void setRotor(String[] rotor) {
		this.notch = rotor[1].charAt(0);
		System.out.println("Notch: " + this.notch);
		for(int i = 0; i < 26; i++) {
			int from 		= (char) ('A'+i);
			int to 			= rotor[0].charAt(i);
			rotorOut[i] 	= from < to ? to - from : (26 - (from - to)) % 26;
			rotorIn[ (i + rotorOut[i]) % 26 ] 		= rotorOut[i];  
		}
	}

	/** 
	 * reset rotation
	 */
	public void reset() {
		this.rotate = 0;
	}
	
	/**
	 * Rotate single rotor one position  
	 */
	protected void rotate() {
		rotate = (rotate + 1) % 26; 
	}
	
}
