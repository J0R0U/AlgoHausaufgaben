
public class TestHeap {
	private static final int[] TEST_NUMBERS       = {1, 6, 8, 18, 23, 5, 17, 20, 26, 21, 9};
	private static final String[] EXPECTED_OUTPUT = {
			"[]",
			"[1]",
			"[6, 1]",
			"[8, 1, 6]",
			"[18, 8, 6, 1]",
			"[23, 18, 6, 1, 8]",
			"[23, 18, 6, 1, 8, 5]",
			"[23, 18, 17, 1, 8, 5, 6]",
			"[23, 20, 17, 18, 8, 5, 6, 1]",
			"[26, 23, 17, 20, 8, 5, 6, 1, 18]",
			"[26, 23, 17, 20, 21, 5, 6, 1, 18, 8]",
			"[26, 23, 17, 20, 21, 5, 6, 1, 18, 8, 9]",
			"26",
			"[23, 21, 17, 20, 9, 5, 6, 1, 18, 8]",
			"23",
			"[21, 20, 17, 18, 9, 5, 6, 1, 8]",
			"21",
			"[20, 18, 17, 8, 9, 5, 6, 1]",
			"20",
			"[18, 9, 17, 8, 1, 5, 6]",
			"18",
			"[17, 9, 6, 8, 1, 5]",
			"17",
			"[9, 8, 6, 5, 1]",
			"9",
			"[8, 5, 6, 1]",
			"8",
			"[6, 5, 1]",
			"6",
			"[5, 1]",
			"5",
			"[1]",
			"1",
			"[]"
	};
	
	public static void main(String args[]) {
		Heap h = new Heap();
		
		checkAndOutput(h);
		
		for(int x : TEST_NUMBERS) {
			h.add(x);
			checkAndOutput(h);
		}
		
		System.out.println();
		
		while(!h.isEmpty()) {
			checkAndOutput(h.getMax());
			checkAndOutput(h);		}
	}
	
	private static int posInStrings = 0;
	private static void checkAndOutput(Object o) {
		String s = o.toString();
		if(!s.equals(EXPECTED_OUTPUT[posInStrings])) {
			throw new RuntimeException(s + "!=" + EXPECTED_OUTPUT[posInStrings]);
		}
		System.out.println(s);
		
		posInStrings++;
	}
}
