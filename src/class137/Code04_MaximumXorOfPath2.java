package class137;

// 路径最大异或和(迭代版)
// 一共有n个点，编号1~n，由m条无向边连接
// 每条边有权值，输入保证图是连通的，可能有环
// 找到1到n的一条路径，路径可以重复经过某些点或边
// 当一条边在路径中出现了多次时，异或的时候也要算多次
// 希望找到一条从1到n的路径，所有边权异或和尽量大，返回这个最大异或和
// 1 <= n <= 50000
// 1 <= m <= 100000
// 0 <= 边权 <= 10^18
// 测试链接 : https://www.luogu.com.cn/problem/P4151
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Code04_MaximumXorOfPath2 {

	public static int MAXN = 50001;

	public static int MAXM = 200002;

	public static int BIT = 60;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static long[] weight = new long[MAXM];

	public static int cnt;

	public static long[] basis = new long[BIT + 1];

	public static boolean[] visited = new boolean[MAXN];

	public static long[] path = new long[MAXN];

	public static void prepare(int n) {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(basis, 0);
	}

	public static void addEdge(int u, int v, long w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static boolean insert(long num) {
		for (int i = BIT; i >= 0; i--)
			if (num >> i == 1) {
				if (basis[i] == 0) {
					basis[i] = num;
					return true;
				}
				num ^= basis[i];
			}
		return false;
	}

	// dfs迭代版
	// 不懂去看讲解118，递归改迭代的部分
	public static int[][] ufe = new int[MAXN][3];

	public static long[] pstack = new long[MAXN];

	public static int u, f, e;

	public static long p;

	public static int stackSize;

	public static void push(int u, int f, int e, long p) {
		ufe[stackSize][0] = u;
		ufe[stackSize][1] = f;
		ufe[stackSize][2] = e;
		pstack[stackSize] = p;
		stackSize++;
	}

	public static void pop() {
		--stackSize;
		u = ufe[stackSize][0];
		f = ufe[stackSize][1];
		e = ufe[stackSize][2];
		p = pstack[stackSize];
	}

	public static void dfs(int root) {
		stackSize = 0;
		push(root, 0, -1, 0);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				path[u] = p;
				visited[u] = true;
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e, p);
				int v = to[e];
				long xor = p ^ weight[e];
				if (visited[v]) {
					insert(xor ^ path[v]);
				} else {
					push(v, u, -1, xor);
				}
			}
		}
	}

	public static long query(long init) {
		for (int i = BIT; i >= 0; i--) {
			init = Math.max(init, init ^ basis[i]);
		}
		return init;
	}

	public static void main(String[] args) {
		Kattio io = new Kattio();
		int n = io.nextInt();
		int m = io.nextInt();
		prepare(n);
		for (int i = 1; i <= m; i++) {
			int u = io.nextInt();
			int v = io.nextInt();
			long w = io.nextLong();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		dfs(1);
		io.println(query(path[n]));
		io.flush();
		io.close();
	}

	// Kattio类IO效率很好，但还是不如StreamTokenizer
	// 只有StreamTokenizer无法正确处理时，才考虑使用这个类
	// 参考链接 : https://oi-wiki.org/lang/java-pro/
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
