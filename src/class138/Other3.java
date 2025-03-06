package class138;

// 题目3，最优比率生成树，另一种二分的写法
// 思路是不变的，二分的写法多种多样
// 代码中打注释的位置，就是更简单的二分逻辑，其他代码没有变化
// 测试链接 : http://poj.org/problem?id=2728
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Other3 {

	public static int MAXN = 1001;

	public static int[] x = new int[MAXN];

	public static int[] y = new int[MAXN];

	public static int[] z = new int[MAXN];

	public static double[][] dist = new double[MAXN][MAXN];

	public static double[][] cost = new double[MAXN][MAXN];

	public static boolean[] visit = new boolean[MAXN];

	public static double[] value = new double[MAXN];

	public static int n;

	public static double prim(double x) {
		for (int i = 1; i <= n; i++) {
			visit[i] = false;
			value[i] = cost[1][i] - x * dist[1][i];
		}
		visit[1] = true;
		double sum = 0;
		for (int i = 1; i <= n - 1; i++) {
			double minDist = Double.MAX_VALUE;
			int next = 0;
			for (int j = 1; j <= n; j++) {
				if (!visit[j] && value[j] < minDist) {
					minDist = value[j];
					next = j;
				}
			}
			sum += minDist;
			visit[next] = true;
			for (int j = 1; j <= n; j++) {
				if (!visit[j] && value[j] > cost[next][j] - x * dist[next][j]) {
					value[j] = cost[next][j] - x * dist[next][j];
				}
			}
		}
		return sum;
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
			double l = 0, r = 100, x;
			// 二分进行60次，足够达到题目要求的精度
			// 二分完成后，l就是答案
			for (int i = 1; i <= 60; i++) {
				x = (l + r) / 2;
				if (prim(x) <= 0) {
					r = x;
				} else {
					l = x;
				}
			}
			out.printf("%.3f\n", l);
			in.nextToken();
			n = (int) in.nval;
		}
		out.flush();
		out.close();
		br.close();
	}

}
