package code;

import java.util.Arrays;

// 基数排序
// 测试链接 : https://leetcode.cn/problems/sort-an-array/
public class Video_028_2_RadixSort {

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

	// 如果会溢出，那么要改用long类型数组来排序
	public static void radixSort(int[] arr) {
		int n = arr.length;
		int min = arr[0];
		for (int i = 1; i < n; i++) {
			min = Math.min(min, arr[i]);
		}
		int max = 0;
		for (int i = 0; i < n; i++) {
			arr[i] -= min;
			max = Math.max(max, arr[i]);
		}
		int m = bits(max);
		for (int offset = 1; m > 0; offset *= BASE, m--) {
			Arrays.fill(cnts, 0);
			for (int i = 0; i < n; i++) {
				cnts[(arr[i] / offset) % BASE]++;
			}
			for (int i = 1; i < BASE; i++) {
				cnts[i] = cnts[i] + cnts[i - 1];
			}
			for (int i = n - 1; i >= 0; i--) {
				help[--cnts[(arr[i] / offset) % BASE]] = arr[i];
			}
			for (int i = 0; i < n; i++) {
				arr[i] = help[i];
			}
		}
		for (int i = 0; i < n; i++) {
			arr[i] += min;
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
