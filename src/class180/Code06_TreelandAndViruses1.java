package class180;

// 树上病毒传播，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF1320E
// 测试链接 : https://codeforces.com/problemset/problem/1320/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Code06_TreelandAndViruses1 {

	static class Node {
		int id, dist, time, source, sourceOrder;

		Node(int id_, int dist_, int time_, int source_, int sourceOrder_) {
			id = id_;
			dist = dist_;
			time = time_;
			source = source_;
			sourceOrder = sourceOrder_;
		}
	}

	static class NodeCmp implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			if (o1.time != o2.time) {
				return o1.time - o2.time;
			}
			return o1.sourceOrder - o2.sourceOrder;
		}
	}

	public static int MAXN = 200001;
	public static int MAXP = 20;
	public static int n, q, k, m;

	public static int[] headg = new int[MAXN];
	public static int[] nextg = new int[MAXN << 1];
	public static int[] tog = new int[MAXN << 1];
	public static int cntg;

	public static int[] headv = new int[MAXN];
	public static int[] nextv = new int[MAXN << 1];
	public static int[] tov = new int[MAXN << 1];
	public static int cntv;

	public static int[] dep = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int[][] stjump = new int[MAXN][MAXP];
	public static int cntd;

	public static int[] start = new int[MAXN];
	public static int[] speed = new int[MAXN];
	public static int[] query = new int[MAXN];
	public static int[] order = new int[MAXN];

	public static int[] arr = new int[MAXN << 1];
	public static int[] tmp = new int[MAXN << 2];
	public static int len;

	public static PriorityQueue<Node> heap = new PriorityQueue<>(new NodeCmp());
	public static boolean[] vis = new boolean[MAXN];
	public static int[] minTime = new int[MAXN];
	public static int[] bestSource = new int[MAXN];

	public static int[] ans = new int[MAXN];

	public static void addEdgeG(int u, int v) {
		nextg[++cntg] = headg[u];
		tog[cntg] = v;
		headg[u] = cntg;
	}

	public static void addEdgeV(int u, int v) {
		nextv[++cntv] = headv[u];
		tov[cntv] = v;
		headv[u] = cntv;
	}

	public static void sortByDfn(int[] nums, int l, int r) {
		if (l >= r) return;
		int i = l, j = r;
		int pivot = nums[(l + r) >> 1];
		while (i <= j) {
			while (dfn[nums[i]] < dfn[pivot]) i++;
			while (dfn[nums[j]] > dfn[pivot]) j--;
			if (i <= j) {
				int tmp = nums[i];
				nums[i] = nums[j];
				nums[j] = tmp;
				i++; j--;
			}
		}
		sortByDfn(nums, l, j);
		sortByDfn(nums, i, r);
	}

	public static void dfs(int u, int fa) {
		dep[u] = dep[fa] + 1;
		dfn[u] = ++cntd;
		stjump[u][0] = fa;
		for (int p = 1; p < MAXP; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = headg[u]; e > 0; e = nextg[e]) {
			if (tog[e] != fa) {
				dfs(tog[e], u);
			}
		}
	}

	public static int getLca(int a, int b) {
		if (dep[a] < dep[b]) {
			int tmp = a; a = b; b = tmp;
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

	public static int buildVirtualTree() {
		int tot = 0;
		for (int i = 1; i <= k; i++) {
			arr[++tot] = start[i];
		}
		for (int i = 1; i <= m; i++) {
			arr[++tot] = query[i];
		}
		sortByDfn(arr, 1, tot);
		len = 0;
		for (int i = 1; i < tot; i++) {
			tmp[++len] = arr[i];
			tmp[++len] = getLca(arr[i], arr[i + 1]);
		}
		tmp[++len] = arr[tot];
		sortByDfn(tmp, 1, len);
		int unique = 1;
		for (int i = 2; i <= len; i++) {
			if (tmp[unique] != tmp[i]) {
				tmp[++unique] = tmp[i];
			}
		}
		cntv = 0;
		for (int i = 1; i <= unique; i++) {
			headv[tmp[i]] = 0;
		}
		for (int i = 1; i < unique; i++) {
			// 这里要两个方向加边
			// 因为病毒感染既可以向上也可以向下
			// 所以跑dijkstra算法时需要无向图
			int lca = getLca(tmp[i], tmp[i + 1]);
			addEdgeV(lca, tmp[i + 1]);
			addEdgeV(tmp[i + 1], lca);
		}
		len = unique;
		return tmp[1];
	}

	public static void dijkstra() {
		for (int i = 1; i <= len; i++) {
			int u = tmp[i];
			minTime[u] = n + 1;
			bestSource[u] = n + 1;
			vis[u] = false;
		}
		for (int i = 1; i <= k; i++) {
			int s = start[i];
			minTime[s] = 0;
			bestSource[s] = s;
			heap.add(new Node(s, 0, 0, s, order[s]));
		}
		while (!heap.isEmpty()) {
			Node cur = heap.poll();
			int u = cur.id;
			int source = cur.source;
			int sourceOrder = cur.sourceOrder;
			if (!vis[u]) {
				vis[u] = true;
				for (int e = headv[u]; e > 0; e = nextv[e]) {
					int v = tov[e];
					int dist = cur.dist + Math.abs(dep[u] - dep[v]);
					int time = (dist + speed[source] - 1) / speed[source];
					if (!vis[v] && (time < minTime[v] || (time == minTime[v] && sourceOrder < order[bestSource[v]]))) {
						minTime[v] = time;
						bestSource[v] = source;
						heap.add(new Node(v, dist, time, source, sourceOrder));
					}
				}
			}
		}
	}

	public static void compute() {
		for (int i = 1; i <= k; i++) {
			order[start[i]] = i;
		}
		buildVirtualTree();
		dijkstra();
		for (int i = 1; i <= m; i++) {
			ans[i] = order[bestSource[query[i]]];
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i < n; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			addEdgeG(x, y);
			addEdgeG(y, x);
		}
		dfs(1, 0);
		q = in.nextInt();
		for (int t = 1; t <= q; t++) {
			k = in.nextInt();
			m = in.nextInt();
			for (int i = 1; i <= k; i++) {
				start[i] = in.nextInt();
				speed[start[i]] = in.nextInt();
			}
			for (int i = 1; i <= m; i++) {
				query[i] = in.nextInt();
			}
			compute();
			for (int i = 1; i <= m; i++) {
				out.print(ans[i] + " ");
			}
			out.println();
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
