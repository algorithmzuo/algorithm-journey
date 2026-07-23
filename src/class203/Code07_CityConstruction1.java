package class203;

// 城市建设，java版
// 一共n个点、m条无向边，边有边权，编号1~m，图保证连通
// 接下来有q次修改，格式 e w，代表第e条边的边权修改为w
// 每次修改之后，打印当前图的最小生成树边权和
// 1 <= n <= 2 * 10^4
// 1 <= m、q <= 5 * 10^4
// 0 <= 边权 <= 5 * 10^7
// 测试链接 : https://www.luogu.com.cn/problem/P3206
// 提交以下的code，提交时请把类名改成"Main"
// 本题卡常数时间，java的实现会有部分测试用例超时
// 想通过用C++实现，本节课Code07_CityConstruction2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_CityConstruction1 {

	public static int MAXN = 200001;
	public static int MAXQ = 50001;
	public static int MAXT = 4000001;
	public static int n, m, q;

	public static int[] ex = new int[MAXN];
	public static int[] ey = new int[MAXN];
	public static int[] ew = new int[MAXN];
	public static int edgeCnt;

	public static int[] changeEdge = new int[MAXQ];
	public static int[] endTime = new int[MAXN];

	public static int[] head = new int[MAXQ << 2];
	public static int[] nxt = new int[MAXT];
	public static int[] toEdge = new int[MAXT];
	public static int taskCnt;

	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	public static int[] maxEdge = new int[MAXN];

	public static int[] rollbackEdge = new int[MAXN << 1];
	public static int[] rollbackType = new int[MAXN << 1];
	public static int opsize;

	public static long mstSum;
	public static long[] ans = new long[MAXQ];

	public static void addTask(int i, int e) {
		nxt[++taskCnt] = head[i];
		toEdge[taskCnt] = e;
		head[i] = taskCnt;
	}

	public static void add(int jobl, int jobr, int jobe, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addTask(i, jobe);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				add(jobl, jobr, jobe, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobe, mid + 1, r, i << 1 | 1);
			}
		}
	}

	public static void up(int x) {
		maxEdge[x] = x <= n ? 0 : x - n;
		if (ew[maxEdge[ls[x]]] > ew[maxEdge[x]]) {
			maxEdge[x] = maxEdge[ls[x]];
		}
		if (ew[maxEdge[rs[x]]] > ew[maxEdge[x]]) {
			maxEdge[x] = maxEdge[rs[x]];
		}
	}

	public static boolean isroot(int x) {
		return ls[fa[x]] != x && rs[fa[x]] != x;
	}

	public static int lr(int x) {
		return ls[fa[x]] == x ? 0 : 1;
	}

	public static void reverse(int x) {
		if (x != 0) {
			int tmp = ls[x];
			ls[x] = rs[x];
			rs[x] = tmp;
			rev[x] = !rev[x];
		}
	}

	public static void down(int x) {
		if (rev[x]) {
			reverse(ls[x]);
			reverse(rs[x]);
			rev[x] = false;
		}
	}

	public static void rotate(int x) {
		int f = fa[x], g = fa[f];
		if (lr(x) == 0) {
			ls[f] = rs[x];
			if (ls[f] != 0) {
				fa[ls[f]] = f;
			}
			rs[x] = f;
		} else {
			rs[f] = ls[x];
			if (rs[f] != 0) {
				fa[rs[f]] = f;
			}
			ls[x] = f;
		}
		if (!isroot(f)) {
			if (lr(f) == 0) {
				ls[g] = x;
			} else {
				rs[g] = x;
			}
		}
		fa[f] = x;
		fa[x] = g;
		up(f);
		up(x);
	}

	public static void splay(int x) {
		int siz = 0;
		sta[++siz] = x;
		for (int y = x; !isroot(y); y = fa[y]) {
			sta[++siz] = fa[y];
		}
		while (siz != 0) {
			down(sta[siz--]);
		}
		while (!isroot(x)) {
			int f = fa[x];
			if (!isroot(f)) {
				if (lr(x) == lr(f)) {
					rotate(f);
				} else {
					rotate(x);
				}
			}
			rotate(x);
		}
	}

	public static void access(int x) {
		for (int y = 0; x != 0; y = x, x = fa[x]) {
			splay(x);
			rs[x] = y;
			up(x);
		}
	}

	public static void makeroot(int x) {
		access(x);
		splay(x);
		reverse(x);
	}

	public static int findroot(int x) {
		access(x);
		splay(x);
		down(x);
		while (ls[x] != 0) {
			x = ls[x];
			down(x);
		}
		splay(x);
		return x;
	}

	public static void split(int x, int y) {
		makeroot(x);
		access(y);
		splay(y);
	}

	public static void link(int x, int y) {
		makeroot(x);
		if (findroot(y) != x) {
			fa[x] = y;
		}
	}

	public static void cut(int x, int y) {
		makeroot(x);
		if (findroot(y) == x && fa[y] == x && ls[y] == 0 && rs[x] == y) {
			fa[y] = rs[x] = 0;
			up(x);
		}
	}

	public static void backup(int edge, int type) {
		rollbackEdge[++opsize] = edge;
		rollbackType[opsize] = type;
	}

	public static void addEdge(int e) {
		int x = ex[e];
		int y = ey[e];
		up(n + e);
		makeroot(x);
		if (findroot(y) != x) {
			link(x, n + e);
			link(y, n + e);
			backup(e, 1);
			mstSum += ew[e];
		} else {
			split(x, y);
			int pre = maxEdge[y];
			if (ew[pre] > ew[e]) {
				cut(ex[pre], n + pre);
				cut(ey[pre], n + pre);
				backup(pre, 2);
				mstSum -= ew[pre];
				link(x, n + e);
				link(y, n + e);
				backup(e, 1);
				mstSum += ew[e];
			}
		}
	}

	public static void undo() {
		int e = rollbackEdge[opsize];
		int t = rollbackType[opsize--];
		if (t == 1) {
			cut(ex[e], n + e);
			cut(ey[e], n + e);
			mstSum -= ew[e];
		} else {
			link(ex[e], n + e);
			link(ey[e], n + e);
			mstSum += ew[e];
		}
	}

	public static void dfs(int l, int r, int i) {
		int tmp = opsize;
		for (int k = head[i]; k != 0; k = nxt[k]) {
			addEdge(toEdge[k]);
		}
		if (l == r) {
			ans[l] = mstSum;
		} else {
			int mid = (l + r) >> 1;
			dfs(l, mid, i << 1);
			dfs(mid + 1, r, i << 1 | 1);
		}
		while (opsize > tmp) {
			undo();
		}
	}

	public static void prepare() {
		for (int i = 1; i <= m; i++) {
			endTime[i] = q + 1;
		}
		for (int i = q; i >= 1; i--) {
			int e = changeEdge[i];
			add(i, endTime[e] - 1, m + i, 1, q, 1);
			endTime[e] = i;
		}
		for (int i = 1; i <= m; i++) {
			if (endTime[i] > 1) {
				add(1, endTime[i] - 1, i, 1, q, 1);
			}
		}
		for (int e = 1; e <= edgeCnt; e++) {
			maxEdge[n + e] = e;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		q = in.nextInt();
		edgeCnt = m;
		for (int i = 1; i <= m; i++) {
			ex[i] = in.nextInt();
			ey[i] = in.nextInt();
			ew[i] = in.nextInt();
		}
		for (int i = 1; i <= q; i++) {
			int e = in.nextInt();
			int w = in.nextInt();
			changeEdge[i] = e;
			ex[++edgeCnt] = ex[e];
			ey[edgeCnt] = ey[e];
			ew[edgeCnt] = w;
		}
		prepare();
		dfs(1, q, 1);
		for (int i = 1; i <= q; i++) {
			out.println(ans[i]);
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
				if (len <= 0) {
					return -1;
				}
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
				val = val * 10 + c - '0';
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}