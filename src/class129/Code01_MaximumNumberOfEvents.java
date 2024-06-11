package class129;

import java.util.Arrays;

// 参加会议II
// 给定n个会议，每个会议有开始时间、结束时间、获得收益三个值
// 如果你参加会议就能得到收益，但是同一时间只能参加一个会议
// 你一共能参加k个会议，如果你选择参加某个会议，那么你必须完整地参加完这个会议
// 会议结束日期是包含在会议内的，不能同时参加一个开始日期与另一个结束日期相同的两个会议
// 请你返回能得到的会议价值最大和
// 测试链接 : https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii/
public class Code01_MaximumNumberOfEvents {

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
