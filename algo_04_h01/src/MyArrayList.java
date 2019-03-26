

public class MyArrayList <T> {
	private Object[] data;
	private int head;
	
	public MyArrayList() {
		init();
	}
	
	public void addLast(T obj) {
		if(head >= data.length) {
			doubleArrayContent();
		}
		
		data[head] = obj;
		head++;
	}
	
	public void addFirst(T obj) {
		if(head >= data.length) {
			doubleArrayContent();
		}
		
		System.arraycopy(data, 0, data, 1, data.length - 1);
		
		data[0] = obj;
		head++;
	}

	@SuppressWarnings("unchecked")
	public T get(int i) {
		if(i < 0 || i >= head) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return (T) data[i];
	}
	
	public void clear() {
		init();
	}
	
	public int size() {
		return head;
	}
	
	private void doubleArrayContent() {
		Object tmp[] = new Object[data.length * 2];
		System.arraycopy(data, 0, tmp, 0, data.length);
		data = tmp;
	}
	
	private void init() {
		data = new Object[10];
		head = 0;
	}
}