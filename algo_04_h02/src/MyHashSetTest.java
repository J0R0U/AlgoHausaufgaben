import java.util.ArrayList;
import java.util.Collections;

/**
 * Diese Klasse testet die MyHashSet Klasse.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class MyHashSetTest {
	
	/**
	 * Die main-Methode welche die Tests ausfuehrt.
	 * @param args Die Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		MyHashSet<Integer> myHash = new MyHashSet<>();
		
		for (int i = 0; i < 30; i++) {
			myHash.add(i);
		}
		
		System.out.println(myHash.contains(5));
		myHash.delete(5);
		
		System.out.println(myHash.contains(5));
		
		ArrayList<Integer> el = myHash.getElements();
		
		System.out.println(el);
		Collections.sort(el);
		System.out.println(el);
	}
}
