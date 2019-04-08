import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GamesHashTest {
	private static final String FILE_NAME = "games20.txt";
	
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
		
		gh.add("Nioh");
		gh.add("Cuphead");
		gh.add("WOW");
	}
}
