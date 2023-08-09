package code;

import java.util.Arrays;

// 基数排序
// 测试链接 : https://leetcode.cn/problems/sort-an-array/
public class Video_028_2_RadixSort {

	// 可以设置进制，不一定10进制，随你设置
	public static int BASE = 10;

	public static int MAXN = 50001;

	public static int[] help = new int[MAXN];

	public static int[] cnts = new int[BASE];

	public static int[] sortArray(int[] arr) {
		if (arr.length > 1) {
			// 如果会溢出，那么要改用long类型数组来排序
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
			radixSort(arr, n, m);
			for (int i = 0; i < n; i++) {
				arr[i] += min;
			}
		}
		return arr;
	}

	// 返回number在BASE进制下有几位
	public static int bits(int number) {
		int ans = 0;
		while (number > 0) {
			ans++;
			number /= BASE;
		}
		return ans;
	}

	// 基数排序核心代码
	// arr内要保证没有负数
	// n是arr的长度
	// m是arr中最大值在BASE进制下有几位
	public static void radixSort(int[] arr, int n, int m) {
		// 理解的时候可以假设BASE = 10
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
	}

}
