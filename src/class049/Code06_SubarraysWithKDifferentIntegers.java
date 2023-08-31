package class049;

import java.util.Arrays;

// K个不同整数的子数组
// 给定一个正整数数组 nums和一个整数 k，返回 nums 中 「好子数组」 的数目。
// 如果 nums 的某个子数组中不同整数的个数恰好为 k
// 则称 nums 的这个连续、不一定不同的子数组为 「好子数组 」。
// 例如，[1,2,3,1,2] 中有 3 个不同的整数：1，2，以及 3。
// 子数组 是数组的 连续 部分。
// 测试链接 : https://leetcode.cn/problems/subarrays-with-k-different-integers/
public class Code06_SubarraysWithKDifferentIntegers {

	public static int subarraysWithKDistinct(int[] arr, int k) {
		return numsMostK(arr, k) - numsMostK(arr, k - 1);
	}

	public static int MAXN = 20001;

	public static int[] cnts = new int[MAXN];

	public static int numsMostK(int[] arr, int k) {
		int i = 0, ans = 0;
		Arrays.fill(cnts, 1, arr.length + 1, 0);
		for (int j = 0; j < arr.length; ++j) {
			if (cnts[arr[j]] == 0) {
				k--;
			}
			cnts[arr[j]]++;
			while (k < 0) {
				if (--cnts[arr[i]] == 0) {
					k++;
				}
				i++;
			}
			ans += j - i + 1;
		}
		return ans;
	}

}
