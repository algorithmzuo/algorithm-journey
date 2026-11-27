package class128;

// 最好的部署
// 感谢热心的同学，找到了题目2的在线测试
// 一共有n台机器，按编号1 ~ n从左到右排成一排
// 每次部署一台机器，部署顺序由你决定，每台机器都要部署恰好一次
// 给定三个长度为n的数组a[]、b[]、c[]
// a[i] : 部署i号机器时，如果相邻机器都还没有部署，获得的收益
// b[i] : 部署i号机器时，如果恰好有1台相邻机器已经部署，获得的收益
// c[i] : 部署i号机器时，如果恰好有2台相邻机器已经部署，获得的收益
// 第1号机器和第n号机器最多只有1台相邻机器
// 求部署所有机器能获得的最大总收益
// 1 <= n <= 3000
// 0 <= a[i]、b[i]、c[i] <= 10^5
// 测试链接 : https://codeforces.com/problemset/problem/358/D
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_BestDeploy2 {

	public static int MAXN = 3001;
	public static int n;

	public static int[] a = new int[MAXN];
	public static int[] b = new int[MAXN];
	public static int[] c = new int[MAXN];

	// 课上讲的时间复杂度O(n)的方法，逻辑没有任何修改
	public static int compute() {
		int[][] dp = new int[n + 1][2];
		dp[n][0] = a[n];
		dp[n][1] = b[n];
		for (int i = n - 1; i >= 1; i--) {
			dp[i][0] = Math.max(a[i] + dp[i + 1][1], b[i] + dp[i + 1][0]);
			dp[i][1] = Math.max(b[i] + dp[i + 1][1], c[i] + dp[i + 1][0]);
		}
		return dp[1][0];
	}

	public static void main(String[] args) throws IOException {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		for (int i = 1; i <= n; i++) {
			a[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			b[i] = in.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			c[i] = in.nextInt();
		}
		out.println(compute());
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {

		private final byte[] buffer = new byte[1 << 16];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buffer);
				ptr = 0;
				if (len <= 0)
					return -1;
			}
			return buffer[ptr++];
		}

		int nextInt() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			int val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}

	}

}
