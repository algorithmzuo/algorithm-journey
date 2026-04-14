package class196;

// 约束条件，java版
// 你需要构造长度为n的数组arr，每个元素的值在1~k之间，数组是非递减的
// 接下来给定m个约束条件，构造方案要满足所有约束条件，约束的格式如下
// 约束 1 i x   : arr[i] != x
// 约束 2 i j x : arr[i] + arr[j] <= x
// 约束 3 i j x : arr[i] + arr[j] >= x
// 如果不存在构造方案打印-1，存在方案就打印arr，任何一种方案都可以
// n、m <= 2 * 10^4
// 2 <= k <= 10
// 测试链接 : https://www.luogu.com.cn/problem/CF1697F
// 测试链接 : https://codeforces.com/problemset/problem/1697/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_Constraints1 {

	public static int MAXN = 20001;
	public static int MAXM = 2000001;
	public static int MAXK = 12;
	public static int MAXT = MAXN * MAXK * 2;
	public static int t, n, m, k;

	public static int[][][] id = new int[MAXN][MAXK][2];
	public static int cntt;

	public static int[] head = new int[MAXT];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXT];
	public static int[] low = new int[MAXT];
	public static int cntd;

	public static int[] sta = new int[MAXT];
	public static int top;

	public static int[] belong = new int[MAXT];
	public static int sccCnt;

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void tarjan(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
		if (dfn[u] == low[u]) {
			sccCnt++;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
			} while (pop != u);
		}
	}

	public static void prepare() {
		cntt = 0;
		for (int i = 1; i <= n; i++) {
			for (int v = 1; v <= k + 1; v++) {
				id[i][v][0] = ++cntt;
				id[i][v][1] = ++cntt;
			}
		}
		cntg = cntd = sccCnt = 0;
		for (int i = 1; i <= cntt; i++) {
			head[i] = dfn[i] = low[i] = belong[i] = 0;
		}
		for (int i = 1; i <= n; i++) {
			for (int v = 1; v <= k; v++) {
				addEdge(id[i][v][0], id[i][v + 1][0]);
				addEdge(id[i][v + 1][1], id[i][v][1]);
			}
		}
		for (int i = 1; i < n; i++) {
			for (int v = 1; v <= k; v++) {
				addEdge(id[i + 1][v][0], id[i][v][0]);
				addEdge(id[i][v][1], id[i + 1][v][1]);
			}
		}
		// 规避错误假设
		// 强连通分量解决2-SAT问题的特性，永远选择拓扑序靠后的假设
		// (值 < 1) -> (值 >= 1)，推出矛盾固然不可行，如果无矛盾，会以后者为准
		// (值 >= k+1) -> (值 < k+1)，推出矛盾固然不可行，如果无矛盾，会以后者为准
		for (int i = 1; i <= n; i++) {
			addEdge(id[i][1][0], id[i][1][1]);
			addEdge(id[i][k + 1][1], id[i][k + 1][0]);
		}
	}

	public static void notEqual(int i, int v) {
		addEdge(id[i][v][1], id[i][v + 1][1]);
		addEdge(id[i][v + 1][0], id[i][v][0]);
	}

	public static void lessEqual(int i, int j, int v) {
		for (int w = 1; w <= k; w++) {
			if (w >= v) {
				notEqual(i, w);
			} else {
				if (v - w + 1 <= k) {
					addEdge(id[i][w][1], id[j][v - w + 1][0]);
					addEdge(id[j][v - w + 1][1], id[i][w][0]);
				}
			}
		}
	}

	public static void moreEqual(int i, int j, int v) {
		for (int w = 1; w <= k; w++) {
			if (w + k < v) {
				notEqual(i, w);
			} else {
				if (v - w > 1) {
					addEdge(id[i][w + 1][0], id[j][v - w][1]);
					addEdge(id[j][v - w][0], id[i][w + 1][1]);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int c = 1; c <= t; c++) {
			n = in.nextInt();
			m = in.nextInt();
			k = in.nextInt();
			prepare();
			for (int i = 1, op, x, y, v; i <= m; i++) {
				op = in.nextInt();
				if (op == 1) {
					x = in.nextInt();
					v = in.nextInt();
					notEqual(x, v);
				} else if (op == 2) {
					x = in.nextInt();
					y = in.nextInt();
					v = in.nextInt();
					lessEqual(x, y, v);
					int tmp = x;
					x = y;
					y = tmp;
					lessEqual(x, y, v);
				} else {
					x = in.nextInt();
					y = in.nextInt();
					v = in.nextInt();
					moreEqual(x, y, v);
					int tmp = x;
					x = y;
					y = tmp;
					moreEqual(x, y, v);
				}
			}
			for (int i = 1; i <= cntt; i++) {
				if (dfn[i] == 0) {
					tarjan(i);
				}
			}
			boolean check = true;
			for (int i = 1; i <= n; i++) {
				for (int v = 1; v <= k + 1; v++) {
					if (belong[id[i][v][0]] == belong[id[i][v][1]]) {
						check = false;
						break;
					}
				}
				if (!check) {
					break;
				}
			}
			if (check) {
				for (int i = 1; i <= n; i++) {
					for (int v = k; v >= 1; v--) {
						if (belong[id[i][v][1]] < belong[id[i][v][0]]) {
							out.print(v + " ");
							break;
						}
					}
				}
				out.println();
			} else {
				out.println(-1);
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
