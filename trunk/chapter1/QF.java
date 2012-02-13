package chapter1;

public class QF {
	private int count = 0;
	private int[] id;
	
	public QF(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
	}
	
	public boolean find(int p, int q) {
		count+=2;
		return id[p] == id[q];
	}
	
	public void union(int p, int q) {
		int pid = id[p];
		count++;
		for (int i=0; i < id.length; i++) {
			if (id[i] == pid)  {
				id[i] = id[q];
				count+=2;
			}
			count++;
		}
	}
	
	public int getArrayAccessCount() {
		int tmp = count;
		count = 0;
		return tmp;
	}
	
	public int[] getId()  {
		return id; 
	}
}
