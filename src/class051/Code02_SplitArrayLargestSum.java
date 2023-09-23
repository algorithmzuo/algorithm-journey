package class051;

// 分割数组的最大值(画匠问题)
// 给定一个非负整数数组 nums 和一个整数 m
// 你需要将这个数组分成 m 个非空的连续子数组。
// 设计一个算法使得这 m 个子数组各自和的最大值最小。
// 测试链接 : https://leetcode.cn/problems/split-array-largest-sum/
public class Code02_SplitArrayLargestSum {

	// 时间复杂度O(n * log(sum))，额外空间复杂度O(1)
	public static int splitArray(int[] nums, int k) {
		long sum = 0;
		for (int num : nums) {
			sum += num;
		}
		long ans = 0;
		// [0,sum]二分
		for (long l = 0, r = sum, m, need; l <= r;) {
			// 中点m
			m = l + ((r - l) >> 1);
			// 必须让数组每一部分的累加和 <= m，请问划分成几个部分才够!
			need = f(nums, m);
			if (need <= k) {
				// 达标
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return (int) ans;
	}

	// 必须让数组arr每一部分的累加和 <= limit，请问划分成几个部分才够!
	// 返回需要的部分数量
	public static int f(int[] arr, long limit) {
		int parts = 1;
		int sum = 0;
		for (int num : arr) {
			if (num > limit) {
				return Integer.MAX_VALUE;
			}
			if (sum + num > limit) {
				parts++;
				sum = num;
			} else {
				sum += num;
			}
		}
		return parts;
	}

}
