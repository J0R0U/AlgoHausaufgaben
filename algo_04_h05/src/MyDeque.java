/**
 * Diese Klasse implemeniert ein simples Deque.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class MyDeque {
	/**
	 * Der erste Knoten der doppelt verketteten Liste.
	 */
	private DequeNode first;
	/**
	 * Der letzte Knoten der doppelt verketteten Liste.
	 */
	private DequeNode last;
	
	/**
	 * Der Konstruktor fuer Objekte der Klasse MyDeque.
	 */
	public MyDeque() {
		first = null;
		last  = null;
	}
	
	/**
	 * Diese Methode prueft ob dieses Deque im Moment leer ist.
	 * @return {@code true}, wenn das Deque leer ist, sonst {@code false}.
	 */
	public boolean isEmpty() {
		return first == null && last == null;
	}
	
	/**
	 * Diese Methode fuegt dem Deque vorne einen neuen Wert hinzu.
	 * @param val Der Wert der hinzugefuegt werden soll.
	 */
	public void addFirst(String val) {
		if(isEmpty()) {
			first = new DequeNode(val);
			last  = first;
		} else {
			DequeNode oldFirst = first;
			first = new DequeNode(val);
			
			first.setNext(oldFirst);
			oldFirst.setPrev(first);
		}
	}
	
	/**
	 * Diese Methode fuegt dem Deque hinten einen neuen Wert hinzu.
	 * @param val Der Wert der hinzugefuegt werden soll.
	 */
	public void addLast(String val) {
		if(isEmpty()) {
			first = new DequeNode(val);
			last  = first;
		} else {
			DequeNode oldLast = last;
			last = new DequeNode(val);
			
			last.setPrev(oldLast);
			oldLast.setNext(first);
		}
	}
	
	/**
	 * Diese Methode entfernt das erste Element und gibt dessen Wert zuruek.
	 * @return Der Wert des ersten Elements.
	 * @throws IndexOutOfBoundsException Wenn das Deque bereits leer ist.
	 */
	public String removeFirst() {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException("Die Liste ist bereits leer.");
		} else if(first == last) {
			String ret = first.getContent();
			first = null;
			last  = null;
			return ret;
		} else {
			DequeNode oldFirst = first;
			first = oldFirst.getNext();
			
			oldFirst.setNext(null);
			first.setPrev(null);

			return oldFirst.getContent();
		}
	}
	
	/**
	 * Diese Methode entfernt das letzte Element und gibt dessen Wert zuruek.
	 * @return Der Wert des letzte Elements.
	 * @throws IndexOutOfBoundsException Wenn das Deque bereits leer ist.
	 */
	public String removeLast() {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException("Die Liste ist bereits leer.");
		} else if(first == last) {
			String ret = first.getContent();
			first = null;
			last  = null;
			return ret;
		} else {
			DequeNode oldLast = last;
			last = oldLast.getPrev();
			
			oldLast.setPrev(null);
			last.setNext(null);

			return oldLast.getContent();
		}
	}
	
	/**
	 * Diese Klasse implemeniert einen Knoten in einem Deque.
	 * 
	 * @author Maxime, Dominik, Jonas
	 * @version V01.01B00
	 */
	private class DequeNode {
		/**
		 * Der Inhalt dieses Knotens.
		 */
		private String content;
		/**
		 * Der naechste Knoten.
		 */
		private DequeNode next;
		/**
		 * Der vorherige Knotens.
		 */
		private DequeNode prev;
		
		/**
		 * Der Konstruktor fuer Objekte der Klasse DequeNode.
		 * Diesem wird der zu speichernde Wert uebergeben.
		 * @param val Der Wert der gespeichert werden soll.
		 */
		public DequeNode(String val) {
			content = val;
			next = null;
			prev = null;
		}
		
		/**
		 * Diese Methode gibt den Inhalt dieses Knotens.
		 * @return Der Inhalt dieses Knotens.
		 */
		public String getContent() {
			return content;
		}
		
		/**
		 * Diese Methode gibt den naechsten Knoten zurueck.
		 * @return Der naechste Knoten.
		 */
		public DequeNode getNext() {
			return next;
		}
		
		/**
		 * Diese Methode setzt den naechsten Knoten.
		 * @param next Der naechste Knoten.
		 */
		public void setNext(DequeNode next) {
			this.next = next;
		}
		
		/**
		 * Diese Methode gibt den vorherigen Knoten zurueck.
		 * @return Der vorherige Knoten.
		 */
		public DequeNode getPrev() {
			return prev;
		}

		/**
		 * Diese Methode setzt den vorherigen Knoten.
		 * @param next Der vorherige Knoten.
		 */
		public void setPrev(DequeNode prev) {
			this.prev = prev;
		}
	}
}
