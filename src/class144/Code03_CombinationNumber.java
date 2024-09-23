package class144;

// 组合数问题
// 组合公式c(i, j)，表示从i个物品中选出j个物品的方案数
// 如果该数值是k的整数倍，那么称(i, j)是一个合法对
// 给定具体的一组数字n和m，当i和j满足：0 <= i <= n，0 <= j <= min(i, m)
// 返回有多少合法对
// 一共有t组测试，所有测试的k都为同一个值
// 每组测试给定n和m，打印每组测试的答案
// 1 <= t <= 10^4
// 2 <= k <= 21
// 0 <= n、m <= 2000
// 测试链接 : https://www.luogu.com.cn/problem/P2822
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_CombinationNumber {

	public static int MAXV = 2000;

	public static int MAXN = 2002;

	public static int[][] c = new int[MAXN][MAXN];

	public static int[][] f = new int[MAXN][MAXN];

	public static int[][] sum = new int[MAXN][MAXN];

	public static int t, k, n, m;

	public static void build() {
		for (int i = 0; i <= MAXV; i++) {
			c[i][0] = 1;
			for (int j = 1; j <= i; j++) {
				c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % k;
			}
		}
		for (int i = 1; i <= MAXV; i++) {
			for (int j = 1; j <= i; j++) {
				f[i][j] = c[i][j] % k == 0 ? 1 : 0;
			}
		}
		for (int i = 2; i <= MAXV; i++) {
			for (int j = 1; j <= i; j++) {
				sum[i][j] = sum[i][j - 1] + sum[i - 1][j] - sum[i - 1][j - 1] + f[i][j];
			}
			sum[i][i + 1] = sum[i][i];
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		t = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		build();
		for (int i = 1; i <= t; i++) {
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			if (m > n) {
				out.println(sum[n][n]);
			} else {
				out.println(sum[n][m]);
			}
		}
		out.flush();
		out.close();
		br.close();
	}
}
