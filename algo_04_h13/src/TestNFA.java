/**
 * Testklasse fuer die NFA Klasse.
 * 
 * @author Maxime, Dominik, Jonas
 * @version 1.0
 */
public class TestNFA {
	// Der Graph
	private static final String TEST_GRAPH = "3,3,1,2,a,1,3,a,2,2,a,2,2,b,2,3,a";
	// Erster Test String
	private static final String TEST_ONE   = "abba";
	// Zweiter Test String
	private static final String TEST_TWO   = "a";
	// Dritter Test String
	private static final String TEST_THREE = "ab";
	
	/**
	 * Die main-Methode welche die Tests ausfuehrt.
	 * @param args Die Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		NFA nfa_test = new NFA(TEST_GRAPH);
		System.out.println(nfa_test.testString(TEST_ONE));
		System.out.println(nfa_test.testString(TEST_TWO));
		System.out.println(nfa_test.testString(TEST_THREE));
	}
}
