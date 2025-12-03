package class185;

// 捉迷藏，正解是树的括号序，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2056
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_HideSeek1 {

	public static int MAXN = 100001;
	public static int MAXS = MAXN * 3;
	public static int MAXT = MAXS << 2;
	public static int INF = 1000000001;
	public static int PAR = -1;
	public static int PAL = -2;
	public static int n, m;

	public static boolean[] light = new boolean[MAXN];
	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] seg = new int[MAXS];
	public static int cntd;

	// 注意区分lminus和rminus
	// pr : 都消掉之后的右括号数量
	// pl : 都消掉之后的左括号数量
	// ladd : 左端点到任意黑点，max(右括号 + 左括号)
	// lminus : 左端点到任意黑点，max(左括号 - 右括号)
	// radd : 任意黑点到右端点，max(右括号 + 左括号)
	// rminus : 任意黑点到右端点，max(右括号 - 左括号)
	// dist : 选择任意两个黑点的最大距离
	public static int[] pr = new int[MAXT];
	public static int[] pl = new int[MAXT];
	public static int[] ladd = new int[MAXT];
	public static int[] lminus = new int[MAXT];
	public static int[] radd = new int[MAXT];
	public static int[] rminus = new int[MAXT];
	public static int[] dist = new int[MAXT];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][3];
	public static int u, f, e;
	public static int stacksize;

	public static void push(int u, int f, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		e = stack[stacksize][2];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 收集括号序递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa) {
		seg[++cntd] = PAL;
		seg[++cntd] = u;
		dfn[u] = cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dfs1(v, u);
			}
		}
		seg[++cntd] = PAR;
	}

	// dfs1的迭代版
	public static void dfs2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				seg[++cntd] = PAL;
				seg[++cntd] = u;
				dfn[u] = cntd;
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
				seg[++cntd] = PAR;
			}
		}
	}

	public static void setSingle(int i, int v) {
		pr[i] = pl[i] = 0;
		ladd[i] = lminus[i] = radd[i] = rminus[i] = -INF;
		dist[i] = -INF;
		if (v == PAR) {
			pr[i] = 1;
		} else if (v == PAL) {
			pl[i] = 1;
		} else if (!light[v]) {
			ladd[i] = lminus[i] = radd[i] = rminus[i] = 0;
		}
	}

	public static void up(int i) {
		int l = i << 1, r = i << 1 | 1;
		if (pl[l] > pr[r]) {
			pr[i] = pr[l];
			pl[i] = pl[l] - pr[r] + pl[r];
		} else {
			pr[i] = pr[l] + pr[r] - pl[l];
			pl[i] = pl[r];
		}
		ladd[i] = Math.max(ladd[l], Math.max(pr[l] + ladd[r] - pl[l], pr[l] + pl[l] + lminus[r]));
		lminus[i] = Math.max(lminus[l], pl[l] - pr[l] + lminus[r]);
		radd[i] = Math.max(radd[r], Math.max(radd[l] - pr[r] + pl[r], rminus[l] + pr[r] + pl[r]));
		rminus[i] = Math.max(rminus[r], rminus[l] + pr[r] - pl[r]);
		dist[i] = Math.max(Math.max(dist[l], dist[r]), Math.max(radd[l] + lminus[r], ladd[r] + rminus[l]));
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			setSingle(i, seg[l]);
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static void update(int jobi, int l, int r, int i) {
		if (l == r) {
			setSingle(i, seg[l]);
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				update(jobi, l, mid, i << 1);
			} else {
				update(jobi, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		// dfs1(1, 0);
		dfs2(1, 0);
		build(1, cntd, 1);
		m = in.nextInt();
		int offCnt = n;
		char op;
		for (int i = 1, x; i <= m; i++) {
			op = in.nextChar();
			if (op == 'C') {
				x = in.nextInt();
				light[x] = !light[x];
				if (!light[x]) {
					offCnt++;
				} else {
					offCnt--;
				}
				update(dfn[x], 1, cntd, 1);
			} else {
				if (offCnt <= 1) {
					out.println(offCnt - 1);
				} else {
					out.println(dist[1]);
				}
			}
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		final private int BUFFER_SIZE = 1 << 16;
		private final InputStream in;
		private final byte[] buffer;
		private int ptr, len;

		public FastReader() {
			in = System.in;
			buffer = new byte[BUFFER_SIZE];
			ptr = len = 0;
		}

		private boolean hasNextByte() throws IOException {
			if (ptr < len)
				return true;
			ptr = 0;
			len = in.read(buffer);
			return len > 0;
		}

		private byte readByte() throws IOException {
			if (!hasNextByte())
				return -1;
			return buffer[ptr++];
		}

		public char nextChar() throws IOException {
			byte c;
			do {
				c = readByte();
				if (c == -1)
					return 0;
			} while (c <= ' ');
			char ans = 0;
			while (c > ' ') {
				ans = (char) c;
				c = readByte();
			}
			return ans;
		}

		public int nextInt() throws IOException {
			int num = 0;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			boolean minus = false;
			if (b == '-') {
				minus = true;
				b = readByte();
			}
			while (!isWhitespace(b) && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			return minus ? -num : num;
		}

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}
