import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.PatternSyntaxException;

/**
 * Klasse welche die textSearch Methode beinhaltet.
 * 
 * @author Maxime, Dominik, Jonas
 * @version 1.0
 */
public class TextSearch {
	
	/**
	 * Diese Methode prueft an welchen Stellen in einem gegebenen String ein bestimmtes Pattern vorkommt und gibt diese in einer Liste zurueck.
	 * @param text Der Text in dem nach einem Pattern gesucht werden soll.
	 * @param pattern Das Pattern mit dem in dem Text gesucht werden soll.
	 * @return Eine {@code ArrayList<Integer>} welche die Positionen der Uebereinstimmungen zurueck gibt.
	 */
	public static ArrayList<Integer> textSearch(String text, String pattern) {
		ArrayList<Integer> ret = new ArrayList<>();
		ArrayList<Token> tokenisedPattern = generateTokenPattern(pattern);
		UsageTable usageTable = generateUsageTable(tokenisedPattern);
		int posInString = 0;
		
		while(posInString + tokenisedPattern.size() - 1 < text.length()) {
			if(isMatch(text, posInString, tokenisedPattern)) {
				ret.add(posInString);
			}
			
			if(posInString + tokenisedPattern.size() < text.length()) {
				posInString += tokenisedPattern.size() - usageTable.getNumber(text.charAt(posInString + tokenisedPattern.size()));
			} else {
				posInString = text.length();
			}
		}
		
		return ret;
	}
	
	/**
	 * Diese Methode generiert aus einem String-Pattern ein Token-Pattern und ueberprueft diese gleichzeitig. 
	 * @param pattern Das String-Pattern welches umgewandelt werden soll.
	 * @return Das resultierende Token-Pattern.
	 * @throws Eine {@code PatternSyntaxException} wenn das gegebene String-Pattern nicht den Anforderungen entspricht.
	 */
	private static ArrayList<Token> generateTokenPattern(String pattern) {
		ArrayList<Token> ret = new ArrayList<>();
		
		boolean escapeNext = false;
		boolean inCharacterClass = false;
		CharacterClass currentClass = null;
		
		for(int i = 0; i < pattern.length(); i++) {
			char curr = pattern.charAt(i);
			
			switch(curr) {
			case '.':
				if(inCharacterClass) {
					currentClass.data.add(curr);
				} else if(escapeNext) {
					ret.add(new OneChar(curr));
					escapeNext = false;
				} else {
					ret.add(new AllChars());
				}
				break;
			case '\\':
				if(inCharacterClass) {
					throw new PatternSyntaxException("No masking character allowed in character class", pattern, i);
				}
				
				if(escapeNext) {
					ret.add(new OneChar(curr));
					escapeNext = false;
				} else {
					escapeNext = true;
				}
				break;
			case '[':
				if(inCharacterClass) {
					throw new PatternSyntaxException("Can not create a character class inside a character class", pattern, i);
				}
				
				if(escapeNext) {
					ret.add(new OneChar(curr));
					escapeNext = false;
				} else {
					currentClass = new CharacterClass();
					inCharacterClass = true;
				}
				break;
			case ']':
				if(!inCharacterClass) {
					throw new PatternSyntaxException("Can not close non existing character class", pattern, i);
				}
				
				if(escapeNext) {
					ret.add(new OneChar(curr));
					escapeNext = false;
				} else {
					if(currentClass.data.size() <= 0) {
						throw new PatternSyntaxException("Can not create a character class without content", pattern, i);
					}
					ret.add(currentClass);
					currentClass = null;
					inCharacterClass = false;
				}
				break;
			default:				
				if(inCharacterClass) {
					currentClass.data.add(curr);
				} else {
					if(escapeNext) {
						escapeNext = false;
					}
					ret.add(new OneChar(curr));
				}
				
				break;
			}
		}
		
		if(inCharacterClass) {
			throw new PatternSyntaxException("Could not find closing bracket for character class", pattern, pattern.length() - 1);
		}
		if(escapeNext) {
			throw new PatternSyntaxException("Pattern should not end with escape character", pattern, pattern.length() - 1);
		}
		
		return ret;
	}

	/**
	 * Diese Methode generiert aus einem Token-Pattern eine Positionstabelle.
	 * @param tokenisedPattern Das Pattern aus welchem die Tabelle generiert werden soll.
	 * @return Die aus dem Pattern generierte Tabelle.
	 */
	private static UsageTable generateUsageTable(ArrayList<Token> tokenisedPattern) {
		UsageTable ret = new UsageTable();
		
		for(int i = tokenisedPattern.size() - 1; i >= 0; i--) {
			if(tokenisedPattern.get(i) instanceof AllChars) {
				ret.others = i;
				break;
			} else if(tokenisedPattern.get(i) instanceof OneChar) {
				char data = ((OneChar)tokenisedPattern.get(i)).data;
				if(!ret.table.containsKey(data)) {
					ret.table.put(data, i);
				}
			} else if(tokenisedPattern.get(i) instanceof CharacterClass) {
				ArrayList<Character> data = ((CharacterClass)tokenisedPattern.get(i)).data;
				for(Character curr : data) {
					if(!ret.table.containsKey(curr)) {
						ret.table.put(curr, i);
					}
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * Diese Methode prueft ob ein String ab einer gegebenen Position mit einem Pattern uebereinstimmt.
	 * @param text Der String der ueberprueft werden soll.
	 * @param start Der Startindex ab welchem gesucht werden soll.
	 * @param pattern Das Pattern auf das ueberprueft werden soll.
	 * @return {@code true} wenn es sich um einen Treffer handelt, sonst {@code false}.
	 */
	private static boolean isMatch(String text, int start, ArrayList<Token> pattern) {
		boolean ret = true;
		
		for(Token curr : pattern) {
			if(!curr.matches(text.charAt(start++))) {
				ret = false;
				break;
			}
		}
		
		return ret;
	}
	
	/**
	 * Diese Klasse repraesentiert einen Teil eines regulaeren Ausdrucks als Token.
	 * 
	 * @author Maxime, Dominik, Jonas
	 * @version 1.0
	 */
	private static abstract class Token {
		/**
		 * Prueft ob dieses Token gleich einem gegebenen Zeichen ist.
		 * @param c Das Zeichen gegen das geprueft werden soll.
		 * @return {@code true} wenn das Zeichen von dem Token repraesentiert wird, sonst {@code false}.
		 */
		public abstract boolean matches(Character c);
	}
	
	/**
	 * Diese Klasse repraesentiert ein Zeichen als Token.
	 * 
	 * @author Maxime, Dominik, Jonas
	 * @version 1.0
	 */
	private static class OneChar extends Token {
		final Character data;
		
		/**
		 * Konstruktor fuer Objekte der Klasse.
		 * @param c Das Zeichen welches von diesem Token vertreten wird.
		 */
		public OneChar(Character c) {
			data = c;
		}
		
		@Override
		public boolean matches(Character c) {
			return data.equals(c);
		}
	}
	
	/**
	 * Diese Klasse repraesentiert alle Zeichen als Token.
	 * 
	 * @author Maxime, Dominik, Jonas
	 * @version 1.0
	 */
	private static class AllChars extends Token {
		
		@Override
		public boolean matches(Character c) {
			return true;
		}
	}
	
	/**
	 * Diese Klasse repraesentiert mehrere Zeichen als Token.
	 * 
	 * @author Maxime, Dominik, Jonas
	 * @version 1.0
	 */
	private static class CharacterClass extends Token {
		final ArrayList<Character> data;
		
		/**
		 * Der Konstruktor fuer Objekte der Klasse CharacterClass.
		 */
		public CharacterClass() {
			data = new ArrayList<>();
		}
		
		@Override
		public boolean matches(Character c) {
			return data.contains(c);
		}
	}
	
	/**
	 * Diese Klasse beinhaltet eine Tabelle welche die letzte Position eines Zeichens behaelt um den Boyer-Moore-Sunday umzusetzen.
	 * 
	 * @author Maxime, Dominik, Jonas
	 * @version 1.0
	 */
	private static class UsageTable {
		private int others;
		private HashMap<Character, Integer> table;
		
		/**
		 * Der Konstruktor fuer Objekte der Klasse UsageTable.
		 */
		public UsageTable() {
			others = -1;
			table = new HashMap<>();
		}
		
		/**
		 * Prueft an welcher Stelle dieses Zeichen in dem Pattern vorkommt.
		 * @param c Das Zeichen dessen Index zurueckgegeben werden soll.
		 * @return Die Position des Zeichens im Pattern.
		 */
		public int getNumber(Character c) {
			if(table.containsKey(c)) {
				return table.get(c);
			}
			return others;
		}
	}
}
