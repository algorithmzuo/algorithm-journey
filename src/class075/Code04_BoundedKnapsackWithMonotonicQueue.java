package class075;

// 多重背包单调队列优化
// 宝物筛选
// 一共有n种货物, 背包容量为t
// 每种货物的价值(v[i])、重量(w[i])、数量(c[i])都给出
// 请返回选择货物不超过背包容量的情况下，能得到的最大的价值
// 测试链接 : https://www.luogu.com.cn/problem/P1776
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_BoundedKnapsackWithMonotonicQueue {

	public static int MAXN = 101;

	public static int MAXW = 40001;

	public static int[] v = new int[MAXN];

	public static int[] w = new int[MAXN];

	public static int[] c = new int[MAXN];

	public static int[] dp = new int[MAXW];

	public static int[] queue = new int[MAXW];

	public static int l, r;

	public static int n, t;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			t = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				v[i] = (int) in.nval;
				in.nextToken();
				w[i] = (int) in.nval;
				in.nextToken();
				c[i] = (int) in.nval;
			}
			out.println(compute2());
		}
		out.flush();
		out.close();
		br.close();
	}

	// 严格位置依赖的动态规划 + 单调队列优化枚举
	public static int compute1() {
		int[][] dp = new int[n + 1][t + 1];
		for (int i = 1; i <= n; i++) {
			// 同余分组
			for (int mod = 0; mod <= Math.min(t, w[i] - 1); mod++) {
				l = r = 0;
				for (int j = mod; j <= t; j += w[i]) {
					while (l < r && value1(dp, i, queue[r - 1]) <= value1(dp, i, j)) {
						r--;
					}
					queue[r++] = j;
					if (queue[l] == j - w[i] * (c[i] + 1)) {
						l++;
					}
					dp[i][j] = value1(dp, i, queue[l]) + j / w[i] * v[i];
				}
			}
		}
		return dp[n][t];
	}

	// 当前来到i号货物，需要j位置的指标，返回指标值
	public static int value1(int[][] dp, int i, int j) {
		return dp[i - 1][j] - j / w[i] * v[i];
	}

	// 空间压缩的动态规划 + 单调队列优化枚举
	// 因为求dp[i][j]时需要上一行左侧的若干格子
	// 所以做空间压缩时，每一行需要从右往左求
	// 以此来保证左侧的格子还没有更新，还是"上一行"的状况
	public static int compute2() {
		for (int i = 1; i <= n; i++) {
			for (int mod = 0; mod <= Math.min(t, w[i] - 1); mod++) {
				l = r = 0;
				// 先把c[i]个的指标进入单调队列
				for (int j = t - mod, cnt = 1; j >= 0 && cnt <= c[i]; j -= w[i], cnt++) {
					while (l < r && value2(i, queue[r - 1]) <= value2(i, j)) {
						r--;
					}
					queue[r++] = j;
				}
				for (int j = t - mod, enter = j - w[i] * c[i]; j >= 0; j -= w[i], enter -= w[i]) {
					// 窗口进入enter位置的指标
					if (enter >= 0) {
						while (l < r && value2(i, queue[r - 1]) <= value2(i, enter)) {
							r--;
						}
						queue[r++] = enter;
					}
					// 计算dp[i][j]
					dp[j] = value2(i, queue[l]) + j / w[i] * v[i];
					// 窗口弹出j位置的指标
					if (queue[l] == j) {
						l++;
					}
				}
			}
		}
		return dp[t];
	}

	// 当前来到i号货物，需要j位置的指标，返回指标值
	public static int value2(int i, int j) {
		return dp[j] - j / w[i] * v[i];
	}

}
