
/**
 * Diese Klasse implementiert eine HashTabelle mit doppeltem Hashing für Strings.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class GamesHash {
	/**
	 * Die groesse der Hash Tabelle.
	 */
	private static final int SIZE = 31;
	/**
	 * Die Hash Tabelle.
	 */
	private String data[];

	/**
	 * Der Konstruktor für Objeke der Klasse GamesHash.
	 */
	GamesHash() {
		data = new String[SIZE];
	}

	/**
	 * Diese Methode fuegt einen neuen String in die Hash Tabelle ein.
	 * @param game Der String der hinzugefuegt weren soll.
	 * @throws HashCollisionException wenn der String schon in der Tabelle ist, oder wenn kein freier Platz ermittelt werden kann.
	 */
	public void add(String game) {
		int index = getIndexOf(game);

		if(data[index] != null) {
			throw new HashCollisionException("Hash collision for %s and %s detected.", game, data[index]);
		}
		data[index] = game;
	}

	/**
	 * Diese Methode prueft ob ein String in der Hash Tabelle ist.
	 * @param game Der String fuer welchen geprueft werden soll ob er in der Hash Tabelle ist.
	 * @return {@code true}, wenn er vorhanden ist, sonst {@code false}
	 */
	public boolean contains(String game) {
		int index = getIndexOf(game);

		return data[index] != null && data[index].equals(game);
	}

	/**
	 * Diese Methode sucht den naechsten freien Index fuer einen String. Wenn der String existiert wird der
	 * Index desselben zurueckgegeben, und wenn kein freier Index gefunden wird, wird der Startindex zurueck gegeben.
	 * @param game Der String zu welchem ein passender Index ermittelt werden soll.
	 * @return Der ermittelte Index.
	 */
	private int getIndexOf(String game) {
		int originalIndex = hashIndex(game) % data.length;
		int currentIndex = originalIndex;
		int increment = hashIncrement(game);

		boolean first = true;

		while(data[currentIndex] != null && !data[currentIndex].equals(game) && (currentIndex != originalIndex || first)) {
			if(first) {
				first = false;
			}
			currentIndex += increment;
			currentIndex %= data.length;
		}

		return currentIndex;
	}
	
	/**
	 * Diese Methode berechnet den Hash-Index eines Strings.
	 * @param game Der String dessen Hash berechnet werden soll.
	 * @return Der Hash des Strings.
	 */
	private int hashIndex(String game) {
		return Math.abs(game.hashCode() - (game.hashCode() / 100) * 100);
	}

	/**
	 * Diese Methode berechnet den Inkrement eines Strings.
	 * @param game Der String dessen Inkrement berechnet werden soll.
	 * @return Der Inkrement des Strings.
	 */
	private int hashIncrement(String game) {
		return Math.abs(game.hashCode() / 100 - (game.hashCode() / 1000) * 10) + 1;
	}
}

/**
 * Die Exception welche geworfen wird, wenn eine Kollision auftritt.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
class HashCollisionException extends RuntimeException {
	private static final long serialVersionUID = 6287500641748714345L;

	/**
	 * Der Konstruktor dieser Exception.
	 * @param format Das Format der Ausgabe in einem String.
	 * @param args Die Formatargumente welche in den String eingefuegt werden sollen.
	 */
	HashCollisionException(String format, Object...args) {
		super(String.format(format, args));
	}
}
