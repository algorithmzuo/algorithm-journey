package class130;

import java.util.Arrays;
import java.util.List;

// 最小移动总距离
// 所有机器人和工厂都分布在x轴上
// 给定长度为n的一维数组robot，robot[i]为第i个机器人的位置
// 给定长度为m的二维数组factory，factory[j][0]为j号工厂的位置，factory[j][0]为容量
// 每个机器人所在的位置都不同，每个工厂所在的位置都不同
// 所有机器人都是坏的，但是机器人可以去往任何工厂进行修理，但是不能超过某个工厂的容量
// 测试数据保证所有机器人都可以被维修，返回所有机器人移动的最小总距离
// 1 <= n、m <= 100
// -10^9 <= robot[i]、factory[j][0] <= +10^9
// 0 <= factory[j][1] <= n
// 测试链接 : https://leetcode.cn/problems/minimum-total-distance-traveled/
public class Code05_MinimumTotalDistanceTraveled {

	public static long minimumTotalDistance1(List<Integer> robot, int[][] factory) {
		int n = robot.size();
		int m = factory.length;
		robot.sort((a, b) -> a - b);
		Arrays.sort(factory, (a, b) -> a[0] - b[0]);
		long[][] dp = new long[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				dp[i][j] = -1;
			}
		}
		return f1(robot, factory, n - 1, m - 1, dp);
	}

	public static long f1(List<Integer> robot, int[][] factory, int i, int j, long[][] dp) {
		if (i < 0) {
			return 0;
		}
		if (j < 0) {
			return Long.MAX_VALUE;
		}
		if (dp[i][j] != -1) {
			return dp[i][j];
		}
		long ans = f1(robot, factory, i, j - 1, dp);
		long distance = 0;
		for (int l = i, num = 1; l >= 0 && num <= factory[j][1]; l--, num++) {
			long curAns = f1(robot, factory, l - 1, j - 1, dp);
			distance += Math.abs(robot.get(l) - factory[j][0]);
			if (curAns != Long.MAX_VALUE) {
				ans = Math.min(ans, curAns + distance);
			}
		}
		dp[i][j] = ans;
		return ans;
	}

	public static long minimumTotalDistance2(List<Integer> robot, int[][] factory) {
		int n = robot.size();
		int m = factory.length;
		robot.sort((a, b) -> a - b);
		Arrays.sort(factory, (a, b) -> a[0] - b[0]);
		long[][] dp = new long[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				// ans = dp[i][j - 1] -> 0...i -> 0...j-1
				long ans = j - 1 >= 0 ? dp[i][j - 1] : Long.MAX_VALUE;
				long distance = 0;
				for (int l = i, num = 1; l >= 0 && num <= factory[j][1]; l--, num++) {
					long curAns = l - 1 < 0 ? 0 : (j - 1 < 0 ? Long.MAX_VALUE : dp[l - 1][j - 1]);
					distance += Math.abs(robot.get(l) - factory[j][0]);
					if (curAns != Long.MAX_VALUE) {
						ans = Math.min(ans, curAns + distance);
					}
				}
				dp[i][j] = ans;
			}
		}
		return dp[n - 1][m - 1];
	}

	// 最优解O(n * m)
	// 目前所有题解都没有达到这个最优的复杂度
	public static long minimumTotalDistance(List<Integer> robot, int[][] factory) {
		int n = robot.size();
		int m = factory.length;
		robot.sort((a, b) -> a - b);
		Arrays.sort(factory, (a, b) -> a[0] - b[0]);
		long[][] dp = new long[n][m];
		long[][] queue = new long[n + 1][2];
		int l, r;
		for (int j = 0; j < m; j++) {
			long add = 0;
			long limit = factory[j][1];
			l = r = 0;
			queue[r][0] = -1;
			queue[r++][1] = 0;
			for (int i = 0; i < n; i++) {
				long p1 = j - 1 >= 0 ? dp[i][j - 1] : Long.MAX_VALUE;
				add += Math.abs((long) robot.get(i) - (long) factory[j][0]);
				if (queue[l][0] == i - limit - 1) {
					l++;
				}
				long p2 = Long.MAX_VALUE;
				if (l < r) {
					long best = queue[l][1];
					if (best != Long.MAX_VALUE) {
						p2 = add + best;
					}
				}
				dp[i][j] = Math.min(p1, p2);
				long fill = p1 == Long.MAX_VALUE ? p1 : (p1 - add);
				while (l < r && queue[r - 1][1] >= fill) {
					r--;
				}
				queue[r][0] = i;
				queue[r++][1] = fill;
			}
		}
		return dp[n - 1][m - 1];
	}

}
