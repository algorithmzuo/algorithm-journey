package class051;

import java.util.Arrays;

// 找出第K小的数对距离
// 数对 (a,b) 由整数 a 和 b 组成，其数对距离定义为 a 和 b 的绝对差值。
// 给你一个整数数组 nums 和一个整数 k
// 数对由 nums[i] 和 nums[j] 组成且满足 0 <= i < j < nums.length
// 返回 所有数对距离中 第 k 小的数对距离。
// 测试链接 : https://leetcode.cn/problems/find-k-th-smallest-pair-distance/
public class Code04_FindKthSmallestPairDistance {

	// 时间复杂度O(n * log(n) + log(max-min) * n)，额外空间复杂度O(1)
	public static int smallestDistancePair(int[] nums, int k) {
		int n = nums.length;
		Arrays.sort(nums);
		int ans = 0;
		// [0, 最大-最小]，不停二分
		for (int l = 0, r = nums[n - 1] - nums[0], m, cnt; l <= r;) {
			// m中点，arr中任意两数的差值 <= m
			m = l + ((r - l) >> 1);
			// 返回数字对的数量
			cnt = f(nums, m);
			if (cnt >= k) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	// arr中任意两数的差值 <= limit
	// 这样的数字配对，有几对？
	public static int f(int[] arr, int limit) {
		int ans = 0;
		// O(n)
		for (int l = 0, r = 0; l < arr.length; l++) {
			// l......r r+1
			while (r + 1 < arr.length && arr[r + 1] - arr[l] <= limit) {
				r++;
			}
			// arr[l...r]范围上的数差值的绝对值都不超过limit
			// arr[0...3]
			// 0,1
			// 0,2
			// 0,3
			ans += r - l;
		}
		return ans;
	}

}
