
public class GamesHash {
	private static final int SIZE = 31;
	private String data[];
	
	GamesHash() {
		data = new String[SIZE];
	}
	
	private int hashIndex(String game) {
		return Math.abs(game.hashCode() - (game.hashCode() / 100) * 100);
	}
	
	private int hashIncrement(String game) {
		return Math.abs(game.hashCode() / 100 - (game.hashCode() / 1000) * 10) + 1;
	}
	
	public void add(String game) {
		int index = (hashIndex(game) + hashIncrement(game)) % data.length; // TODO keine Ahnung wie das geht
		
		if(data[index] != null && !data[index].equals(game)) {
			throw new HashCollisionException("Hash collision between %s and %s detected.", data[index], game);
		}
		
		data[index] = game;
	}
	
	public boolean contains(String game) {
		int index = (hashIndex(game) + hashIncrement(game)) % data.length; // TODO muss hier entsprechend angepasst werden
		return data[index] != null && !data[index].equals(game);
	}
	
	class HashCollisionException extends RuntimeException {
		private static final long serialVersionUID = 6287500641748714345L;

		HashCollisionException(String format, Object...args) {
			super(String.format(format, args));
		}
	}
}
