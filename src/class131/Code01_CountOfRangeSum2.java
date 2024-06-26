package class131;

import java.util.Arrays;

// 达标子数组的个数
// 给定一个长度为n的数组nums，给定两个整数lower和upper
// 子数组达标的条件是累加和在[lower, upper]范围上
// 返回nums中有多少个达标子数组
// 1 <= n <= 10^5
// nums[i]可能是任意整数
// -10^5 <= lower <= upper <= +10^5
// 测试链接 : https://leetcode.cn/problems/count-of-range-sum/
public class Code01_CountOfRangeSum2 {

	// 树状数组 + 离散化的解法，理解难度较低
	public static int countRangeSum(int[] nums, int lower, int upper) {
		build(nums);
		long sum = 0;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			sum += nums[i];
			ans += sum(rank(sum - lower)) - sum(rank(sum - upper - 1));
			if (lower <= sum && sum <= upper) {
				ans++;
			}
			add(rank(sum), 1);
		}
		return ans;
	}

	public static int MAXN = 100002;

	public static int n, m;

	public static long[] sort = new long[MAXN];

	public static int[] tree = new int[MAXN];

	public static void build(int[] nums) {
		// 生成前缀和数组
		n = nums.length;
		for (int i = 1, j = 0; i <= n; i++, j++) {
			sort[i] = sort[i - 1] + nums[j];
		}
		// 前缀和数组排序和去重，最终有m个不同的前缀和
		Arrays.sort(sort, 1, n + 1);
		m = 1;
		for (int i = 2; i <= n; i++) {
			if (sort[m] != sort[i]) {
				sort[++m] = sort[i];
			}
		}
		// 初始化树状数组，下标1~m
		Arrays.fill(tree, 1, m + 1, 0);
	}

	// 返回 <=v 并且尽量大的前缀和是第几号前缀和
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

	// 树状数组模版代码，没有任何修改
	// i号前缀和，个数增加c个
	public static void add(int i, int c) {
		while (i <= m) {
			tree[i] += c;
			i += i & -i;
		}
	}

	// 树状数组模版代码，没有任何修改
	// 查询1~i号前缀和一共有几个
	public static int sum(int i) {
		int ans = 0;
		while (i > 0) {
			ans += tree[i];
			i -= i & -i;
		}
		return ans;
	}

}
