import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * Klasse fuer den Dijkstra Algorithmus.
 * 
 * @author Maxime, Dominik, Jonas
 * @version 1.0
 */
public class Dijkstra {
	public static void printDijkstra(int[] kanten) {
		
		DijkstraNode[] adjacencyList = toAdjacencyList(kanten, 1);
		PriorityQueue<Integer> queue = new PriorityQueue<>((Integer arg0, Integer arg1) -> 
					Integer.compare(adjacencyList[arg0].distanceToSource, adjacencyList[arg1].distanceToSource)
					);
		
		for(int i = 1; i < adjacencyList.length; ++i) {
			queue.add(adjacencyList[i].id);
		}
		
		printHeader(adjacencyList);
		
		while(!queue.isEmpty()) {
			int curr = queue.poll();
			calculateNextStep(curr, adjacencyList, queue);
			printState(curr, adjacencyList);
		}
	}
	
	private static void printHeader(DijkstraNode[] adjacencyList) {
		StringBuilder tempHeader = new StringBuilder();
		StringBuilder tempUnderscores = new StringBuilder("-----");
		
		for(int i = 1; i < adjacencyList.length; ++i) {
			if(adjacencyList[i].state != DijkstraNode.NodeState.SOURCE) {
				tempHeader.append(String.format(" %2d", adjacencyList[i].id));
				tempUnderscores.append("------");
			}
		}
		
		System.out.println("vi|" + tempHeader + "|" + tempHeader + "|");
		System.out.println(tempUnderscores);
	}
	
	private static void printState(int curr, DijkstraNode[] adjacencyList) {
		StringBuilder tempDistance = new StringBuilder();
		StringBuilder tempPreNeighbour = new StringBuilder();
		
		for(int i = 1; i < adjacencyList.length; ++i) {
			if(adjacencyList[i].state != DijkstraNode.NodeState.SOURCE) {
				if(adjacencyList[i].state == DijkstraNode.NodeState.NODE_WITH_PRE_NEIGHBOUR) {
					tempDistance.append(String.format(" %2d", adjacencyList[i].distanceToSource));
					tempPreNeighbour.append(String.format(" %2d", adjacencyList[i].preNeighbour));
				} else {
					tempDistance.append(" --");
					tempPreNeighbour.append(" --");
				}
			}
		}
		
		System.out.printf("%2d|" + tempDistance + "|" + tempPreNeighbour + "|%n", curr);
	}
	
	private static DijkstraNode[] toAdjacencyList(int[] input, int startNode) {
		DijkstraNode[] ret = new DijkstraNode[input[0] + 1];
		
		for(int i = 1; i < ret.length; ++i) {
			ret[i] = new DijkstraNode(i);
		}
		
		for(int i = 1; i < input.length; i += 3) {
			ret[input[i]].neighbours.put(input[i+1], input[i+2]);
		}
		
		ret[startNode].state = DijkstraNode.NodeState.SOURCE;
		ret[startNode].distanceToSource = 0;
		
		return ret;
	}
	
	private static void calculateNextStep(int curr, DijkstraNode[] adjacencyList, PriorityQueue<Integer> queue) {		
		for(Map.Entry<Integer,Integer> entry : adjacencyList[curr].neighbours.entrySet()) {
		  Integer toNode = entry.getKey();
		  Integer distance = entry.getValue();
		  int distaceToNode = adjacencyList[curr].distanceToSource + distance;
		  
		  if(adjacencyList[toNode].state == DijkstraNode.NodeState.NODE_WITHOUT_PRE_NEIGHBOUR
				  || adjacencyList[toNode].distanceToSource > distaceToNode) {
			  adjacencyList[toNode].preNeighbour = curr;
			  adjacencyList[toNode].distanceToSource = distaceToNode;
			  adjacencyList[toNode].state = DijkstraNode.NodeState.NODE_WITH_PRE_NEIGHBOUR;
			  
			  queue.remove(toNode);
			  queue.add(toNode);
		  }
		}
	}
	
	private static class DijkstraNode {
		private enum NodeState {
			SOURCE,
			NODE_WITHOUT_PRE_NEIGHBOUR,
			NODE_WITH_PRE_NEIGHBOUR
		}
		
		private int id;
		private TreeMap<Integer, Integer> neighbours;
		private NodeState state;
		private int preNeighbour;
		private int distanceToSource;
		
		public DijkstraNode(int id) {
			this.id = id;
			neighbours = new TreeMap<>();
			
			state = NodeState.NODE_WITHOUT_PRE_NEIGHBOUR;
			preNeighbour = 0;
			distanceToSource = Integer.MAX_VALUE;
		}
	}
}
