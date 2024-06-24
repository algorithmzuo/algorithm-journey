package class131;

import java.util.Arrays;

// 区间和的个数
// 测试链接 : https://leetcode.cn/problems/count-of-range-sum/
public class Code01_CountOfRangeSum2 {

	// 树状数组 + 离散化
	// 理解难度较低
	public static int countRangeSum(int[] nums, int lower, int upper) {
		build(nums);
		add(0, 1);
		long sum = 0;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			sum += nums[i];
			ans += sum(sum - lower) - sum(sum - upper - 1);
			add(sum, 1);
		}
		return ans;
	}

	public static int MAXN = 100002;

	public static int n, m;

	public static long[] sort = new long[MAXN];

	public static int[] tree = new int[MAXN];

	public static void build(int[] nums) {
		n = nums.length;
		for (int i = 1, j = 0; i <= n; i++, j++) {
			sort[i] = sort[i - 1] + nums[j];
		}
		sort[n + 1] = 0;
		Arrays.sort(sort, 1, n + 2);
		m = 1;
		for (int i = 2; i <= n + 1; i++) {
			if (sort[m] != sort[i]) {
				sort[++m] = sort[i];
			}
		}
		Arrays.fill(tree, 1, m + 1, 0);
	}

	public static int rank(long v) {
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

	public static void add(long v, int c) {
		int i = rank(v);
		while (i <= m) {
			tree[i] += c;
			i += lowbit(i);
		}
	}

	public static int sum(long v) {
		int i = rank(v);
		int ans = 0;
		while (i > 0) {
			ans += tree[i];
			i -= lowbit(i);
		}
		return ans;
	}

}
