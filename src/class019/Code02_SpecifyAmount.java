package class019;

// 展示acm风格的测试方式
// 子矩阵的最大累加和问题，不要求会解题思路，后面的课会讲
// 每一组测试都给定数据规模
// 需要任何空间都动态生成，在大厂笔试或者比赛中，这种方式并不推荐
// 测试链接 : https://www.nowcoder.com/practice/cb82a97dcd0d48a7b1f4ee917e2c0409?
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_SpecifyAmount{

	public static void main(String[] args) throws IOException {
		// 把文件里的内容，load进来，保存在内存里，很高效，很经济，托管的很好
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 一个一个读数字
		StreamTokenizer in = new StreamTokenizer(br);
		// 提交答案的时候用的，也是一个内存托管区
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) { // 文件没有结束就继续
			// n，二维数组的行
			int n = (int) in.nval;
			in.nextToken();
			// m，二维数组的列
			int m = (int) in.nval;
			// 装数字的矩阵，临时动态生成
			int[][] mat = new int[n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					in.nextToken();
					mat[i][j] = (int) in.nval;
				}
			}
			out.println(maxSumSubmatrix(mat, n, m));
		}
		out.flush();
		br.close();
		out.close();
	}

	// 求子矩阵的最大累加和，后面的课会讲
	public static int maxSumSubmatrix(int[][] mat, int n, int m) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			// 需要的辅助数组，临时动态生成
			int[] arr = new int[m];
			for (int j = i; j < n; j++) {
				for (int k = 0; k < m; k++) {
					arr[k] += mat[j][k];
				}
				max = Math.max(max, maxSumSubarray(arr, m));
			}
		}
		return max;
	}

	// 求子数组的最大累加和，后面的课会讲
	public static int maxSumSubarray(int[] arr, int m) {
		int max = Integer.MIN_VALUE;
		int cur = 0;
		for (int i = 0; i < m; i++) {
			cur += arr[i];
			max = Math.max(max, cur);
			cur = cur < 0 ? 0 : cur;
		}
		return max;
	}

}
