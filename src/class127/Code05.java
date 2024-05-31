package class127;

import java.util.Arrays;

// 参加会议II
// 测试链接 : https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii/
public class Code05 {

	public static int maxValue(int[][] events, int k) {
		int n = events.length;
		Arrays.sort(events, (a, b) -> a[1] - b[1]);
		int[][] dp = new int[n][k + 1];
		for (int j = 1; j <= k; j++) {
			dp[0][j] = events[0][2];
		}
		for (int i = 1, pre; i < n; i++) {
			pre = find(events, i - 1, events[i][0]);
			for (int j = 1; j <= k; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], (pre == -1 ? 0 : dp[pre][j - 1]) + events[i][2]);
			}
		}
		return dp[n - 1][k];
	}

	public static int find(int[][] events, int i, int s) {
		int l = 0, r = i, mid;
		int ans = -1;
		while (l <= r) {
			mid = (l + r) / 2;
			if (events[mid][1] < s) {
				ans = mid;
				l = mid + 1;
			} else {
				r = mid - 1;
			}
		}
		return ans;
	}

}
