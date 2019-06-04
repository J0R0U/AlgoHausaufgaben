/**
 * Testklasse fuer die textSearch Methode.
 * 
 * @author Maxime, Dominik, Jonas
 * @version 1.0
 */
public class TestTextSearch {
	
	/**
	 * Die main-Methode welche die Tests ausfuehrt.
	 * @param args Die Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		System.out.println(TextSearch.textSearch("abcabcdababdc.", "ab"));
		System.out.println(TextSearch.textSearch("abcabcdababdc.", "c."));
		System.out.println(TextSearch.textSearch("abcabcdababdc.", "c\\."));
		System.out.println(TextSearch.textSearch("abcabcdababdc.", "b[cd]"));
		System.out.println(TextSearch.textSearch("abcabcdababdc.", "a....c"));
		System.out.println(TextSearch.textSearch("a[aababa][ab]a", "a[ab]a"));
		System.out.println(TextSearch.textSearch("a[aababa][ab]a", "a.\\[a"));
	}
}
