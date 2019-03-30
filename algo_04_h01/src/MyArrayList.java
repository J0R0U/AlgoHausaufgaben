
/**
 * Diese Klasse ist eine einfache Implementation einer ArrayListe.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 * @param <T> Der generische Typ welcher von der ArrayList verwaltet werden
 *        soll.
 */
public class MyArrayList<T> {
	private Object[] data; // Java erlaubt keine triviale Loesung fuer generische Arrays.
	private int head; // Die aktuelle Position des ersten unbeschriebene Elements.

	/**
	 * Der Konstruktor fuer Objekte der Klasse MyArrayList.
	 */
	public MyArrayList() {
		init();
	}

	/**
	 * Diese Methode fuegt ein Objekt der Klasse T an das Ende der Liste an. Sollte
	 * die Liste voll sein, wird ihre Speicherkapazitaet verdoppelt.
	 * 
	 * @param obj Das Objekt welches hinten angefuegt werden soll.
	 */
	public void addLast(T obj) {
		if (head >= data.length) {
			doubleArrayContent(0);
		}

		data[head] = obj;
		head++;
	}

	/**
	 * Diese Methode fuegt ein Objekt der Klasse T an den Anfang der Liste an.
	 * Sollte die Liste voll sein, wird ihre Speicherkapazitaet verdoppelt.
	 * 
	 * @param obj Das Objekt welches vorne angefuegt werden soll.
	 */
	public void addFirst(T obj) {
		if (head >= data.length) {
			doubleArrayContent(1);
		} else {
			System.arraycopy(data, 0, data, 1, data.length - 1);
		}

		data[0] = obj;
		head++;
	}

	/**
	 * Diese Methode gibt das Objekt welches an einer bestimmten Stelle gespeichert
	 * ist zurueck.
	 * 
	 * @param i Die Position an dessen Stelle das Objekt zurueckgegben werden soll.
	 * @return Das an der Position gespeicherte Objekt.
	 * @throws Eine ArrayIndexOutOfBoundsException wenn i < 0 ist, oder groesser als
	 *              der letzte beschriebene Index des Arrays.
	 */
	@SuppressWarnings("unchecked") // Diese Annotation muss hier stehen, da die Umwandlung von Object -> T
									// stattfindet und instanceof nicht mit dem dynamischen parameter T moeglich
									// ist.
	public T get(int i) {
		if (i < 0 || i >= head) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return (T) data[i];
	}

	/**
	 * Diese Methode loescht den gesamten inhalt der Liste und setzt ihre Groesse
	 * auf 10 Elemente.
	 */
	public void clear() {
		init();
	}

	/**
	 * Diese Methode ermittelt die Anzahl der gespeicherten Elemente dieser Liste.
	 * 
	 * @return Die groesse der Liste.
	 */
	public int size() {
		return head;
	}

	/**
	 * Diese Methode verdoppelt die Kapazitaet der Liste.
	 */
	private void doubleArrayContent(int destPos) {
		Object tmp[] = new Object[data.length * 2];
		System.arraycopy(data, 0, tmp, destPos, data.length);
		data = tmp;
	}

	/**
	 * Diese Methode initialiesiert das Array mit einer Größe von 10 Elementen und
	 * setzt den Anfang auf 0.
	 */
	private void init() {
		data = new Object[10];
		head = 0;
	}
}