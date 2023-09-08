package class055;

// 和至少为 K 的最短子数组
// 给定一个数组arr，其中的值有可能正、负、0
// 给定一个正数k
// 返回累加和>=k的所有子数组中，最短的子数组长度
// 测试链接 : https://leetcode.cn/problems/shortest-subarray-with-sum-at-least-k/
public class Code01_ShortestSubarrayWithSumAtLeastK {

	public static int MAXN = 100001;

	public static long[] sum = new long[MAXN];

	public static int[] deque = new int[MAXN];

	public static int l, r;

	public static int shortestSubarray(int[] arr, int K) {
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			sum[i + 1] = sum[i] + arr[i];
		}
		int ans = Integer.MAX_VALUE;
		l = r = 0;
		for (int i = 0; i < n + 1; i++) {
			while (l != r && sum[i] - sum[deque[l]] >= K) {
				ans = Math.min(ans, i - deque[l++]);
			}
			while (l != r && sum[deque[r - 1]] >= sum[i]) {
				r--;
			}
			deque[r++] = i;
		}
		return ans != Integer.MAX_VALUE ? ans : -1;
	}

}
