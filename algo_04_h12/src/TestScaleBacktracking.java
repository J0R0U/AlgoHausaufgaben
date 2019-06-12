import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * Testklasse fuer die ScaleBacktracking Klasse.
 * 
 * @author Maxime, Dominik, Jonas
 * @version 1.0
 */
public class TestScaleBacktracking {
	/**
	 * Die Gewichte die der Waage zur Verfuegung stehen.
	 */
	private static final List<Integer> TEST_WEIGHTS = Arrays.asList(1, 3, 8, 20);
	
	/**
	 * Die main-Methode welche die Tests ausfuehrt.
	 * @param args Die Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		ScaleBacktracking test = new ScaleBacktracking();
		
		test.setWeights(TEST_WEIGHTS);
		
		String tmp;
		while((tmp = JOptionPane.showInputDialog("Bitte ein Gewicht das ueberprueft werden soll eingeben. '-q' oder Abbrechen beendet das Programm.")) != null && !tmp.equals("-q")) {
			try {
				int val = Integer.parseInt(tmp);
				if(val < 0) {
					System.out.println("Das gegebene Gewicht muss groesser oder gleich Null sein.");
				} else {
					System.out.printf("Ueberpruefung von %d:%n", val);
					test.printAllCombinationsToCreateWeight(val);
				}
			} catch(NumberFormatException e) {
				System.out.println("Eingabe war fehlerhaft. Bitte versuche es erneut.");
			}
			System.out.println();
		}
	}
}
