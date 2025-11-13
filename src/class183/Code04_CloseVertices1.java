package class183;

// 相近的点对数量，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF293E
// 测试链接 : https://codeforces.com/problemset/problem/293/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code04_CloseVertices1 {

	public static int MAXN = 100005;
	public static int n, limitl, limitw, total;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static boolean[] vis = new boolean[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] maxPart = new int[MAXN];
	public static int centroid;

	public static int[] disArr = new int[MAXN];
	public static int[] depArr = new int[MAXN];
	public static int cnta;

	public static int[] tree = new int[MAXN];

	public static void sort(int l, int r) {
		if (l >= r) return;
		int i = l, j = r, pivot = disArr[(l + r) >> 1], tmp;
		while (i <= j) {
			while (disArr[i] < pivot) i++;
			while (disArr[j] > pivot) j--;
			if (i <= j) {
				tmp = disArr[i]; disArr[i] = disArr[j]; disArr[j] = tmp;
				tmp = depArr[i]; depArr[i] = depArr[j]; depArr[j] = tmp;
				i++; j--;
			}
		}
		sort(l, j);
		sort(i, r);
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
		while (i <= limitl + 1) {
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

	public static void getCentroid(int u, int fa) {
		siz[u] = 1;
		maxPart[u] = 0;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				getCentroid(v, u);
				siz[u] += siz[v];
				maxPart[u] = Math.max(siz[v], maxPart[u]);
			}
		}
		maxPart[u] = Math.max(maxPart[u], total - siz[u]);
		if (centroid == 0 || maxPart[u] < maxPart[centroid]) {
			centroid = u;
		}
	}

	public static void dfs(int u, int fa, int dis, int dep) {
		if (dis > limitw || dep > limitl + 1) {
			return;
		}
		disArr[++cnta] = dis;
		depArr[cnta] = dep;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (v != fa && !vis[v]) {
				dfs(v, u, dis + weight[e], dep + 1);
			}
		}
	}

	public static long calc(int u, int dis, int dep) {
		cnta = 0;
		dfs(u, 0, dis, dep);
		sort(1, cnta);
		for (int i = 1; i <= cnta; i++) {
			add(depArr[i], 1);
		}
		long ret = 0;
		for (int l = 1, r = cnta; l <= r;) {
			if (disArr[l] + disArr[r] <= limitw) {
				add(depArr[l], -1);
				ret += sum(limitl - depArr[l] + 2);
				l++;
			} else {
				add(depArr[r], -1);
				r--;
			}
		}
		return ret;
	}

	public static long solve(int u) {
		vis[u] = true;
		long ans = calc(u, 0, 1);
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (!vis[v]) {
				ans -= calc(v, weight[e], 2);
				total = siz[v];
				centroid = 0;
				getCentroid(v, 0);
				ans += solve(centroid);
			}
		}
		return ans;
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		limitl = in.nextInt();
		limitw = in.nextInt();
		for (int i = 2, fa, w; i <= n; i++) {
			fa = in.nextInt();
			w = in.nextInt();
			addEdge(i, fa, w);
			addEdge(fa, i, w);
		}
		total = n;
		centroid = 0;
		getCentroid(1, 0);
		out.println(solve(centroid));
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
