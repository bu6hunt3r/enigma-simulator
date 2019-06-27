package gui.listener;

public interface RotorListener {
	public void configure(
			String[] leftRotor, String[] middleRotor, String[] rightRotor,
			char leftStart, char middleStart, char rightStart,
			char leftRing, char middleRing, char rightRing,
			String reflector);
}
