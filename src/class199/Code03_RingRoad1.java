package class199;

// 城市环路，并查集找环，java版
// 测试链接 : https://www.luogu.com.cn/problem/P1453
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_RingRoad1 {

	public static int MAXN = 100001;
	public static int n;
	public static double k;
	public static int[] arr = new int[MAXN];

	public static int[] fa = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[][] dp = new int[MAXN][2];
	public static int root1, root2;

	// 并查集find改成迭代版需要的栈，递归版java会爆栈，C++不会
	public static int[] sta = new int[MAXN];

	// 树上动态规划改成迭代版需要的栈，讲解118，详解了改法，递归版java会爆栈，C++不会
	public static int[][] ufe = new int[MAXN][3];
	public static int stacksize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stacksize][0] = u;
		ufe[stacksize][1] = f;
		ufe[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = ufe[stacksize][0];
		f = ufe[stacksize][1];
		e = ufe[stacksize][2];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 并查集find方法，迭代版
	public static int find(int i) {
		int si = 0;
		while (i != fa[i]) {
			sta[++si] = i;
			i = fa[i];
		}
		sta[si + 1] = i;
		for (int j = si; j >= 1; j--) {
			fa[sta[j]] = i;
		}
		return i;
	}

	public static void union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (fx != fy) {
			fa[fx] = fy;
		}
	}

	// 树上动态规划，递归版
	public static void dpOnTree1(int u, int fa) {
		dp[u][0] = 0;
		dp[u][1] = arr[u];
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dpOnTree1(v, u);
				dp[u][0] += Math.max(dp[v][0], dp[v][1]);
				dp[u][1] += dp[v][0];
			}
		}
	}

	// 树上动态规划，迭代版
	public static void dpOnTree2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dp[u][0] = 0;
				dp[u][1] = arr[u];
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, e);
				int v = to[e];
				if (v != f) {
					push(v, u, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f) {
						dp[u][0] += Math.max(dp[v][0], dp[v][1]);
						dp[u][1] += dp[v][0];
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			fa[i] = i;
		}
		for (int i = 1, u, v; i <= n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			u++;
			v++;
			if (find(u) == find(v)) {
				root1 = u;
				root2 = v;
			} else {
				union(u, v);
				addEdge(u, v);
				addEdge(v, u);
			}
		}
		k = in.nextDouble();
		// dpOnTree1(root1, 0);
		dpOnTree2(root1, 0);
		int ans = dp[root1][0];
		// dpOnTree1(root2, 0);
		dpOnTree2(root2, 0);
		ans = Math.max(ans, dp[root2][0]);
		out.printf("%.1f\n", k * ans);
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {

		private final byte[] buffer = new byte[1 << 16];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buffer);
				ptr = 0;
				if (len <= 0)
					return -1;
			}
			return buffer[ptr++];
		}

		int nextInt() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			int val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

		double nextDouble() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			long intPart = 0;
			while (c > ' ' && c != -1 && c != '.') {
				intPart = intPart * 10 + (c - '0');
				c = readByte();
			}
			double val = (double) intPart;
			if (c == '.') {
				c = readByte();
				double base = 0.1;
				while (c > ' ' && c != -1) {
					val += (c - '0') * base;
					base *= 0.1;
					c = readByte();
				}
			}
			return neg ? -val : val;
		}

	}

}
