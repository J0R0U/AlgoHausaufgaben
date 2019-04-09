import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Diese Klasse testet die GameHash Klasse.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class GamesHashTest {
	/**
	 * Die Datei in der die Namen der Spiele gespeichert sind.
	 */
	private static final String FILE_NAME = "games20.txt";
	
	/**
	 * Die main-Methode welche die Tests ausfuehrt.
	 * @param args Die Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		GamesHash gh = new GamesHash();
		
		try(Scanner sc = new Scanner(new File(FILE_NAME))) {
			while(sc.hasNextLine() ) {
				String line = sc.nextLine();
				gh.add(line.split("\t")[1]);
			}
		} catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println(gh.contains("Nioh"));
		System.out.println(gh.contains("Cuphead"));
		System.out.println(gh.contains("WOW"));
	}
}
