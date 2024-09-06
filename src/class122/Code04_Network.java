package class122;

// 边差分实战
// 使图不连通的方法数
// 有n个节点，给定n-1条老边使其连接成一棵树，再给定m条新边额外加在树上
// 你可以切断两条边让这个图不连通，切断的两条边必须是一条老边和一条新边
// 返回方法数
// 测试链接 : http://poj.org/problem?id=3417
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_Network {

	public static int MAXN = 100001;

	public static int LIMIT = 17;

	public static int power;

	public static int log2(int n) {
		int ans = 0;
		while ((1 << ans) <= (n >> 1)) {
			ans++;
		}
		return ans;
	}

	public static int n, m;

	public static int[] num = new int[MAXN];

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] deep = new int[MAXN];

	public static int[][] stjump = new int[MAXN][LIMIT];

	public static int ans;

	public static void build() {
		power = log2(n);
		Arrays.fill(num, 1, n + 1, 0);
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		ans = 0;
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs1(int u, int f) {
		deep[u] = deep[f] + 1;
		stjump[u][0] = f;
		for (int p = 1; p <= power; p++) {
			stjump[u][p] = stjump[stjump[u][p - 1]][p - 1];
		}
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				dfs1(to[e], u);
			}
		}
	}

	public static int lca(int a, int b) {
		if (deep[a] < deep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int p = power; p >= 0; p--) {
			if (deep[stjump[a][p]] >= deep[b]) {
				a = stjump[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = power; p >= 0; p--) {
			if (stjump[a][p] != stjump[b][p]) {
				a = stjump[a][p];
				b = stjump[b][p];
			}
		}
		return stjump[a][0];
	}

	public static void dfs2(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs2(v, u);
			}
		}
		for (int e = head[u], v, w; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				w = 0 + num[v];
				if (w == 0) {
					ans += m;
				} else if (w == 1) {
					ans += 1;
				} else {
					ans += 0;
				}
				num[u] += num[v];
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		build();
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs1(1, 0);
		for (int i = 1, u, v, lca; i <= m; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			lca = lca(u, v);
			num[u]++;
			num[v]++;
			num[lca] -= 2;
		}
		dfs2(1, 0);
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
