package class075;

// 观赏樱花
// 给定一个背包的容量t，一共有n种货物，并且给定每种货物的信息
// 花费(cost)、价值(val)、数量(cnt)
// 如果cnt == 0，代表这种货物可以无限选择
// 如果cnt > 0，那么cnt代表这种货物的数量
// 挑选货物的总容量在不超过t的情况下，返回能得到的最大价值
// 背包容量不超过1000，每一种货物的花费都>0
// 测试链接 : https://www.luogu.com.cn/problem/P1833
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

// 完全背包转化为多重背包
// 再把多重背包通过二进制分组转化为01背包
public class Code03_CherryBlossomViewing {

	public static int MAXN = 100001;

	public static int MAXW = 1001;

	public static int ENOUGH = 1001;

	public static int[] v = new int[MAXN];

	public static int[] w = new int[MAXN];

	public static int[] dp = new int[MAXW];

	public static int hour1, minute1, hour2, minute2;

	public static int t, n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		in.parseNumbers();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			hour1 = (int) in.nval;
			// 跳过冒号
			in.nextToken();
			in.nextToken();
			minute1 = (int) in.nval;
			in.nextToken();
			hour2 = (int) in.nval;
			// 跳过冒号
			in.nextToken();
			in.nextToken();
			minute2 = (int) in.nval;
			if (minute1 > minute2) {
				hour2--;
				minute2 += 60;
			}
			// 计算背包容量
			t = (hour2 - hour1) * 60 + minute2 - minute1;
			in.nextToken();
			n = (int) in.nval;
			m = 0;
			for (int i = 0, cost, val, cnt; i < n; i++) {
				in.nextToken();
				cost = (int) in.nval;
				in.nextToken();
				val = (int) in.nval;
				in.nextToken();
				cnt = (int) in.nval;
				if (cnt == 0) {
					cnt = ENOUGH;
				}
				// 二进制分组
				for (int k = 1; k <= cnt; k <<= 1) {
					v[++m] = k * val;
					w[m] = k * cost;
					cnt -= k;
				}
				if (cnt > 0) {
					v[++m] = cnt * val;
					w[m] = cnt * cost;
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
