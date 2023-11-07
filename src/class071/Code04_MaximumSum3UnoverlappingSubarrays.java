package class071;

// 三个无重叠子数组的最大和
// 给你一个整数数组 nums 和一个整数 k
// 找出三个长度为 k 、互不重叠、且全部数字和（3 * k 项）最大的子数组
// 并返回这三个子数组
// 以下标的数组形式返回结果，数组中的每一项分别指示每个子数组的起始位置
// 如果有多个结果，返回字典序最小的一个
// 测试链接 : https://leetcode.cn/problems/maximum-sum-of-3-non-overlapping-subarrays/
public class Code04_MaximumSum3UnoverlappingSubarrays {

	public static int[] maxSumOfThreeSubarrays(int[] nums, int k) {
		int n = nums.length;
		// sums[i] : 以i开头并且长度为k的子数组的累加和
		int[] sums = new int[n];
		for (int i = 0, j = 0, sum = 0; j < n; j++) {
			sum += nums[j];
			if (j - i + 1 == k) {
				sums[i] = sum;
				sum -= nums[i];
				i++;
			}
		}
		// prefix[i] :
		// 0~i范围上所有长度为k的子数组中，拥有最大累加和的子数组，是以什么位置开头的
		int[] prefix = new int[n];
		for (int l = 1, r = k; r < n; l++, r++) {
			if (sums[l] > sums[prefix[r - 1]]) {
				prefix[r] = l;
			} else {
				prefix[r] = prefix[r - 1];
			}
		}
		// suffix[i] :
		// i~n-1范围上所有长度为k的子数组中，拥有最大累加和的子数组，是以什么位置开头的
		int[] suffix = new int[n];
		suffix[n - k] = n - k;
		for (int i = n - k - 1; i >= 0; i--) {
			if (sums[i] >= sums[suffix[i + 1]]) {
				suffix[i] = i;
			} else {
				suffix[i] = suffix[i + 1];
			}
		}
		int a = 0, b = 0, c = 0, max = 0;
		// 0...i-1    i...j    j+1...n-1
		//   左     中(长度为k)     右
		for (int i = k, j = 2 * k - 1, sum; j < n - k; i++, j++) {
			sum = sums[prefix[i - 1]] + sums[i] + sums[suffix[j + 1]];
			if (sum > max) {
				max = sum;
				a = prefix[i - 1];
				b = i;
				c = suffix[j + 1];
			}
		}
		return new int[] { a, b, c };
	}

}
