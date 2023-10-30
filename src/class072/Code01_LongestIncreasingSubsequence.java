package class072;

// 最长递增子序列和最长不下降子序列
// 给定一个整数数组nums
// 找到其中最长严格递增子序列长度、最长不下降子序列长度
// 测试链接 : https://leetcode.cn/problems/longest-increasing-subsequence/
public class Code01_LongestIncreasingSubsequence {

	public static int lengthOfLIS(int[] nums) {
		int n = nums.length;
		int[] ends = new int[n];
		int len = 0;
		for (int i = 0, find; i < n; i++) {
			find = bs1(ends, len, nums[i]);
			if (find == -1) {
				ends[len++] = nums[i];
			} else {
				ends[find] = nums[i];
			}
		}
		return len;
	}

	// "最长递增子序列"使用如下二分搜索 :
	// ends[0...len-1]是严格升序的
	// 在其中找到>=num的最左位置，如果不存在返回-1
	public static int bs1(int[] ends, int len, int num) {
		int l = 0, r = len - 1, m, ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (num <= ends[m]) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	// "最长不下降子序列"使用如下二分搜索 :
	// ends[0...len-1]是不降序的
	// 在其中找到>num的最左位置，如果不存在返回-1
	// 求"最长不下降子序列"就是在lengthOfLIS中把bs1方法换成bs2方法
	// 整体测试已经用对数器测了
	public static int bs2(int[] ends, int len, int num) {
		int l = 0, r = len - 1, m, ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (num < ends[m]) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}
