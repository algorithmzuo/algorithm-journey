package code;

import java.util.Arrays;

// 基数排序
// 测试链接 : https://leetcode.cn/problems/sort-an-array/
public class Video_028_RadixSort {

	public static int[] sortArray(int[] nums) {
		if (nums.length > 1) {
			radixSort(nums);
		}
		return nums;
	}

	// 可以设置进制，不一定10进制，随你设置
	public static int BASE = 10;

	public static int MAXN = 50001;

	public static int[] help = new int[MAXN];

	public static int[] cnts = new int[BASE];

	public static void radixSort(int[] nums) {
		int n = nums.length;
		Arrays.fill(help, 0, n, 0);
		int min = nums[0];
		for (int i = 1; i < n; i++) {
			min = Math.min(min, nums[i]);
		}
		int max = 0;
		for (int i = 0; i < n; i++) {
			nums[i] -= min;
			max = Math.max(max, nums[i]);
		}
		int m = bits(max);
		for (int offset = 1; m > 0; offset *= BASE, m--) {
			Arrays.fill(cnts, 0);
			for (int i = 0; i < n; i++) {
				cnts[(nums[i] / offset) % BASE]++;
			}
			for (int i = 1; i < BASE; i++) {
				cnts[i] = cnts[i] + cnts[i - 1];
			}
			for (int i = n - 1; i >= 0; i--) {
				help[--cnts[(nums[i] / offset) % BASE]] = nums[i];
			}
			for (int i = 0; i < n; i++) {
				nums[i] = help[i];
			}
		}
		for (int i = 0; i < n; i++) {
			nums[i] += min;
		}
	}

	public static int bits(int max) {
		int ans = 0;
		while (max > 0) {
			ans++;
			max /= BASE;
		}
		return ans;
	}

}
