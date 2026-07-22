package class203;

// 变化的道路，java版
// 一共n个点，n-1条无向道路，所有点组成一棵树，这些道路始终存在
// 然后给定m条变化道路，每条道路只在[l, r]天存在
// 每一天都要游览所有城市，每条道路只有第一次经过时才会减少L值
// 打印每天出发时L值至少是多少，一共32766天
// 测试链接 : https://www.luogu.com.cn/problem/P4319
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_ChangingRoads1 {

	public static int MAXN = 200001;
	public static int MAXD = 32767;
	public static int MAXT = 4000001;
	public static int DAY = 32766;
	public static int n, m;

	// 所有道路的信息
	// 初始道路和变化道路统一编号
	public static int[] ex = new int[MAXN];
	public static int[] ey = new int[MAXN];
	public static int[] ew = new int[MAXN];
	public static int edgeCnt;

	// 时间轴线段树，线段树节点拥有哪些道路任务，链式前向星表示
	public static int[] head = new int[MAXD << 2];
	public static int[] nxt = new int[MAXT];
	public static int[] toEdge = new int[MAXT];
	public static int taskCnt;

	// 辅助splay
	public static int[] fa = new int[MAXN];
	public static int[] ls = new int[MAXN];
	public static int[] rs = new int[MAXN];
	public static boolean[] rev = new boolean[MAXN];
	public static int[] sta = new int[MAXN];

	// maxEdge[x]表示以x为根的辅助splay中，权值最大的边的编号
	public static int[] maxEdge = new int[MAXN];

	// LCT最小生成树修改的回滚栈
	// type == 1 : 这条边加入
	// type == 2 : 这条边删除
	public static int[] rollbackEdge = new int[MAXN << 1];
	public static int[] rollbackType = new int[MAXN << 1];
	public static int opsize;

	// 当前最小生成树的边权和，再加1就是答案
	public static long mstSum = 1;
	public static long[] ans = new long[MAXD];

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

	// 尝试把第e条边加入当前最小生成树
	public static void addEdge(int e) {
		int x = ex[e];
		int y = ey[e];
		makeroot(x);
		if (findroot(y) != x) {
			// 两端不连通，直接加入最小生成树
			link(x, n + e);
			link(y, n + e);
			backup(e, 1);
			mstSum += ew[e];
		} else {
			// 两端已经连通，查询路径上的最大边
			split(x, y);
			int pre = maxEdge[y];
			if (ew[pre] > ew[e]) {
				// 先删除旧边
				cut(ex[pre], n + pre);
				cut(ey[pre], n + pre);
				backup(pre, 2);
				mstSum -= ew[pre];
				// 再加入新边
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
		if (t == 1) { // 当时加入，现在删除
			cut(ex[e], n + e);
			cut(ey[e], n + e);
			mstSum -= ew[e];
		} else { // 当时删除，现在加入
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

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i < n; i++) {
			ex[++edgeCnt] = in.nextInt();
			ey[edgeCnt] = in.nextInt();
			ew[edgeCnt] = in.nextInt();
			add(1, DAY, edgeCnt, 1, DAY, 1);
		}
		m = in.nextInt();
		for (int i = 1, l, r; i <= m; i++) {
			ex[++edgeCnt] = in.nextInt();
			ey[edgeCnt] = in.nextInt();
			ew[edgeCnt] = in.nextInt();
			l = in.nextInt();
			r = in.nextInt();
			add(l, r, edgeCnt, 1, DAY, 1);
		}
		dfs(1, DAY, 1);
		for (int i = 1; i <= DAY; i++) {
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
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}