package class184;

// 快递员，java版
// 一共有n个节点，给定n-1条边，每条边给定边权，所有节点组成一棵树
// 对于点对(a, b)，假设你选择的中心点为x，那么点对的距离如下
// 点对(a, b)的距离 = a到x的路径权值和 + b到x的路径权值和
// 一共有m个点对，你需要选择中心点x，使得点对距离的最大值尽量小
// 打印这个最小的点对距离最大值
// 1 <= n、m <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4886
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_Courier1 {

	public static int MAXN = 100001;
	public static int n, m;
	public static int[] a = new int[MAXN];
	public static int[] b = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];

	public static int[] tree = new int[MAXN];
	public static int[] dist = new int[MAXN];

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][5];
	public static int u, f, d, t, e;
	public static int stacksize;

	public static void push(int u, int f, int d, int t, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = d;
		stack[stacksize][3] = t;
		stack[stacksize][4] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		d = stack[stacksize][2];
		t = stack[stacksize][3];
		e = stack[stacksize][4];
	}

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	// 得到子树大小递归版，java会爆栈，C++可以通过
	public static void getSize1(int u, int fa) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getSize1(v, u);
				siz[u] += siz[v];
			}
		}
	}

	// 得到子树大小迭代版
	public static void getSize2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, 0, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
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
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
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

	// 收集信息递归版，java会爆栈，C++可以通过
	public static void dfs1(int u, int fa, int d, int t) {
		tree[u] = t;
		dist[u] = d;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa) {
				dfs1(v, u, d + weight[e], t);
			}
		}
	}

	// 收集信息迭代版
	public static void dfs2(int cur, int fa, int di, int tr) {
		stacksize = 0;
		push(cur, fa, di, tr, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				tree[u] = t;
				dist[u] = d;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, d, t, e);
				int v = to[e];
				if (v != f) {
					push(v, u, d + weight[e], t, -1);
				}
			}
		}
	}

	public static int compute() {
		int ans = 1000000001;
		int u = getCentroid(1, 0);
		while (!vis[u]) {
			vis[u] = true;
			tree[u] = u;
			dist[u] = 0;
			for (int e = head[u]; e > 0; e = nxt[e]) {
				int v = to[e];
				int w = weight[e];
				// dfs1(v, u, w, v);
				dfs2(v, u, w, v);
			}
			int maxDist = 0, son = 0;
			for (int i = 1; i <= m; i++) {
				int curDist = dist[a[i]] + dist[b[i]];
				int t1 = tree[a[i]];
				int t2 = tree[b[i]];
				if (maxDist < curDist) {
					maxDist = curDist;
					son = t1 == t2 ? t1 : 0;
				} else if (maxDist == curDist && (t1 != t2 || t1 != son)) {
					son = 0;
				}
			}
			ans = Math.min(ans, maxDist);
			if (son == 0) {
				break;
			}
			u = getCentroid(son, u);
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
		}
		out.println(compute());
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
