/**
 * Diese Klasse testet die MyArrayListKlasse.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class MyArrayListTest {

	/**
	 * Die main-Methode welche die Tests ausführt.
	 * @param args Die Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		MyArrayList<Integer> list1 = new MyArrayList<>();
		list1.addLast(2);
		System.out.println("Groesse der Liste ist: " + list1.size()); // 1
		for (int i = 0; i < 10; i++) {
			list1.addFirst(i);
		}
		System.out.println("Groesse der Liste ist: " + list1.size()); // 11
		for (int i = 0; i < list1.size(); i++) {
			System.out.print(list1.get(i) + " "); // 9 8 7 6 5 4 3 2 1 0 2
		}
		System.out.println();
		list1.clear();
		System.out.println("Groesse der Liste ist: " + list1.size()); // 0

		MyArrayList<String> list2 = new MyArrayList<>();
		list2.addFirst("Algorithmen");
		String s = list2.get(0);
		System.out.println(s);
	}
}
