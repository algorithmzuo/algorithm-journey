package class189;

// 稳定婚姻，java版
// 测试链接 : https://www.luogu.com.cn/problem/P1407
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class Code04_SafeMarriage1 {

	public static int MAXN = 10001;
	public static int MAXM = 30001;
	public static int n, m;

	public static int[] b = new int[MAXN];
	public static int[] g = new int[MAXN];
	public static int cntn;
	public static HashMap<String, Integer> nameId = new HashMap<>();

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXM];
	public static int[] to = new int[MAXM];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static boolean[] ins = new boolean[MAXN];
	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] belong = new int[MAXN];
	public static int sccCnt;

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void tarjan(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		ins[u] = true;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (ins[v]) {
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
				ins[pop] = false;
			} while (pop != u);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		String boy, girl;
		for (int i = 1; i <= n; i++) {
			boy = in.nextString();
			girl = in.nextString();
			b[i] = ++cntn;
			g[i] = ++cntn;
			nameId.put(boy, b[i]);
			nameId.put(girl, g[i]);
			addEdge(b[i], g[i]);
		}
		m = in.nextInt();
		for (int i = 1; i <= m; i++) {
			boy = in.nextString();
			girl = in.nextString();
			addEdge(nameId.get(girl), nameId.get(boy));
		}
		for (int i = 1; i <= cntn; i++) {
			if (dfn[i] == 0) {
				tarjan(i);
			}
		}
		for (int i = 1; i <= n; i++) {
			if (belong[b[i]] == belong[g[i]]) {
				out.println("Unsafe");
			} else {
				out.println("Safe");
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

		boolean isLetter(int c) {
			return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
		}

		String nextString() throws IOException {
			int c;
			do {
				c = readByte();
				if (c == -1)
					return null;
			} while (!isLetter(c));
			StringBuilder sb = new StringBuilder();
			while (isLetter(c)) {
				sb.append((char) c);
				c = readByte();
				if (c == -1)
					break;
			}
			return sb.toString();
		}

	}

}
