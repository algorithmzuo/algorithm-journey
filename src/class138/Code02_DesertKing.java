package class138;

// 最优比率生成树
// 测试链接 : http://poj.org/problem?id=2728

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_DesertKing {

	public static int MAXN = 1001;

	public static double sml = 1e-6;

	public static int[] x = new int[MAXN];

	public static int[] y = new int[MAXN];

	public static int[] z = new int[MAXN];

	public static double[][] dist = new double[MAXN][MAXN];

	public static double[][] cost = new double[MAXN][MAXN];

	public static boolean[] visit = new boolean[MAXN];

	public static double[] weight = new double[MAXN];

	public static int n;

	// 邻接矩阵结构下的prim算法，从节点1出发得到最小生成树
	public static boolean check(double x) {
		for (int i = 1; i <= n; i++) {
			visit[i] = false;
			weight[i] = cost[1][i] - x * dist[1][i];
		}
		visit[1] = true;
		double sum = 0;
		for (int i = 1; i <= n; i++) {
			double minDist = Double.MAX_VALUE;
			int next = 0;
			for (int j = 1; j <= n; j++) {
				if (!visit[j] && weight[j] < minDist) {
					minDist = weight[j];
					next = j;
				}
			}
			if (next != 0) {
				sum += minDist;
				visit[next] = true;
				for (int j = 1; j <= n; j++) {
					if (!visit[j] && weight[j] > cost[next][j] - x * dist[next][j]) {
						weight[j] = cost[next][j] - x * dist[next][j];
					}
				}
			}
		}
		return sum >= 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		while (n != 0) {
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				x[i] = (int) in.nval;
				in.nextToken();
				y[i] = (int) in.nval;
				in.nextToken();
				z[i] = (int) in.nval;
			}
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (i != j) {
						dist[i][j] = Math.sqrt((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j]));
						cost[i][j] = Math.abs(z[i] - z[j]);
					}
				}
			}
			double l = 0, r = 100, x, ans = 0;
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
			in.nextToken();
			n = (int) in.nval;
		}
		out.flush();
		out.close();
		br.close();
	}

}
