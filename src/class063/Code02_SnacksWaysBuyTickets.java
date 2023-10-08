package class063;

// 牛牛的背包问题 & 世界冰球锦标赛
// 牛牛准备参加学校组织的春游, 出发前牛牛准备往背包里装入一些零食, 牛牛的背包容量为w。
// 牛牛家里一共有n袋零食, 第i袋零食体积为v[i]。
// 牛牛想知道在总体积不超过背包容量的情况下,他一共有多少种零食放法(总体积为0也算一种放法)。
// 输入描述：
// 输入包括两行
// 第一行为两个正整数n和w(1 <= n <= 30, 1 <= w <= 2 * 10^9),表示零食的数量和背包的容量
// 第二行n个正整数v[i](0 <= v[i] <= 10^9),表示每袋零食的体积
// 输出描述：
// 输出一个正整数, 表示牛牛一共有多少种零食放法。
// 测试链接 : https://www.nowcoder.com/practice/d94bb2fa461d42bcb4c0f2b94f5d4281
// 测试链接 : https://www.luogu.com.cn/problem/P4799
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下所有代码，把主类名改成Main，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_SnacksWaysBuyTickets {

	public static int MAXN = 40;

	public static int MAXM = 1 << 20;

	public static long[] arr = new long[MAXN];

	public static long[] lsum = new long[MAXM];

	public static long[] rsum = new long[MAXM];

	public static int n;

	public static long w;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			w = (long) in.nval;
			for (int i = 0; i < n; i++) {
				in.nextToken();
				arr[i] = (long) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static long compute() {
		int lsize = f(0, n >> 1, 0, w, lsum, 0);
		int rsize = f(n >> 1, n, 0, w, rsum, 0);
		Arrays.sort(lsum, 0, lsize);
		Arrays.sort(rsum, 0, rsize);
		long ans = 0;
		for (int i = lsize - 1, j = 0; i >= 0; i--) {
			while (j < rsize && lsum[i] + rsum[j] <= w) {
				j++;
			}
			ans += j;
		}
		return ans;
	}

	// arr[i....e]结束，e再往右不展开了！
	// 返回值 : ans数组填到了什么位置！
	public static int f(int i, int e, long s, long w, long[] ans, int j) {
		if (s > w) {
			return j;
		}
		// s <= w
		if (i == e) {
			ans[j++] = s;
		} else {
			// 不要arr[i]位置的数
			j = f(i + 1, e, s, w, ans, j);
			// 要arr[i]位置的数
			j = f(i + 1, e, s + arr[i], w, ans, j);
		}
		return j;
	}

}