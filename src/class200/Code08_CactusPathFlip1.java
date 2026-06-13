package class200;

// 仙人掌路径翻转，java版
// 给定n个点、m条边的仙人掌图，没有自环，没有重边，1号节点是仙人掌的根
// 输入保证每个简单环的边数都为奇数，所以点x到根的最短路和最长路都是唯一的
// 以点x为头的子仙人掌，是指删掉根到x路径上的边后，包含x的连通块
// 每个节点只有黑白两种颜色，初始时所有节点为黑，一共有q条操作，类型如下
// 操作 1 x : 点x到根的最短路上所有节点，颜色翻转
// 操作 2 x : 点x到根的最长路上所有节点，颜色翻转
// 操作 3 x : 查询以点x为头的子仙人掌中，黑色节点的数量
// 1 <= n、m、q <= 5 * 10^4
// 测试链接 : https://uoj.ac/problem/158
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code08_CactusPathFlip1 {

	public static int MAXN = 100001;
	public static int n, m, q, cntn;

	// 原图
	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXN << 1];
	public static int[] to1 = new int[MAXN << 1];
	public static int cnt1;

	// 圆方树
	public static int[] head2 = new int[MAXN];
	public static int[] next2 = new int[MAXN];
	public static int[] to2 = new int[MAXN];
	public static int cnt2;

	// tarjan算法
	public static int[] dfn = new int[MAXN];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int stasiz;

	// 树链剖分
	public static int[] fa = new int[MAXN];
	public static int[] dep = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];

	// 仙人掌中，一个节点可能是多个环的环顶，但不做环顶的话，最多参与一个环
	// belongCycle[x]，表示节点x归属的环，x不是环顶节点
	public static int[] belongCycle = new int[MAXN];
	// pos[x]，表示节点x在归属环中的位置，x不是环顶节点
	public static int[] pos = new int[MAXN];

	// 环顶节点，只对方点有意义
	public static int[] cycleRoot = new int[MAXN];
	// 环的边数，只对方点有意义
	public static int[] cycleLen = new int[MAXN];

	// 除了环顶和重儿子之外，环中圆点的dfn编号范围，只对方点有意义
	public static int[] cyclel = new int[MAXN];
	public static int[] cycler = new int[MAXN];

	// 子树上，所有后代节点的dfn编号范围，只对圆点有意义
	public static int[] treel = new int[MAXN];
	public static int[] treer = new int[MAXN];

	// dfnType[x] = 0，dfn序号为x的节点是方点
	// dfnType[x] = 1，dfn序号为x的节点是圆点，位于环顶到环中重儿子的短路径侧
	// dfnType[x] = 2，dfn序号为x的节点是圆点，位于环顶到环中重儿子的长路径侧
	// dfnType[x] = 3，dfn序号为x的节点是圆点，如果不是1或2类型，那就是3类型，表示必经点
	// 必经点的情况比较多，可能是环中重儿子 或者 只做环顶 或者 不参与任何环
	// 总之无论短路径还是长路径，通过重链向上必经的点，就算3类型
	public static int[] dfnType = new int[MAXN];

	// 短路径线段树
	public static int[] all1 = new int[MAXN << 2];
	public static int[] black1 = new int[MAXN << 2];
	public static boolean[] lazy1 = new boolean[MAXN << 2];

	// 长路径线段树
	public static int[] all2 = new int[MAXN << 2];
	public static int[] black2 = new int[MAXN << 2];
	public static boolean[] lazy2 = new boolean[MAXN << 2];

	// 必经点线段树
	public static int[] all3 = new int[MAXN << 2];
	public static int[] black3 = new int[MAXN << 2];
	public static boolean[] lazy3 = new boolean[MAXN << 2];

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

	// 注意先统计cnt，然后pos的赋值要从大到小
	// 因为当前弹栈顺序是，环上最深的点 -> ... -> v
	// 链式前向星是头插法，先插入的节点，后遍历
	// 所以后续cycleDfn时，环上分配dfn的顺序是，v -> ... -> 环上最深的点
	// 于是让pos从大到小赋值，为了匹配环上每个点的dfn分配顺序
	// 后续才能用pos正确的判断短路径/长路径
	public static void cycleLink(int u, int v) {
		cntn++;
		cycleRoot[cntn] = u;
		addEdge2(u, cntn);
		int tmp = stasiz;
		int pop;
		int cnt = 0;
		do {
			pop = sta[tmp--];
			cnt++;
		} while (pop != v);
		cycleLen[cntn] = cnt + 1;
		do {
			pop = sta[stasiz--];
			belongCycle[pop] = cntn;
			pos[pop] = cnt--;
			addEdge2(cntn, pop);
		} while (pop != v);
	}

	public static void tarjan(int u, int preEdge) {
		dfn[u] = low[u] = ++cntd;
		sta[++stasiz] = u;
		for (int e = head1[u]; e > 0; e = next1[e]) {
			if ((e ^ 1) == preEdge) {
				continue;
			}
			int v = to1[e];
			if (dfn[v] == 0) {
				tarjan(v, e);
				if (low[v] < dfn[u]) {
					low[u] = Math.min(low[u], low[v]);
				} else if (low[v] > dfn[u]) {
					stasiz--;
					addEdge2(u, v);
				} else {
					cycleLink(u, v);
				}
			} else if (dfn[v] < dfn[u]) {
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
			}
		}
	}

	public static void cycleDfn(int u) {
		int h = son[u];
		boolean near = pos[h] * 2 < cycleLen[u];
		cyclel[u] = cntd + 1;
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa[u] && v != h) {
				dfn[v] = ++cntd;
				if ((near && pos[v] < pos[h]) || (!near && pos[v] > pos[h])) {
					dfnType[cntd] = 1;
				} else {
					dfnType[cntd] = 2;
				}
			}
		}
		cycler[u] = cntd;
		dfn[h] = ++cntd;
	}

	public static void dfs2(int u, int t) {
		top[u] = t;
		if (dfn[u] == 0) {
			dfn[u] = ++cntd;
		}
		if (u > n) {
			cycleDfn(u);
		}
		if (u <= n) {
			treel[u] = cntd + 1;
		}
		if (son[u] != 0) {
			dfs2(son[u], t);
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
		if (u <= n) {
			treer[u] = cntd;
		}
	}

	public static void up(int i) {
		all1[i] = all1[i << 1] + all1[i << 1 | 1];
		all2[i] = all2[i << 1] + all2[i << 1 | 1];
		all3[i] = all3[i << 1] + all3[i << 1 | 1];
		black1[i] = black1[i << 1] + black1[i << 1 | 1];
		black2[i] = black2[i << 1] + black2[i << 1 | 1];
		black3[i] = black3[i << 1] + black3[i << 1 | 1];
	}

	public static void reverse1(int i) {
		black1[i] = all1[i] - black1[i];
		lazy1[i] = !lazy1[i];
	}

	public static void reverse2(int i) {
		black2[i] = all2[i] - black2[i];
		lazy2[i] = !lazy2[i];
	}

	public static void reverse3(int i) {
		black3[i] = all3[i] - black3[i];
		lazy3[i] = !lazy3[i];
	}

	public static void down(int i) {
		if (lazy1[i]) {
			reverse1(i << 1);
			reverse1(i << 1 | 1);
			lazy1[i] = false;
		}
		if (lazy2[i]) {
			reverse2(i << 1);
			reverse2(i << 1 | 1);
			lazy2[i] = false;
		}
		if (lazy3[i]) {
			reverse3(i << 1);
			reverse3(i << 1 | 1);
			lazy3[i] = false;
		}
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			if (dfnType[l] == 1) {
				all1[i] = black1[i] = 1;
			}
			if (dfnType[l] == 2) {
				all2[i] = black2[i] = 1;
			}
			if (dfnType[l] == 3) {
				all3[i] = black3[i] = 1;
			}
		} else {
			int mid = (l + r) >> 1;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	public static void reverse(int jobl, int jobr, int jobt, int l, int r, int i) {
		if (jobl > jobr) {
			return;
		}
		if (jobl <= l && r <= jobr) {
			if (jobt == 1 || jobt == 3) {
				reverse1(i);
			}
			if (jobt == 2 || jobt == 3) {
				reverse2(i);
			}
			reverse3(i);
			return;
		}
		down(i);
		int mid = (l + r) >> 1;
		if (jobl <= mid) {
			reverse(jobl, jobr, jobt, l, mid, i << 1);
		}
		if (mid < jobr) {
			reverse(jobl, jobr, jobt, mid + 1, r, i << 1 | 1);
		}
		up(i);
	}

	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl > jobr) {
			return 0;
		}
		if (jobl <= l && r <= jobr) {
			return black1[i] + black2[i] + black3[i];
		}
		down(i);
		int mid = (l + r) >> 1;
		int ans = 0;
		if (jobl <= mid) {
			ans += query(jobl, jobr, l, mid, i << 1);
		}
		if (mid < jobr) {
			ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
		}
		return ans;
	}

	public static void flipCycle(int u, int x, int op) {
		int h = son[u];
		boolean near = pos[x] * 2 < cycleLen[u];
		if ((near && op == 1) || (!near && op == 2)) {
			reverse(cyclel[u], dfn[x], 3, 1, cntn, 1);
			if (pos[h] < pos[x]) {
				reverse(dfn[h], dfn[h], 3, 1, cntn, 1);
			}
		} else {
			reverse(dfn[x], cycler[u], 3, 1, cntn, 1);
			if (pos[h] > pos[x]) {
				reverse(dfn[h], dfn[h], 3, 1, cntn, 1);
			}
		}
	}

	public static void flip(int x, int op) {
		while (x != 0) {
			int xtop = top[x];
			if (x != xtop) {
				if (xtop <= n) {
					if (belongCycle[xtop] == 0) {
						reverse(dfn[xtop], dfn[x], op, 1, cntn, 1);
						x = fa[xtop];
					} else {
						reverse(dfn[son[xtop]], dfn[x], op, 1, cntn, 1);
						x = xtop;
					}
				} else {
					reverse(cyclel[xtop], dfn[x], op, 1, cntn, 1);
					x = fa[xtop];
				}
			} else {
				if (belongCycle[x] == 0) {
					reverse(dfn[x], dfn[x], op, 1, cntn, 1);
					x = fa[x];
				} else {
					flipCycle(belongCycle[x], x, op);
					x = cycleRoot[belongCycle[x]];
				}
			}
		}
	}

	public static void prepare() {
		tarjan(1, 0);
		cntd = 0;
		for (int i = 1; i <= n; i++) {
			dfn[i] = 0;
		}
		dfs1(1, 0);
		dfs2(1, 1);
		for (int i = 1; i <= n; i++) {
			if (dfnType[dfn[i]] == 0) {
				dfnType[dfn[i]] = 3;
			}
		}
		build(1, cntn, 1);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		q = in.nextInt();
		cntn = n;
		cnt1 = 1;
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge1(u, v);
			addEdge1(v, u);
		}
		prepare();
		for (int i = 1, op, x; i <= q; i++) {
			op = in.nextInt();
			x = in.nextInt();
			if (op == 1 || op == 2) {
				flip(x, op);
			} else {
				int ans = query(dfn[x], dfn[x], 1, cntn, 1);
				ans += query(treel[x], treer[x], 1, cntn, 1);
				out.println(ans);
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
