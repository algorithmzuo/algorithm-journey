package class129;

import java.util.Arrays;

// 参加会议II
// 给定n个会议，每个会议有开始时间、结束时间、收益三个值
// 参加会议就能得到收益，但是同一时间只能参加一个会议
// 一共能参加k个会议，如果选择参加某个会议，那么必须完整的参加完这个会议
// 会议结束日期是包含在会议内的，一个会议的结束时间等于另一个会议的开始时间，不能两个会议都参加
// 返回能得到的会议价值最大和
// 1 <= n * k <= 10^6
// 1 <= 开始时间、结束时间 <= 10^9
// 1 <= 收益 <= 10^6
// 测试链接 : https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii/
public class Code01_MaximumNumberOfEvents {

	// events[i][0] : 开始时间
	// events[i][1] : 结束时间
	// events[i][2] : 收益
	public static int maxValue(int[][] events, int k) {
		int n = events.length;
		Arrays.sort(events, (a, b) -> a[1] - b[1]);
		// dp[i][j] : 0..i范围上最多选j个会议召开，最大收益是多少
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

	// 0...i范围上的会议，根据结束日期排序了
	// 找到结束时间<s，最右的会议编号
	// 如果不存在这样的会议返回-1
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
