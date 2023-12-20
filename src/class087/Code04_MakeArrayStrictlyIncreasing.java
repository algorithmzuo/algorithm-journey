package class087;

import java.util.Arrays;

// 使数组严格递增的最小操作数
// 给你两个整数数组 arr1 和 arr2
// 返回使 arr1 严格递增所需要的最小操作数（可能为0）
// 每一步操作中，你可以分别从 arr1 和 arr2 中各选出一个索引
// 分别为 i 和 j，0 <= i < arr1.length 和 0 <= j < arr2.length
// 然后进行赋值运算 arr1[i] = arr2[j]
// 如果无法让 arr1 严格递增，请返回-1
// 1 <= arr1.length, arr2.length <= 2000
// 0 <= arr1[i], arr2[i] <= 10^9
// 测试链接 : https://leetcode.cn/problems/make-array-strictly-increasing/
public class Code04_MakeArrayStrictlyIncreasing {

	public static int makeArrayIncreasing(int[] arr1, int[] arr2) {
		Arrays.sort(arr2);
		int m = 1;
		for (int i = 1; i < arr2.length; i++) {
			if (arr2[i] != arr2[m - 1]) {
				arr2[m++] = arr2[i];
			}
		}
		int n = arr1.length;
		int[] dp = new int[n + 2];
		for (int i = n - 1; i >= -1; i--) {
			int cur = i == -1 ? Integer.MIN_VALUE : arr1[i];
			int find = bs(arr2, m, cur);
			dp[i + 1] = Integer.MAX_VALUE;
			int times = 0;
			for (int j = i + 1; j <= n; j++) {
				if (j == n || cur < arr1[j]) {
					if (dp[j + 1] != Integer.MAX_VALUE) {
						dp[i + 1] = Math.min(dp[i + 1], times + dp[j + 1]);
					}
				}
				if (find != -1 && find < m) {
					cur = arr2[find++];
					times++;
				} else {
					break;
				}
			}
		}
		return dp[0] == Integer.MAX_VALUE ? -1 : dp[0];
	}

	public static int bs(int[] arr2, int size, int num) {
		int l = 0;
		int r = size - 1;
		int m, ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (arr2[m] > num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}
