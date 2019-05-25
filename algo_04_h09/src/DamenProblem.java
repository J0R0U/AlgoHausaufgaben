import java.util.ArrayList;

/**
 * Klasse fuer das Damenproblem.
 * 
 * @author Maxime, Dominik, Jonas
 * @version 1.0
 */
public class DamenProblem {

	/**
	 * Gibt alle Loesungen des Damenproblems mit gegebener Brettgroesse in der Konsole aus.
	 * 
	 * @param brettgroesse Groesse des Schachbretts
	 */
	public void damenProblem(int brettgroesse) {
		damenProblem(brettgroesse, new ArrayList<>());
	}
	
	/**
	 * Hilfsmethode für Rekursionsaufrufe. Fuegt, wenn moeglich, eine neue Dame an eine freie Stelle hinzu
	 * und wiederholt diesen Schritt bis das Feld voll ist und gibt die Loesung in der Konsole aus.
	 * 
	 * @param brettgroesse Die Groesse des Schachbretts
	 * @param state aktueller Zustand des Schachbretts mit den Positionen der Damen
	 */
	private void damenProblem(int brettgroesse, ArrayList<Integer> state) {
		if(state.size() == brettgroesse) {
			System.out.println(state);
			return;
		}
		
		for(int i = 1; i <= brettgroesse; i++) {
			if(isValidPosition(state, i)) {
				state.add(i);
				damenProblem(brettgroesse, state);
				state.remove(state.size() - 1);
			}
		}
	}
	
	/**
	 * Prueft, ob eine Dame auf der neuen Position geschlagen werden kann.
	 * 
	 * @param state aktueller Stand des Schachbrett
	 * @param newPos Position einer neuen Dame
	 * @return ob die Position moeglich ist
	 */
	private boolean isValidPosition(ArrayList<Integer> state, int newPos) {
		int newCol = state.size();
		for(int i = 0; i < newCol; i++) {
			int val = state.get(i);
			int colDiff = newCol - i;
			int rowDiff = Math.abs(newPos - val);
			if(colDiff == rowDiff || val == newPos)
				return false;
		}
		return true;
	}
}
