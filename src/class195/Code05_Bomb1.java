package class195;

// 炸弹，java版
// 一共有n个炸弹，所有炸弹排成一条直线，给定每个炸弹的坐标xi、爆炸半径ri
// 炸弹A引爆时，如果炸弹B在其影响范围里，那么炸弹B也会引爆，进而引发一连串的爆炸
// 炸弹i如果作为初始引爆的炸弹，最终会引爆多少个炸弹记为query(i)
// 计算i = 1 2 .. n时，i * query(i)的累加和，答案对 1000000007 取余
// 1 <= n <= 5 * 10^5
// -(10^18) <= xi <= +(10^18)，题目依次输入的坐标保证严格递增
// 0 <= ri <= 2 * 10^18
// 测试链接 : https://www.luogu.com.cn/problem/P5025
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_Bomb1 {

	public static int MAXN = 500001;
	public static int MAXT = MAXN * 5;
	public static int MAXE = MAXN * 20;
	public static int INF = 1 << 30;
	public static int MOD = 1000000007;
	public static int n;
	public static long[] location = new long[MAXN];
	public static long[] radius = new long[MAXN];

	public static int[] a = new int[MAXE];
	public static int[] b = new int[MAXE];
	public static int cnte;

	public static int[] head = new int[MAXT];
	public static int[] nxt = new int[MAXE];
	public static int[] to = new int[MAXE];
	public static int cntg;

	public static int[] rangel = new int[MAXT];
	public static int[] ranger = new int[MAXT];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int root;
	public static int cntt;

	public static int[] dfn = new int[MAXT];
	public static int[] low = new int[MAXT];
	public static int cntd;
	public static int[] sta = new int[MAXT];
	public static int top;

	public static int[] belong = new int[MAXT];
	public static int[] mostl = new int[MAXT];
	public static int[] mostr = new int[MAXT];
	public static int sccCnt;

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXT][3];
	public static int u, status, e;
	public static int stacksize;

	public static void push(int u, int status, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = status;
		stack[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		status = stack[stacksize][1];
		e = stack[stacksize][2];
	}

	public static void addEdge(int u, int v) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void saveEdge(int u, int v) {
		a[++cnte] = u;
		b[cnte] = v;
	}

	public static int lower(long num) {
		int l = 1, r = n, mid, ans = n + 1;
		while (l <= r) {
			mid = (l + r) >> 1;
			if (location[mid] >= num) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	public static int build(int l, int r) {
		int rt;
		if (l == r) {
			rt = l;
		} else {
			rt = ++cntt;
			int mid = (l + r) >> 1;
			ls[rt] = build(l, mid);
			rs[rt] = build(mid + 1, r);
			addEdge(ls[rt], rt);
			addEdge(rs[rt], rt);
			saveEdge(ls[rt], rt);
			saveEdge(rs[rt], rt);
		}
		rangel[rt] = l;
		ranger[rt] = r;
		return rt;
	}

	public static void rangeToX(int jobl, int jobr, int jobx, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			addEdge(i, jobx);
			saveEdge(i, jobx);
		} else {
			int mid = (l + r) >> 1;
			if (jobl <= mid) {
				rangeToX(jobl, jobr, jobx, l, mid, ls[i]);
			}
			if (jobr > mid) {
				rangeToX(jobl, jobr, jobx, mid + 1, r, rs[i]);
			}
		}
	}

	// 递归版
	public static void tarjan1(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (dfn[v] == 0) {
				tarjan1(v);
				low[u] = Math.min(low[u], low[v]);
			} else {
				if (belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
			}
		}
		if (dfn[u] == low[u]) {
			sccCnt++;
			mostl[sccCnt] = INF;
			mostr[sccCnt] = -INF;
			int pop;
			do {
				pop = sta[top--];
				belong[pop] = sccCnt;
				mostl[sccCnt] = Math.min(mostl[sccCnt], rangel[pop]);
				mostr[sccCnt] = Math.max(mostr[sccCnt], ranger[pop]);
			} while (pop != u);
		}
	}

	// 迭代版
	public static void tarjan2(int node) {
		stacksize = 0;
		push(node, -1, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				e = head[u];
			} else {
				v = to[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
				}
				if (status == 1 && belong[v] == 0) {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = nxt[e];
			}
			if (e != 0) {
				v = to[e];
				if (dfn[v] == 0) {
					push(u, 0, e);
					push(v, -1, -1);
				} else {
					push(u, 1, e);
				}
			} else {
				if (dfn[u] == low[u]) {
					sccCnt++;
					mostl[sccCnt] = INF;
					mostr[sccCnt] = -INF;
					int pop;
					do {
						pop = sta[top--];
						belong[pop] = sccCnt;
						mostl[sccCnt] = Math.min(mostl[sccCnt], rangel[pop]);
						mostr[sccCnt] = Math.max(mostr[sccCnt], ranger[pop]);
					} while (pop != u);
				}
			}
		}
	}

	public static void condense() {
		cntg = 0;
		for (int i = 1; i <= sccCnt; i++) {
			head[i] = 0;
		}
		for (int i = 1; i <= cnte; i++) {
			int scc1 = belong[a[i]];
			int scc2 = belong[b[i]];
			if (scc1 != scc2) {
				addEdge(scc1, scc2);
			}
		}
	}

	public static void dpOnDAG() {
		for (int u = sccCnt; u > 0; u--) {
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				mostl[v] = Math.min(mostl[v], mostl[u]);
				mostr[v] = Math.max(mostr[v], mostr[u]);
			}
		}
	}

	public static int query(int u) {
		int scc = belong[u];
		int num = mostr[scc] - mostl[scc] + 1;
		return num;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		cntt = n;
		for (int i = 1; i <= n; i++) {
			location[i] = in.nextLong();
			radius[i] = in.nextLong();
		}
		root = build(1, n);
		for (int i = 1; i <= n; i++) {
			int l = lower(location[i] - radius[i]);
			int r = lower(location[i] + radius[i] + 1) - 1;
			rangeToX(l, r, i, 1, n, root);
		}
		for (int i = 1; i <= cntt; i++) {
			if (dfn[i] == 0) {
				// tarjan1(i);
				tarjan2(i);
			}
		}
		condense();
		dpOnDAG();
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans = (ans + 1L * query(i) * i) % MOD;
		}
		out.println(ans);
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

		long nextLong() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			long val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}
