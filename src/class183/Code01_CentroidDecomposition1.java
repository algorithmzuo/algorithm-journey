package class183;

// 点分治，也叫重心分治，java版
// 测试链接 : https://www.luogu.com.cn/problem/P3806
// 提交以下的code，提交时请把类名改成"Main"
// java实现的逻辑一定是正确的，但是本题卡常，无法通过所有测试用例
// 想通过用C++实现，本节课Code01_CentroidDecomposition2文件就是C++的实现
// 两个版本的逻辑完全一样，C++版本可以通过所有测试

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code01_CentroidDecomposition1 {

	public static int MAXN = 10001;
	public static int MAXV = 15000001;
	public static int n, m, total;
	public static int[] query = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] maxp = new int[MAXN];
	public static int centroid;

	public static int[] dis = new int[MAXN];
	public static int[] valArr = new int[MAXV];
	public static int[] valQue = new int[MAXV];
	public static boolean[] valJudge = new boolean[MAXV];
	public static int cnta, cntq;

	public static boolean[] ans = new boolean[MAXN];

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	// 找重心递归版，java会爆栈，C++可以通过
	public static void getCentroid1(int u, int fa) {
		siz[u] = 1;
		maxp[u] = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getCentroid1(v, u);
				siz[u] += siz[v];
				maxp[u] = Math.max(siz[v], maxp[u]);
			}
		}
		maxp[u] = Math.max(maxp[u], total - siz[u]);
		if (maxp[u] < maxp[centroid]) {
			centroid = u;
		}
	}

	// 讲解118，递归改迭代需要的栈
	public static int[][] ufe = new int[MAXN][3];
	public static int stacksize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stacksize][0] = u;
		ufe[stacksize][1] = f;
		ufe[stacksize][2] = e;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = ufe[stacksize][0];
		f = ufe[stacksize][1];
		e = ufe[stacksize][2];
	}

	// 找重心迭代版
	public static void getCentroid2(int cur, int fa) {
		stacksize = 0;
		push(cur, fa, -1);
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				siz[u] = 1;
				maxp[u] = 0;
				e = head[u];
			} else {
				e = nxt[e];
			}
			if (e != 0) {
				push(u, f, e);
				int v = to[e];
				if (v != f && !vis[v]) {
					push(v, u, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = nxt[ei]) {
					int v = to[ei];
					if (v != f && !vis[v]) {
						siz[u] += siz[v];
						maxp[u] = Math.max(siz[v], maxp[u]);
					}
				}
				maxp[u] = Math.max(maxp[u], total - siz[u]);
				if (maxp[u] < maxp[centroid]) {
					centroid = u;
				}
			}
		}
	}

	public static void getDistance(int u, int fa, int w) {
		dis[u] = dis[fa] + w;
		valArr[++cnta] = dis[u];
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getDistance(v, u, weight[e]);
			}
		}
	}

	public static void calc(int u) {
		dis[u] = 0;
		cntq = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (!vis[v]) {
				cnta = 0;
				getDistance(v, u, w);
				for (int k = cnta; k > 0; k--) {
					for (int l = 1; l <= m; l++) {
						if (query[l] >= valArr[k]) {
							ans[l] |= valJudge[query[l] - valArr[k]];
						}
					}
				}
				for (int k = cnta; k > 0; k--) {
					valQue[++cntq] = valArr[k];
					valJudge[valArr[k]] = true;
				}
			}
		}
		for (int i = cntq; i > 0; i--) {
			valJudge[valQue[i]] = false;
		}
	}

	public static void solve(int u) {
		vis[u] = true;
		valJudge[0] = true;
		calc(u);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				total = siz[v];
				centroid = 0;
				maxp[centroid] = n;
				// getCentroid1(v, u);
				getCentroid2(v, u);
				solve(centroid);
			}
		}
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
			query[i] = in.nextInt();
		}
		total = n;
		centroid = 0;
		maxp[centroid] = n;
		// getCentroid1(1, 0);
		getCentroid2(1, 0);
		solve(centroid);
		for (int i = 1; i <= m; i++) {
			if (ans[i]) {
				out.println("AYE");
			} else {
				out.println("NAY");
			}
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
