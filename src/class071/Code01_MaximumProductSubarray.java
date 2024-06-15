package class071;

// 乘积最大子数组
// 给你一个整数数组 nums
// 请你找出数组中乘积最大的非空连续子数组
// 并返回该子数组所对应的乘积
// 测试链接 : https://leetcode.cn/problems/maximum-product-subarray/
public class Code01_MaximumProductSubarray {

	// 这节课讲完之后，测试数据又增加了
	// 用int类型的变量会让中间结果溢出
	// 所以改成用double类型的变量
	// 思路是不变的
	public static int maxProduct(int[] nums) {
		double ans = nums[0], min = nums[0], max = nums[0], curmin, curmax;
		for (int i = 1; i < nums.length; i++) {
			curmin = Math.min(nums[i], Math.min(min * nums[i], max * nums[i]));
			curmax = Math.max(nums[i], Math.max(min * nums[i], max * nums[i]));
			min = curmin;
			max = curmax;
			ans = Math.max(ans, max);
		}
		return (int) ans;
	}

}
