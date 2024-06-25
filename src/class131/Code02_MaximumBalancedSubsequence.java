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
			k = rank(nums[i] - i);
			pre = max(k);
			if (pre > 0) {
				update(k, pre + nums[i]);
			} else {
				update(k, nums[i]);
			}
		}
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

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void update(int i, long v) {
		while (i <= m) {
			tree[i] = Math.max(tree[i], v);
			i += lowbit(i);
		}
	}

	public static long max(int i) {
		long ans = Long.MIN_VALUE;
		while (i > 0) {
			ans = Math.max(ans, tree[i]);
			i -= lowbit(i);
		}
		return ans;
	}

}
