package class075;

// 多重背包二进制分组优化(模版)
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
import java.util.Arrays;

public class Code02_BoundedKnapsackWithBinarySplitting {

	public static int MAXN = 1001;

	public static int MAXW = 40001;

	public static int[] v = new int[MAXN];

	public static int[] w = new int[MAXN];

	public static int[] dp = new int[MAXW];

	public static int n, t, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			t = (int) in.nval;
			m = 0;
			for (int i = 1, value, weight, cnt; i <= n; i++) {
				in.nextToken();
				value = (int) in.nval;
				in.nextToken();
				weight = (int) in.nval;
				in.nextToken();
				cnt = (int) in.nval;
				// 整个文件最重要的逻辑 : 二进制分组
				for (int k = 1; k <= cnt; k <<= 1) {
					v[++m] = k * value;
					w[m] = k * weight;
					cnt -= k;
				}
				if (cnt > 0) {
					v[++m] = cnt * value;
					w[m] = cnt * weight;
				}
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	// 01背包的空间压缩代码(模版)
	public static int compute() {
		Arrays.fill(dp, 0, t + 1, 0);
		for (int i = 1; i <= m; i++) {
			for (int j = t; j >= w[i]; j--) {
				dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
			}
		}
		return dp[t];
	}

}
