package class130;

// 向下收集获得最大能量
// 有一个n * m的区域，行和列的编号从1开始
// 每个能量点用(i, j, v)表示，i行j列上有价值为v的能量点
// 一共有k个能量点，并且所有能量点一定在不同的位置
// 一开始可以在第1行的任意位置，然后每一步必须向下移动
// 向下去往哪个格子是一个范围，如果当前在(i, j)位置
// 那么往下可以选择(i+1, j-t)...(i+1, j+t)其中的一个格子
// 到达最后一行时，收集过程停止，返回能收集到的最大能量价值
// 1 <= n、m、k、t <= 4000
// 1 <= v <= 100
// 测试链接 : https://www.luogu.com.cn/problem/P3800
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_CollectDown {

	public static int MAXN = 4001;

	public static int MAXM = 4001;

	public static int[][] dp = new int[MAXN][MAXM];

	public static int[] queue = new int[MAXM];

	public static int l, r;

	public static int n, m, k, t;

	public static void build() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				dp[i][j] = 0;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		in.nextToken();
		t = (int) in.nval;
		build();
		for (int i = 1, r, c, v; i <= k; i++) {
			in.nextToken();
			r = (int) in.nval;
			in.nextToken();
			c = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			dp[r][c] = v;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		for (int i = 2; i <= n; i++) {
			l = r = 0;
			for (int j = 1; j <= t; j++) {
				add(i - 1, j);
			}
			for (int j = 1; j <= m; j++) {
				add(i - 1, j + t);
				overdue(j - t - 1);
				dp[i][j] += dp[i - 1][queue[l]];
			}
		}
		int ans = Integer.MIN_VALUE;
		for (int j = 1; j <= m; j++) {
			ans = Math.max(ans, dp[n][j]);
		}
		return ans;
	}

	public static void add(int i, int j) {
		if (j <= m) {
			while (l < r && dp[i][queue[r - 1]] <= dp[i][j]) {
				r--;
			}
			queue[r++] = j;
		}
	}

	public static void overdue(int t) {
		if (l < r && queue[l] == t) {
			l++;
		}
	}

}
