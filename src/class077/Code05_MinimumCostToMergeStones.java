package class077;

// 合并石头的最低成本
// 有 n 堆石头排成一排，第 i 堆中有 stones[i] 块石头
// 每次 移动 需要将 连续的 k 堆石头合并为一堆，而这次移动的成本为这 k 堆中石头的总数
// 返回把所有石头合并成一堆的最低成本
// 如果无法合并成一堆返回-1
// 测试链接 : https://leetcode.cn/problems/minimum-cost-to-merge-stones/
public class Code05_MinimumCostToMergeStones {

	// 时间复杂度O(n^4)
	public static int mergeStones1(int[] stones, int k) {
		int n = stones.length;
		if ((n - 1) % (k - 1) > 0) {
			return -1;
		}
		int[] presum = new int[n + 1];
		for (int i = 0; i < n; i++) {
			presum[i + 1] = presum[i] + stones[i];
		}
		int[][][] dp = new int[n][n][k + 1];
		return f(stones, k, presum, 0, n - 1, 1, dp);
	}

	public static int f(int[] stones, int k, int[] presum, int l, int r, int p, int[][][] dp) {
		if (dp[l][r][p] != 0) {
			return dp[l][r][p];
		}
		int ans = Integer.MAX_VALUE;
		if (l == r) {
			ans = 0;
		} else {
			if (p == 1) {
				ans = f(stones, k, presum, l, r, k, dp) + presum[r + 1] - presum[l];
			} else {
				for (int m = l; m < r; m += k - 1) {
					// m每次跳k-1个位置，这是观察出来的剪枝策略
					ans = Math.min(ans, f(stones, k, presum, l, m, 1, dp) + f(stones, k, presum, m + 1, r, p - 1, dp));
				}
			}
		}
		dp[l][r][p] = ans;
		return ans;
	}

	// 时间复杂度O(n^3)
	public static int mergeStones2(int[] stones, int k) {
		int n = stones.length;
		if ((n - 1) % (k - 1) != 0) {
			return -1;
		}
		// dp[l][r] : l...r范围上的石头，合并到不能再合并，最小代价是多少
		int[][] dp = new int[n][n];
		int[] presum = new int[n + 1];
		for (int i = 0, j = 1, sum = 0; i < n; i++, j++) {
			sum += stones[i];
			presum[j] = sum;
		}
		for (int l = n - 2, ans; l >= 0; l--) {
			for (int r = l + 1; r < n; r++) {
				// 如果l.....r最终一定是1份
				// 那么l.....p最终一定是1份，那么p+1...r最终一定是k-1份
				// 如果l.....r最终一定是s份
				// 那么l.....p最终一定是1份，那么p+1...r最终一定是s-1份
				// l...r上到底最终几份，根本就无法改变，所以不纠结了
				// 那么也就知道，0....n-1最终一定是1份，也无法改变
				// p += k-1很重要
				// p每次跳k-1，如果l.....r最终一定是1份
				// 就一定能保证l.....p最终一定是1份
				// 也一定能保证p+1...r最终一定是k-1份
				// 最后合并成1份即可
				// p每次跳k-1，如果l.....r最终一定是s份
				// 就一定能保证l.....p最终一定是1份
				// 也一定能保证p+1...r最终一定是s-1份
				// 最终不用合并
				ans = Integer.MAX_VALUE;
				for (int p = l; p < r; p += k - 1) {
					ans = Math.min(ans, dp[l][p] + dp[p + 1][r]);
				}
				if ((r - l) % (k - 1) == 0) {
					ans += presum[r + 1] - presum[l];
				}
				dp[l][r] = ans;
			}
		}
		return dp[0][n - 1];
	}

}
