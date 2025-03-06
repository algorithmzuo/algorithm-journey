package class128;

// 感谢热心的同学，找到了题目5的在线测试
// 最大平均值和中位数
// 给定一个长度为n的数组arr，现在要选出一些数
// 满足 任意两个相邻的数中至少有一个数被选择
// 被选中的数字平均值的最大值，打印的答案为double类型，误差在0.001以内
// 被选中的数字中位数的最大值，打印的答案为int类型，中位数认为是上中位数
// 2 <= n <= 10^5
// 1 <= arr[i] <= 10^9
// 测试链接 : https://atcoder.jp/contests/abc236/tasks/abc236_e
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_MaximizeMedian2 {

	public static int MAXN = 100005;
	public static int n;
	public static int[] arr = new int[MAXN];

	// 求最大平均数需要
	public static double[] help1 = new double[MAXN];
	public static double[][] dp1 = new double[MAXN][2];

	// 求最大上中位数需要
	public static int[] sorted = new int[MAXN];
	public static int[] help2 = new int[MAXN];
	public static int[][] dp2 = new int[MAXN][2];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(average());
		out.println(median());
		out.flush();
		out.close();
		br.close();
	}

	// 最大平均数
	// 课上没有讲，但是很好理解，也是二分答案
	// 假设arr中最小值为l，最大值为r
	// 那么最大平均值必然在[l, r]范围上
	// 假设平均值设为中点m，arr中所有的数字都减去m
	// 如果此时，任意两个相邻的数中至少有一个数被选择
	// 最后得到的结果 >= 0，说明最大平均值至少是m，去右侧二分
	// 否则去左侧二分
	public static double average() {
		double l = Double.MAX_VALUE, r = Double.MIN_VALUE, m;
		for (int i = 1; i <= n; i++) {
			l = Math.min(l, arr[i]);
			r = Math.max(r, arr[i]);
		}
		// 二分60次，足够让误差小于0.001
		for (int i = 1; i <= 60; i++) {
			m = (l + r) / 2;
			if (check1(m)) {
				l = m;
			} else {
				r = m;
			}
		}
		return l;
	}

	public static boolean check1(double x) {
		// arr中所有的数字都减去x，得到的数字填入help1
		for (int i = 1; i <= n; i++) {
			help1[i] = (double) arr[i] - x;
		}
		// 和课上讲的一样的逻辑
		// 任意两个相邻的数中至少有一个数被选择，去得到dp表
		dp1[n + 1][0] = dp1[n + 1][1] = 0;
		for (int i = n; i >= 1; i--) {
			dp1[i][0] = Math.max(help1[i] + dp1[i + 1][0], dp1[i + 1][1]);
			dp1[i][1] = help1[i] + dp1[i + 1][0];
		}
		return dp1[1][0] >= 0;
	}

	// 最大上中位数，就和课上讲的一样了
	public static int median() {
		for (int i = 1; i <= n; i++) {
			sorted[i] = arr[i];
		}
		Arrays.sort(sorted, 1, n + 1);
		int l = 1, r = n, m, ans = 0;
		while (l <= r) {
			m = (l + r) / 2;
			if (check2(sorted[m])) {
				ans = sorted[m];
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	public static boolean check2(int x) {
		for (int i = 1; i <= n; i++) {
			help2[i] = arr[i] >= x ? 1 : -1;
		}
		dp2[n + 1][0] = dp2[n + 1][1] = 0;
		for (int i = n; i >= 1; i--) {
			dp2[i][0] = Math.max(help2[i] + dp2[i + 1][0], dp2[i + 1][1]);
			dp2[i][1] = help2[i] + dp2[i + 1][0];
		}
		return dp2[1][0] > 0;
	}

}