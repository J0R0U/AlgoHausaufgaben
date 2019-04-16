/**
 * 
 *
 */
public class BTreeTest {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BTree btree = new BTree();
		int [] toInsert = {5,15,10,20,25,30,3,4,29,28,2,21,26,22,8,12,13};
		
		final String solution = "00002000011001002";
		String statusOfInsertion = "";
		
		for(int x : toInsert){
			statusOfInsertion += btree.insert(x);
		}
		
		System.out.println("Das Einfuegen in den Baum hat richtig funktioniert: " + solution.equals(statusOfInsertion));
		System.out.println(btree.search(26)); //true
		System.out.println(btree.search(100)); //false
	}
}
