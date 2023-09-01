package class050;

// 接雨水
// 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水
// 测试链接 : https://leetcode.cn/problems/trapping-rain-water/
public class Code05_TrappingRainWater {

	
	
	
	// 提交时改名为trap
	public static int trap2(int[] nums) {
		int l = 1, r = nums.length - 2, lmax = nums[0], rmax = nums[nums.length - 1];
		int ans = 0;
		while (l <= r) {
			if (lmax <= rmax) {
				ans += Math.max(0, lmax - nums[l]);
				lmax = Math.max(lmax, nums[l++]);
			} else {
				ans += Math.max(0, rmax - nums[r]);
				rmax = Math.max(rmax, nums[r--]);
			}
		}
		return ans;
	}

}
