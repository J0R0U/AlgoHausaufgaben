import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Diese Klasse ist ein Graph.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 */
public class Graph {
	ArrayList<Integer>[] adjacencyList;
	
	/**
	 * Das ist ein Konstruktor.
	 * @param v Die Anzahl an Knoten.
	 */
	public Graph(int v) {
		createArray(v);
	}
	
	/**
	 * Das ist auch ein Konstruktor.
	 * @param v Die Anzahl an Knoten.
	 * @param e Die Anzahl an Kanten.
	 */
	public Graph(int v, int e) {
		createArray(v);
		
		if(e < 0 || e > v*v) {
			throw new RuntimeException("Could not create a graph with less than zero or more than " + v*(v-1)
					+ " (v * v is the maximum number of edges an a graph with v vertices) edges.");
		}
		
		ArrayList<Edge<Integer, Integer>> allPossibleEdges = new ArrayList<>();
		for(int f = 1; f <= v; f++) {
			for(int t = 1; t <= v; t++) {
				allPossibleEdges.add(new Edge<Integer, Integer>(f, t));
			}
		}
		
		Collections.shuffle(allPossibleEdges);
		
		for(int i = 0; i < e; i++) {
			Edge<Integer, Integer> curr = allPossibleEdges.get(0);
			addEdge(curr.from, curr.to);
			allPossibleEdges.remove(0);
		}
		
	}
	
	/**
	 * Das ist ein weiterer Konstruktor.
	 * @param list Eine Kantenliste.
	 */
	public Graph(int[] list) {
		createArray(list[0]);
		
		for(int i = 2; i < list.length; ++i) {
			addEdge(list[i], list[++i]);
		}
	}
	
	/**
	 * Das ist jetzt aber wirklich der letzte Konstruktor.
	 * @param in Eine Kantenliste
	 * @throws IOException Wenn der Inputstream nicht gelesen werden kann.
	 */
	public Graph(InputStream in) throws IOException {
		ArrayList<Edge<Integer, Integer>> givenEdges = new ArrayList<>();
		createArray(accumulateData(in, givenEdges));
		
		for(Edge<Integer, Integer> edge : givenEdges) {
			addEdge(edge.from, edge.to);
		}
	}
	
	/**
	 * Gibt die Anzahl an Knoten zurueck.
	 * @return Die Anzahl an Knoten.
	 */
	public int getVertexCount() {
		return adjacencyList.length - 1;
	}
	
	/**
	 * Gibt die Anzahl an Kanten zurueck.
	 * @return Die Anzahl an Kanten.
	 */
	public int getEdgeCount() {
		int ret = 0;
		
		for(int i = 1; i < adjacencyList.length; ++i) {
			ret += adjacencyList[i].size();
		}
		
		return ret;
	}
	
	/**
	 * Prueft ob eine Kante zwischen zwei Knoten existiert.
	 * @param from Knoten eins.
	 * @param to Knoten zwei.
	 * @return {@code true} wenn eine Kante existiert, sonst {@code false}
	 */
	public boolean hasEdge(int from, int to) {
		if(!validVertex(from) || !validVertex(to)) {
			throw new RuntimeException("One of the given vertices does not exist.");
		}
		
		return !(Collections.binarySearch(adjacencyList[from], to) < 0);
	}
	
	/**
	 * Fuegt eine Kante zwischen zwei Knoten hinzu.
	 * @param from Knoten eins.
	 * @param to Knoten zwei.
	 */
	public void addEdge(int from, int to) {
		if(!validVertex(from) || !validVertex(to)) {
			throw new RuntimeException("One of the given vertices does not exist.");
		}
		
		int newIndex = Collections.binarySearch(adjacencyList[from], to);
		
		if(newIndex < 0) {
			newIndex +=  1;
			newIndex *= -1;
		}
		
		adjacencyList[from].add(newIndex, to);
	}
	
	/**
	 * Gibt alle Nachbarn eines Knotens zurueck.
	 * @param v Der Knoten dessen Nachbarn ermittelt werden sollen.
	 * @return Die Nachbarn des Knotens.
	 */
	public ArrayList<Integer> getAdjacent(int v) {
		if(!validVertex(v)) {
			throw new RuntimeException("The vertex " + v + " does not exists.");
		}
		
		return adjacencyList[v];
	}
	
	/**
	 * Die Tiefensuche.
	 * @param start Der Startknoten.
	 * @return Eine Liste mit allen Knoten die erreicht wurden.
	 */
	public ArrayList<Integer> dfs(int start){
		if(!validVertex(start)) {
			throw new RuntimeException("The vertex " + start + " does not exists.");
		}
		
		ArrayList<Integer> ret = new ArrayList<>();
		LinkedList<Integer> stack = new LinkedList<>();
		boolean[] visited = new boolean[adjacencyList.length];
		
		stack.push(start);
		
		while(!stack.isEmpty()) {
			Integer curr = stack.pop();

			if(!visited[curr]) {
				ret.add(curr);
				visited[curr] = true;
				
				ArrayList<Integer> neighbours = getAdjacent(curr);
	
				for(int i = neighbours.size() - 1; i >= 0; i--) {
					stack.push(neighbours.get(i));
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * Die Breitensuche.
	 * @param start Der Startknoten.
	 * @return Eine Liste mit allen Knoten die erreicht wurden.
	 */
	public ArrayList<Integer> bfs(int start){
		if(!validVertex(start)) {
			throw new RuntimeException("The vertex " + start + " does not exists.");
		}
		
		ArrayList<Integer> ret = new ArrayList<>();
		LinkedList<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[adjacencyList.length];
		
		queue.add(start);
		visited[start] = true;
		
		while(!queue.isEmpty()) {
			Integer curr = queue.poll();
			
			ret.add(curr);
			
			for(Integer neighbour : getAdjacent(curr)) {
				if(!visited[neighbour]) {
					queue.add(neighbour);
					visited[neighbour] = true;
				}
			}
		}
		
		return ret;
	}
	
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		
		ret.append("[");
		
		for(int i = 1; i < adjacencyList.length; ++i) {
			ret.append(" ");
			ret.append(adjacencyList[i]);
		}
		
		ret.append(" ]");
		
		return ret.toString();
	}
	
	/**
	 * Erstellt ein Array mit einer gegebenen Groesse.
	 * @param size Die Groesse des Arrays.
	 */
	@SuppressWarnings("unchecked")
	private void createArray(int size) {
		if(size < 0) {
			throw new RuntimeException("Could not create a graph with less than zero vertices.");
		}
		
		adjacencyList = new ArrayList[size + 1];
		for(int i = 1; i < adjacencyList.length; i++) {
			adjacencyList[i] = new ArrayList<>();
		}
	}
	
	/**
	 * Prueft ob ein Knoten mit einem Index existiert.
	 * @param v Der Index des Knotens.
	 * @return {@code true} wenn er existiert, {@code false}
	 */
	private boolean validVertex(int v) {
		return v >= 1 && v < adjacencyList.length;
	}
	
	/**
	 * List die Daten aus einem InputStream in eine Liste.
	 * @param in Der InputStream.
	 * @param edges Die Liste der Kanten.
	 * @return Die Anzahl an Knoten in der Liste.
	 * @throws IOException Wenn ein Problem mit dem Lesen des Streams existieren.
	 */
	private static int accumulateData(InputStream in, ArrayList<Edge<Integer, Integer>> edges) throws IOException {
		int nodeCount = 0;
	    String line;
	    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( in ) );
	    
	    while( (line = bufferedReader.readLine()) != null ) { 
	    	nodeCount++;
	        for(String neighbour : line.split(";")) {
	        	if(!neighbour.isEmpty()) {
	        		edges.add(new Edge<Integer, Integer>(nodeCount, Integer.parseInt(neighbour)));
	        	}
	        }
	    }
	    
	    return nodeCount;
	}
	
	/**
	 * Diese Klasse ist eine Kante.
	 * 
	 * @author Maxime, Dominik, Jonas
	 * @version V01.01B00
	 */
	private static class Edge<F,T> {
		F from;
		T to;
		
		Edge(F f, T t) {
			from = f;
			to = t;
		}
	}
}
