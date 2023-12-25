package class071;

// 乘积最大子数组
// 给你一个整数数组 nums
// 请你找出数组中乘积最大的非空连续子数组
// 并返回该子数组所对应的乘积
// 测试链接 : https://leetcode.cn/problems/maximum-product-subarray/
public class Code01_MaximumProductSubarray {

	// 本方法对于double类型的数组求最大累乘积也同样适用
	public static int maxProduct(int[] nums) {
		int ans = nums[0];
		for (int i = 1, min = nums[0], max = nums[0], curmin, curmax; i < nums.length; i++) {
			curmin = Math.min(nums[i], Math.min(min * nums[i], max * nums[i]));
			curmax = Math.max(nums[i], Math.max(min * nums[i], max * nums[i]));
			min = curmin;
			max = curmax;
			ans = Math.max(ans, max);
		}
		return ans;
	}

}
