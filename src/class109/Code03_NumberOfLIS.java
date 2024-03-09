package class109;

import java.util.Arrays;

// 最长递增子序列的个数
// 给定一个未排序的整数数组nums，返回最长递增子序列的个数
// 测试链接 : https://leetcode.cn/problems/number-of-longest-increasing-subsequence/
// 本题在讲解072，最长递增子序列问题与扩展，就做出过预告
// 具体可以看讲解072视频最后的部分
// 用树状数组实现时间复杂度O(n * logn)
public class Code03_NumberOfLIS {

	public static int MAXN = 2001;

	public static int[] arr = new int[MAXN];

	public static int[] lens = new int[MAXN];

	public static int[] cnts = new int[MAXN];

	public static int n, a, b;

	public static void build() {
		Arrays.fill(lens, 1, n + 1, 0);
		Arrays.fill(cnts, 1, n + 1, 0);
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void query(int i) {
		a = b = 0;
		while (i > 0) {
			if (a == lens[i]) {
				b += cnts[i];
			} else if (a < lens[i]) {
				a = lens[i];
				b = cnts[i];
			}
			i -= lowbit(i);
		}
	}

	public static void add(int i, int l, int c) {
		while (i <= n) {
			if (lens[i] == l) {
				cnts[i] += c;
			} else if (lens[i] < l) {
				lens[i] = l;
				cnts[i] = c;
			}
			i += lowbit(i);
		}
	}

	public static int findNumberOfLIS(int[] nums) {
		for (int i = 1; i <= nums.length; i++) {
			arr[i] = nums[i - 1];
		}
		sort(nums.length);
		build();
		int rank;
		for (int num : nums) {
			rank = rank(num);
			query(rank - 1);
			add(rank, a + 1, Math.max(b, 1));
		}
		query(n);
		return b;
	}

	public static void sort(int size) {
		Arrays.sort(arr, 1, size + 1);
		n = 1;
		for (int i = 2; i <= size; i++) {
			if (arr[n] != arr[i]) {
				arr[++n] = arr[i];
			}
		}
	}

	public static int rank(int v) {
		int ans = 0;
		int l = 1, r = n, m;
		while (l <= r) {
			m = (l + r) / 2;
			if (arr[m] >= v) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}
