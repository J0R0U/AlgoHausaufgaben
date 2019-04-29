import java.util.ArrayList;
import java.util.Collections;

public class Heap {
	private ArrayList<Integer> data;
	
	public Heap() {
		data = new ArrayList<>();
		data.add(0);  // Dummy Element hinzufuegen
	}
	
	public boolean isEmpty() {
		return data.size() == 1;
	}
	
	public void add(int i) {
		data.add(i);
		upheap();
	}
	
	public int getMax() {
		if(isEmpty()) {
			throw new IndexOutOfBoundsException("Der Heap ist leer");
		}
		int ret = data.get(1);
		
		Collections.swap(data, 1, data.size()-1);
		data.remove(data.size()-1);
		
		downheap();
		return ret;
	}
	
	public String toString() {
		StringBuilder ret = new StringBuilder("[");
		
		for(int i = 1; i < data.size(); i++) {
			ret.append(data.get(i));
			if(i < data.size() -1) {
				ret.append(", ");
			}
		}
		
		ret.append("]");
		return ret.toString();
	}
	
	private void upheap() {
		int curr   = data.size() - 1;
		int parent = curr / 2;
		
		while(parent != 0) {
			if(data.get(curr) <= data.get(parent)) {
				break;
			}
			
			Collections.swap(data, curr, parent);
			
			curr = parent;
			parent = curr / 2;
		}
	}
	
	private void downheap() {
		int curr      = 1;
		int currChild = curr * 2;
		
		while(currChild < data.size()) {
			if(currChild + 1 < data.size() && data.get(currChild) < data.get(currChild + 1)) {
				currChild++;
			}
			if(data.get(currChild) < data.get(curr)) {
				break;
			}
			
			Collections.swap(data, curr, currChild);
			
			curr      = currChild;
			currChild = 2 * curr;
		}
	}
}
