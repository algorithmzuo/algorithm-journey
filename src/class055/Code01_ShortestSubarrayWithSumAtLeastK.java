package class055;

// 和至少为K的最短子数组
// 给定一个数组arr，其中的值有可能正、负、0
// 给定一个正数k
// 返回累加和>=k的所有子数组中，最短的子数组长度
// 测试链接 : https://leetcode.cn/problems/shortest-subarray-with-sum-at-least-k/
public class Code01_ShortestSubarrayWithSumAtLeastK {

	public static int MAXN = 100001;

	// sum[0] : 前0个数的前缀和
	// sum[i] : 前i个数的前缀和
	public static long[] sum = new long[MAXN];

	public static int[] deque = new int[MAXN];

	public static int h, t;

	public static int shortestSubarray(int[] arr, int K) {
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			// [3,4,5]
			//  0 1 2
			// sum[0] = 0
			// sum[1] = 3
			// sum[2] = 7
			// sum[3] = 12
			sum[i + 1] = sum[i] + arr[i];
		}
		h = t = 0;
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i <= n; i++) {
			// 前0个数前缀和
			// 前1个数前缀和
			// 前2个数前缀和
			// ...
			// 前n个数前缀和
			while (h != t && sum[i] - sum[deque[h]] >= K) {
				// 如果当前的前缀和 - 头前缀和，达标！
				ans = Math.min(ans, i - deque[h++]);
			}
			// 前i个数前缀和，从尾部加入
			// 小 大
			while (h != t && sum[deque[t - 1]] >= sum[i]) {
				t--;
			}
			deque[t++] = i;
		}
		return ans != Integer.MAX_VALUE ? ans : -1;
	}

}
