package class066;

import java.util.Arrays;

// 最低票价
// 在一个火车旅行很受欢迎的国度，你提前一年计划了一些火车旅行
// 在接下来的一年里，你要旅行的日子将以一个名为 days 的数组给出
// 每一项是一个从 1 到 365 的整数
// 火车票有 三种不同的销售方式
// 一张 为期1天 的通行证售价为 costs[0] 美元
// 一张 为期7天 的通行证售价为 costs[1] 美元
// 一张 为期30天 的通行证售价为 costs[2] 美元
// 通行证允许数天无限制的旅行
// 例如，如果我们在第 2 天获得一张 为期 7 天 的通行证
// 那么我们可以连着旅行 7 天(第2~8天)
// 返回 你想要完成在给定的列表 days 中列出的每一天的旅行所需要的最低消费
// 测试链接 : https://leetcode.cn/problems/minimum-cost-for-tickets/
public class Code02_myMinimumCostForTickets {

	public static int[] durations = new int[]{1,7,30};
	public static int mincostTickets1(int[] days, int[] costs) {
		return f1(days,costs, 0);
	}

	private static int f1(int[] days, int[] costs, int i) {
		if(i == days.length){
			return 0;
		}
		int ans = Integer.MAX_VALUE;
		for (int k = 0, j = i; k < 3 ; k++) {
			while (j < days.length && days[j] - days[i] < durations[k]){
				j++;
			}
			ans = Math.min(ans, costs[k] + f1(days, costs, j));
		}
		return ans;

	}

	public static int mincostTickets2(int[] days, int[] costs) {
		int[] dp = new int[days.length];
		Arrays.fill(dp, Integer.MAX_VALUE);
		return f2(days,costs, dp,0);
	}

	private static int f2(int[] days, int[] costs, int[] dp, int i){
		if(i == days.length){
			return 0;
		}

		if(dp[i] != Integer.MAX_VALUE){
			return dp[i];
		}
		for (int k = 0, j= i ; k < 3; k++) {
			while(j < days.length && days[j] - days[i] < durations[k]){
				j++;
			}
			dp[i] = Math.min(dp[i], costs[k] + f2(days, costs, dp, j));
		}
		return dp[i];
	}


	// 严格位置依赖的动态规划
	// 从底到顶的动态规划
	public static int MAXN = 366;

	public static int[] dp = new int[MAXN];

	// 從底到頂構建 =》 由確定到不確定
	public static int mincostTicket3(int[] days , int[] costs){
		int n = days.length;
		// 1- 將dp
		Arrays.fill(dp, 0, n + 1, Integer.MAX_VALUE);
		// 2- 初始化dp[n] => 越界值
		dp[n] = 0;

		for (int i = n -1 ; i >=0 ; i--) {
			for (int k = 0, j = i ; k < 3; k++) {
				while(j < days.length && days[j] - days[i] < durations[k]){
					j++;
				}
				dp[i] = Math.min(dp[i], costs[i] + dp[j]);
			}
		}
		return dp[0];
	}

}
