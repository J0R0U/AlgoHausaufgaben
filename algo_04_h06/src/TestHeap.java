/**
 * Diese Klasse testet die Heap Klasse.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class TestHeap {
	/**
	 * Die Zahlen die in den Heap hinzugefuegt werden sollen.
	 */
	private static final int[] TEST_NUMBERS       = {1, 6, 8, 18, 23, 5, 17, 20, 26, 21, 9};
	
	/**
	 * Die main-Methode welche die Tests ausfuehrt.
	 * @param args Die Kommandozeilenparameter.
	 */
	public static void main(String args[]) {
		Heap h = new Heap();
		
		System.out.println(h);
		
		for(int x : TEST_NUMBERS) {
			h.add(x);
			System.out.println(h);
		}
		
		System.out.println();
		
		while(!h.isEmpty()) {
			System.out.println(h.getMax());
			System.out.println(h);
		}
	}
}
