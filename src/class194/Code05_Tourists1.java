package class194;

// 游客，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF487E
// 测试链接 : https://codeforces.com/problemset/problem/487/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeMap;

public class Code05_Tourists1 {

	public static int MAXN = 100001;
	public static int MAXM = 100001;
	public static int INF = 1000000001;
	public static int n, m, q, cntn;
	public static int[] arr = new int[MAXN];

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
	public static int cnts;

	public static int[] fa = new int[MAXN << 1];
	public static int[] dep = new int[MAXN << 1];
	public static int[] siz = new int[MAXN << 1];
	public static int[] son = new int[MAXN << 1];
	public static int[] top = new int[MAXN << 1];
	public static int[] nid = new int[MAXN << 1];
	public static int cnti;

	public static HashMap<Integer, TreeMap<Integer, Integer>> maps = new HashMap<>();
	public static int[] val = new int[MAXN << 1];
	public static int[] minv = new int[MAXN << 3];

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

	public static void addNum(int u, int num) {
		if (!maps.containsKey(u)) {
			maps.put(u, new TreeMap<>());
		}
		maps.get(u).put(num, maps.get(u).getOrDefault(num, 0) + 1);
	}

	public static void delNum(int u, int num) {
		int cnt = maps.get(u).get(num);
		if (cnt == 1) {
			maps.get(u).remove(num);
		} else {
			maps.get(u).put(num, cnt - 1);
		}
	}

	public static int getMin(int u) {
		return maps.get(u).firstKey();
	}

	public static void tarjan(int u) {
		dfn[u] = low[u] = ++cntd;
		sta[++cnts] = u;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			int v = to1[e];
			if (dfn[v] == 0) {
				tarjan(v);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >= dfn[u]) {
					cntn++;
					addEdge2(cntn, u);
					addEdge2(u, cntn);
					int pop;
					do {
						pop = sta[cnts--];
						addEdge2(cntn, pop);
						addEdge2(pop, cntn);
					} while (pop != v);
				}
			} else {
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	public static void dfs1(int u, int f) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != f) {
				dfs1(v, u);
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
				if (u > n) {
					addNum(u, arr[v]);
				}
			}
		}
	}

	public static void dfs2(int u, int t) {
		top[u] = t;
		nid[u] = ++cnti;
		val[nid[u]] = u <= n ? arr[u] : getMin(u);
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head2[u], v; e > 0; e = next2[e]) {
			v = to2[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static void up(int i) {
		minv[i] = Math.min(minv[i << 1], minv[i << 1 | 1]);
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			minv[i] = val[l];
		} else {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static void update(int jobi, int jobv, int l, int r, int i) {
		if (l == r) {
			minv[i] = jobv;
		} else {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				update(jobi, jobv, l, mid, i << 1);
			} else {
				update(jobi, jobv, mid + 1, r, i << 1 | 1);
			}
			up(i);
		}
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return minv[i];
		}
		int mid = (l + r) / 2;
		int ans = INF;
		if (jobl <= mid) {
			ans = Math.min(ans, query(jobl, jobr, l, mid, i << 1));
		}
		if (jobr > mid) {
			ans = Math.min(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
		}
		return ans;
	}

	public static int pathMin(int x, int y) {
		int ans = INF;
		while (top[x] != top[y]) {
			if (dep[top[x]] < dep[top[y]]) {
				int tmp = x;
				x = y;
				y = tmp;
			}
			ans = Math.min(ans, query(nid[top[x]], nid[x], 1, cnti, 1));
			x = fa[top[x]];
		}
		if (dep[x] < dep[y]) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		ans = Math.min(ans, query(nid[y], nid[x], 1, cnti, 1));
		if (y > n) {
			ans = Math.min(ans, arr[fa[y]]);
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		q = in.nextInt();
		cntn = n;
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge1(u, v);
			addEdge1(v, u);
		}
		tarjan(1);
		dfs1(1, 0);
		dfs2(1, 1);
		build(1, cnti, 1);
		for (int i = 1; i <= q; i++) {
			char op = in.nextChar();
			int x = in.nextInt();
			int y = in.nextInt();
			if (op == 'C') {
				int father = fa[x];
				if (father > 0) {
					delNum(father, arr[x]);
					addNum(father, y);
					update(nid[father], getMin(father), 1, cnti, 1);
				}
				arr[x] = y;
				update(nid[x], y, 1, cnti, 1);
			} else {
				out.println(pathMin(x, y));
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

		char nextChar() throws IOException {
			int c;
			do {
				c = readByte();
				if (c == -1)
					return 0;
			} while (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')));
			return (char) c;
		}
	}

}
