import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 *
 */
public class BTree {
	/**
	 * 
	 */
	static final int THRESHHOLD = 2;

	/**
	 * 
	 */
	BTreeNode root;

	/**
	 * 
	 */
	public BTree() {
		root = null;
	}

	/**
	 * 
	 * @param x
	 * @return
	 */
	public boolean search(int x) {		
		if(root == null) {
			return false;
		}

		return root.search(x);
	}

	/**
	 * 
	 * @param x
	 * @return
	 */
	public int insert(int x) {
		if(root == null) {
			root = new BTreeNode();
		}

		int ret = root.insert(x);
		
		return ret;
	}
	
	/**
	 * 
	 *
	 */
	class BTreeNode {
		private ArrayList<Integer> values;
		private ArrayList<BTreeNode> children;
		private BTreeNode parent;

		/**
		 * 
		 */
		public BTreeNode() {
			values   = new ArrayList<>();
			children = new ArrayList<>();
			parent   = null;
		}
		
		/**
		 * 
		 * @param x
		 * @return
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
		 * 
		 * @param x
		 * @return
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
		 * 
		 * @param currentModifycationStatus
		 * @return
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
		 * 
		 * @return
		 */
		private int getMiddleElement() {
			return values.get(THRESHHOLD);
		}
		
		/**
		 * 
		 * @param tree
		 * @param start
		 * @param end
		 */
		private void fillTreeValues(BTreeNode tree, int start, int end) {
			for(int i = start; i < end; i++) {
				tree.values.add(values.get(i));
			}
		}
		
		/**
		 * 
		 * @param tree
		 * @param start
		 * @param end
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
		 * 
		 * @param middleElement
		 * @param left
		 * @param right
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
		 * 
		 * @param middleElement
		 * @param left
		 * @param right
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
		 * 
		 */
		private void invalidate() {
			values   = null;
			children = null;
			parent   = null;
		}		
	}
}
