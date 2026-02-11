package class191;

// 贝尔敦道路，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF118E
// 测试链接 : https://codeforces.com/problemset/problem/118/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_BertownRoads1 {

	public static int MAXN = 100001;
	public static int MAXM = 300001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM << 1];
	public static int[] to = new int[MAXM << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static boolean check;
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];
	public static int cnta;

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to[e];
			// 树边和回边需要处理，弃边不要处理
			if (dfn[v] == 0 || dfn[v] < dfn[u]) {
				a[++cnta] = u;
				b[cnta] = v;
			}
			if (dfn[v] == 0) {
				tarjan(v, e);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] > dfn[u]) {
					check = false;
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		cntg = 1;
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		check = true;
		tarjan(1, 0);
		if (!check) {
			out.println(0);
		} else {
			for (int i = 1; i <= cnta; i++) {
				out.println(a[i] + " " + b[i]);
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
