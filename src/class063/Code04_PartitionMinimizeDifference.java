package class063;

import java.util.Arrays;

// 将数组分成两个数组并最小化数组和的差
// 这道题课上没讲，其实就是题目3加上了选取数字个数的限制
// 比如一共30个数，分配到两个部分
// 要求每个部分都是15个数，并且做到累加和差值最小
// 那么就是题目3的代码，加上个数的考虑就可以，以下代码提交可以直接通过
// 测试链接 : https://leetcode.cn/problems/partition-array-into-two-arrays-to-minimize-sum-difference/

public class Code04_PartitionMinimizeDifference {

	class Solution {

		public static int MAXK = 16;
		public static int MAXS = 1 << 15;

		// siz[k] : 选了k个数的情况下，一共有多少个累加和
		public static int[] lsiz = new int[MAXK];
		public static int[] rsiz = new int[MAXK];

		// sum[k][...] : 选了k个数的情况下，收集的每个累加和都是什么
		// 到底有几个累加和？就是siz[k]的数量
		public static int[][] lsum = new int[MAXK][MAXS];
		public static int[][] rsum = new int[MAXK][MAXS];

		public static int minimumDifference(int[] nums) {
			int m = nums.length;
			int n = m >> 1;
			int sum = 0;
			for (int num : nums) {
				sum += num;
			}
			// 排序只是为了递归收集时压缩相同数字，常数优化
			Arrays.sort(nums);
			Arrays.fill(lsiz, 0, n + 1, 0);
			Arrays.fill(rsiz, 0, n + 1, 0);
			collect(nums, 0, n, 0, 0, lsum, lsiz);
			collect(nums, n, m, 0, 0, rsum, rsiz);
			for (int i = 0; i <= n; i++) {
				Arrays.sort(lsum[i], 0, lsiz[i]);
				Arrays.sort(rsum[i], 0, rsiz[i]);
			}
			long ans = Long.MAX_VALUE;
			for (int k = 0; k <= n; k++) {
				int need = n - k;
				for (int i = 0, j = rsiz[need] - 1; i < lsiz[k]; i++) {
					while (j > 0 && Math.abs((long) sum - 2L * (lsum[k][i] + rsum[need][j - 1])) <= Math
							.abs((long) sum - 2L * (lsum[k][i] + rsum[need][j]))) {
						j--;
					}
					ans = Math.min(ans, Math.abs((long) sum - 2L * (lsum[k][i] + rsum[need][j])));
				}
			}
			return (int) ans;
		}

		public static void collect(int[] nums, int i, int end, int s, int c, int[][] sum, int[] siz) {
			if (i == end) {
				sum[c][siz[c]++] = s;
			} else {
				int j = i + 1;
				while (j < end && nums[j] == nums[i]) {
					j++;
				}
				for (int k = 0; k <= j - i; k++) {
					collect(nums, j, end, s + k * nums[i], c + k, sum, siz);
				}
			}
		}

	}

}
