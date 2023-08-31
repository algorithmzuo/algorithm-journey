package class052;

// 最大宽度坡
// 给定一个整数数组 A，坡是元组 (i, j)，其中  i < j 且 A[i] <= A[j]
// 这样的坡的宽度为 j - i，找出 A 中的坡的最大宽度，如果不存在，返回 0
// 测试链接 : https://leetcode.cn/problems/maximum-width-ramp/
public class Code01_MaximumWidthRamp {

	public static int maxWidthRamp(int[] arr) {
		int n = arr.length;
		int[] stack = new int[n];
		int r = 0;
		for (int i = 0; i < n; i++) {
			if (r == 0 || arr[stack[r - 1]] > arr[i]) {
				stack[r++] = i;
			}
		}
		int ans = 0;
		for (int j = n - 1; j >= 0; j--) {
			while (r != 0 && arr[stack[r - 1]] <= arr[j]) {
				int i = stack[--r];
				ans = Math.max(ans, j - i);
			}
		}
		return ans;
	}

}
