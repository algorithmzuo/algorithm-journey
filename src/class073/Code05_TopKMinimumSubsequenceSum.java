package class073;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

// 前k个最小的子序列累加和
// 给定一个数组arr，含有n个数字，都是非负数
// 给定一个正数k，返回所有子序列中累加和最小的前k个累加和
// 注意 : 假设n很大，数值也很大！但是k不大，怎么算最快？
// 对数器验证
public class Code05_TopKMinimumSubsequenceSum {

	// 暴力方法
	// 为了验证
	public static int[] topKSum1(int[] arr, int k) {
		ArrayList<Integer> allSubsequences = new ArrayList<>();
		f1(arr, 0, 0, allSubsequences);
		allSubsequences.sort((a, b) -> a.compareTo(b));
		int[] ans = new int[k];
		for (int i = 0; i < k; i++) {
			ans[i] = allSubsequences.get(i);
		}
		return ans;
	}

	// 暴力方法
	// 得到所有子序列的和
	public static void f1(int[] arr, int index, int sum, ArrayList<Integer> ans) {
		if (index == arr.length) {
			ans.add(sum);
		} else {
			f1(arr, index + 1, sum, ans);
			f1(arr, index + 1, sum + arr[index], ans);
		}
	}

	// 01背包来实现
	// 这种方法此时不是最优解
	// 因为当n很大，数值也很大时
	// 动态规划的计算过程太耗时了
	public static int[] topKSum2(int[] arr, int k) {
		int sum = 0;
		for (int num : arr) {
			sum += num;
		}
		// dp[i][j]
		// 1) dp[i-1][j]
		// 2) dp[i-1][j-num]
		int[] dp = new int[sum + 1];
		dp[0] = 1;
		for (int num : arr) {
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
	// 不用01背包，因为n和数值都很大，而k不大
	// 用堆来做就是最优解了
	// 时间复杂度O(n * log n) + O(n * log k)
	public static int[] topKSum3(int[] arr, int k) {
		Arrays.sort(arr);
		// (最右的下标，集合的累加和)
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		heap.add(new int[] { 0, arr[0] });
		int[] ans = new int[k];
		for (int i = 1; i < k; i++) {
			int[] cur = heap.poll();
			int last = cur[0];
			int sum = cur[1];
			ans[i] = sum;
			if (last + 1 < arr.length) {
				heap.add(new int[] { last + 1, sum - arr[last] + arr[last + 1] });
				heap.add(new int[] { last + 1, sum + arr[last + 1] });
			}
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * value);
		}
		return arr;
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
			int[] arr = randomArray(len, v);
			int k = (int) (Math.random() * ((1 << len) - 1)) + 1;
			int[] ans1 = topKSum1(arr, k);
			int[] ans2 = topKSum2(arr, k);
			int[] ans3 = topKSum3(arr, k);
			if (!equals(ans1, ans2) || !equals(ans1, ans3)) {
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");
	}

}
