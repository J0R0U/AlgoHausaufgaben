import java.util.Arrays;
import java.util.List;

/**
 * Diese Klasse testet die Brackets Klasse.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class TestBrackets {
	/**
	 * Alle Testtexte die geprueft werden sollen.
	 */
	private static final List<String> TESTS = Arrays.asList(
		     new String[] {
		    		 "(([[]]))",
		    		 "([)]",
		    		 "([]])",
		    		 "(()))",
		    		 "(()",
		    		 "({[])}",
		    		 "",
		    		 "(asdf([[fhfh]]kh))",
		    		 "(sg[)fgsd]",
		    		 "hfsg([]sdf])",
		    		 "(ghfs()sdf))",
		    		 "((fhjfk)",
		    		 "uhi({vcbx[])hfhj}",
		    		 "yffhjdfnfzkfg",
		    		 }
		);	
	
	/**
	 * Die main-Methode welche die Tests ausfuehrt.
	 * @param args Die Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		Brackets b = new Brackets();
		
		for(String test : TESTS) {
			System.out.println(test + ": " + (b.isValid(test) ? "true" : "false"));
		}
	}
}
