package class051;

// 分割数组的最大值
// 给定一个非负整数数组 nums 和一个整数 m
// 你需要将这个数组分成 m 个非空的连续子数组。
// 设计一个算法使得这 m 个子数组各自和的最大值最小。
// 测试链接 : https://leetcode.cn/problems/split-array-largest-sum/
public class Code02_SplitArrayLargestSum {

	public static int splitArray(int[] nums, int k) {
		long sum = 0;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
		}
		long ans = 0;
		for (long l = 0, r = sum, m, cur; l <= r;) {
			m = l + ((r - l) >> 1);
			cur = f(nums, m);
			if (cur <= k) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return (int) ans;
	}

	public static int f(int[] arr, long aim) {
		int parts = 1;
		int all = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > aim) {
				return Integer.MAX_VALUE;
			}
			if (all + arr[i] > aim) {
				parts++;
				all = arr[i];
			} else {
				all += arr[i];
			}
		}
		return parts;
	}

}
