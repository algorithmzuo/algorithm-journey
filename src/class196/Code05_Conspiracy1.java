package class196;

// 密谋，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3513
// 提交以下的code，提交时请把类名改成"Main"
// 本题卡空间，java的实现就是无法通过
// 想通过用C++实现，本节课Code05_Conspiracy2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Conspiracy1 {

	public static int MAXN = 5001;
	public static int MAXS = MAXN << 1;
	public static int MAXM = MAXN * MAXN;
	public static int n, k, x;
	public static boolean[][] know = new boolean[MAXN][MAXN];

	public static int[] head = new int[MAXS];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXS];
	public static int[] low = new int[MAXS];
	public static int cntd;

	public static int[] sta = new int[MAXS];
	public static int top;

	public static int[] belong = new int[MAXS];
	public static int sccCnt;

	public static int[] set1 = new int[MAXN];
	public static int[] set2 = new int[MAXN];
	public static boolean[] in1 = new boolean[MAXN];
	public static int cnt1, cnt2;

	public static int[] conflict = new int[MAXN];
	public static int[] other = new int[MAXN];

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

	public static int compute() {
		for (int i = 1; i <= n; i++) {
			if (belong[i] == belong[i + n]) {
				return 0;
			}
		}
		for (int i = 1; i <= n; i++) {
			if (belong[i] < belong[i + n]) {
				set1[++cnt1] = i;
				in1[i] = true;
			} else {
				set2[++cnt2] = i;
			}
		}
		int ans = cnt1 > 0 && cnt2 > 0 ? 1 : 0;
		for (int i = 1; i <= cnt1; i++) {
			int x = set1[i];
			for (int j = 1; j <= cnt2; j++) {
				int y = set2[j];
				if (know[x][y]) {
					conflict[x]++;
					other[x] = y;
				} else {
					conflict[y]++;
					other[y] = x;
				}
			}
		}
		for (int i = 1; i <= n; i++) {
			if (conflict[i] == 0) {
				if ((in1[i] && cnt1 > 1) || (!in1[i] && cnt2 > 1)) {
					ans++;
				}
			}
			if (conflict[i] == 1) {
				if (conflict[other[i]] == 0) {
					ans++;
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			k = in.nextInt();
			for (int j = 1; j <= k; j++) {
				x = in.nextInt();
				know[i][x] = true;
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = i + 1; j <= n; j++) {
				if (know[i][j]) {
					addEdge(i + n, j);
					addEdge(j + n, i);
				} else {
					addEdge(i, j + n);
					addEdge(j, i + n);
				}
			}
		}
		for (int i = 1; i <= n << 1; i++) {
			if (dfn[i] == 0) {
				tarjan(i);
			}
		}
		int ans = compute();
		out.println(ans);
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
