package class164;

// 删边和查询，java版
// 图里有n个点，m条无向边，初始时点权都不同，图里可能有若干个连通的部分
// 一共有q条操作，每条操作是如下两种类型中的一种
// 操作 1 x : 点x所在的连通区域中，假设点y拥有最大的点权
//            打印y的点权，然后把y的点权修改为0
// 操作 2 x : 删掉第x条边
// 1 <= n <= 2 * 10^5    1 <= m <= 3 * 10^5    1 <= q <= 5 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF1416D
// 测试链接 : https://codeforces.com/problemset/problem/1416/D
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class Code06_GraphQueries1 {

	public static int MAXN = 200001;
	public static int MAXK = 400001;
	public static int MAXM = 300001;
	public static int MAXQ = 500001;
	public static int MAXP = 20;
	public static int n, m, q;

	// 节点值的数组，需要记录，线段树也要使用
	public static int[] node = new int[MAXN];
	// 所有边的数组，逆序处理删除操作，设置每条边的权值
	public static int[][] edge = new int[MAXM][3];
	// 记录所有操作
	public static int[][] ques = new int[MAXQ][2];

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
	public static int[][] stjump = new int[MAXK][MAXP];
	// 子树上的叶节点个数
	public static int[] leafsiz = new int[MAXK];
	// 子树上叶节点的dfn序号最小值
	public static int[] leafDfnMin = new int[MAXK];
	// leafseg[i] = j，表示dfn序号为i的叶节点，原始编号为j
	public static int[] leafseg = new int[MAXK];
	// dfn的计数
	public static int cntd = 0;

	// 线段树的下标是dfn序号，维护范围内，拥有最大点权的dfn序号
	public static int[] maxValueDfn = new int[MAXN << 2];

	public static void prepare() {
		for (int i = 1; i <= q; i++) {
			if (ques[i][0] == 2) {
				edge[ques[i][1]][2] = -1;
			}
		}
		int weight = 0;
		for (int i = 1; i <= m; i++) {
			if (edge[i][2] != -1) {
				edge[i][2] = ++weight;
			}
		}
		for (int i = q; i >= 1; i--) {
			if (ques[i][0] == 2) {
				edge[ques[i][1]][2] = ++weight;
			}
		}
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
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
		for (int p = 1; p < MAXP; p++) {
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

	public static int getAncestor(int u, int limit) {
		for (int p = MAXP - 1; p >= 0; p--) {
			if (stjump[u][p] > 0 && nodeKey[stjump[u][p]] <= limit) {
				u = stjump[u][p];
			}
		}
		return u;
	}

	public static void up(int i) {
		int l = i << 1;
		int r = i << 1 | 1;
		if (node[leafseg[maxValueDfn[l]]] > node[leafseg[maxValueDfn[r]]]) {
			maxValueDfn[i] = maxValueDfn[l];
		} else {
			maxValueDfn[i] = maxValueDfn[r];
		}
	}

	public static void build(int l, int r, int i) {
		if (l == r) {
			maxValueDfn[i] = l;
		} else {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
			up(i);
		}
	}

	// dfn序号为jobi，点权更新成jobv
	public static void update(int jobi, int jobv, int l, int r, int i) {
		if (l == r) {
			node[leafseg[jobi]] = jobv;
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

	// dfn范围[jobl..jobr]，哪个节点拥有最大点权，返回该节点的dfn序号
	public static int query(int jobl, int jobr, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			return maxValueDfn[i];
		} else {
			int mid = (l + r) / 2;
			int ldfn = 0, rdfn = 0;
			if (jobl <= mid) {
				ldfn = query(jobl, jobr, l, mid, i << 1);
			}
			if (jobr > mid) {
				rdfn = query(jobl, jobr, mid + 1, r, i << 1 | 1);
			}
			if (node[leafseg[ldfn]] > node[leafseg[rdfn]]) {
				return ldfn;
			} else {
				return rdfn;
			}
		}
	}

	public static int queryAndUpdate(int x, int limit) {
		int anc = getAncestor(x, limit);
		int dfn = query(leafDfnMin[anc], leafDfnMin[anc] + leafsiz[anc] - 1, 1, n, 1);
		int ans = node[leafseg[dfn]];
		update(dfn, 0, 1, n, 1);
		return ans;
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
			edge[i][2] = 0;
		}
		for (int i = 1; i <= q; i++) {
			ques[i][0] = io.nextInt();
			ques[i][1] = io.nextInt();
		}
		prepare();
		kruskalRebuild();
		for (int i = 1; i <= cntu; i++) {
			if (i == father[i]) {
				dfs(i, 0);
			}
		}
		build(1, n, 1);
		int limit = m;
		for (int i = 1; i <= q; i++) {
			if (ques[i][0] == 1) {
				io.writelnInt(queryAndUpdate(ques[i][1], limit));
			} else {
				limit--;
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
