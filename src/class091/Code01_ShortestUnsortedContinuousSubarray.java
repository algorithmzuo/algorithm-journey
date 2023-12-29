package class091;

// 最短无序连续子数组
// 给你一个整数数组nums，你需要找出一个 连续子数组
// 如果对这个子数组进行升序排序，那么整个数组都会变为升序排序
// 请你找出符合题意的最短子数组，并输出它的长度
// 测试链接 : https://leetcode.cn/problems/shortest-unsorted-continuous-subarray/
public class Code01_ShortestUnsortedContinuousSubarray {

	public static int findUnsortedSubarray(int[] nums) {
		int n = nums.length;
		// max > 当前数，认为不达标
		// 从左往右遍历，记录最右不达标的位置
		int right = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			if (max > nums[i]) {
				right = i;
			}
			max = Math.max(max, nums[i]);
		}
		int min = Integer.MAX_VALUE;
		// min < 当前数，认为不达标
		// 从右往左遍历，记录最左不达标的位置
		int left = n;
		for (int i = n - 1; i >= 0; i--) {
			if (min < nums[i]) {
				left = i;
			}
			min = Math.min(min, nums[i]);
		}
		return Math.max(0, right - left + 1);
	}

}
