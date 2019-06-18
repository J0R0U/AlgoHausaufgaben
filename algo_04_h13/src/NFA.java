import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.PatternSyntaxException;

/**
 * Die NFA Klasse implementiert Methoden zur loesung des gegebenen Problems.
 * 
 * @author Maxime, Dominik, Jonas
 * @version 1.0
 */
public class NFA {
	// Der Knoten im Graph an welchem gestartet werden soll.
	private static final int START_NODE = 1;
	
	private HashMap<Integer,HashMap<Character,HashSet<Integer>>> adjacencyList;
	private int targetNode;
	
	/**
	 * Der Konstruktor fuer einen NFA.
	 * @param pGraph Der Graph des NFA
	 */
	public NFA(String pGraph) {
		String[] tokens = pGraph.split(",");
		int expectedSize;
		
		adjacencyList = new HashMap<>();
		
		try {
			expectedSize = Integer.parseInt(tokens[0]);
		} catch(NumberFormatException e) {
			throw new PatternSyntaxException(e.getMessage(), pGraph, 0);
		}
		
		try {
			targetNode = Integer.parseInt(tokens[1]);
		} catch(NumberFormatException e) {
			throw new PatternSyntaxException(e.getMessage(), pGraph, 2);
		}
		
		for(int i = 2; i < tokens.length - 2; i += 3) {
			int  from;
			int  to;
			char val;
			
			try {
				from = Integer.parseInt(tokens[i]);
			} catch(NumberFormatException e) {
				throw new PatternSyntaxException(e.getMessage(), pGraph, 2 * i);
			}
			
			try {
				to = Integer.parseInt(tokens[i + 1]);
			} catch(NumberFormatException e) {
				throw new PatternSyntaxException(e.getMessage(), pGraph, 2 * i + 2);
			}
			
			if(tokens[i + 2].length() != 1) {
				throw new PatternSyntaxException("Expected one character for transition", pGraph, 2 * i + 4);
			}
			val = tokens[i + 2].charAt(0);
			
			addNode(from);
			addNode(to);
			addEdge(from, to, val);
		}
		
		if(expectedSize != adjacencyList.size()) {
			throw new InvalidParameterException("The given node count does not match the nodecount acctally needed.");
		}
		if(!adjacencyList.containsKey(START_NODE)) {
			throw new InvalidParameterException("Could not detect a start node.");
		}
		if(!adjacencyList.containsKey(targetNode)) {
			throw new InvalidParameterException("Given target node not detected inside the graph.");
		}
	}
	
	/**
	 * Die Methode prueft ob ein Text diesem NFA entspricht.
	 * @param pString Der Text der geprueft werden soll.
	 * @return {@code true} wenn der String dem NFA entspricht, sonst {@code false}
	 */
	public boolean testString(String pString) {
		boolean[] currentlyOccupied = new boolean[adjacencyList.size()];
		HashMap<Integer, Integer> mapToIndex = new HashMap<>();
		HashMap<Integer, Integer> indexToMap = new HashMap<>();
		
		int x = 0;
		for(int val : adjacencyList.keySet()) {
			mapToIndex.put(val, x);
			indexToMap.put(x, val);
			x++;
		}
		
		currentlyOccupied[mapToIndex.get(START_NODE)] = true;
		
		
		for(char curr : pString.toCharArray()) {
			currentlyOccupied = markNextPossibilities(curr, currentlyOccupied, mapToIndex, indexToMap);
			
			boolean finished = true;
			for(boolean tmp : currentlyOccupied) {
				if(tmp) {
					finished = false;
					break;
				}
			}
			if(finished) {
				break;
			}
		}
		
		return currentlyOccupied[mapToIndex.get(targetNode)];
	}
	
	/**
	 * Erstellt eine Tabelle mit den belegten Feldern aus einer gegebenen Verteilung.
	 * @param pCurr Das aktuell eingelesene Zeichen.
	 * @param pCurrOccupied Die aktuelle Verteilung der moeglichen Zustaende.
	 * @param pMapToIndex Der Name eines Knotens in dem Feld.
	 * @param pIndexToMap Der Feldindex als Name eines Knotens.
	 * @return Das neu errechnete Feld.
	 */
	private boolean[] markNextPossibilities(char pCurr, boolean[] pCurrOccupied, HashMap<Integer, Integer> pMapToIndex, HashMap<Integer, Integer> pIndexToMap) {
		boolean[] nextOccupied = new boolean[adjacencyList.size()];
		
		for(int i = 0; i < pCurrOccupied.length; ++i) {
			if(pCurrOccupied[i]) {
				HashSet<Integer> neighbours = adjacencyList.get(pIndexToMap.get(i)).get(pCurr);
				if(neighbours != null) {
					for(int nextMarked : neighbours) {
						nextOccupied[pMapToIndex.get(nextMarked)] = true;
					}
				}
			}
		}
		
		return nextOccupied;
	}
	
	/**
	 * Dem Graphen soll ein Knoten hinzugefuegt werden.
	 * @param pName Der Name des Knotens.
	 */
	private void addNode(int pName) {
		if(!adjacencyList.containsKey(pName)) {
			adjacencyList.put(pName, new HashMap<>());
		}
	}
	
	/**
	 * Dem Graphen soll eine kante hinzugefuegt werden.
	 * @param pFrom Der Name des Knotens von dem eine Kante ausgeht.
	 * @param pTo Der Name des Knotens zu dem eine Kante hinfuehrt.
	 * @param pVal Der Wert der Kante.
	 */
	private void addEdge(int pFrom, int pTo, char pVal) {
		if(adjacencyList.containsKey(pFrom) && adjacencyList.containsKey(pTo)) {
			if(!adjacencyList.get(pFrom).containsKey(pVal)) {
				adjacencyList.get(pFrom).put(pVal, new HashSet<>());
			}
			adjacencyList.get(pFrom).get(pVal).add(pTo);
		}
	}
}
