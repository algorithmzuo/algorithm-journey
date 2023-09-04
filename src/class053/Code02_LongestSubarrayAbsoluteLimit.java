package class053;

// 绝对差不超过限制的最长连续子数组
// 给你一个整数数组 nums ，和一个表示限制的整数 limit
// 请你返回最长连续子数组的长度
// 该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit
// 如果不存在满足条件的子数组，则返回 0
// 测试链接 : https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
public class Code02_LongestSubarrayAbsoluteLimit {

	public static int MAXN = 100001;

	public static int[] maxDeque = new int[MAXN];

	public static int[] minDeque = new int[MAXN];

	public static int maxl, maxr, minl, minr;

	public static int longestSubarray(int[] nums, int limit) {
		int n = nums.length;
		int ans = 0;
		maxl = maxr = minl = minr = 0;
		for (int l = 0, r = -1; l < n; l++) {
			while (r < n && ok(nums, limit)) {
				add(nums, ++r);
			}
			ans = Math.max(ans, r - l);
			pop(l);
		}
		return ans;
	}

	public static boolean ok(int[] nums, int limit) {
		int max = maxl < maxr ? nums[maxDeque[maxl]] : 0;
		int min = minl < minr ? nums[minDeque[minl]] : 0;
		return max - min <= limit;
	}

	public static void add(int[] nums, int i) {
		if (i < nums.length) {
			while (maxl < maxr && nums[maxDeque[maxr - 1]] <= nums[i]) {
				maxr--;
			}
			maxDeque[maxr++] = i;
			while (minl < minr && nums[minDeque[minr - 1]] >= nums[i]) {
				minr--;
			}
			minDeque[minr++] = i;
		}
	}

	public static void pop(int i) {
		if (maxl < maxr && maxDeque[maxl] == i) {
			maxl++;
		}
		if (minl < minr && minDeque[minl] == i) {
			minl++;
		}
	}

}
