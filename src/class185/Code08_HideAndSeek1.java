package class185;

// 捉迷藏，最优解是树上括号序，java版
// 测试链接 : https://www.luogu.com.cn/problem/P2056
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_HideAndSeek1 {

	public static int MAXN = 100001;
	public static int MAXS = MAXN * 3;
	public static int INF = 1000000001;
	public static int PAL = -1;
	public static int PAR = -2;
	public static int n, m;

	public static boolean[] light = new boolean[MAXN];
	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] seg = new int[MAXS];
	public static int cntd;

	public static int[] pal = new int[MAXS << 2];
	public static int[] par = new int[MAXS << 2];
	public static int[] ladd = new int[MAXS << 2];
	public static int[] lminus = new int[MAXS << 2];
	public static int[] radd = new int[MAXS << 2];
	public static int[] rminus = new int[MAXS << 2];
	public static int[] dist = new int[MAXS << 2];

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

	public static void set(int i, int v) {
		par[i] = pal[i] = 0;
		ladd[i] = lminus[i] = radd[i] = rminus[i] = -INF;
		dist[i] = -INF;
		if (v == PAL) {
			pal[i] = 1;
		} else if (v == PAR) {
			par[i] = 1;
		} else if (!light[v]) {
			ladd[i] = lminus[i] = radd[i] = rminus[i] = 0;
		}
	}

	public static void up(int i) {
		int l = i << 1, r = i << 1 | 1;
		if (pal[l] > par[r]) {
			par[i] = par[l];
			pal[i] = pal[l] - par[r] + pal[r];
		} else {
			par[i] = par[l] + par[r] - pal[l];
			pal[i] = pal[r];
		}
		ladd[i] = Math.max(ladd[l], Math.max(ladd[r] + par[l] - pal[l], lminus[r] + par[l] + pal[l]));
		lminus[i] = Math.max(lminus[l], lminus[r] - par[l] + pal[l]);
		radd[i] = Math.max(radd[r], Math.max(radd[l] - par[r] + pal[r], rminus[l] + par[r] + pal[r]));
		rminus[i] = Math.max(rminus[r], rminus[l] + par[r] - pal[r]);
		dist[i] = Math.max(Math.max(dist[l], dist[r]), Math.max(radd[l] + lminus[r], rminus[l] + ladd[r]));
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			set(i, seg[l]);
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static void update(int jobi, int l, int r, int i) {
		if (l == r) {
			set(i, seg[l]);
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
