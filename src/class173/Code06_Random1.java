package class173;

// 树上分块模版题，随机撒点，java版
// 一共有n个节点，每个节点有点权，给定n-1条边，所有节点连成一棵树
// 接下来有m条操作，每条操作都要打印两个答案，描述如下
// 操作 k x1 y1 x2 y2 .. (一共k个点对) 
// 每个点对(x, y)，在树上都有从x到y的路径，那么k个点对就有k条路径
// 先打印k条路径上不同点权的数量，再打印点权集合中没有出现的最小非负数(mex)
// 1 <= n、点对总数 <= 10^5    点权 <= 30000
// 题目要求强制在线，具体规则可以打开测试链接查看
// 测试链接 : https://www.luogu.com.cn/problem/P3603
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code06_Random1 {

	public static int MAXN = 100001;
	public static int MAXB = 301;
	public static int MAXV = 30001;
	public static int MAXP = 17;
	public static int n, m, f, k;
	public static int[] arr = new int[MAXN];

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	// 树上倍增求LCA
	public static int[] dep = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];

	// 随机撒点，预处理阶段需要的信息
	// markNum表示关键点数量
	public static int markNum;
	// vis[i]表示i号节点是否已经是关键点
	public static boolean[] vis = new boolean[MAXN];
	// markNode[k] = i 表示第k个关键点是编号为i的节点
	public static int[] markNode = new int[MAXB];

	// 随机撒点，预处理阶段生成的信息
	// kthMark[i] = k 表示i号节点是第k个关键点，kthMark[i] = 0 表示i号节点是非关键点
	public static int[] kthMark = new int[MAXN];
	// up[i] = j，表示i号节点是关键点，它往上跳到最近的关键点是j号节点
	public static int[] up = new int[MAXN];
	// downSet[k]的含义，路径是[第k个的关键点 .. 最近的上方关键点)，沿途所有节点值组成的位图
	public static BitSet[] downSet = new BitSet[MAXB];

	public static BitSet ans = new BitSet();

	static class BitSet {

		int len;

		public int[] set;

		public BitSet() {
			len = (MAXV + 31) / 32;
			set = new int[len];
		}

		public void clear() {
			for (int i = 0; i < len; i++) {
				set[i] = 0;
			}
		}

		public void setOne(int v) {
			set[v / 32] |= 1 << (v % 32);
		}

		public void or(BitSet obj) {
			for (int i = 0; i < len; i++) {
				set[i] |= obj.set[i];
			}
		}

		public int countOnes() {
			int ans = 0;
			for (int x : set) {
				ans += Integer.bitCount(x);
			}
			return ans;
		}

		public int mex() {
			for (int i = 0, inv; i < len; i++) {
				inv = ~set[i];
				if (inv != 0) {
					return i * 32 + Integer.numberOfTrailingZeros(inv);
				}
			}
			return -1;
		}

	}

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 树上倍增递归版
	public static void dfs1(int u, int fa) {
		dep[u] = dep[fa] + 1;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != fa) {
				dfs1(to[e], u);
			}
		}
	}

	// 树上倍增迭代版，讲解118进行了详细讲述
	public static int[][] ufe = new int[MAXN][3];

	public static int stacksize, cur, fath, edge;

	public static void push(int u, int f, int e) {
		ufe[stacksize][0] = u;
		ufe[stacksize][1] = f;
		ufe[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		cur = ufe[stacksize][0];
		fath = ufe[stacksize][1];
		edge = ufe[stacksize][2];
	}

	public static void dfs2() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) {
				dep[cur] = dep[fath] + 1;
				stjump[cur][0] = fath;
				for (int p = 1; p < MAXP; p++) {
					stjump[cur][p] = stjump[stjump[cur][p - 1]][p - 1];
				}
				edge = head[cur];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(cur, fath, edge);
				if (to[edge] != fath) {
					push(to[edge], cur, -1);
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

	public static void query(int x, int xylca) {
		while (kthMark[x] == 0 && x != xylca) {
			ans.setOne(arr[x]);
			x = stjump[x][0];
		}
		while (up[x] > 0 && dep[up[x]] > dep[xylca]) {
			ans.or(downSet[kthMark[x]]);
			x = up[x];
		}
		while (x != xylca) {
			ans.setOne(arr[x]);
			x = stjump[x][0];
		}
	}

	public static void updateAns(int x, int y) {
		int xylca = lca(x, y);
		ans.setOne(arr[xylca]);
		query(x, xylca);
		query(y, xylca);
	}

	public static void prepare() {
		dfs2();
		int len = (int) Math.sqrt(20.0 * n);
		markNum = (n + len - 1) / len;
		for (int b = 1, pick; b <= markNum; b++) {
			do {
				pick = (int) (Math.random() * n) + 1;
			} while (vis[pick]);
			vis[pick] = true;
			markNode[b] = pick;
			kthMark[pick] = b;
		}
		for (int b = 1, cur; b <= markNum; b++) {
			downSet[b] = new BitSet();
			downSet[b].setOne(arr[markNode[b]]);
			cur = stjump[markNode[b]][0];
			while (cur != 0) {
				if (kthMark[cur] > 0) {
					up[markNode[b]] = cur;
					break;
				} else {
					downSet[b].setOne(arr[cur]);
					cur = stjump[cur][0];
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		f = in.nextInt();
		for (int i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
		}
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		prepare();
		for (int i = 1, last = 0; i <= m; i++) {
			ans.clear();
			k = in.nextInt();
			for (int j = 1, x, y; j <= k; j++) {
				x = in.nextInt();
				y = in.nextInt();
				if (f > 0) {
					x = x ^ last;
					y = y ^ last;
				}
				updateAns(x, y);
			}
			int ans1 = ans.countOnes();
			int ans2 = ans.mex();
			out.println(ans1 + " " + ans2);
			last = ans1 + ans2;
		}
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {
		private final byte[] buffer = new byte[1 << 20];
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
