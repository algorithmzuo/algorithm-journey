package class183;

// 路径魔力总和，java版
// 测试链接 : https://www.luogu.com.cn/problem/P5351
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_Maschera1 {

	public static int MAXN = 100001;
	public static int n, l, r, total;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static int[] tree = new int[MAXN];

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] maxPart = new int[MAXN];
	public static int centroid;

	public static int[] allMaxv = new int[MAXN];
	public static int[] allEdge = new int[MAXN];
	public static int cnta;

	public static int[] curMaxv = new int[MAXN];
	public static int[] curEdge = new int[MAXN];
	public static int cntc;

	public static long ans;

	// 讲解118，递归函数改成迭代所需要的栈
	public static int[][] stack = new int[MAXN][5];
	public static int stacksize, u, f, maxv, edge, e;

	public static void push(int u, int f, int maxv, int edge, int e) {
		stack[stacksize][0] = u;
		stack[stacksize][1] = f;
		stack[stacksize][2] = maxv;
		stack[stacksize][3] = edge;
		stack[stacksize][4] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = stack[stacksize][0];
		f = stack[stacksize][1];
		maxv = stack[stacksize][2];
		edge = stack[stacksize][3];
		e = stack[stacksize][4];
	}

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= r) {
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

	// 找重心的递归版，java会爆栈，C++可以通过
	public static void getCentroid1(int u, int fa) {
		siz[u] = 1;
		maxPart[u] = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getCentroid1(v, u);
				siz[u] += siz[v];
				maxPart[u] = Math.max(siz[v], maxPart[u]);
			}
		}
		maxPart[u] = Math.max(maxPart[u], total - siz[u]);
		if (centroid == 0 || maxPart[u] < maxPart[centroid]) {
			centroid = u;
		}
	}

	// 找重心的迭代版
	public static void getCentroid2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, 0, 0, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				maxPart[u] = 0;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, 0, 0, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(to[e], u, 0, 0, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
						maxPart[u] = Math.max(siz[v], maxPart[u]);
					}
				}
				maxPart[u] = Math.max(maxPart[u], total - siz[u]);
				if (centroid == 0 || maxPart[u] < maxPart[centroid]) {
					centroid = u;
				}
			}
		}
	}

	// 收集路径的递归版，java会爆栈，C++可以通过
	public static void getPath1(int u, int fa, int maxv, int edge) {
		if (edge > r) {
			return;
		}
		curMaxv[++cntc] = maxv;
		curEdge[cntc] = edge;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getPath1(v, u, Math.max(maxv, weight[e]), edge + 1);
			}
		}
	}

	// 收集路径的迭代版
	public static void getPath2(int cur, int fa, int pmaxv, int pedge) {
		stacksize = 0;
		push(cur, fa, pmaxv, pedge, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				if (edge > r) {
					continue;
				}
				curMaxv[++cntc] = maxv;
				curEdge[cntc] = edge;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, maxv, edge, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(to[e], u, Math.max(maxv, weight[e]), edge + 1, -1);
				}
			}
		}
	}

	public static void sort(int[] maxv, int[] edge, int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pv = maxv[(l + r) >> 1], pe = edge[(l + r) >> 1], tmp;
		while (i <= j) {
			while (maxv[i] < pv || (maxv[i] == pv && edge[i] < pe)) i++;
			while (maxv[j] > pv || (maxv[j] == pv && edge[j] > pe)) j--;
			if (i <= j) {
				tmp = maxv[i]; maxv[i] = maxv[j]; maxv[j] = tmp;
				tmp = edge[i]; edge[i] = edge[j]; edge[j] = tmp;
				i++; j--;
			}
		}
		sort(maxv, edge, l, j);
		sort(maxv, edge, i, r);
	}

	public static void calc(int u) {
		cnta = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				cntc = 0;
				// getPath1(v, u, weight[e], 1);
				getPath2(v, u, weight[e], 1);
				if (cntc > 0) {
					sort(curMaxv, curEdge, 1, cntc);
					for (int i = 1; i <= cntc; i++) {
						int left = l - curEdge[i] - 1;
						int right = r - curEdge[i];
						if (right >= 0) {
							left = Math.max(left, 0);
							right = Math.min(right, r);
							ans -= 1L * curMaxv[i] * (sum(right) - sum(left));
						}
						add(curEdge[i], 1);
					}
					for (int i = 1; i <= cntc; i++) {
						add(curEdge[i], -1);
					}
					for (int i = 1; i <= cntc; i++) {
						allMaxv[++cnta] = curMaxv[i];
						allEdge[cnta] = curEdge[i];
					}
				}
			}
		}
		if (cnta > 0) {
			sort(allMaxv, allEdge, 1, cnta);
			for (int i = 1; i <= cnta; i++) {
				int left = l - allEdge[i] - 1;
				int right = r - allEdge[i];
				if (right >= 0) {
					left = Math.max(left, 0);
					right = Math.min(right, r);
					ans += 1L * allMaxv[i] * (sum(right) - sum(left));
				}
				add(allEdge[i], 1);
			}
			for (int i = 1; i <= cnta; i++) {
				add(allEdge[i], -1);
			}
			for (int i = 1; i <= cnta; i++) {
				if (allEdge[i] >= l) {
					ans += allMaxv[i];
				}
			}
		}
	}

	public static void compute(int u) {
		calc(u);
		vis[u] = true;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				total = siz[v];
				centroid = 0;
				// getCentroid1(v, 0);
				getCentroid2(v, 0);
				compute(centroid);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		l = in.nextInt();
		r = in.nextInt();
		for (int i = 1, u, v, w; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		total = n;
		centroid = 0;
		// getCentroid1(1, 0);
		getCentroid2(1, 0);
		compute(centroid);
		out.println(ans << 1);
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
