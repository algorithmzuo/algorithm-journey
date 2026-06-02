package class200;

// 最短路最大值，java版
// 测试链接 : https://uoj.ac/problem/87
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_MaximumOfShortestPath1 {

	public static int MAXN = 300001;
	public static int MAXM = 600001;
	public static int MAXT = MAXN << 1;
	public static int n, m, q, k, cntn;

	// 原图
	public static int[] head1 = new int[MAXN];
	public static int[] next1 = new int[MAXM << 1];
	public static int[] to1 = new int[MAXM << 1];
	public static long[] weight1 = new long[MAXM << 1];
	public static int cnt1;

	// 圆方树
	public static int[] head2 = new int[MAXT];
	public static int[] next2 = new int[MAXM << 1];
	public static int[] to2 = new int[MAXM << 1];
	public static long[] weight2 = new long[MAXM << 1];
	public static int cnt2;

	// 虚树
	public static int[] head3 = new int[MAXT];
	public static int[] next3 = new int[MAXT];
	public static int[] to3 = new int[MAXT];
	public static int cnt3;

	// dfn空间开大一点，不仅tarjan算法使用，圆方树的点建虚树也要使用
	public static int[] dfn = new int[MAXT];
	public static int[] low = new int[MAXN];
	public static int cntd;
	public static int[] sta = new int[MAXN];
	public static int stasiz;

	public static long[] fromWeight = new long[MAXN];
	public static long[] cycleDist = new long[MAXN];
	public static long[] cycleSum = new long[MAXT];

	// 树链剖分
	public static int[] fa = new int[MAXT];
	public static int[] dep = new int[MAXT];
	public static int[] siz = new int[MAXT];
	public static long[] dist = new long[MAXT];
	public static int[] son = new int[MAXT];
	public static int[] top = new int[MAXT];

	// 虚树dp
	public static int[] arr = new int[MAXN];
	public static int[] tmp = new int[MAXT];
	public static long[] dp = new long[MAXT];
	public static long ans;

	// 方点单调队列
	public static int[] ringNode = new int[MAXT];
	public static long[] ringPos = new long[MAXT];
	public static long[] ringVal = new long[MAXT];
	public static int[] que = new int[MAXT];

	public static void addEdge1(int u, int v, long w) {
		next1[++cnt1] = head1[u];
		to1[cnt1] = v;
		weight1[cnt1] = w;
		head1[u] = cnt1;
	}

	public static void addEdge2(int u, int v, long w) {
		next2[++cnt2] = head2[u];
		to2[cnt2] = v;
		weight2[cnt2] = w;
		head2[u] = cnt2;
	}

	public static void addEdge3(int u, int v) {
		next3[++cnt3] = head3[u];
		to3[cnt3] = v;
		head3[u] = cnt3;
	}

	public static void sortByDfn(int[] nums, int l, int r) {
		if (l >= r)
			return;
		int i = l, j = r;
		int pivot = nums[(l + r) >> 1];
		while (i <= j) {
			while (dfn[nums[i]] < dfn[pivot])
				i++;
			while (dfn[nums[j]] > dfn[pivot])
				j--;
			if (i <= j) {
				int tmp = nums[i];
				nums[i] = nums[j];
				nums[j] = tmp;
				i++;
				j--;
			}
		}
		sortByDfn(nums, l, j);
		sortByDfn(nums, i, r);
	}

	public static void sortByDist(int[] nums, int l, int r) {
		if (l >= r)
			return;
		int i = l, j = r;
		int pivot = nums[(l + r) >> 1];
		while (i <= j) {
			while (cycleDist[nums[i]] < cycleDist[pivot])
				i++;
			while (cycleDist[nums[j]] > cycleDist[pivot])
				j--;
			if (i <= j) {
				int tmp = nums[i];
				nums[i] = nums[j];
				nums[j] = tmp;
				i++;
				j--;
			}
		}
		sortByDist(nums, l, j);
		sortByDist(nums, i, r);
	}

	public static void cycleLink(int u, int v) {
		cntn++;
		cycleSum[cntn] = fromWeight[u];
		addEdge2(u, cntn, 0);
		int tmp = stasiz;
		int pop;
		do {
			pop = sta[tmp--];
			cycleDist[pop] = cycleSum[cntn];
			cycleSum[cntn] += fromWeight[pop];
		} while (pop != v);
		do {
			pop = sta[stasiz--];
			addEdge2(cntn, pop, Math.min(cycleDist[pop], cycleSum[cntn] - cycleDist[pop]));
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
			long w = weight1[e];
			if (dfn[v] == 0) {
				tarjan(v, e);
				fromWeight[v] = w;
				if (low[v] < dfn[u]) {
					low[u] = Math.min(low[u], low[v]);
				} else if (low[v] > dfn[u]) {
					stasiz--;
					addEdge2(u, v, w);
				} else {
					cycleLink(u, v);
				}
			} else if (dfn[v] < dfn[u]) {
				fromWeight[v] = w;
				low[u] = Math.min(low[u], dfn[v]);
			}
		}
	}

	public static void dfs1(int u, int f, long dis) {
		fa[u] = f;
		dep[u] = dep[f] + 1;
		siz[u] = 1;
		dist[u] = dis;
		dfn[u] = ++cntd;
		for (int e = head2[u], v; e > 0; e = next2[e]) {
			v = to2[e];
			if (v != f) {
				dfs1(v, u, dist[u] + weight2[e]);
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	public static void dfs2(int u, int t) {
		top[u] = t;
		if (son[u] != 0) {
			dfs2(son[u], t);
		}
		for (int e = head2[u]; e > 0; e = next2[e]) {
			int v = to2[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static int find(int cur, int lca) {
		int ans = 0;
		while (top[cur] != top[lca]) {
			ans = top[cur];
			cur = fa[top[cur]];
		}
		return cur == lca ? ans : son[lca];
	}

	public static int lca(int a, int b) {
		while (top[a] != top[b]) {
			if (dep[top[a]] <= dep[top[b]]) {
				b = fa[top[b]];
			} else {
				a = fa[top[a]];
			}
		}
		return dep[a] <= dep[b] ? a : b;
	}

	// 建虚树的方式，二次排序 + 相邻LCA连边
	public static int buildVirtualTree() {
		sortByDfn(arr, 1, k);
		int len = 0;
		for (int i = 1; i < k; i++) {
			tmp[++len] = arr[i];
			tmp[++len] = lca(arr[i], arr[i + 1]);
		}
		tmp[++len] = arr[k];
		sortByDfn(tmp, 1, len);
		int unique = 1;
		for (int i = 2; i <= len; i++) {
			if (tmp[unique] != tmp[i]) {
				tmp[++unique] = tmp[i];
			}
		}
		cnt3 = 0;
		for (int i = 1; i <= unique; i++) {
			head3[tmp[i]] = 0;
		}
		for (int i = 1; i < unique; i++) {
			addEdge3(lca(tmp[i], tmp[i + 1]), tmp[i + 1]);
		}
		return tmp[1];
	}

	public static long query(int u, int siz) {
		long ans = 0;
		if (siz >= 2) {
			sortByDist(ringNode, 1, siz);
			for (int i = 1; i <= siz; i++) {
				ringPos[i] = cycleDist[ringNode[i]];
				ringPos[siz + i] = ringPos[i] + cycleSum[u];
				ringVal[i] = dp[ringNode[i]];
				ringVal[siz + i] = ringVal[i];
			}
			int l = 1;
			int r = 0;
			for (int i = 1; i <= siz << 1; i++) {
				while (l <= r && (ringPos[i] - ringPos[que[l]]) * 2 > cycleSum[u]) {
					l++;
				}
				if (l <= r) {
					ans = Math.max(ans, ringVal[que[l]] - ringPos[que[l]] + ringVal[i] + ringPos[i]);
				}
				while (l <= r && ringVal[que[r]] - ringPos[que[r]] <= ringVal[i] - ringPos[i]) {
					r--;
				}
				que[++r] = i;
			}
		}
		return ans;
	}

	public static void dpOnTree(int u) {
		dp[u] = 0;
		for (int e = head3[u]; e > 0; e = next3[e]) {
			int v = to3[e];
			dpOnTree(v);
		}
		int siz = 0;
		for (int e = head3[u]; e > 0; e = next3[e]) {
			int v = to3[e];
			if (u <= n) {
				ans = Math.max(ans, dp[u] + dp[v] + dist[v] - dist[u]);
			} else {
				int f = find(v, u);
				dp[f] = dp[v] + dist[v] - dist[f];
				ringNode[++siz] = f;
			}
			dp[u] = Math.max(dp[u], dp[v] + dist[v] - dist[u]);
		}
		if (u > n) {
			ans = Math.max(ans, query(u, siz));
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		cntn = n;
		cnt1 = 1;
		for (int i = 1, u, v, w; i <= m; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge1(u, v, w);
			addEdge1(v, u, w);
		}
		tarjan(1, 0);
		cntd = 0;
		dfs1(1, 0, 0);
		dfs2(1, 1);
		q = in.nextInt();
		for (int i = 1; i <= q; i++) {
			k = in.nextInt();
			for (int j = 1; j <= k; j++) {
				arr[j] = in.nextInt();
			}
			ans = 0;
			int root = buildVirtualTree();
			dpOnTree(root);
			out.println(ans);
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