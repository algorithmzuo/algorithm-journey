package class185;

// 烁烁的游戏，java版
// 树上有n个点，每个点的初始点权是0，给定n-1条边，边权都是1
// 接下来有m条操作，每条操作是如下两种类型中的一种
// 操作 M x k v : 点x出发，距离<=k的所有点，点权都加上v
// 操作 Q x     : 打印点x的点权
// 1 <= n、m <= 10^5
// -10000 <= v <= +10000
// 测试链接 : https://www.luogu.com.cn/problem/P10603
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_Game1 {

	public static int MAXN = 100001;
	public static int MAXH = 18;
	public static int MAXT = 20000001;
	public static int n, m;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg;

	public static int[] dep = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXH];

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] centfa = new int[MAXN];

	public static int[] addTree = new int[MAXN];
	public static int[] minusTree = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] sum = new int[MAXT];
	public static int cntt;

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

	// 树上倍增收集信息递归版，java会爆栈，C++不会
	public static void dfs1(int u, int fa) {
		dep[u] = dep[fa] + 1;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXH; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dfs1(v, u);
			}
		}
	}

	// dfs1的迭代版
	public static void dfs2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dep[u] = dep[f] + 1;
				stjump[u][0] = f;
				for (int p = 1; p < MAXH; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
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
			}
		}
	}

	public static int getLca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXH - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static int getDist(int x, int y) {
		return dep[x] + dep[y] - (dep[getLca(x, y)] << 1);
	}

	// 找重心需要计算子树大小的递归版，java会爆栈，C++不会
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// getSize1的迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
					}
				}
			}
		}
	}

	public static int getCentroid(int u, int fa) {
		// getSize1(u, fa);
		getSize2(u, fa);
		int half = siz[u] >> 1;
		boolean find = false;
		while (!find) {
			find = true;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				if (v != fa && !vis[v] && siz[v] > half) {
					fa = u;
					u = v;
					find = false;
					break;
				}
			}
		}
		return u;
	}

	public static void centroidTree(int u, int fa) {
		centfa[u] = fa;
		vis[u] = true;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				centroidTree(getCentroid(v, u), u);
			}
		}
	}

	public static int add(int jobi, int jobv, int l, int r, int i) {
		if (i == 0) {
			i = ++cntt;
		}
		if (l == r) {
			sum[i] += jobv;
		} else {
			int mid = (l + r) >> 1;
			if (jobi <= mid) {
				ls[i] = add(jobi, jobv, l, mid, ls[i]);
			} else {
				rs[i] = add(jobi, jobv, mid + 1, r, rs[i]);
			}
			sum[i] = sum[ls[i]] + sum[rs[i]];
		}
		return i;
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (i == 0) {
			return 0;
		}
		if (jobl <= l && r <= jobr) {
			return sum[i];
		}
		int mid = (l + r) >> 1;
		int ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, ls[i]);
		}
		if (jobr > mid) {
			ans += query(jobl, jobr, mid + 1, r, rs[i]);
		}
		return ans;
	}

	public static void add(int x, int k, int v) {
		addTree[x] = add(k, v, 0, n - 1, addTree[x]);
		for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
			int dist = getDist(x, fa);
			if (k - dist >= 0) {
				addTree[fa] = add(k - dist, v, 0, n - 1, addTree[fa]);
				minusTree[cur] = add(k - dist, v, 0, n - 1, minusTree[cur]);
			}
		}
	}

	public static int query(int x) {
		int ans = query(0, n - 1, 0, n - 1, addTree[x]);
		for (int cur = x, fa = centfa[cur]; fa > 0; cur = fa, fa = centfa[cur]) {
			int dist = getDist(x, fa);
			ans += query(dist, n - 1, 0, n - 1, addTree[fa]);
			ans -= query(dist, n - 1, 0, n - 1, minusTree[cur]);
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		// dfs1(1, 0);
		dfs2(1, 0);
		centroidTree(getCentroid(1, 0), 0);
		char op;
		int x, k, v;
		for (int i = 1; i <= m; i++) {
			op = in.nextChar();
			if (op == 'M') {
				x = in.nextInt();
				k = in.nextInt();
				v = in.nextInt();
				add(x, k, v);
			} else {
				x = in.nextInt();
				out.println(query(x));
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
