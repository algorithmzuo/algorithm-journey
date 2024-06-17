package class130;

// 向右跳跃获得最大得分
// 给定长度为n+1的数组arr，下标编号0 ~ n，给定正数a、b
// 一开始在0位置，每次可以选择[a,b]之间的一个整数，作为向右跳跃的距离
// 每来到一个位置i，可以获得arr[i]作为得分，位置一旦大于n就停止
// 返回能获得的最大得分
// 1 <= n <= 2 * 10^5
// 1 <= a <= b <= n
// -1000 <= arr[i] <= +1000
// 测试链接 : https://www.luogu.com.cn/problem/P1725
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_JumpRight {

	public static int MAXN = 200001;

	public static int NA = Integer.MIN_VALUE;

	public static int[] arr = new int[MAXN];

	public static int[] dp = new int[MAXN];

	public static int[] queue = new int[MAXN];

	public static int l, r;

	public static int n, a, b;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		a = (int) in.nval;
		in.nextToken();
		b = (int) in.nval;
		for (int i = 0; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		dp[0] = arr[0];
		Arrays.fill(dp, 1, n + 1, NA);
		l = r = 0;
		for (int i = a; i <= n; i++) {
			add(i - a);
			overdue(i - b - 1);
			if (l < r) {
				dp[i] = dp[queue[l]] + arr[i];
			}
		}
		int ans = NA;
		for (int i = n + 1 - b; i <= n; i++) {
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	public static void add(int p) {
		if (dp[p] != NA) {
			while (l < r && dp[queue[r - 1]] <= dp[p]) {
				r--;
			}
			queue[r++] = p;
		}
	}

	public static void overdue(int p) {
		if (l < r && queue[l] == p) {
			l++;
		}
	}

}
