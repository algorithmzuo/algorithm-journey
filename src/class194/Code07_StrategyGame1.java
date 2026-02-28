package class194;

// 战略游戏，java版
// 测试链接 : https://www.luogu.com.cn/problem/P4606
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_StrategyGame1 {

	public static int MAXN = 100001;
	public static int MAXM = 200001;
	public static int MAXP = 20;
	public static int t, n, m, q, s, cntn;

	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXM << 1];
	public static int[] to1 = new int[MAXM << 1];
	public static int cnt1;

	public static int[] head2 = new int[MAXN << 1];
	public static int[] next2 = new int[MAXM << 2];
	public static int[] to2 = new int[MAXM << 2];
	public static int cnt2;

	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;

	public static int[] sta = new int[MAXN];
	public static int top;

	public static int[] nid = new int[MAXN << 1];
	public static int[] dist = new int[MAXN << 1];
	public static int[] dep = new int[MAXN << 1];
	public static int[][] stjump = new int[MAXN << 1][MAXP];
	public static int cnti;

	public static int[] arr = new int[MAXN];

	// 迭代版需要的栈，讲解118讲了递归改迭代的技巧
	public static int[][] stack = new int[MAXN << 1][4];
	public static int u, status, fa, e;
	public static int stacksize;

	public static void push(int u, int status, int fa, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = status;
		stack[stacksize][2] = fa;
		stack[stacksize][3] = e;
		stacksize++;
	}

	public static void pop() {
		stacksize--;
		u = stack[stacksize][0];
		status = stack[stacksize][1];
		fa = stack[stacksize][2];
		e = stack[stacksize][3];
	}

	public static void addEdge1(int u, int v) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		head2[u] = cnt2;
	}

	// 递归版
	public static void tarjan1(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++top] = u;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (dfn[v] == 0) {
				tarjan1(v);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					cntn++;
					addEdge2(cntn, u);
					addEdge2(u, cntn);
					int pop;
					do {
						pop = sta[top--];
						addEdge2(cntn, pop);
						addEdge2(pop, cntn);
					} while (pop != v);
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	// 迭代版
	public static void tarjan2(int node) {
		stacksize = 0;
		push(node, -1, 0, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (status == -1) {
				dfn[u] = low[u] = ++cntd;
				sta[++top] = u;
				e = head1[u];
			} else {
				v = to1[e];
				if (status == 0) {
					low[u] = Math.min(low[u], low[v]);
					if (low[v] >= dfn[u]) {
						cntn++;
						addEdge2(cntn, u);
						addEdge2(u, cntn);
						int pop;
						do {
							pop = sta[top--];
							addEdge2(cntn, pop);
							addEdge2(pop, cntn);
						} while (pop != v);
					}
				} else {
					low[u] = Math.min(low[u], dfn[v]);
				}
				e = next1[e];
			}
			if (e != 0) {
				v = to1[e];
				if (dfn[v] == 0) {
					push(u, 0, 0, e);
					push(v, -1, 0, -1);
				} else {
					push(u, 1, 0, e);
				}
			}
		}
	}

	// 递归版
	public static void dfs1(int u, int fa) {
		nid[u] = ++cnti;
		dist[u] = dist[fa] + (u <= n ? 1 : 0);
		dep[u] = dep[fa] + 1;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa) {
				dfs1(v, u);
			}
		}
	}

	// 迭代版
	public static void dfs2(int cur, int father) {
		stacksize = 0;
		push(cur, 0, father, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				nid[u] = ++cnti;
				dist[u] = dist[fa] + (u <= n ? 1 : 0);
				dep[u] = dep[fa] + 1;
				stjump[u][0] = fa;
				for (int p = 1; p < MAXP; p++) {
					stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
				}
				e = head2[u];
			} else {
				e = next2[e];
			}
			if (e != 0) {
				push(u, 0, fa, e);
				if (to2[e] != fa) {
					push(to2[e], 0, u, -1);
				}
			}
		}
	}

	public static int getLca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a; a = b; b = tmp;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (dep[stjump[a][p]] >= dep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static int getDist(int x, int y) {
		return dist[x] + dist[y] - 2 * dist[getLca(x, y)];
	}

	public static void sort(int[] nums, int l, int r) {
		if (l >= r) return;
		int i = l, j = r;
		int pivot = nums[(l + r) >> 1];
		while (i <= j) {
			while (nid[nums[i]] < nid[pivot]) i++;
			while (nid[nums[j]] > nid[pivot]) j--;
			if (i <= j) {
				int tmp = nums[i]; nums[i] = nums[j]; nums[j] = tmp;
				i++;
				j--;
			}
		}
		sort(nums, l, j);
		sort(nums, i, r);
	}

	public static int compute() {
		sort(arr, 1, s);
		int ans = 0;
		for (int i = 1; i < s; i++) {
			ans += getDist(arr[i], arr[i + 1]);
		}
		ans += getDist(arr[1], arr[s]);
		ans /= 2;
		ans += getLca(arr[1], arr[s]) <= n ? 1 : 0;
		ans -= s;
		return ans;
	}

	public static void prepare() {
		cnt1 = cnt2 = cntd = top = cnti = 0;
		for (int i = 1; i <= n; i++) {
			head1[i] = head2[i] = dfn[i] = 0;
			head2[i + n] = 0;
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		t = in.nextInt();
		for (int c = 1; c <= t; c++) {
			n = in.nextInt();
			m = in.nextInt();
			cntn = n;
			prepare();
			for (int i = 1, u, v; i <= m; i++) {
				u = in.nextInt();
				v = in.nextInt();
				addEdge1(u, v);
				addEdge1(v, u);
			}
			// tarjan1(1);
			tarjan2(1);
			// dfs1(1, 0);
			dfs2(1, 0);
			q = in.nextInt();
			for (int i = 1; i <= q; i++) {
				s = in.nextInt();
				for (int j = 1; j <= s; j++) {
					arr[j] = in.nextInt();
				}
				out.println(compute());
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
