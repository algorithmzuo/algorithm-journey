package class188;

// 无向图的欧拉路径，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2731
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code02_UndirectedEuler1 {

	public static class EdgeCmp implements Comparator<int[]> {
		@Override
		public int compare(int[] e1, int[] e2) {
			return e1[0] != e2[0] ? (e1[0] - e2[0]) : (e1[1] - e2[1]);
		}
	}

	public static int MAXN = 501;
	public static int MAXM = 3001;
	public static int n, m;
	public static int[][] edgeArr = new int[MAXM][3];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int[] eid = new int[MAXM];
	public static int cntg;

	public static int[] cur = new int[MAXN];
	public static int[] deg = new int[MAXN];
	public static boolean[] vis = new boolean[MAXM];

	public static int[] path = new int[MAXM];
	public static int cntp;

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[] stack = new int[MAXM];
	public static int u;
	public static int stacksize;

	public static void push(int u) {
		stack[stacksize++] = u;
	}

	public static void pop() {
		u = stack[--stacksize];
	}

	public static void addEdge(int u, int v, int id) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		eid[cntg] = id;
		head[u] = cntg;
	}

	public static void connect() {
		int mm = m << 1;
		Arrays.sort(edgeArr, 1, mm + 1, new EdgeCmp());
		for (int l = 1, r = 1; l <= mm; l = ++r) {
			while (r + 1 <= mm && edgeArr[l][0] == edgeArr[r + 1][0]) {
				r++;
			}
			for (int i = r, u, v, id; i >= l; i--) {
				u = edgeArr[i][0];
				v = edgeArr[i][1];
				id = edgeArr[i][2];
				deg[u]++;
				addEdge(u, v, id);
			}
		}
		for (int i = 1; i <= n; i++) {
			cur[i] = head[i];
		}
	}

	public static int undirectedStart() {
		int odd = 0;
		for (int i = 1; i <= n; i++) {
			if ((deg[i] & 1) == 1) {
				odd++;
			}
		}
		if (odd != 0 && odd != 2) {
			return -1;
		}
		for (int i = 1; i <= n; i++) {
			if (odd != 0 && (deg[i] & 1) == 1) {
				return i;
			}
			if (odd == 0 && deg[i] > 0) {
				return i;
			}
		}
		return -1;
	}

	// Hierholzer算法递归版
	public static void euler1(int u) {
		for (int e = cur[u]; e > 0; e = cur[u]) {
			cur[u] = nxt[e];
			if (!vis[eid[e]]) {
				vis[eid[e]] = true;
				euler1(to[e]);
			}
		}
		path[++cntp] = u;
	}

	// Hierholzer算法迭代版
	public static void euler2(int node) {
		stacksize = 0;
		push(node);
		while (stacksize > 0) {
			pop();
			int e = cur[u];
			if (e != 0) {
				cur[u] = nxt[e];
				if (!vis[eid[e]]) {
					vis[eid[e]] = true;
					push(u);
					push(to[e]);
				} else {
					push(u);
				}
			} else {
				path[++cntp] = u;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = 500;
		m = in.nextInt();
		int minNode = n;
		for (int i = 1, u, v, k = 0; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			minNode = Math.min(minNode, Math.min(u, v));
			edgeArr[++k][0] = u;
			edgeArr[k][1] = v;
			edgeArr[k][2] = i;
			edgeArr[++k][0] = v;
			edgeArr[k][1] = u;
			edgeArr[k][2] = i;
		}
		connect();
		int start = undirectedStart();
		if (start == -1) {
			start = minNode;
		}
		// euler1(start);
		euler2(start);
		for (int i = cntp; i >= 1; i--) {
			out.println(path[i]);
		}
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
	}

}
