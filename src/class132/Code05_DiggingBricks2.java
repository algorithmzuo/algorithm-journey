package class132;

// 从上到下挖砖块(优化枚举+空间压缩)
// 一堆组成倒三角形状的砖埋在地里，一共有n层，第1层有n块砖，每层递减，类似如下数据
// 4 2 9 5
//  3 1 7
//   2 4
//    8
// 需要从第1层开始挖，每挖开一块砖都可以获得数值作为收益，第1层的砖可以随意挖
// 但是挖到下面的砖是有条件的，想挖i层的j号砖，你需要确保i-1层的(j、j+1)块砖已经被挖开
// 最多可以挖m块砖，返回最大的收益
// 1 <= n <= 50
// 1 <= m <= 1300
// 砖块数值 <= 100
// 测试链接 : https://www.luogu.com.cn/problem/P1437
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_DiggingBricks2 {

	public static int MAXN = 51;

	public static int MAXM = 1301;

	public static int[][] grid = new int[MAXN][MAXN];

	public static int[][] dp = new int[MAXN][MAXM];

	public static int[][] max = new int[MAXN][MAXM];

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

	// 优化枚举 + 空间压缩
	// 时间复杂度O(n^2 * m)
	public static int compute() {
		int ans = 0;
		for (int i = 1, cur; i <= n; i++) {
			prepare(i - 1);
			cur = 0;
			for (int j = 0; j <= i; j++) {
				cur += grid[i][j];
				for (int k = (j + 1) * j / 2; k <= m; k++) {
					dp[j][k] = max[Math.max(0, j - 1)][k - j] + cur;
					ans = Math.max(ans, dp[j][k]);
				}
			}
		}
		return ans;
	}

	// 预处理结构优化枚举
	public static void prepare(int rowLimit) {
		for (int col = 0; col <= m; col++) {
			for (int row = rowLimit, suf = 0; row >= 0; row--) {
				suf = Math.max(suf, dp[row][col]);
				max[row][col] = suf;
			}
		}
	}

}
