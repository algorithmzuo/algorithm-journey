package class138;

// 最佳团体
// 测试链接 : https://www.luogu.com.cn/problem/P4322

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_BestTeam {

	public static int MAXN = 2501;

	public static int LIMIT = 10000;

	public static double NA = Double.NEGATIVE_INFINITY;

	public static double sml = 1e-6;

	public static double[][] arr = new double[MAXN][3];

	// 链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	public static int cnt;

	// 树型dp
	public static double[][] dp = new double[MAXN][MAXN];

	public static int[] size = new int[MAXN];

	public static int k, n;

	public static void prepare() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static boolean check(double x) {
		for (int i = 1; i <= n; i++) {
			arr[i][2] = arr[i][1] - x * arr[i][0];
		}
		for (int i = 0; i <= n; i++) {
			for (int j = 1; j <= k + 1; j++) {
				dp[i][j] = NA;
			}
		}
		dfs(0, -1);
		return dp[0][k + 1] >= 0;
	}

	public static void dfs(int u, int f) {
		dp[u][1] = arr[u][2];
		size[u] = 1;
		for (int e = head[u]; e != 0; e = next[e]) {
			int v = to[e];
			if (v != f) {
				dfs(v, u);
				size[u] += size[v];
				for (int j = Math.min(size[u], k + 1); j >= 2; j--) {
					for (int p = 1; p <= Math.min(size[v], j - 1); p++) {
						dp[u][j] = Math.max(dp[u][j], dp[u][j - p] + dp[v][p]);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		k = (int) in.nval;
		in.nextToken();
		n = (int) in.nval;
		prepare();
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i][0] = in.nval;
			in.nextToken();
			arr[i][1] = in.nval;
			in.nextToken();
			addEdge((int) in.nval, i);
		}
		double l = 0, r = LIMIT, x, ans = 0;
		while (l < r && r - l >= sml) {
			x = (l + r) / 2;
			if (check(x)) {
				ans = x;
				l = x + sml;
			} else {
				r = x - sml;
			}
		}
		out.printf("%.3f\n", ans);
		out.flush();
		out.close();
		br.close();
	}

}
