package class185;

// 捉迷藏，最优解，树的括号序 + 巧妙线段树，java版
// 树上有n个点，点的初始颜色为黑，给定n-1条边，边权都是1
// 一共有m条操作，每条操作是如下两种类型中的一种
// 操作 C x : 改变点x的颜色，黑变成白，白变成黑
// 操作 G   : 打印最远的两个黑点的距离，只有一个黑点打印0，无黑点打印-1
// 1 <= n <= 10^5    1 <= m <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P2056
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_HideSeek3 {

	public static int MAXN = 100001;
	public static int MAXT = MAXN * 12;
	public static int INF = 1000000001;
	public static int PAR = -1;
	public static int PAL = -2;
	public static int n, m;
	public static boolean[] black = new boolean[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int[] seg = new int[MAXN * 3];
	public static int cntd;

	// 以下概念都是括号序列消除到最简状态之后的情况
	// pr : 右括号数量
	// pl : 左括号数量
	// ladd   : 左端点到任意黑点，max(右括号 + 左括号)
	// lminus : 左端点到任意黑点，max(左括号 - 右括号)
	// radd   : 任意黑点到右端点，max(右括号 + 左括号)
	// rminus : 任意黑点到右端点，max(右括号 - 左括号)
	// dist   : 选择两个黑点的最大距离
	// 注意区分lminus、rminus
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

	public static void up(int i) {
		int pre = i << 1;
		int post = i << 1 | 1;
		if (pl[pre] > pr[post]) {
			pr[i] = pr[pre];
			pl[i] = pl[pre] - pr[post] + pl[post];
		} else {
			pr[i] = pr[pre] + pr[post] - pl[pre];
			pl[i] = pl[post];
		}
		ladd[i] = Math.max(ladd[pre], Math.max(pr[pre] + ladd[post] - pl[pre], pr[pre] + pl[pre] + lminus[post]));
		lminus[i] = Math.max(lminus[pre], pl[pre] - pr[pre] + lminus[post]);
		radd[i] = Math.max(radd[post], Math.max(radd[pre] - pr[post] + pl[post], rminus[pre] + pr[post] + pl[post]));
		rminus[i] = Math.max(rminus[post], rminus[pre] + pr[post] - pl[post]);
		dist[i] = Math.max(Math.max(dist[pre], dist[post]), Math.max(radd[pre] + lminus[post], rminus[pre] + ladd[post]));
	}

	public static void point(int i, int v) {
		pr[i] = pl[i] = 0;
		ladd[i] = lminus[i] = radd[i] = rminus[i] = dist[i] = -INF;
		if (v == PAR) {
			pr[i] = 1;
		} else if (v == PAL) {
			pl[i] = 1;
		} else if (black[v]) {
			ladd[i] = lminus[i] = radd[i] = rminus[i] = dist[i] = 0;
		}
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			point(i, seg[l]);
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static void update(int jobi, int l, int r, int i) {
		if (l == r) {
			point(i, seg[l]);
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
		for (int i = 1; i <= n; i++) {
			black[i] = true;
		}
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
		int blackCnt = n;
		char op;
		for (int i = 1, x; i <= m; i++) {
			op = in.nextChar();
			if (op == 'C') {
				x = in.nextInt();
				black[x] = !black[x];
				if (black[x]) {
					blackCnt++;
				} else {
					blackCnt--;
				}
				update(dfn[x], 1, cntd, 1);
			} else {
				if (blackCnt <= 1) {
					out.println(blackCnt - 1);
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
