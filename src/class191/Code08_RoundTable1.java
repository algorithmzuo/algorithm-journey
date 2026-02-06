package class191;

// 圆桌骑士，java版
// 测试链接 : https://www.luogu.com.cn/problem/SP2878
// 测试链接 : https://www.spoj.com/problems/KNIGHTS/
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_RoundTable1 {

	public static int MAXN = 1001;
	public static int MAXM = 1000001;
	public static int n, m;
	public static boolean[][] hate = new boolean[MAXN][MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] vbccArr = new int[MAXN << 1];
	public static int[] vbccl = new int[MAXN];
	public static int[] vbccr = new int[MAXN];
	public static int idx;
	public static int vbccCnt;

	public static int[] color = new int[MAXN];
	public static boolean[] block = new boolean[MAXN];
	public static boolean[] keep = new boolean[MAXN];

	public static void prepare() {
		cntg = cntd = top = idx = vbccCnt = 0;
		for (int i = 1; i <= n; i++) {
			head[i] = dfn[i] = low[i] = 0;
			keep[i] = false;
			for (int j = 1; j <= n; j++) {
				hate[i][j] = false;
			}
		}
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void tarjan(int u, boolean root) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		if (root && head[u] == 0) {
			vbccCnt++;
			vbccArr[++idx] = u;
			vbccl[vbccCnt] = vbccr[vbccCnt] = idx;
			return;
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan(v, false);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					vbccCnt++;
					vbccl[vbccCnt] = idx + 1;
					int pop;
					do {
						pop = sta[top--];
						vbccArr[++idx] = pop;
					} while (pop != v);
					vbccArr[++idx] = u;
					vbccr[vbccCnt] = idx;
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	public static boolean dfs(int u, int c) {
		color[u] = c;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (block[v]) {
				if (color[v] == 0) {
					if (dfs(v, c == 1 ? 2 : 1)) {
						return true;
					}
				}
				if (color[v] == c) {
					return true;
				}
			}
		}
		return false;
	}

	public static int compute() {
		for (int i = 1; i <= vbccCnt; i++) {
			for (int j = vbccl[i]; j <= vbccr[i]; j++) {
				color[vbccArr[j]] = 0;
				block[vbccArr[j]] = true;
			}
			boolean odd = dfs(vbccArr[vbccr[i]], 1);
			for (int j = vbccl[i]; j <= vbccr[i]; j++) {
				keep[vbccArr[j]] |= odd;
				block[vbccArr[j]] = false;
			}
		}
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			if (!keep[i]) {
				ans++;
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		while (n != 0 || m != 0) {
			prepare();
			for (int i = 1, u, v; i <= m; i++) {
				u = in.nextInt();
				v = in.nextInt();
				hate[u][v] = true;
				hate[v][u] = true;
			}
			for (int u = 1; u <= n; u++) {
				for (int v = u + 1; v <= n; v++) {
					if (!hate[u][v]) {
						addEdge(u, v);
						addEdge(v, u);
					}
				}
			}
			for (int i = 1; i <= n; i++) {
				if (dfn[i] == 0) {
					tarjan(i, true);
				}
			}
			int ans = compute();
			out.println(ans);
			n = in.nextInt();
			m = in.nextInt();
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
