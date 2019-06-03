import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.PatternSyntaxException;

public class TextSearch {
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
	
	private static abstract class Token {
		public abstract boolean matches(Character c);
	}
	
	private static class OneChar extends Token {
		final Character data;
		
		public OneChar(Character c) {
			data = c;
		}
		
		public boolean matches(Character c) {
			return data.equals(c);
		}
	}
	
	private static class AllChars extends Token {
		public boolean matches(Character c) {
			return true;
		}
	}
	
	private static class CharacterClass extends Token {
		final ArrayList<Character> data;
		
		public CharacterClass() {
			data = new ArrayList<>();
		}
		
		public boolean matches(Character c) {
			return data.contains(c);
		}
	}
	
	private static class UsageTable {
		private int others;
		private HashMap<Character, Integer> table;
		
		public UsageTable() {
			others = -1;
			table = new HashMap<>();
		}
		
		public int getNumber(Character c) {
			if(table.containsKey(c)) {
				return table.get(c);
			}
			return others;
		}
	}
}
