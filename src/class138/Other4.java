package class138;

// 题目4，最小圈，另一种二分的写法
// 思路是不变的，二分的写法多种多样
// 代码中打注释的位置，就是更简单的二分逻辑，其他代码没有变化
// 测试链接 : https://www.luogu.com.cn/problem/P3199
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Other4 {

	public static int MAXN = 3001;

	public static int MAXM = 10001;

	public static double MAXE = 1e7;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static double[] weight = new double[MAXM];

	public static int cnt;

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

	public static boolean check(double x) {
		Arrays.fill(value, 1, n + 1, 0);
		Arrays.fill(path, 1, n + 1, false);
		return dfs(0, x);
	}

	public static boolean dfs(int u, double x) {
		if (u == 0) {
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
				if (value[v] > value[u] + w) {
					value[v] = value[u] + w;
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
		double l = -MAXE, r = MAXE, x;
		// 二分进行60次，足够达到题目要求的精度
		// 二分完成后，l就是答案
		for (int i = 1; i <= 60; i++) {
			x = (l + r) / 2;
			if (check(x)) {
				r = x;
			} else {
				l = x;
			}
		}
		out.printf("%.8f\n", l);
		out.flush();
		out.close();
		br.close();
	}

}
