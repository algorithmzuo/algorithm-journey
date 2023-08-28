package class052;

// 接雨水
// 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水
// 测试链接 : https://leetcode.cn/problems/trapping-rain-water/
public class Code03_TrappingRainWater {

	public static int trap(int[] arr) {
		int l = 1, r = arr.length - 2;
		int leftMax = arr[0], rightMax = arr[arr.length - 1];
		int ans = 0;
		while (l <= r) {
			if (leftMax <= rightMax) {
				ans += Math.max(0, leftMax - arr[l]);
				leftMax = Math.max(leftMax, arr[l++]);
			} else {
				ans += Math.max(0, rightMax - arr[r]);
				rightMax = Math.max(rightMax, arr[r--]);
			}
		}
		return ans;
	}

}
