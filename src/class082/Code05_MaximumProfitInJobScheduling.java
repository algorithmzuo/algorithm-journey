package class082;

import java.util.Arrays;

// 测试链接 : https://leetcode.cn/problems/maximum-profit-in-job-scheduling/
public class Code05_MaximumProfitInJobScheduling {

	public static int MAXN = 50001;

	public static int[][] jobs = new int[MAXN][3];

	public static int[] dp = new int[MAXN];

	public static int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
		int n = startTime.length;
		for (int i = 0; i < n; i++) {
			jobs[i][0] = startTime[i];
			jobs[i][1] = endTime[i];
			jobs[i][2] = profit[i];
		}
		Arrays.sort(jobs, 0, n, (a, b) -> a[1] - b[1]);
		dp[0] = jobs[0][2];
		for (int i = 1, start; i < n; i++) {
			dp[i] = jobs[i][2];
			start = jobs[i][0];
			if (jobs[0][1] <= start) {
				dp[i] += dp[find(i, start)];
			}
			dp[i] = Math.max(dp[i], dp[i - 1]);
		}
		return dp[n - 1];
	}

	public static int find(int i, int start) {
		int ans = 0;
		int l = 0;
		int r = i - 1;
		int m;
		while (l <= r) {
			m = (l + r) / 2;
			if (jobs[m][1] <= start) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

}
