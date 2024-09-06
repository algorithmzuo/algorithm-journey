package class123;

// 染色的最大收益
// 给定一棵n个点的树，初始时所有节点全是白点
// 第一次操作，你可以选择任意点染黑
// 以后每次操作，必须选择已经染黑的点的相邻点继续染黑，一直到所有的点都被染完
// 每次都获得，当前被染色点的白色连通块大小，作为收益
// 返回可获得的最大收益和
// 测试链接 : https://www.luogu.com.cn/problem/CF1187E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_TreePainting {

	public static int MAXN = 200001;

	public static int n;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] size = new int[MAXN];

	public static long[] dp = new long[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(size, 1, n + 1, 0);
		Arrays.fill(dp, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	// dp[i]更新成
	// 节点i作为自己这棵子树最先染的点，染完子树后，收益是多少
	public static void dfs1(int u, int f) {
		size[u] = 1;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
				size[u] += size[v];
				dp[u] += dp[v];
			}
		}
		dp[u] += size[u];
	}

	// dp[i]更新成
	// 节点i作为整棵树最先染的点，染完整棵树后，收益是多少
	public static void dfs2(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dp[v] = dp[u] + n - size[v] - size[v];
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
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs1(1, 0);
		dfs2(1, 0);
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			ans = Math.max(ans, dp[i]);
		}
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
