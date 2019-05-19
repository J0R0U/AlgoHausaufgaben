import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Diese Klasse implementiert den Algorithmus von Prim.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class AlgorithmOfPrim {
	/**
	 * Diese Klasse representiert einen Knoten, der genutzt wird um den Algorithmus von Prim einfacher durchfuehren zu koennen.
	 * 
	 * @author Maxime, Dominik, Jonas
	 * @version V01.01B00
	 */
	private static class PrimNode {
		int nodeID;
		int neighbourInMST;
		int distanceFromMST;
		
		/**
		 * Diese Methode prueft ob ein Objekt gleich einem andern ist.
		 * @param o Das Objekt welches mit dem aktuellen verglichen werden soll.
		 * @return {@code true} wenn o ein PrimNode ist und die IDs uebereinstimmen, sonst {@code false}
		 */
		public boolean equals(Object o) {
			return o instanceof PrimNode && ((PrimNode)o).nodeID == nodeID;
		}
	}
	
	/**
	 * Mithilfe dieser Methode wird eine PriorityQueue mit einer gewissen Groesse
	 * initialiesiert. Alle Knoten sind dabei alle soweit wie moeglich von dem MST
	 * entfernt, ausser der Startknoten dieser hat eine Entfernung -1, damit
	 * dieser oben auf dem Heap landet.
	 * @param size Die Groesse 
	 * @param start Der Startknoten welcher die Wurzel des MST bildet.
	 * @return Eine PriorityQueue welche initialiesiert wurde um den Algorithmus von Prim durchzufuehren.
	 */
	private static PriorityQueue<PrimNode> initQueue(int size, int start) {
		PriorityQueue<PrimNode> queue = new PriorityQueue<PrimNode>(new Comparator<PrimNode>() {
			public int compare(PrimNode o1, PrimNode o2) {
				return Integer.compare(o1.distanceFromMST, o2.distanceFromMST);
			}
		});
		
		for(int i = 0; i < size; i++) {
			PrimNode newNode = new PrimNode();

			newNode.nodeID = i;
			newNode.neighbourInMST = -1;
			
			if(i == start) {
				newNode.distanceFromMST = -1;
			} else {
				newNode.distanceFromMST = Integer.MAX_VALUE;
			}
			
			queue.add(newNode);
		}
		
		return queue;
	}
	
	/**
	 * Diese Methode ermittelt einen MST aus einer gebenen Adjazenzmatrix mit dem Startknoten an Index 0.
	 * @param edges Die Adjazenzmatrix welche die Kanten des Graphen enthaelt.
	 * @return Die Kosten des MST.
	 */
	private static int getMST(int[][] edges) {		
		PriorityQueue<PrimNode> queue = initQueue(edges.length, 0);
		int ret = 0;
		
		System.out.println("Waehle 1 als Wurzel");
		
		while(!queue.isEmpty()) {
			PrimNode nextNode = queue.poll();
			if(nextNode.neighbourInMST != -1) {
				ret += nextNode.distanceFromMST;
				System.out.println("Kante hinzugefuegt von " + (nextNode.neighbourInMST + 1) + " zu " + (nextNode.nodeID + 1));
			}
			
			for(int i = 0; i < edges.length; i++) {
				if(edges[nextNode.nodeID][i] != 0) {
					for(PrimNode primNode : queue) {
						if(primNode.nodeID == i && primNode.distanceFromMST > edges[nextNode.nodeID][i]) {
							primNode.neighbourInMST = nextNode.nodeID;
							primNode.distanceFromMST = edges[nextNode.nodeID][i];
							
							queue.remove(primNode);
							queue.add(primNode);
							
							break;
						}
					}
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * Die main-Methode welche die Tests ausfuehrt.
	 * @param args Die Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		int[][] adjazenzmatrix = { { 0, 3, 0, 2, 0, 0 },
								   { 3, 0, 2, 0, 3, 0 },
								   { 0, 2, 0, 1, 6, 0 },
								   { 2, 0, 1, 0, 0, 0 },
								   { 0, 3, 6, 0, 0, 5 },
								   { 0, 0, 0, 0, 5, 0 } };
		
		System.out.println("Resultierende kosten: " + getMST(adjazenzmatrix));
	}
}
