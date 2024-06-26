package class131;

import java.util.Arrays;

// 平衡子序列的最大和
// 给定一个长度为n的数组nums，下面定义平衡子序列
// 如果下标i和下标j被选进了子序列，i在j的左边
// 那么必须有nums[j] - nums[i] >= j - i
// 如果一个子序列中任意的两个下标都满足上面的要求，那子序列就是平衡的
// 返回nums所有平衡子序列里，最大的累加和是多少
// 1 <= n <= 10^5
// -10^9 <= nums[i] <= +10^9
// 测试链接 : https://leetcode.cn/problems/maximum-balanced-subsequence-sum/
public class Code02_MaximumBalancedSubsequence {

	public static long maxBalancedSubsequenceSum(int[] nums) {
		build(nums);
		long pre;
		for (int i = 0, k; i < n; i++) {
			// k的含义为当前的指标是第几号指标
			k = rank(nums[i] - i);
			// dp[1号..k号指标]中的最大值是多少
			pre = max(k);
			if (pre < 0) {
				// 如果之前的最好情况是负数，那么不要之前的数了
				// 当前数字自己单独形成平衡子序列
				// 去更新dp[k号指标]，看能不能变得更大
				update(k, nums[i]);
			} else {
				// 如果之前的最好情况不是负数，那么和当前数字一起形成更大的累加和
				// 去更新dp[k号指标]，看能不能变得更大
				update(k, pre + nums[i]);
			}
		}
		// 返回dp[1号..m号指标]中的最大值
		return max(m);
	}

	public static int MAXN = 100001;

	public static int[] sort = new int[MAXN];

	public static long[] tree = new long[MAXN];

	public static int n, m;

	public static void build(int[] nums) {
		n = nums.length;
		for (int i = 1, j = 0; i <= n; i++, j++) {
			sort[i] = nums[j] - j;
		}
		Arrays.sort(sort, 1, n + 1);
		m = 1;
		for (int i = 2; i <= n; i++) {
			if (sort[m] != sort[i]) {
				sort[++m] = sort[i];
			}
		}
		Arrays.fill(tree, 1, m + 1, Long.MIN_VALUE);
	}

	// 当前的指标值是v，返回这是第几号指标
	public static int rank(int v) {
		int left = 1, right = m, mid;
		int ans = 0;
		while (left <= right) {
			mid = (left + right) / 2;
			if (sort[mid] <= v) {
				ans = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return ans;
	}

	// dp[i号指标]，当前算出的值是v
	public static void update(int i, long v) {
		while (i <= m) {
			tree[i] = Math.max(tree[i], v);
			i += i & -i;
		}
	}

	// dp[1..i]，最大值多少返回
	public static long max(int i) {
		long ans = Long.MIN_VALUE;
		while (i > 0) {
			ans = Math.max(ans, tree[i]);
			i -= i & -i;
		}
		return ans;
	}

}
