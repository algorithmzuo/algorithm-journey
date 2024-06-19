package class130;

// 粉刷木板的最大收益
// 一共有n个木板，每个木板长度为1，最多粉刷一次，也可以不刷
// 一共有m个工人，每个工人用(li, pi, si)表示：
// 该工人必须刷连续区域的木板，并且连续的长度不超过li
// 该工人每刷一块木板可以得到pi的钱
// 该工人刷的连续区域必须包含si位置的木板
// 返回所有工人最多能获得多少钱
// 1 <= n <= 16000
// 1 <= m <= 100
// 1 <= pi <= 10000
// 测试链接 : http://poj.org/problem?id=1821
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Comparator;

public class Code04_PaintingMaximumScore {

	public static int MAXN = 16001;

	public static int MAXM = 101;

	public static int[][] workers = new int[MAXM][3];

	public static int[][] dp = new int[MAXM][MAXN];

	public static int[] queue = new int[MAXN];

	public static int l, r;

	public static int n, m;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			n = (int) in.nval;
			in.nextToken();
			m = (int) in.nval;
			for (int i = 1; i <= m; i++) {
				in.nextToken();
				workers[i][0] = (int) in.nval;
				in.nextToken();
				workers[i][1] = (int) in.nval;
				in.nextToken();
				workers[i][2] = (int) in.nval;
			}
			out.println(compute());
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		Arrays.sort(workers, 1, m + 1, new WorkerComparator());
		for (int i = 1, li, pi, si; i <= m; i++) {
			li = workers[i][0];
			pi = workers[i][1];
			si = workers[i][2];
			l = r = 0;
			for (int j = Math.max(0, si - li); j < si; j++) {
				while (l < r && value(i, pi, queue[r - 1]) <= value(i, pi, j)) {
					r--;
				}
				queue[r++] = j;
			}
			for (int j = 1; j <= n; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				if (j >= si) {
					if (l < r && queue[l] == j - li - 1) {
						l++;
					}
					if (l < r) {
						dp[i][j] = Math.max(dp[i][j], value(i, pi, queue[l]) + pi * j);
					}
				}
			}
		}
		return dp[m][n];
	}

	// 之前工人负责的区域以j号木板结尾时，返回指标的值
	public static int value(int i, int pi, int j) {
		return dp[i - 1][j] - pi * j;
	}

	// poj平台java版本较老，不支持Lambda表达式方式的比较器，需要自己定义
	public static class WorkerComparator implements Comparator<int[]> {

		@Override
		public int compare(int[] o1, int[] o2) {
			return o1[2] - o2[2];
		}

	}

}
