package class193;

// 矿场搭建，java版
// 一共n个地点，地点至少2个，每个地点都有人，m条双向道路连通所有地点
// 地震会发生在任何一个地点，地震发生时，其他地点的人都要去往救援点
// 你可以在任何地点设立救援点，但是发生地震的地点，道路和救援点都会失效
// 打印至少需要几个救援点，打印设立救援点的方案总数，方案认为是无序集合
// 1 <= n <= 1000
// 1 <= m <= 1000
// 测试链接 : https://www.luogu.com.cn/problem/P3225
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_MiningFarm1 {

	public static int MAXN = 1001;
	public static int MAXM = 1001;
	public static int t, n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static boolean[] cutVertex = new boolean[MAXN];
	public static int[] vbccSiz = new int[MAXN];
	public static int[] vbccArr = new int[MAXN << 1];
	public static int[] vbccl = new int[MAXN];
	public static int[] vbccr = new int[MAXN];
	public static int idx;
	public static int vbccCnt;

	public static long ans1, ans2;

	public static void prepare() {
		cntg = cntd = top = idx = vbccCnt = 0;
		for (int i = 1; i < MAXN; i++) {
			head[i] = dfn[i] = low[i] = 0;
			cutVertex[i] = false;
		}
		n = 0;
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void tarjan(int u, boolean root) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		int son = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				son++;
				tarjan(v, false);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					if (!root || son >= 2) {
						cutVertex[u] = true;
					}
					vbccCnt++;
					vbccSiz[vbccCnt] = 1;
					vbccArr[++idx] = u;
					vbccl[vbccCnt] = idx;
					int pop;
					do {
						pop = sta[top--];
						vbccSiz[vbccCnt]++;
						vbccArr[++idx] = pop;
					} while (pop != v);
					vbccr[vbccCnt] = idx;
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	public static void compute() {
		if (vbccCnt == 1) {
			ans1 = 2;
			ans2 = n * (n - 1) / 2;
		} else {
			ans1 = 0;
			ans2 = 1;
			for (int i = 1; i <= vbccCnt; i++) {
				int siz = vbccSiz[i], cut = 0;
				for (int j = vbccl[i]; j <= vbccr[i]; j++) {
					if (cutVertex[vbccArr[j]]) {
						cut++;
					}
				}
				if (cut == 1) {
					ans1 += 1;
					ans2 = ans2 * (siz - 1);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = 0;
		m = in.nextInt();
		while (m != 0) {
			prepare();
			for (int i = 1, u, v; i <= m; i++) {
				u = in.nextInt();
				v = in.nextInt();
				n = Math.max(n, u);
				n = Math.max(n, v);
				addEdge(u, v);
				addEdge(v, u);
			}
			tarjan(1, true);
			compute();
			out.println("Case " + (++t) + ": " + ans1 + " " + ans2);
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
