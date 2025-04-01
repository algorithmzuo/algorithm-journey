package class164;

// 边权上限内第k大点权，java版
// 图里有n个点，m条无向边，点有点权，边有边权，图里可能有若干个连通的部分
// 一共有q条查询，查询格式如下
// 查询 u x k : 从点u开始，只能走过权值<=x的边
//              所有能到达的点中，打印第k大点权，如果不存在打印-1
// 1 <= n <= 10^5
// 0 <= m、q <= 5 * 10^5
// 1 <= 点权、边权 <= 10^9
// 本题要求强制在线，具体规定请打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P7834
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是通过不了
// 因为这道题根据C++的运行空间，制定通过标准，根本没考虑java的用户
// 想通过用C++实现，本节课Code07_Peaks2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class Code07_Peaks1 {

	public static int MAXN = 100001;
	public static int MAXK = 200001;
	public static int MAXM = 500001;
	public static int MAXT = MAXN * 40;
	public static int MAXH = 20;
	public static int n, m, q;

	public static int[] node = new int[MAXN];
	public static int[] sorted = new int[MAXN];
	public static int diff;
	public static int[][] edge = new int[MAXM][3];

	// 并查集
	public static int[] father = new int[MAXK];

	// Kruskal重构树
	public static int[] head = new int[MAXK];
	public static int[] next = new int[MAXK];
	public static int[] to = new int[MAXK];
	public static int cntg = 0;
	public static int[] nodeKey = new int[MAXK];
	public static int cntu;

	// 倍增表
	public static int[][] stjump = new int[MAXK][MAXH];
	// 子树上的叶节点个数
	public static int[] leafsiz = new int[MAXK];
	// 子树上叶节点的dfn序号最小值
	public static int[] leafDfnMin = new int[MAXK];
	// leafseg[i] = j，表示dfn序号为i的叶节点，原始编号为j
	public static int[] leafseg = new int[MAXK];
	// dfn的计数
	public static int cntd = 0;

	// 可持久化线段树
	// 线段树的下标为某个数字，所以是值域线段树
	// 数值范围[l..r]上，一共有几个数字，就是numcnt的含义
	public static int[] root = new int[MAXN];
	public static int[] ls = new int[MAXT];
	public static int[] rs = new int[MAXT];
	public static int[] numcnt = new int[MAXT];
	public static int cntt = 0;

	public static int kth(int num) {
		int left = 1, right = diff, mid;
		while (left <= right) {
			mid = (left + right) / 2;
			if (sorted[mid] == num) {
				return mid;
			} else if (sorted[mid] < num) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return -1;
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			sorted[i] = node[i];
		}
		sorted[n + 1] = 0;
		Arrays.sort(sorted, 1, n + 2);
		diff = 1;
		for (int i = 2; i <= n + 1; i++) {
			if (sorted[diff] != sorted[i]) {
				sorted[++diff] = sorted[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			node[i] = kth(node[i]);
		}
	}

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void kruskalRebuild() {
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
		Arrays.sort(edge, 1, m + 1, (a, b) -> a[2] - b[2]);
		cntu = n;
		for (int i = 1, fx, fy; i <= m; i++) {
			fx = find(edge[i][0]);
			fy = find(edge[i][1]);
			if (fx != fy) {
				father[fx] = father[fy] = ++cntu;
				father[cntu] = cntu;
				nodeKey[cntu] = edge[i][2];
				addEdge(cntu, fx);
				addEdge(cntu, fy);
			}
		}
	}

	public static void dfs(int u, int fa) {
		stjump[u][0] = fa;
		for (int p = 1; p < MAXH; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e > 0; e = next[e]) {
			dfs(to[e], u);
		}
		if (u <= n) {
			leafsiz[u] = 1;
			leafDfnMin[u] = ++cntd;
			leafseg[cntd] = u;
		} else {
			leafsiz[u] = 0;
			leafDfnMin[u] = n + 1;
		}
		for (int e = head[u]; e > 0; e = next[e]) {
			leafsiz[u] += leafsiz[to[e]];
			leafDfnMin[u] = Math.min(leafDfnMin[u], leafDfnMin[to[e]]);
		}
	}

	// 可持久化线段树的build
	public static int build(int l, int r) {
		int rt = ++cntt;
		numcnt[rt] = 0;
		if (l < r) {
			int mid = (l + r) / 2;
			ls[rt] = build(l, mid);
			rs[rt] = build(mid + 1, r);
		}
		return rt;
	}

	// 数值范围[l..r]，加了一个数字jobi，生成新版本的可持久化线段树
	public static int insert(int jobi, int l, int r, int i) {
		int rt = ++cntt;
		ls[rt] = ls[i];
		rs[rt] = rs[i];
		numcnt[rt] = numcnt[i] + 1;
		if (l < r) {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				ls[rt] = insert(jobi, l, mid, ls[rt]);
			} else {
				rs[rt] = insert(jobi, mid + 1, r, rs[rt]);
			}
		}
		return rt;
	}

	// 可持久化线段树，前版本pre，后版本post，两个版本做差，得到数值范围[l..r]的词频
	// 查询这个数值范围上，第jobk大的数字是什么
	public static int query(int jobk, int l, int r, int pre, int post) {
		if (l == r) {
			return l;
		}
		int rsize = numcnt[rs[post]] - numcnt[rs[pre]];
		int mid = (l + r) / 2;
		if (rsize >= jobk) {
			return query(jobk, mid + 1, r, rs[pre], rs[post]);
		} else {
			return query(jobk - rsize, l, mid, ls[pre], ls[post]);
		}
	}

	public static int kthMax(int u, int x, int k) {
		for (int p = MAXH - 1; p >= 0; p--) {
			if (stjump[u][p] > 0 && nodeKey[stjump[u][p]] <= x) {
				u = stjump[u][p];
			}
		}
		int idx = query(k, 1, diff, root[leafDfnMin[u] - 1], root[leafDfnMin[u] + leafsiz[u] - 1]);
		return sorted[idx];
	}

	public static void main(String[] args) {
		FastIO io = new FastIO(System.in, System.out);
		n = io.nextInt();
		m = io.nextInt();
		q = io.nextInt();
		for (int i = 1; i <= n; i++) {
			node[i] = io.nextInt();
		}
		for (int i = 1; i <= m; i++) {
			edge[i][0] = io.nextInt();
			edge[i][1] = io.nextInt();
			edge[i][2] = io.nextInt();
		}
		prepare();
		kruskalRebuild();
		for (int i = 1; i <= cntu; i++) {
			if (i == father[i]) {
				dfs(i, 0);
			}
		}
		root[0] = build(1, diff);
		for (int i = 1; i <= n; i++) {
			root[i] = insert(node[leafseg[i]], 1, diff, root[i - 1]);
		}
		for (int i = 1, u, x, k, lastAns = 0; i <= q; i++) {
			u = io.nextInt();
			x = io.nextInt();
			k = io.nextInt();
			u = (u ^ lastAns) % n + 1;
			x = x ^ lastAns;
			k = (k ^ lastAns) % n + 1;
			lastAns = kthMax(u, x, k);
			if (lastAns == 0) {
				io.writelnInt(-1);
			} else {
				io.writelnInt(lastAns);
			}
		}
		io.flush();
	}

	// 读写工具类
	static class FastIO {
		private final InputStream is;
		private final OutputStream os;
		private final byte[] inbuf = new byte[1 << 16];
		private int lenbuf = 0;
		private int ptrbuf = 0;
		private final StringBuilder outBuf = new StringBuilder();

		public FastIO(InputStream is, OutputStream os) {
			this.is = is;
			this.os = os;
		}

		private int readByte() {
			if (ptrbuf >= lenbuf) {
				ptrbuf = 0;
				try {
					lenbuf = is.read(inbuf);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				if (lenbuf == -1) {
					return -1;
				}
			}
			return inbuf[ptrbuf++] & 0xff;
		}

		private int skip() {
			int b;
			while ((b = readByte()) != -1) {
				if (b > ' ') {
					return b;
				}
			}
			return -1;
		}

		public int nextInt() {
			int b = skip();
			if (b == -1) {
				throw new RuntimeException("No more integers (EOF)");
			}
			boolean negative = false;
			if (b == '-') {
				negative = true;
				b = readByte();
			}
			int val = 0;
			while (b >= '0' && b <= '9') {
				val = val * 10 + (b - '0');
				b = readByte();
			}
			return negative ? -val : val;
		}

		public void write(String s) {
			outBuf.append(s);
		}

		public void writeInt(int x) {
			outBuf.append(x);
		}

		public void writelnInt(int x) {
			outBuf.append(x).append('\n');
		}

		public void flush() {
			try {
				os.write(outBuf.toString().getBytes());
				os.flush();
				outBuf.setLength(0);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
