import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Die ScaleBacktracking Klasse implementiert Methoden zur loesung des gegebenen Problems.
 * 
 * @author Maxime, Dominik, Jonas
 * @version 1.0
 */
public class ScaleBacktracking {
	private final ArrayList<Integer> weights;
	
	/**
	 * Der Konstruktor initialiesiert die Liste aller zur Verfuegung stehenden Gewichte mit einer leeren Liste.
	 */
	public ScaleBacktracking() {
		weights = new ArrayList<Integer>();
	}
	
	/**
	 * Diese Methode setzt die zur Verfuegung stehenden Gewichte.
	 * @param pWeights Die Gewichte die genutzt werden sollen.
	 */
	public void setWeights(Collection<Integer> pWeights) {
		weights.clear();
		weights.addAll(pWeights);
	}
	
	/**
	 * Diese Methode prueft mit welchen Kombinationen ein gegebenes Gewicht mit den zur Verfuegung stehenden Gewichten abgebildet werden kann.
	 * @param pWeight Das Gewicht welches abgebildet werden soll.
	 */
	public void printAllCombinationsToCreateWeight(int pWeight) {
		checkCombination(0, new ArrayList<>(), 0, pWeight);
	}
	
	/**
	 * Diese Methode prueft rekursiv alle Kombinationen der Gewichte und ob diese einen vorgegebenen Wert ereichen.
	 * @param pCurrentWeight Das aktuelle Gewicht der Waage
	 * @param pCurrentPath Der aktulle Pfad durch den Baum.
	 * @param pCurrentIndex Der aktuelle Index in den Gewichten.
	 * @param pTargetWeight Das gewicht welches erreicht werden soll.
	 */
	private void checkCombination(int pCurrentWeight, ArrayList<ScaleOption>  pCurrentPath, int pCurrentIndex, int pTargetWeight) {
		if(pCurrentIndex >= weights.size()) {
			if(Math.abs(pCurrentWeight) == pTargetWeight) {
				outputPath(pCurrentPath);
			}
		} else {		
			for(ScaleOption option : ScaleOption.values()) {
				if(!symetricPath(pCurrentPath, option)) {
					pCurrentPath.add(option);
					checkCombination(addUp(pCurrentWeight, weights.get(pCurrentIndex), option), pCurrentPath, pCurrentIndex + 1, pTargetWeight);
					pCurrentPath.remove(pCurrentPath.size() - 1);
				}
			}
		}
	}

	/**
	 * Diese Methode gibt einen gegbenen Pfad auf der Konsole aus.
	 * @param pPath Der Pfad der auf der Konsole ausgegeben werden soll.
	 */
	private void outputPath(ArrayList<ScaleOption> pPath) {
		StringBuilder outputOne = new StringBuilder();
		StringBuilder outputTwo = new StringBuilder();
		int valOne = 0;
		int valTwo = 0;
		
		for(int i = 0; i < pPath.size(); ++i) {
			if(pPath.get(i) != ScaleOption.NOT_ON_SCALE) {
				outputOne.append(String.format("%c %d ", toChar(pPath.get(i)), weights.get(i)));
				outputTwo.append(String.format("%c %d ", toChar(negate(pPath.get(i))), weights.get(i)));
				
				valOne = addUp(valOne, weights.get(i), pPath.get(i));
				valTwo = addUp(valTwo, weights.get(i), negate(pPath.get(i)));
			}
		}
		
		outputOne.append(String.format("= %d", valOne));
		outputTwo.append(String.format("= %d", valTwo));
		
		System.out.println(outputOne);
		System.out.println(outputTwo);
	}
	
	/**
	 * Es wird geprueft ob es sich bei diesem Pfad um einen symmetrischen zu einem bereits behandelten dreht.
	 * Das ist der Fall, wenn alle zuvor abgearbeiteten Gewicht nicht genommen wurden und das aktuelle Gewicht auf die linke Seite soll.
	 * @param pCurrentPath Die Position aller vorherigen Gewichte .
	 * @param option Die Position des neuen Gewichts.
	 * @return {@code true} wenn es sich um einen symetrischen Pfad handelt, sonst {@code false}
	 */
	private static boolean symetricPath(ArrayList<ScaleOption> pCurrentPath, ScaleOption option) {
		if(option != ScaleOption.LEFT) {
			return false;
		} else {
			return pCurrentPath.parallelStream().allMatch(x -> x == ScaleOption.NOT_ON_SCALE);
		}
	}
	
	/**
	 * Aufgrund der gegebene Gewichsposition werden zwei gegebene Gewichte miteinander verrechnet und zurueckgegeben.
	 * 	 LEFT  -> pOne - pTwo
	 *   RIGHT -> pOne + pTwo
	 *   NOT_ON_SCALE -> pOne
	 * @param pOne Das aktuelle gewicht der Waage.
	 * @param pTwo Das Gewicht welches mit zu der Waage soll.
	 * @param pOption Die Position an die das Gewicht gestellt werden soll.
	 * @return Das Ergebnis gemaess der oben stehenden Tabelle.
	 */
	private static int addUp(int pOne, int pTwo, ScaleOption pOption) {
		switch(pOption) {
		case LEFT:
			return pOne - pTwo;
		case NOT_ON_SCALE:
			return pOne;
		case RIGHT:
			return pOne + pTwo;
		default:
			throw new InvalidParameterException("Could not charify option "
				+ pOption.toString()
				+ ".");
		}
	}
	
	/**
	 * Eine gegebene Gewichtsposition wird ihrer Character zugewiesen, das heisst:
	 *   LEFT  -> '-'
	 *   RIGHT -> '+'
	 * Bei anderen Eingaben wird ein Fehler geworfen.
	 * @param pOption Die Gewichtsposition.
	 * @return Der Charakter der diese Position representiert.
	 */
	private static char toChar(ScaleOption pOption) {
		switch(pOption) {
		case LEFT:
			return '-';
		case RIGHT:
			return '+';
		default:
			throw new InvalidParameterException("Could not charify option "
				+ pOption.toString()
				+ ".");
		}
	}
	
	/**
	 * Eine gegebene Gewichtsposition wird negiert, das heisst:
	 *   LEFT  -> RIGHT
	 *   RIGHT -> LEFT
	 * Bei anderen Eingaben wird ein Fehler geworfen.
	 * @param pOption Die Gewichtsposition.
	 * @return Die negierte Gewichtsposition.
	 */
	private static ScaleOption negate(ScaleOption pOption) {
		switch(pOption) {
		case LEFT:
			return ScaleOption.RIGHT;
		case RIGHT:
			return ScaleOption.LEFT;
		default:
			throw new InvalidParameterException("Could not negate option "
					+ pOption.toString()
					+ ".");
		}
	}
	
	/**
	 * Dieses Enum haelt Informationen ueber die Aktion die mit einem Gewicht gemacht werden soll. 
	 * 
	 * @author Maxime, Dominik, Jonas
	 * @version 1.0
	 */
	private enum ScaleOption {
		LEFT, // Das Gewicht kommt auf die linke Seite.
		NOT_ON_SCALE, // Das Gewicht kommt nicht auf die Waage.
		RIGHT // Das Gewicht kommt auf die rechte Seite.
	}

}
