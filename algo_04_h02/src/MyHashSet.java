import java.util.ArrayList;

/**
 * Diese Klasse ist eine einfache Implementation eines HashSets.
 * 
 * @author Maxime, Dominik, Jonas
 * @version V01.01B00
 * @param <K> Der generische Typ welcher von dem HashSet verwaltet werden
 *        soll.
 */
public class MyHashSet<K> {
	private ArrayList<K>[] data;
	private int elements;
	private int lists;

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public MyHashSet() {
		data = (ArrayList<K>[]) new ArrayList[10];
		elements = 0;
		lists = 0;
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public boolean add(K element) {
		if(addElement(element, data)) {
			return true;
		}

		if(elements * 1. / lists > 2) {
			@SuppressWarnings("unchecked")
			ArrayList<K>[] temp = (ArrayList<K>[]) new ArrayList[data.length * 2];

			lists = 0;
			elements = 0;

			for(K i : getElements()) {
				addElement(i, temp);
			}

			data = temp;
		}

		return false;
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public boolean delete(K element) {
		int pos = hash(element, data.length);

		if(data[pos] != null && data[pos].contains(element)) {
			data[pos].remove(element);
			elements--;
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public boolean contains(K element) {
		int pos = hash(element, data.length);

		if(data[pos] != null && data[pos].contains(element)) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<K> getElements() {
		ArrayList<K> ret = new ArrayList<>();

		for(int i = 0; i < data.length; i++) {
			for(K element : data[i]) {
				ret.add(element);
			}
		}

		return ret;
	}

	/**
	 * 
	 * @param element
	 * @param hashMap
	 * @return
	 */
	private boolean addElement(K element, ArrayList<K>[] hashMap) {
		int pos = hash(element, hashMap.length);

		if(hashMap[pos] == null) {
			hashMap[pos] = new ArrayList<>();
			lists++;
		}

		if(hashMap[pos].contains(element)) {
			return true;
		}

		hashMap[pos].add(element);
		elements++;
		return false;
	}

	/**
	 * 
	 * @param element
	 * @param size
	 * @return
	 */
	private int hash(K element, int size) {
		return Math.abs(element.hashCode() % size);
	}
}
