package class054;

// 滑动窗口最大值
// 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧
// 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
// 返回 滑动窗口中的最大值 。
// 测试链接 : https://leetcode.cn/problems/sliding-window-maximum/
public class Code01_SlidingWindowMaximum {

	public static int MAXN = 100001;

	public static int[] deque = new int[MAXN];

	public static int l, r;

	public static int[] maxSlidingWindow(int[] arr, int w) {
		int n = arr.length;
		l = r = 0;
		int[] ans = new int[n - w + 1];
		for (int i = 0, fill = 0; i < n; i++) {
			while (l < r && arr[deque[r - 1]] <= arr[i]) {
				r--;
			}
			deque[r++] = i;
			if (i >= w - 1) {
				ans[fill] = arr[deque[l]];
				if (deque[l] == fill) {
					l++;
				}
				fill++;
			}
		}
		return ans;
	}

}
