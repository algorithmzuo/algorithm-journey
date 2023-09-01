package class051;

import java.util.Arrays;

// 找出第K小的数对距离
// 数对 (a,b) 由整数 a 和 b 组成，其数对距离定义为 a 和 b 的绝对差值。
// 给你一个整数数组 nums 和一个整数 k
// 数对由 nums[i] 和 nums[j] 组成且满足 0 <= i < j < nums.length
// 返回 所有数对距离中 第 k 小的数对距离。
// 测试链接 : https://leetcode.cn/problems/find-k-th-smallest-pair-distance/
public class Code04_FindKthSmallestPairDistance {

	public static int smallestDistancePair(int[] nums, int k) {
		int n = nums.length;
		Arrays.sort(nums);
		int l = 0;
		int r = nums[n - 1] - nums[0];
		int ans = 0;
		while (l <= r) {
			int dis = l + ((r - l) >> 1);
			int cnt = f(nums, dis);
			if (cnt >= k) {
				ans = dis;
				r = dis - 1;
			} else {
				l = dis + 1;
			}
		}
		return ans;
	}

	public static int f(int[] arr, int dis) {
		int cnt = 0;
		for (int l = 0, r = 0; l < arr.length; l++) {
			while (r < arr.length && arr[r] <= arr[l] + dis) {
				r++;
			}
			cnt += r - l - 1;
		}
		return cnt;
	}

}
