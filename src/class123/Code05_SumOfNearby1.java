package class123;

// 每个节点距离k以内的权值和(递归版)
// 给定一棵n个点的树，每个点有点权
// 到达每个节点的距离不超过k的节点就有若干个
// 把这些节点权值加起来，就是该点不超过距离k的点权和
// 打印每个节点不超过距离k的点权和
// 注意k并不大
// 测试链接 : https://www.luogu.com.cn/problem/P3047
// 提交以下的code，提交时请把类名改成"Main"
// C++这么写能通过，java会因为递归层数太多而爆栈
// java能通过的写法参考本节课Code05_SumOfNearby2文件

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_SumOfNearby1 {

	public static int MAXN = 100001;

	public static int MAXK = 21;

	public static int n;

	public static int k;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	// sum[u][i] : 以u为头的子树内，距离为i的节点权值和
	public static int[][] sum = new int[MAXN][MAXK];

	// dp[u][i] : 以u做根，整棵树上，距离为i的节点权值和
	public static int[][] dp = new int[MAXN][MAXK];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs1(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				for (int j = 1; j <= k; j++) {
					sum[u][j] += sum[v][j - 1];
				}
			}
		}
	}

	public static void dfs2(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dp[v][0] = sum[v][0];
				dp[v][1] = sum[v][1] + dp[u][0];
				for (int i = 2; i <= k; i++) {
					dp[v][i] = sum[v][i] + dp[u][i - 1] - sum[v][i - 2];
				}
				dfs2(v, u);
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
		k = (int) in.nval;
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			sum[i][0] = (int) in.nval;
		}
		dfs1(1, 0);
		for (int i = 0; i <= k; i++) {
			dp[1][i] = sum[1][i];
		}
		dfs2(1, 0);
		for (int i = 1, ans; i <= n; i++) {
			ans = 0;
			for (int j = 0; j <= k; j++) {
				ans += dp[i][j];
			}
			out.println(ans);
		}
		out.flush();
		out.close();
		br.close();
	}

}
