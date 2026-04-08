package class196;

// 电台，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF1215F
// 测试链接 : https://codeforces.com/problemset/problem/1215/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_RadioStations1 {

	public static int MAXN = 400001;
	public static int MAXS = 2000001;
	public static int MAXM = 4000001;
	public static int n, p, x, m, cntt;

	public static int[] l = new int[MAXN];
	public static int[] r = new int[MAXN];

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

	// 注意含义，频率不在..
	public static int[] pre = new int[MAXN];
	public static int[] suf = new int[MAXN];

	public static int ans;
	public static int[] arr = new int[MAXN];
	public static int cnta;

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void link() {
		pre[1] = ++cntt;
		for (int i = 2; i <= x; i++) {
			pre[i] = ++cntt;
			addEdge(pre[i], pre[i - 1]);
		}
		suf[x] = ++cntt;
		for (int i = x - 1; i >= 1; i--) {
			suf[i] = ++cntt;
			addEdge(suf[i], suf[i + 1]);
		}
		for (int i = 1; i <= p; i++) {
			if (l[i] - 1 >= 1) {
				addEdge(i, pre[l[i] - 1]);
			}
			if (r[i] + 1 <= x) {
				addEdge(i, suf[r[i] + 1]);
			}
			addEdge(suf[l[i]], i + p);
			addEdge(pre[r[i]], i + p);
		}
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

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		p = in.nextInt();
		x = in.nextInt();
		m = in.nextInt();
		cntt = p << 1;
		for (int i = 1, u, v; i <= n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u + p, v);
			addEdge(v + p, u);
		}
		for (int i = 1; i <= p; i++) {
			l[i] = in.nextInt();
			r[i] = in.nextInt();
		}
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v + p);
			addEdge(v, u + p);
		}
		link();
		for (int i = 1; i <= cntt; i++) {
			if (dfn[i] == 0) {
				tarjan(i);
			}
		}
		boolean check = true;
		for (int i = 1; i <= p; i++) {
			if (belong[i] == belong[i + p]) {
				check = false;
				break;
			}
		}
		if (check) {
			for (int i = 1; i <= p; i++) {
				if (belong[i] < belong[i + p]) {
					ans = Math.max(ans, l[i]);
					arr[++cnta] = i;
				}
			}
			out.println(cnta + " " + ans);
			for (int i = 1; i <= cnta; i++) {
				out.print(arr[i] + " ");
			}
			out.println();
		} else {
			out.println(-1);
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
