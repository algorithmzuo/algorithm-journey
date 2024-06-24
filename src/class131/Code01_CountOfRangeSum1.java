package class131;

// 区间和的个数
// 测试链接 : https://leetcode.cn/problems/count-of-range-sum/
public class Code01_CountOfRangeSum1 {

	// 归并分治
	// 理解难度稍高
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
			return sum[l] >= low && sum[l] <= up ? 1 : 0;
		}
		int m = (l + r) / 2;
		return f(l, m) + f(m + 1, r) + merge(l, m, r);
	}

	public static int merge(int l, int m, int r) {
		// 统计过程
		int ans = 0;
		int wl = l, wr = l;
		long max, min;
		for (int i = m + 1; i <= r; i++) {
			max = sum[i] - low;
			min = sum[i] - up;
			while (wr <= m && sum[wr] <= max) {
				wr++;
			}
			while (wl <= m && sum[wl] < min) {
				wl++;
			}
			ans += wr - wl;
		}
		// 合并过程
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
