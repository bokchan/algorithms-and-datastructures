package chapter1;

public class UF {
	private int[] id;
	private int[] sz;
	private int count;
	private int arrayAccessCount;

	public UF(int N) {
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i< N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
		count = N;
	} 

	private int root(int i) {
		while(i != id[i]) {
			i = id[i];

		}
		return i;
	}

	public boolean find(int p, int q) {
		return root(p) == root(q); 
	}

	public void union(int p, int q) {
		int i = root(p);
		int j = root(q); 
		if (sz[i] < sz[j]) {
			id[i] = j; 
			sz[i] += sz[j];
		} else {
			id[j] = i;
			sz[i] += sz[j] ;
		}
		
		count--;

	}
	int count() {
		return count;
	}
	
	public int[] getArray() {
		return id;
	}
}