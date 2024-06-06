package class128;

// 挖砖块问题
// 一堆组成倒三角形状的砖埋在地里
// 一共有n层，第1层有n块砖，每层递减，第n层有1块砖，类似如下数据
// 4 2 9 5
//  3 1 7
//   2 4
//    8
// 你需要从第1层开始挖，每挖开一块砖都可以获得数值作为收益
// 第一层的砖块可以随意挑选，但是想挖到下面的砖块是有条件的
// 如果你想挖i层的第j号砖，你需要确保i-1层的第j块、i-1层的第j+1块砖已经被挖开
// 比如2层第3号砖，也就是7数值的位置，如果你想挖
// 你必须把第一层数值9、5的这两块砖挖开才行
// 你最多挖m块砖，返回最大的收益
// 1 <= n <= 50
// 1 <= m <= 1300
// 测试链接 : https://www.luogu.com.cn/problem/P1437
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_BreakOut {

	public static int MAXN = 51;

	public static int MAXM = 1301;

	public static int[][] grid = new int[MAXN][MAXN];

	public static int[][][] dp = new int[MAXN][MAXN][MAXM];

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		for (int j = 1; j <= n; j++) {
			for (int i = n; i >= j; i--) {
				in.nextToken();
				grid[i][j] = (int) in.nval;
			}
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		int sum;
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			sum = 0;
			for (int j = 0; j <= i; j++) {
				sum += grid[i][j];
				for (int k = (j + 1) * j / 2; k <= m; k++) {
					for (int p = Math.max(0, j - 1); p <= Math.min(k - j, i - 1); p++) {
						dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][p][k - j] + sum);
					}
					ans = Math.max(ans, dp[i][j][k]);
				}
			}
		}
		return ans;
	}

}
