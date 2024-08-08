package class138;

// 最小圈
// 一共有n个节点，m条有向边，每条边有权值
// 这个有向带权图中，可能有若干环，规定环的平均值为
// 环中边的权值和 / 环中边的数量
// 返回所有环的平均值中最少的平均值，结果保留小数点后8位，其余部分舍弃
// 1 <= n <= 3000
// 1 <= m <= 10000
// -10^7 <= 边权 <= 10^7
// 测试链接 : https://www.luogu.com.cn/problem/P3199
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_MinimumAverageCircle {

	public static int MAXN = 3001;

	public static int MAXM = 10001;

	public static double LIMIT = 1e7;

	public static double sml = 1e-9;

	// 链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static double[] weight = new double[MAXM];

	public static int cnt;

	// dfs判断负环
	public static double[] value = new double[MAXN];

	public static boolean[] path = new boolean[MAXN];

	public static int n, m;

	public static void prepare() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v, double w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	// 利用dfs判断图中是否存在负环，课上重点图解
	public static boolean check(double x) {
		Arrays.fill(value, 1, n + 1, 0);
		Arrays.fill(path, 1, n + 1, false);
		return dfs(0, x);
	}

	public static boolean dfs(int u, double x) {
		if (u == 0) {
			// 认为0号点是超级源点，具有通往所有点的有向边
			for (int i = 1; i <= n; i++) {
				if (dfs(i, x)) {
					return true;
				}
			}
		} else {
			path[u] = true;
			for (int e = head[u]; e != 0; e = next[e]) {
				int v = to[e];
				double w = weight[e] - x;
				// 只有让v的权值变小才会继续递归
				// 非常强的剪枝，类似spfa
				if (value[v] > value[u] + w) {
					value[v] = value[u] + w;
					// 如果v在递归的路径上，但是再次遇到，说明遇到的负环
					// 或者
					// 后续递归的过程中发现了负环
					// 直接返回true
					if (path[v] || dfs(v, x)) {
						return true;
					}
				}
			}
			path[u] = false;
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		prepare();
		for (int i = 1; i <= m; i++) {
			in.nextToken();
			int u = (int) in.nval;
			in.nextToken();
			int v = (int) in.nval;
			in.nextToken();
			double w = in.nval;
			addEdge(u, v, w);
		}
		double l = -LIMIT, r = LIMIT, x, ans = 0;
		while (l < r && r - l >= sml) {
			x = (l + r) / 2;
			if (check(x)) {
				r = x - sml;
			} else {
				ans = x;
				l = x + sml;
			}
		}
		out.printf("%.8f\n", ans);
		out.flush();
		out.close();
		br.close();
	}

}
