import java.util.ArrayList;

/**
 * Diese Klasse ist eine einfache Implementation eines HashSets.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 * @param <K> Der generische Typ welcher von dem HashSet verwaltet werden
 *        soll.
 */
public class MyHashSet<K> {
	private static final int INITIAL_SIZE = 10; // Die initiale Groesse der HashTabelle
	private static final int THRESHHOLD   =  2; // Der Wert ab welchem die HashTabelle vergroessert werden soll
	private static final int FACTOR       =  2; // Der Factor um den die HashTabelle erweitert werden soll

	private ArrayList<K>[] data; // Die HashTabelle
	private int elements;        // Die Anzahl der Elemente
	
	/**
	 * In den Methoden @code{add(K element)} und @code{delete(K element)} wurde bewusst
	 * auf die Verwendung der Methode contains verzichtet, da das Berechnen einen
	 * HashWerts eventuell ein schlechtes Laufzeitverhalten hat.
	 */

	/**
	 * Der Konstruktor fuer Objekte der Klasse MyHashSet.
	 */
	public MyHashSet() {
		data     = createArray(INITIAL_SIZE);
		elements = 0;
	}

	/**
	 * Diese Methode fuegt dem Set ein weiteres Element hinzu. Sollte die Anzahl der Elemente geteilt
	 * durch die Anzahl der Listen groesser als 2 sein, wird das Set dynamisch erweitert. 
	 * @param element Das Element welches hinzugefuegt werden soll.
	 * @return {@code true} Wenn das Element bereits existriert, sonst {@code false}
	 */
	public boolean add(K element) {
		if(addElement(element, data)) {
			return true;
		}

		// Pruefen ob erweitert werden muss
		if((double) elements / data.length > THRESHHOLD) {
			doubleSize();
		}

		return false;
	}

	/**
	 * Diese Methode loescht ein Element welches in dem HashSet gespeichert wird.
	 * @param element Das Element welches geloescht werden soll.
	 * @return {@code true} Wenn das Element existrierte, sonst {@code false}
	 */
	public boolean delete(K element) {
		int pos = hash(element, data.length);

		if(data[pos] != null && data[pos].contains(element)) {
			data[pos].remove(element);
			elements--;
			return true;
		}

		return false;
	}

	/**
	 * Diese Methode prueft ob ein Element in dem aktuellen HashSet vorhanden ist.
	 * @param element Das Element fuer welches geprueft werden soll ob es bereits vorhanden ist.
	 * @return {@code true} Wenn das Element existriert, sonst {@code false}
	 */
	public boolean contains(K element) {
		int pos = hash(element, data.length);

		if(data[pos] != null && data[pos].contains(element)) {
			return true;
		}

		return false;
	}

	/**
	 * Diese Methode gibt alle gespeicherten Elemente dieses HashSets in einer ArrayListe zurueck.
	 * @return Eine {@code ArrayList<K>} die alle Elemente enthaelt.
	 */
	public ArrayList<K> getElements() {
		ArrayList<K> ret = new ArrayList<>();

		for(int i = 0; i < data.length; i++) {
			for(K element : data[i]) {
				ret.add(element);
			}
		}

		return ret;
	}

	/**
	 * Diese Methode erwitert die Groesse der HashTabelle indem ein neues Feld angelegt wird.
	 * Es muessen alle Elemente erneut eingefuegt werden.
	 */
	private void doubleSize() {
		ArrayList<K>[] temp = createArray(data.length * FACTOR);

		elements = 0;
		
		for(int i = 0; i < data.length; i++) {
			for(K element : data[i]) {
				addElement(element, temp);
			}
		}

		data = temp;
	}

	/**
	 * Diese Methode fuegt einem uebergebenen HashSet ein neues Element hinzu.
	 * @param element Das Element welches hinzugefuegt werden soll.
	 * @param hashMap Die HashTabelle wo ein Element hinzuefuegt werden soll.
	 * @return {@code true} Wenn das Element bereits existriert, sonst {@code false}
	 */
	private boolean addElement(K element, ArrayList<K>[] hashSet) {
		int pos = hash(element, hashSet.length);

		if(hashSet[pos] == null) {
			hashSet[pos] = new ArrayList<>();
		}

		if(hashSet[pos].contains(element)) {
			return true;
		}

		hashSet[pos].add(element);
		elements++;
		return false;
	}

	/**
	 * Die Methode liefert den Hash-Wert fuer ein Element gemaess der definierten
	 * HashFunktion welche die Groesse des Feldes benoetigt.
	 * @param element Das Element von dem der Hash-Wert berechnet werden soll.
	 * @param size Die Groesse der Hash-Tabelle.
	 * @return Den ermittelten Wert als {@code int}.
	 */
	private int hash(K element, int size) {
		return Math.abs(element.hashCode() % size);
	}

	/**
	 * Diese Methode gibt ein Array mit dem richtigen Typ und der richtigen Groesse zurueck.
	 * @param length Die Groesse des zu erzeugenden Arrays.
	 * @return Ein {@code ArrayList<K>[]} mit der angegebenen Groesse.
	 */
	@SuppressWarnings("unchecked") // Java unterstruetzt leider immer noch keine Arrays fuer generische Typen.
	private  ArrayList<K>[] createArray(int length) {
		return (ArrayList<K>[]) new ArrayList[length];
	}
}
