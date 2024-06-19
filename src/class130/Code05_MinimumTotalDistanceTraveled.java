package class130;

import java.util.Arrays;
import java.util.List;

// 最小移动总距离
// 所有工厂和机器人都分布在x轴上
// 给定长度为n的二维数组factory，factory[i][0]为i号工厂的位置，factory[i][1]为容量
// 给定长度为m的一维数组robot，robot[j]为第j个机器人的位置
// 每个工厂所在的位置都不同，每个机器人所在的位置都不同，机器人到工厂的距离为位置差的绝对值
// 所有机器人都是坏的，但是机器人可以去往任何工厂进行修理，但是不能超过某个工厂的容量
// 测试数据保证所有机器人都可以被维修，返回所有机器人移动的最小总距离
// 1 <= n、m <= 100
// -10^9 <= factory[i][0]、robot[j] <= +10^9
// 0 <= factory[i][1] <= m
// 测试链接 : https://leetcode.cn/problems/minimum-total-distance-traveled/
public class Code05_MinimumTotalDistanceTraveled {

	public static long NA = Long.MAX_VALUE;

	public static int MAXN = 101;

	public static int MAXM = 101;

	public static int n, m;

	// 工厂下标从1开始，fac[i][0]表示位置，fac[i][1]表示容量
	public static int[][] fac = new int[MAXN][2];

	// 机器人下标从1开始，rob[i]表示位置
	public static int[] rob = new int[MAXM];

	// dp[i][j] : 1...i工厂去修理1...j号机器人，最短总距离是多少
	public static long[][] dp = new long[MAXN][MAXM];

	// 前缀和数组
	public static long[] sum = new long[MAXM];

	// 单调队列
	public static int[] queue = new int[MAXM];

	public static int l, r;

	public static void build(int[][] factory, List<Integer> robot) {
		// 工厂和机器人都根据所在位置排序
		Arrays.sort(factory, (a, b) -> a[0] - b[0]);
		robot.sort((a, b) -> a - b);
		n = factory.length;
		m = robot.size();
		// 让工厂和机器人的下标都从1开始
		for (int i = 1; i <= n; i++) {
			fac[i][0] = factory[i - 1][0];
			fac[i][1] = factory[i - 1][1];
		}
		for (int i = 1; i <= m; i++) {
			rob[i] = robot.get(i - 1);
		}
		// dp初始化
		for (int j = 1; j <= m; j++) {
			dp[0][j] = NA;
		}
	}

	// 最优解O(n * m)
	// 其他题解都没有达到这个最优复杂度
	public static long minimumTotalDistance(List<Integer> robot, int[][] factory) {
		build(factory, robot);
		for (int i = 1, cap; i <= n; i++) {
			// i号工厂的容量
			cap = fac[i][1];
			// sum[j]表示
			// 1号机器人去往i号工厂的距离
			// 2号机器人去往i号工厂的距离
			// ...
			// j号机器人去往i号工厂的距离
			// 上面全加起来，也就是前缀和的概念
			for (int j = 1; j <= m; j++) {
				sum[j] = sum[j - 1] + dist(i, j);
			}
			l = r = 0;
			for (int j = 1; j <= m; j++) {
				dp[i][j] = dp[i - 1][j];
				if (value(i, j) != NA) {
					while (l < r && value(i, queue[r - 1]) >= value(i, j)) {
						r--;
					}
					queue[r++] = j;
				}
				if (l < r && queue[l] == j - cap) {
					l++;
				}
				if (l < r) {
					dp[i][j] = Math.min(dp[i][j], value(i, queue[l]) + sum[j]);
				}
			}
		}
		return dp[n][m];
	}

	// i号工厂和j号机器人的距离
	public static long dist(int i, int j) {
		return Math.abs((long) fac[i][0] - rob[j]);
	}

	// i号工厂从j号机器人开始负责的指标
	// 真的可行，返回指标的值
	// 如果不可行，返回NA
	public static long value(int i, int j) {
		if (dp[i - 1][j - 1] == NA) {
			return NA;
		}
		return dp[i - 1][j - 1] - sum[j - 1];
	}

}
