import java.util.ArrayList;
import java.util.Collections;

/**
 * Diese Klasse implemeniert einen Heap.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class Heap {
	/**
	 * Der Wert des Dummyelements.
	 */
	private static int DUMMY_VALUE = 0;
	
	/**
	 * Die Daten die in dem Heap gespeichert werden sollen.
	 */
	private ArrayList<Integer> data;
	
	/**
	 * Der Konstruktor initialiesiert die Liste der daten und fuegt in Dummy an erster Stelle ein.
	 */
	public Heap() {
		data = new ArrayList<>();
		data.add(DUMMY_VALUE);
	}
	
	/**
	 * Prueft ob der Heap nur noch das Dummyelement enthaelt und somit leer ist.
	 * @return {@code true}, wenn der Heap leer ist, sonst {@code false}
	 */
	public boolean isEmpty() {
		return data.size() == 1;
	}
	
	/**
	 * Fuegt dem Heap ein neues Element hinzu.
	 * @param i Das Element das hinzugefuegt werden sollt.
	 */
	public void add(int i) {
		data.add(i);
		upheap();
	}
	
	/**
	 * Loscht das groeﬂte Element und gibt dessen Wert zurueck.
	 * @return Das vormals groeﬂte Element.
	 * @throws Eine {@code IndexOutOfBoundsException} wenn der Heap leer ist.
	 */
	public int getMax() {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException("Der Heap ist leer");
		}
		int ret = data.get(1);
		
		Collections.swap(data, 1, data.size()-1);
		data.remove(data.size()-1);
		
		downheap();
		return ret;
	}
	

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder("[");
		
		for(int i = 1; i < data.size(); i++) {
			ret.append(data.get(i));
			if(i < data.size() -1) {
				ret.append(", ");
			}
		}
		
		ret.append("]");
		return ret.toString();
	}
	
	/**
	 * Diese Methode implementiert das upheap-Verfahren.
	 */
	private void upheap() {
		int curr   = data.size() - 1;
		int parent = curr / 2;
		
		while(parent != 0) {
			if(data.get(curr) <= data.get(parent)) {
				break;
			}
			
			Collections.swap(data, curr, parent);
			
			curr = parent;
			parent = curr / 2;
		}
	}
	
	/**
	 * Diese Methode implementiert das downheap-Verfahren.
	 */
	private void downheap() {
		int curr      = 1;
		int currChild = curr * 2;
		
		while(currChild < data.size()) {
			if(currChild + 1 < data.size() && data.get(currChild) < data.get(currChild + 1)) {
				currChild++;
			}
			if(data.get(currChild) < data.get(curr)) {
				break;
			}
			
			Collections.swap(data, curr, currChild);
			
			curr      = currChild;
			currChild = 2 * curr;
		}
	}
}
