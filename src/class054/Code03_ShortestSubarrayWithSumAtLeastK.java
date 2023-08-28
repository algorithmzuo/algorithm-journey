package class054;

// 和至少为 K 的最短子数组
// 给定一个数组arr，其中的值有可能正、负、0
// 给定一个正数k
// 返回累加和>=k的所有子数组中，最短的子数组长度
// 本题测试链接 : https://leetcode.cn/problems/shortest-subarray-with-sum-at-least-k/
public class Code03_ShortestSubarrayWithSumAtLeastK {

	public static int shortestSubarray(int[] arr, int K) {
		int N = arr.length;
		long[] sum = new long[N + 1];
		for (int i = 0; i < N; i++) {
			sum[i + 1] = sum[i] + arr[i];
		}
		int ans = Integer.MAX_VALUE;
		int[] dq = new int[N + 1];
		int l = 0;
		int r = 0;
		for (int i = 0; i < N + 1; i++) {
			while (l != r && sum[i] - sum[dq[l]] >= K) {
				ans = Math.min(ans, i - dq[l++]);
			}
			while (l != r && sum[dq[r - 1]] >= sum[i]) {
				r--;
			}
			dq[r++] = i;
		}
		return ans != Integer.MAX_VALUE ? ans : -1;
	}

}

