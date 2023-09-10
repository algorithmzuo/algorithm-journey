package class053;

// 最大宽度坡
// 给定一个整数数组 A，坡是元组 (i, j)，其中  i < j 且 A[i] <= A[j]
// 这样的坡的宽度为 j - i，找出 A 中的坡的最大宽度，如果不存在，返回 0
// 测试链接 : https://leetcode.cn/problems/maximum-width-ramp/
public class Code01_MaximumWidthRamp {

	public static int MAXN = 50001;

	public static int[] stack = new int[MAXN];

	public static int r;

	public static int maxWidthRamp(int[] arr) {
		// 令r=1相当于0位置进栈了
		// stack[0] = 0，然后栈的大小变成1
		r = 1;
		int n = arr.length;
		for (int i = 1; i < n; i++) {
			if (arr[stack[r - 1]] > arr[i]) {
				stack[r++] = i;
			}
		}
		int ans = 0;
		for (int j = n - 1; j >= 0; j--) {
			while (r > 0 && arr[stack[r - 1]] <= arr[j]) {
				ans = Math.max(ans, j - stack[--r]);
			}
		}
		return ans;
	}

}
