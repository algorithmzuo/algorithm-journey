package class118;

// LCA问题树上倍增解法
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 提交以下的code，提交时请把类名改成"Main"
// 本文件和Code01_LCAMultiply1文件区别只有dfs实现方式的不同
// java这么写能通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_LCAMultiply2 {

	public static int MAXN = 500001;

	public static int LIMIT = 20;

	public static int power;

	public static int cnt;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[][] stfa = new int[MAXN][LIMIT];

	public static int[] deep = new int[MAXN];

	public static int log2(int n) {
		int ans = 0;
		while ((1 << ans) <= (n >> 1)) {
			ans++;
		}
		return ans;
	}

	public static void build(int n) {
		power = log2(n);
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	// dfs迭代版
	// nodes、fathers、edges是为了实现迭代版而准备的三个栈
	public static int[] nodes = new int[MAXN];

	public static int[] fathers = new int[MAXN];

	public static int[] edges = new int[MAXN];

	public static void dfs(int root) {
		nodes[0] = root;
		fathers[0] = 0;
		edges[0] = -1;
		int n = 1, u, f, e;
		while (n > 0) {
			u = nodes[--n];
			f = fathers[n];
			e = edges[n];
			if (e == -1) {
				deep[u] = deep[f] + 1;
				stfa[u][0] = f;
				for (int s = 1; (1 << s) <= deep[u]; s++) {
					stfa[u][s] = stfa[stfa[u][s - 1]][s - 1];
				}
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				nodes[n] = u;
				fathers[n] = f;
				edges[n++] = e;
				if (to[e] != f) {
					nodes[n] = to[e];
					fathers[n] = u;
					edges[n++] = -1;
				}
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
			if (deep[a] - (1 << p) >= deep[b]) {
				a = stfa[a][p];
			}
		}
		if (a == b) {
			return a;
		}
		for (int p = power; p >= 0; p--) {
			if (stfa[a][p] != stfa[b][p]) {
				a = stfa[a][p];
				b = stfa[b][p];
			}
		}
		return stfa[a][0];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		in.nextToken();
		int root = (int) in.nval;
		build(n);
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs(root);
		for (int i = 1, a, b; i <= m; i++) {
			in.nextToken();
			a = (int) in.nval;
			in.nextToken();
			b = (int) in.nval;
			out.println(lca(a, b));
		}
		out.flush();
		out.close();
		br.close();
	}

}
