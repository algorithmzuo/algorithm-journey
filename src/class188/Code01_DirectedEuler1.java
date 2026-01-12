package class188;

// 有向图的欧拉路径，java版
// 图中有n个点，m条有向边，每条边给出两个端点
// 如果存在欧拉路径，输出字典序最小的结果，如果不存在打印No
// 1 <= n <= 10^5
// 1 <= m <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P7771
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Code01_DirectedEuler1 {

	public static class EdgeCmp implements Comparator<int[]> {
		@Override
		public int compare(int[] e1, int[] e2) {
			return e1[0] != e2[0] ? (e1[0] - e2[0]) : (e1[1] - e2[1]);
		}
	}

	public static int MAXN = 100001;
	public static int MAXM = 200002;
	public static int n, m;
	public static int[][] edgeArr = new int[MAXM][2];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int cntg;

	public static int[] cur = new int[MAXN];
	public static int[] outDeg = new int[MAXN];
	public static int[] inDeg = new int[MAXN];

	public static int[] path = new int[MAXM];
	public static int cntp;

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void connect() {
		Arrays.sort(edgeArr, 1, m + 1, new EdgeCmp());
		for (int l = 1, r = 1; l <= m; l = ++r) {
			while (r + 1 <= m && edgeArr[l][0] == edgeArr[r + 1][0]) {
				r++;
			}
			for (int i = r, u, v; i >= l; i--) {
				u = edgeArr[i][0];
				v = edgeArr[i][1];
				outDeg[u]++;
				inDeg[v]++;
				addEdge(u, v);
			}
		}
		for (int i = 1; i <= n; i++) {
			cur[i] = head[i];
		}
	}

	public static int directedStart() {
		int start = -1, end = -1;
		for (int i = 1; i <= n; i++) {
			int v = outDeg[i] - inDeg[i];
			if (v < -1 || v > 1 || (v == 1 && start != -1) || (v == -1 && end != -1)) {
				return -1;
			}
			if (v == 1) {
				start = i;
			}
			if (v == -1) {
				end = i;
			}
		}
		if ((start == -1) ^ (end == -1)) {
			return -1;
		}
		if (start != -1) {
			return start;
		}
		for (int i = 1; i <= n; i++) {
			if (outDeg[i] > 0) {
				return i;
			}
		}
		return -1;
	}

	// Hierholzer算法递归版，java会爆栈，C++可以通过
	public static void euler1(int u) {
		for (int e = cur[u]; e > 0; e = cur[u]) {
			cur[u] = nxt[e];
			euler1(to[e]);
		}
		path[++cntp] = u;
	}

	// Hierholzer算法迭代版
	public static int[] sta = new int[MAXM];
	public static int top;

	public static void euler2(int node) {
		top = 0;
		sta[++top] = node;
		while (top > 0) {
			int u = sta[top--];
			int e = cur[u];
			if (e != 0) {
				cur[u] = nxt[e];
				sta[++top] = u;
				sta[++top] = to[e];
			} else {
				path[++cntp] = u;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			edgeArr[i][0] = in.nextInt();
			edgeArr[i][1] = in.nextInt();
		}
		connect();
		int start = directedStart();
		if (start == -1) {
			out.println("No");
		} else {
			// euler1(start);
			euler2(start);
			if (cntp != m + 1) {
				out.println("No");
			} else {
				for (int i = cntp; i >= 1; i--) {
					out.print(path[i] + " ");
				}
				out.println();
			}
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
