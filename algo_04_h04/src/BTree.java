import java.util.ArrayList;
import java.util.Collections;

/**
 * Die Klasse repraesentiert einen B-Baum.
 *
 */
public class BTree {
	/**
	 * Die Ordnung des Baums.
	 */
	static final int THRESHHOLD = 2;

	/**
	 * Die Wurzel des Baums.
	 */
	BTreeNode root;

	/**
	 * Der Konstruktor fuer Objekte der Klasse BTree.
	 */
	public BTree() {
		root = null;
	}

	/**
	 * Die Methode sucht einen Wert in dem Baum.
	 * @param x Der Wert nach dem gesucht werden soll.
	 * @return {@code true}, wenn der Inhalt in dem Baum ist, sonst {@code false}.
	 */
	public boolean search(int x) {		
		if(root == null) {
			return false;
		}

		return root.search(x);
	}

	/**
	 * Die Methode fuegt dem Baum einen Wert hinzu.
	 * @param x Der Wert der hinzugefuegt werden soll.
	 * @return {@code 0}, wenn der Inhalt einfach eingefuegt werden konnte,
	 *         {@code 1} wenn eine neuer Knoten erstellt werden musste und
	 *         {@code 2} wenn eine neue Wurzel erzeugt wurde.
	 */
	public int insert(int x) {
		if(root == null) {
			root = new BTreeNode();
		}

		int ret = root.insert(x);
		
		return ret;
	}
	
	/**
	 * Diese Klasse stellt einen Knoten in einem B-Baum dar.
	 */
	private class BTreeNode {
		/**
		 * Die Werte die von diesem Knoten verwaltet werden.
		 */
		private ArrayList<Integer> values;
		/**
		 * Die Kinderelemente dieses Knotens.
		 */
		private ArrayList<BTreeNode> children;
		/**
		 * Der Elternknoten dieses Knotens.
		 */
		private BTreeNode parent;

		/**
		 * Der Konstruktor für Objekte der Klasse BTreeNode.
		 */
		public BTreeNode() {
			values   = new ArrayList<>();
			children = new ArrayList<>();
			parent   = null;
		}
		
		/**
		 * Diese Methode sucht nach einem Wert. Wenn der Wert nich in den eigenen Werten
		 * gefunden wird rekursiv die Methode für das entsprechende Kind aufgerufen.
		 * @param x Der Wert nach dem gesucht werden soll.
		 * @return {@code true} Wenn der Wert in diesem oder in einem der Unterknoten existiert, sonst {@code false}
		 */
		public boolean search(int x) {
			int index = Collections.binarySearch(values, x);
			if( index >= 0) {
				return true;
			} else {
				index *= -1;
				index -= 1;
				try {
					return children.get(index).search(x);
				} catch (IndexOutOfBoundsException e) {
					return false;
				}
			}
		}

		/**
		 * Diese Methode fuegt dem Baum einen Inhalt rekursiv hinzu.
		 * @param x Der Wert der Hinzugefuegt werden soll.
		 * @return {@code 0}, wenn der Inhalt einfach eingefuegt werden konnte,
		 *         {@code 1}, wenn eine neuer Knoten erstellt werden musste und
		 *         {@code 2}, wenn eine neue Wurzel erzeugt wurde.
		 */
		private int insert(int x) {
			int index = Collections.binarySearch(values, x);
			if( index >= 0) {
				throw new ArithmeticException("Element existiert bereits");
			}
			
			index = index * (-1) - 1;
			
			if(children.size() == 0) {
				values.add(index, x);
				return modifyIfNeeded(0);
			} else {
				int currentModifycationStatus = children.get(index).insert(x);
				return modifyIfNeeded(currentModifycationStatus);
			}
		}
		
		/**
		 * Diese Methode prueft ob der Knoten geaendert werden muss, also mehr
		 * als 2n Werte hat, und aufgeteilt werden muss.
		 * @param currentModifycationStatus 
		 * @return {@code 0}, wenn der Inhalt einfach eingefuegt werden konnte,
		 *         {@code 1}, wenn eine neuer Knoten erstellt werden musste und
		 *         {@code 2}, wenn eine neue Wurzel erzeugt wurde. 
		 */
		private int modifyIfNeeded(int currentModifycationStatus) {
			if(values.size() > 2 * THRESHHOLD) {
				BTreeNode leftTree  = new BTreeNode();
				BTreeNode rightTree = new BTreeNode();
				int middleElement   = getMiddleElement();
				int ret;
				
				fillTreeValues  (leftTree, 0, THRESHHOLD    );
				fillTreeChildren(leftTree, 0, THRESHHOLD + 1);
				
				fillTreeValues  (rightTree, THRESHHOLD + 1, 2 * THRESHHOLD + 1);
				fillTreeChildren(rightTree, THRESHHOLD + 1, 2 * THRESHHOLD + 2);
				
				if(parent == null) {
					createNewParent(middleElement, leftTree, rightTree);
					ret = 2;
				} else {
					addToParent(middleElement, leftTree, rightTree);
					ret = 1;
				}
				
				invalidate();				
				return ret;
			} else {
				return currentModifycationStatus;				
			}
		}
		
		/**
		 * Diese Methode gibt das mittlere Element eines ueberbesetzten Knotens zurueck.
		 * @return Das mitllere Element.
		 */
		private int getMiddleElement() {
			return values.get(THRESHHOLD);
		}
		
		/**
		 * Diese Methode kopiert x Werte aus den Werten dieses Knotens in einen anderen uebergebenen Knoten.
		 * @param tree Der Knoten dem Hinzugefügt werden soll.
		 * @param start Der Startpunkt ab dem hinzugefuegt werden soll.
		 * @param end Der Endpunkt ab dem hinzugefuegt werden soll.
		 */
		private void fillTreeValues(BTreeNode tree, int start, int end) {
			for(int i = start; i < end; i++) {
				tree.values.add(values.get(i));
			}
		}
		
		/**
		 * Diese Methode kopiert x Kinder aus den Kindern dieses Knotens in einen anderen uebergebenen
		 * Knoten und setzt deren Parent auf den neuen Knoten.
		 * @param tree Der Knoten dem Hinzugefügt werden soll.
		 * @param start Der Startpunkt ab dem hinzugefuegt werden soll.
		 * @param end Der Endpunkt ab dem hinzugefuegt werden soll.
		 */
		private void fillTreeChildren(BTreeNode tree, int start, int end) {
			if(!children.isEmpty()) {
				for(int i = start; i < end; i++) {
					children.get(i).parent = tree;
					tree.children.add(children.get(i));
				}
			}
		}
		
		/**
		 * Diese Methode wird bei einem Knoten aufgerufen der geteilt werden soll und die Wurzel ist.
		 * @param middleElement Das Element welches in der neuen Wurzel stehen soll.
		 * @param left Der neue linke Teilbaum.
		 * @param right Der neue rechte Teilbaum.
		 */
		private void createNewParent(int middleElement, BTreeNode left, BTreeNode right) {
			root = new BTreeNode();
			
			left.parent = root;
			right.parent = root;
			
			root.values.add(middleElement);
			
			root.children.add(left);
			root.children.add(right);
		}
		
		/**
		 * Diese Methode wird bei einem Knoten aufgerufen der geteilt werden soll einen Elternknoten besitzt.
		 * @param middleElement Das Element welches welches dem Elternknoten hinzugefuegt werden soll.
		 * @param left Der neue linke Teilbaum.
		 * @param right Der neue rechte Teilbaum.
		 */
		private void addToParent(int middleElement, BTreeNode left, BTreeNode right) {
			parent.children.remove(this);
			
			left.parent = parent;
			right.parent = parent;
			
			int indexForParent = Collections.binarySearch(parent.values, middleElement) * (-1) - 1;
			
			parent.values.add(indexForParent, middleElement);
			
			parent.children.add(indexForParent, right);
			parent.children.add(indexForParent, left);
		}
		
		/**
		 * Diese Methode setzt alle Refernezen des Knotens auf null, sodass ein leerer Knoten entsteht.
		 */
		private void invalidate() {
			values   = null;
			children = null;
			parent   = null;
		}		
	}
}
