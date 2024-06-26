package class131;

// 达标子数组的个数
// 给定一个长度为n的数组nums，给定两个整数lower和upper
// 子数组达标的条件是累加和在[lower, upper]范围上
// 返回nums中有多少个达标子数组
// 1 <= n <= 10^5
// nums[i]可能是任意整数
// -10^5 <= lower <= upper <= +10^5
// 测试链接 : https://leetcode.cn/problems/count-of-range-sum/
public class Code01_CountOfRangeSum1 {

	// 归并分治的解法，理解难度稍高，需要先掌握讲解022 - 归并分治
	public static int countRangeSum(int[] nums, int lower, int upper) {
		int n = nums.length;
		sum[0] = nums[0];
		for (int i = 1; i < n; i++) {
			sum[i] = sum[i - 1] + nums[i];
		}
		low = lower;
		up = upper;
		return f(0, n - 1);
	}

	public static int MAXN = 100001;

	public static long[] sum = new long[MAXN];

	public static long[] help = new long[MAXN];

	public static int low, up;

	public static int f(int l, int r) {
		if (l == r) {
			return low <= sum[l] && sum[l] <= up ? 1 : 0;
		}
		int m = (l + r) / 2;
		return f(l, m) + f(m + 1, r) + merge(l, m, r);
	}

	public static int merge(int l, int m, int r) {
		// 归并分治的统计过程
		int ans = 0;
		int wl = l, wr = l;
		long max, min;
		for (int i = m + 1; i <= r; i++) {
			max = sum[i] - low;
			min = sum[i] - up;
			// 有效窗口是[wl,wr)，左闭右开
			while (wr <= m && sum[wr] <= max) {
				wr++;
			}
			while (wl <= m && sum[wl] < min) {
				wl++;
			}
			ans += wr - wl;
		}
		// 正常排序的合并过程
		int p1 = l;
		int p2 = m + 1;
		int i = l;
		while (p1 <= m && p2 <= r) {
			help[i++] = sum[p1] <= sum[p2] ? sum[p1++] : sum[p2++];
		}
		while (p1 <= m) {
			help[i++] = sum[p1++];
		}
		while (p2 <= r) {
			help[i++] = sum[p2++];
		}
		for (i = l; i <= r; i++) {
			sum[i] = help[i];
		}
		return ans;
	}

}
