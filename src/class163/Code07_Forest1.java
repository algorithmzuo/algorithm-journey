package class163;

// 森林，java版
// 一共有n个节点，编号1~n，初始时给定m条边，所有节点可能组成森林结构
// 每个节点都给定非负的点权，一共有t条操作，每条操作是如下两种类型中的一种
// 操作 Q x y k : 点x到点y路径上所有的权值中，打印第k小的权值是多少
//                题目保证x和y联通，并且路径上至少有k个点
// 操作 L x y   : 点x和点y之间连接一条边
//                题目保证操作后，所有节点仍然是森林
// 题目要求强制在线，请不要使用离线算法
// 1 <= n、m、t <= 8 * 10^4
// 点权 <= 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P3302
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code07_Forest1 {

	public static int MAXN = 80001;
	public static int MAXT = MAXN * 110;
	public static int MAXH = 20;
	public static int testcase;
	public static int n, m, t;

	public static int[] arr = new int[MAXN];
	public static int[] sorted = new int[MAXN];
	public static int diff;

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	public static int[] root = new int[MAXN];
	public static int[] left = new int[MAXT];
	public static int[] right = new int[MAXT];
	public static int[] siz = new int[MAXT];
	public static int cntt = 0;

	public static int[] dep = new int[MAXN];
	public static int[] treeHead = new int[MAXN];
	public static int[] headSiz = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXH];

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

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static int insert(int jobi, int l, int r, int i) {
		int rt = ++cntt;
		left[rt] = left[i];
		right[rt] = right[i];
		siz[rt] = siz[i] + 1;
		if (l < r) {
			int mid = (l + r) / 2;
			if (jobi <= mid) {
				left[rt] = insert(jobi, l, mid, left[rt]);
			} else {
				right[rt] = insert(jobi, mid + 1, r, right[rt]);
			}
		}
		return rt;
	}

	public static int query(int jobk, int l, int r, int u, int v, int lca, int lcafa) {
		if (l == r) {
			return l;
		}
		int lsize = siz[left[u]] + siz[left[v]] - siz[left[lca]] - siz[left[lcafa]];
		int mid = (l + r) / 2;
		if (lsize >= jobk) {
			return query(jobk, l, mid, left[u], left[v], left[lca], left[lcafa]);
		} else {
			return query(jobk - lsize, mid + 1, r, right[u], right[v], right[lca], right[lcafa]);
		}
	}

	// 递归版，C++可以通过，java无法通过，递归会爆栈
	public static void dfs1(int u, int fa, int treeh) {
		root[u] = insert(arr[u], 1, diff, root[fa]);
		dep[u] = dep[fa] + 1;
		treeHead[u] = treeh;
		headSiz[treeh]++;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXH; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e > 0; e = next[e]) {
			if (to[e] != fa) {
				dfs1(to[e], u, treeh);
			}
		}
	}

	// 迭代版，都可以通过
	// 讲解118，详解了从递归版改迭代版
	public static int[][] stack = new int[MAXN][4];

	public static int stackSize, cur, father, treehead, edge;

	public static void push(int cur, int father, int treehead, int edge) {
		stack[stackSize][0] = cur;
		stack[stackSize][1] = father;
		stack[stackSize][2] = treehead;
		stack[stackSize][3] = edge;
		stackSize++;
	}

	public static void pop() {
		--stackSize;
		cur = stack[stackSize][0];
		father = stack[stackSize][1];
		treehead = stack[stackSize][2];
		edge = stack[stackSize][3];
	}

	// dfs1的迭代版
	public static void dfs2(int i, int fa, int treeh) {
		stackSize = 0;
		push(i, fa, treeh, -1);
		while (stackSize > 0) {
			pop();
			if (edge == -1) {
				root[cur] = insert(arr[cur], 1, diff, root[father]);
				dep[cur] = dep[father] + 1;
				treeHead[cur] = treehead;
				headSiz[treehead]++;
				stjump[cur][0] = father;
				for (int p = 1; p < MAXH; p++) {
					stjump[cur][p] = stjump[stjump[cur][p - 1]][p - 1];
				}
				edge = head[cur];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(cur, father, treehead, edge);
				if (to[edge] != father) {
					push(to[edge], cur, treehead, -1);
				}
			}
		}
	}

	public static int lca(int a, int b) {
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

	public static int queryKth(int x, int y, int k) {
		int xylca = lca(x, y);
		int lcafa = stjump[xylca][0];
		int i = query(k, 1, diff, root[x], root[y], root[xylca], root[lcafa]);
		return sorted[i];
	}

	public static void connect(int x, int y) {
		addEdge(x, y);
		addEdge(y, x);
		int fx = treeHead[x];
		int fy = treeHead[y];
		if (headSiz[fx] >= headSiz[fy]) {
			dfs2(y, x, fx); // 调用dfs1的迭代版
		} else {
			dfs2(x, y, fy); // 调用dfs1的迭代版
		}
	}

	public static void prepare() {
		for (int i = 1; i <= n; i++) {
			sorted[i] = arr[i];
		}
		Arrays.sort(sorted, 1, n + 1);
		diff = 1;
		for (int i = 2; i <= n; i++) {
			if (sorted[diff] != sorted[i]) {
				sorted[++diff] = sorted[i];
			}
		}
		for (int i = 1; i <= n; i++) {
			arr[i] = kth(arr[i]);
		}
		for (int i = 1; i <= n; i++) {
			if (treeHead[i] == 0) {
				dfs2(i, 0, i); // 调用dfs1的迭代版
			}
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		testcase = in.nextInt();
		n = in.nextInt();
		m = in.nextInt();
		t = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, u, v; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		prepare();
		char op;
		int x, y, k, lastAns = 0;
		for (int i = 1; i <= t; i++) {
			op = in.nextChar();
			x = in.nextInt() ^ lastAns;
			y = in.nextInt() ^ lastAns;
			if (op == 'Q') {
				k = in.nextInt() ^ lastAns;
				lastAns = queryKth(x, y, k);
				out.println(lastAns);
			} else {
				connect(x, y);
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

		public double nextDouble() throws IOException {
			double num = 0, div = 1;
			byte b = readByte();
			while (isWhitespace(b))
				b = readByte();
			boolean minus = false;
			if (b == '-') {
				minus = true;
				b = readByte();
			}
			while (!isWhitespace(b) && b != '.' && b != -1) {
				num = num * 10 + (b - '0');
				b = readByte();
			}
			if (b == '.') {
				b = readByte();
				while (!isWhitespace(b) && b != -1) {
					num += (b - '0') / (div *= 10);
					b = readByte();
				}
			}
			return minus ? -num : num;
		}

		private boolean isWhitespace(byte b) {
			return b == ' ' || b == '\n' || b == '\r' || b == '\t';
		}
	}

}
