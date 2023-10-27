package class070;

// 子矩阵最大累加和问题
// 测试链接 : https://www.nowcoder.com/questionTerminal/cb82a97dcd0d48a7b1f4ee917e2c0409
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code06_MaximumSubmatrix {

	public static int MAXN = 201;

	public static int MAXM = 201;

	public static int[][] grid = new int[MAXN][MAXM];

	public static int[] nums = new int[MAXM];

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					in.nextToken();
					grid[i][j] = (int) in.nval;
				}
			}
			out.println(maxSubMatrix());
		}
		out.flush();
		br.close();
		out.close();
	}

	public static int maxSubMatrix() {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			Arrays.fill(nums, 0, m, 0);
			for (int j = i; j < n; j++) {
				for (int k = 0; k < m; k++) {
					nums[k] += grid[j][k];
				}
				max = Math.max(max, maxSubArray());
			}
		}
		return max;
	}

	public static int maxSubArray() {
		int ans = nums[0];
		for (int i = 1, pre = nums[0]; i < n; i++) {
			pre = Math.max(nums[i], nums[i] + pre);
			ans = Math.max(ans, pre);
		}
		return ans;
	}

}
