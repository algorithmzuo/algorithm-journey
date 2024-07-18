package class132;

// 炮兵阵地
// 给定一个n * m的二维数组grid，其中的1代表可以摆放炮兵，0代表不可以摆放
// 任何炮兵攻击范围是一个"十字型"的区域，具体是上下左右两个格子的区域
// 你的目的是在gird里摆尽量多的炮兵，但要保证任何两个炮兵之间无法互相攻击
// 返回最多能摆几个炮兵
// 1 <= n <= 100
// 1 <= m <= 10
// 0 <= grid[i][j] <= 1
// 测试链接 : https://www.luogu.com.cn/problem/P2704
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Code02_SoldierPosition1 {

	public static int MAXN = 100;

	public static int MAXM = 10;

	public static int MAXS = 60;

	public static int[][] grid = new int[MAXN][MAXM];

	public static int[][][] dp = new int[MAXN][MAXS][MAXS];

	public static int[] sta = new int[MAXS];

	public static int n, m, k;

//	// 打印num第m-1位到第0位的二进制状态
//	public static void printBinary(int num) {
//		for (int i = m - 1; i >= 0; i--) {
//			System.out.print((num & (1 << i)) == 0 ? "0" : "1");
//		}
//		System.out.println();
//	}

	public static void main(String[] args) throws IOException {
//		m = 10;
//		k = 0;
//		prepare(0, 0);
//		for (int i = 0; i < k; i++) {
//			printBinary(sta[i]);
//		}
//		System.out.println("有效状态数量 : " + k);

		Kattio io = new Kattio();
		n = io.nextInt();
		m = io.nextInt();
		k = 0;
		char[] line;
		for (int i = 0; i < n; i++) {
			line = io.next().toCharArray();
			for (int j = 0; j < m; j++) {
				grid[i][j] = line[j] == 'H' ? 0 : 1;
			}
		}
		prepare(0, 0);
		io.println(compute());
		io.flush();
		io.close();
	}

	public static void prepare(int j, int s) {
		if (j >= m) {
			sta[k++] = s;
		} else {
			prepare(j + 1, s);
			prepare(j + 3, s | (1 << j));
		}
	}

	public static int compute() {
		for (int a = 0, cnt; a < k; a++) {
			cnt = cnt(0, sta[a]);
			dp[0][a][0] = cnt;
		}
		for (int i = 1; i < n; i++) {
			for (int a = 0, cnt; a < k; a++) {
				cnt = cnt(i, sta[a]);
				for (int b = 0; b < k; b++) {
					if ((sta[a] & sta[b]) == 0) {
						for (int c = 0; c < k; c++) {
							if ((sta[b] & sta[c]) == 0 && (sta[a] & sta[c]) == 0) {
								dp[i][a][b] = Math.max(dp[i][a][b], dp[i - 1][b][c] + cnt);
							}
						}
					}
				}
			}
		}
		int ans = 0;
		for (int a = 0; a < k; a++) {
			for (int b = 0; b < k; b++) {
				ans = Math.max(ans, dp[n - 1][a][b]);
			}
		}
		return ans;
	}

	// 第i行士兵状态为s，结合grid第i号的状况
	// 返回摆放士兵的数量
	public static int cnt(int i, int s) {
		int ans = 0;
		for (int j = 0; j < m; j++) {
			if (((s >> j) & 1) == 1 && grid[i][j] == 1) {
				ans++;
			}
		}
		return ans;
	}

	// Kattio类IO效率很好，但还是不如StreamTokenizer
	// 只有StreamTokenizer无法正确处理时，才考虑使用这个类
	// 参考链接 : https://oi-wiki.org/lang/java-pro/
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
