package class128;

// 感谢热心的同学，找到了题目2的在线测试
// 最好的部署
// 一共有n台机器，编号1 ~ n，所有机器排成一排
// 你只能一台一台的部署机器，你可以决定部署的顺序，最终所有机器都要部署
// 给定三个长度为n的数组a[]、b[]、c[]
// a[i] : 如果i号机器部署时，相邻有0台机器部署，此时能获得的收益
// b[i] : 如果i号机器部署时，相邻有1台机器部署，此时能获得的收益
// c[i] : 如果i号机器部署时，相邻有2台机器部署，此时能获得的收益
// 第1号机器、第n号机器当然不会有两台相邻的机器
// 返回部署的最大收益
// 1 <= n、a[i]、b[i]、c[i] <= 10^4
// 测试链接 : https://vjudge.net/problem/OpenJ_Bailian-4150
// 测试链接 : http://bailian.openjudge.cn/practice/4150/
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code02_BestDeploy2 {

	public static int MAXN = 10002;
	public static int n;

	public static int[] a = new int[MAXN];
	public static int[] b = new int[MAXN];
	public static int[] c = new int[MAXN];

	// 课上讲的时间复杂度O(n)的方法
	// 逻辑没有任何修改
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
