import java.util.Arrays;
import java.util.List;

/**
 * Diese Klasse implemeniert eine simple Syntaxueberpruefung
 * welche auf folgende Bedingungen prueft:
 *   - Jede sich oeffnende Klammer wird von einer Klammer gleicher Art wieder geschlossen.
 *   - Jede sich schliessende Klammer wurde von einer Klammer gleicher Art geoeffnet.
 *   - Es duerfen keine Ueberschneidungen in der Form [ ( ] ) stattfinden.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class Brackets {
	/**
	 * Alle Zeichen die als oeffnende Klammern interpretiert werden sollen.
	 */
	private static final List<Character> OPENING_BRACKETS = Arrays.asList(
		     new Character[] {'(', '[', '{'}
		);
	/**
	 * Alle Zeichen die als schliessende Klammern interpretiert werden sollen.
	 */
	private static final List<Character> CLOSING_BRACKETS = Arrays.asList(
		     new Character[] {')', ']', '}'}
		);
	
	/**
	 * Diese Methode prueft ob ein Text valide ist. Das heisst,
	 * dass folgende Bedingungen erfuellt sein muessen:
	 *   - Jede sich oeffnende Klammer wird von einer Klammer gleicher Art wieder geschlossen.
	 *   - Jede sich schliessende Klammer wurde von einer Klammer gleicher Art geoeffnet.
	 *   - Es duerfen keine Ueberschneidungen in der Form [ ( ] ) stattfinden.
	 * @param s Der Text der Ueberprueft werden soll.
	 * @return {@code true}, wenn der Text valide ist, sonst {@code false}.
	 */
	public boolean isValid(String s) {
		MyDeque stack = new MyDeque();
		
		for(char c : s.toCharArray()) {
			if(isOpeningBracket(c)) {
				stack.addLast("" + c);
			} else if(isClosingBracket(c)) {
				char o;
				try {
					o = stack.removeLast().charAt(0);
				} catch(IndexOutOfBoundsException e) {
					return false;
				}
				
				if(!doBracketsFit(o, c)) {
					return false;
				}
			}
		}
		
		return stack.isEmpty();
	}
	
	/**
	 * Diese Methode prueft ob ein gegebenes Zeichen eine oeffnende Klammer ist.
	 * @param c Das Zeichen welches ueberprueft werden soll.
	 * @return {@code true}, wenn es eine oeffnende Klammer ist, sonst {@code false}.
	 */
	private static boolean isOpeningBracket(char c) {
		return OPENING_BRACKETS.contains(c);
	}
	
	/**
	 * Diese Methode prueft ob ein gegebenes Zeichen eine schliessende Klammer ist.
	 * @param c Das Zeichen welches ueberprueft werden soll.
	 * @return {@code true}, wenn es eine schliessende Klammer ist, sonst {@code false}.
	 */
	private static boolean isClosingBracket(char c) {
		return CLOSING_BRACKETS.contains(c);
	}
	
	/**
	 * Diese Methode prueft zwei gegeben Klammern vom gleichen Typ sind.
	 * @param open Die oeffnende Klammer.
	 * @param close Die schliessende Klammer.
	 * @return {@code true}, wenn beide Klammern vom selben Typ sind, sonst {@code false}.
	 */
	private static boolean doBracketsFit(char open, char close) {
		int indexOpen  = OPENING_BRACKETS.indexOf(open);
		int indexClose = CLOSING_BRACKETS.indexOf(close);
		return indexOpen != -1 && indexClose != -1 && indexOpen == indexClose;
	}
}
