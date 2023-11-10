package class073;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

// 非负数组前k个最小的子序列累加和
// 给定一个数组nums，含有n个数字，都是非负数
// 给定一个正数k，返回所有子序列中累加和最小的前k个累加和
// 子序列是包含空集的
// 1 <= n <= 10^5
// 1 <= nums[i] <= 10^6
// 1 <= k <= 10^5
// 注意这个数据量，用01背包的解法是不行的，时间复杂度太高了
// 对数器验证
public class Code06_TopKMinimumSubsequenceSum {

	// 暴力方法
	// 为了验证
	public static int[] topKSum1(int[] nums, int k) {
		ArrayList<Integer> allSubsequences = new ArrayList<>();
		f1(nums, 0, 0, allSubsequences);
		allSubsequences.sort((a, b) -> a.compareTo(b));
		int[] ans = new int[k];
		for (int i = 0; i < k; i++) {
			ans[i] = allSubsequences.get(i);
		}
		return ans;
	}

	// 暴力方法
	// 得到所有子序列的和
	public static void f1(int[] nums, int index, int sum, ArrayList<Integer> ans) {
		if (index == nums.length) {
			ans.add(sum);
		} else {
			f1(nums, index + 1, sum, ans);
			f1(nums, index + 1, sum + nums[index], ans);
		}
	}

	// 01背包来实现
	// 这种方法此时不是最优解
	// 因为n很大，数值也很大，那么可能的累加和就更大
	// 时间复杂度太差
	public static int[] topKSum2(int[] nums, int k) {
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		// dp[i][j]
		// 1) dp[i-1][j]
		// 2) dp[i-1][j-nums[i]
		int[] dp = new int[sum + 1];
		dp[0] = 1;
		for (int num : nums) {
			for (int j = sum; j >= num; j--) {
				dp[j] += dp[j - num];
			}
		}
		int[] ans = new int[k];
		int index = 0;
		for (int j = 0; j <= sum && index < k; j++) {
			for (int i = 0; i < dp[j] && index < k; i++) {
				ans[index++] = j;
			}
		}
		return ans;
	}

	// 正式方法
	// 用堆来做是最优解，时间复杂度O(n * log n) + O(k * log k)
	public static int[] topKSum3(int[] nums, int k) {
		Arrays.sort(nums);
		// (子序列的最右下标，子序列的累加和)
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		heap.add(new int[] { 0, nums[0] });
		int[] ans = new int[k];
		for (int i = 1; i < k; i++) {
			int[] cur = heap.poll();
			int right = cur[0];
			int sum = cur[1];
			ans[i] = sum;
			if (right + 1 < nums.length) {
				heap.add(new int[] { right + 1, sum - nums[right] + nums[right + 1] });
				heap.add(new int[] { right + 1, sum + nums[right + 1] });
			}
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int len, int value) {
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * value);
		}
		return ans;
	}

	// 为了测试
	public static boolean equals(int[] ans1, int[] ans2) {
		if (ans1.length != ans2.length) {
			return false;
		}
		for (int i = 0; i < ans1.length; i++) {
			if (ans1[i] != ans2[i]) {
				return false;
			}
		}
		return true;
	}

	// 为了测试
	// 对数器
	public static void main(String[] args) {
		int n = 15;
		int v = 40;
		int testTime = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * n) + 1;
			int[] nums = randomArray(len, v);
			int k = (int) (Math.random() * ((1 << len) - 1)) + 1;
			int[] ans1 = topKSum1(nums, k);
			int[] ans2 = topKSum2(nums, k);
			int[] ans3 = topKSum3(nums, k);
			if (!equals(ans1, ans2) || !equals(ans1, ans3)) {
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");
	}

}
