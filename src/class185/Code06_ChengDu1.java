package class185;

// 成都七中，java版
// 树上有n个点，每个点给定颜色，给定n-1条边
// 一共m条查询，查询之间不会相互影响，格式如下
// 查询 l r x : 只保留编号在[l, r]的节点，打印点x所在连通块的颜色数量
// 1 <= 所有数据 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P5311
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_ChengDu1 {

	public static int MAXN = 100001;
	public static int n, m;
	public static int[] color = new int[MAXN];

	// 建树
	public static int[] headg = new int[MAXN];
	public static int[] nxtg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int cntg;

	// 每个节点的问题列表
	public static int[] headq = new int[MAXN];
	public static int[] nxtq = new int[MAXN];
	public static int[] ql = new int[MAXN];
	public static int[] qr = new int[MAXN];
	public static int[] qid = new int[MAXN];
	public static int cntq;

	// 点分治
	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	// 每来到一个重心，收集节点列表，nodel和noder是解锁条件，ncolor是节点颜色
	public static int[] nodel = new int[MAXN];
	public static int[] noder = new int[MAXN];
	public static int[] ncolor = new int[MAXN];
	public static int cntNode;

	// 每来到一个重心，收集问题列表，只收集值得讨论的问题
	public static int[] quesl = new int[MAXN];
	public static int[] quesr = new int[MAXN];
	public static int[] quesid = new int[MAXN];
	public static int cntQues;

	// pos[v] = i，表示颜色v的左边界，最右位置出现在i
	public static int[] pos = new int[MAXN];
	// 树状数组
	public static int[] tree = new int[MAXN];
	// 问题答案
	public static int[] ans = new int[MAXN];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][5];
	public static int u, f, nl, nr, e;
	public static int stacksize;

	public static void push(int u, int f, int nl, int nr, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = nl;
		stack[stacksize][3] = nr;
		stack[stacksize][4] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		nl = stack[stacksize][2];
		nr = stack[stacksize][3];
		e = stack[stacksize][4];
	}

	public static void addEdge(int u, int v) {
		nxtg[++cntg] = headg[u];
		tog[cntg] = v;
		headg[u] = cntg;
	}

	public static void addQuery(int u, int l, int r, int id) {
		nxtq[++cntq] = headq[u];
		ql[cntq] = l;
		qr[cntq] = r;
		qid[cntq] = id;
		headq[u] = cntq;
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		if (i <= 0) {
			return;
		}
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static int sum(int i) {
		int ret = 0;
		while (i > 0) {
			ret += tree[i];
			i -= lowbit(i);
		}
		return ret;
	}

	public static int query(int l, int r) {
		return sum(r) - sum(l - 1);
	}

	// 找重心需要计算子树大小的递归版，java会爆栈，C++不会
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = headg[u]; e > 0; e = nxtg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// getSize1的迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = headg[u];
			} else {
				e = nxtg[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, 0, -1);
				}
			} else {
				for (int ei = headg[u]; ei > 0; ei = nxtg[ei]) {
					int v = tog[ei];
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
			for (int e = headg[u]; e > 0; e = nxtg[e]) {
				int v = tog[e];
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

	public static void dfs1(int u, int fa, int nl, int nr) {
		nodel[++cntNode] = nl;
		noder[cntNode] = nr;
		ncolor[cntNode] = color[u];
		for (int ei = headq[u]; ei > 0; ei = nxtq[ei]) {
			if (ql[ei] <= nl && nr <= qr[ei]) {
				quesl[++cntQues] = ql[ei];
				quesr[cntQues] = qr[ei];
				quesid[cntQues] = qid[ei];
			}
		}
		for (int e = headg[u]; e > 0; e = nxtg[e]) {
			int v = tog[e];
			if (v != fa && !vis[v]) {
				dfs1(v, u, Math.min(v, nl), Math.max(v, nr));
			}
		}
	}

	// dfs1的迭代版
	public static void dfs2(int cur, int fa, int nodeMin, int nodeMax) {
		stacksize = 0;
		push(cur, fa, nodeMin, nodeMax, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				nodel[++cntNode] = nl;
				noder[cntNode] = nr;
				ncolor[cntNode] = color[u];
				for (int ei = headq[u]; ei > 0; ei = nxtq[ei]) {
					if (ql[ei] <= nl && nr <= qr[ei]) {
						quesl[++cntQues] = ql[ei];
						quesr[cntQues] = qr[ei];
						quesid[cntQues] = qid[ei];
					}
				}
				e = headg[u];
			} else {
				e = nxtg[e];
			}
			if (e != 0) {
				push(u, f, nl, nr, e);
				int v = tog[e];
				if (v != f && !vis[v]) {
					push(v, u, Math.min(v, nl), Math.max(v, nr), -1);
				}
			}
		}
	}

	public static void sort(int[] al, int[] ar, int[] ai, int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = ar[(l + r) >> 1], tmp;
		while (i <= j) {
			while (ar[i] < pivot) i++;
			while (ar[j] > pivot) j--;
			if (i <= j) {
				tmp = al[i]; al[i] = al[j]; al[j] = tmp;
				tmp = ar[i]; ar[i] = ar[j]; ar[j] = tmp;
				tmp = ai[i]; ai[i] = ai[j]; ai[j] = tmp;
				i++; j--;
			}
		}
		sort(al, ar, ai, l, j);
		sort(al, ar, ai, i, r);
	}

	public static void calc(int u) {
		cntNode = 0;
		cntQues = 0;
		// dfs1(u, 0, u, u);
		dfs2(u, 0, u, u);
		sort(nodel, noder, ncolor, 1, cntNode);
		sort(quesl, quesr, quesid, 1, cntQues);
		for (int i = 1, j = 1; i <= cntQues; i++) {
			while (j <= cntNode && noder[j] <= quesr[i]) {
				if (nodel[j] > pos[ncolor[j]]) {
					add(pos[ncolor[j]], -1);
					pos[ncolor[j]] = nodel[j];
					add(pos[ncolor[j]], 1);
				}
				j++;
			}
			ans[quesid[i]] = Math.max(ans[quesid[i]], query(quesl[i], n));
		}
		for (int i = 1; i <= cntNode; i++) {
			add(pos[ncolor[i]], -1);
			pos[ncolor[i]] = 0;
		}
	}

	public static void solve(int u) {
		vis[u] = true;
		calc(u);
		for (int e = headg[u]; e > 0; e = nxtg[e]) {
			int v = tog[e];
			if (!vis[v]) {
				solve(getCentroid(v, u));
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1; i <= n; i++) {
			color[i] = in.nextInt();
		}
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1, l, r, x; i <= m; i++) {
			l = in.nextInt();
			r = in.nextInt();
			x = in.nextInt();
			addQuery(x, l, r, i);
		}
		solve(getCentroid(1, 0));
		for (int i = 1; i <= m; i++) {
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
