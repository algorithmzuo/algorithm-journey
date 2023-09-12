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

	public static int h, t;

	public static int shortestSubarray(int[] arr, int K) {
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			sum[i + 1] = sum[i] + arr[i];
		}
		int ans = Integer.MAX_VALUE;
		h = t = 0;
		for (int r = 0; r <= n; r++) {
			while (h != t && sum[r] - sum[deque[h]] >= K) {
				ans = Math.min(ans, r - deque[h++]);
			}
			while (h != t && sum[deque[t - 1]] >= sum[r]) {
				t--;
			}
			deque[t++] = r;
		}
		return ans != Integer.MAX_VALUE ? ans : -1;
	}

}
