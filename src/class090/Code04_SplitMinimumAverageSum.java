package class090;

import java.util.ArrayList;
import java.util.Arrays;

// 来自学员问题
// 真实大厂笔试题
// 给定一个数组arr，长度为n
// 再给定一个数字k，表示一定要将arr划分成k个集合
// 每个数字只能进一个集合
// 返回每个集合的平均值都累加起来的最小值
// 平均值向下取整
// 1 <= n <= 10^5
// 0 <= arr[i] <= 10^5
// 1 <= k <= n
// 来自真实大厂笔试，没有在线测试，对数器验证
public class Code04_SplitMinimumAverageSum {

	// 暴力方法
	// 为了验证
	public static int minAverageSum1(int[] arr, int k) {
		if (arr.length < k) {
			return -1;
		}
		ArrayList<Info> sets = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			sets.add(new Info(0, 0));
		}
		return f(arr, 0, k, sets);
	}

	// 暴力方法
	// 为了验证
	public static class Info {
		public int sum;
		public int cnt;

		public Info(int s, int c) {
			sum = s;
			cnt = c;
		}
	}

	// 暴力方法
	// 为了验证
	public static int f(int[] arr, int i, int k, ArrayList<Info> sets) {
		if (i == arr.length) {
			int ans = 0;
			for (Info set : sets) {
				if (set.cnt == 0) {
					return Integer.MAX_VALUE;
				} else {
					ans += set.sum / set.cnt;
				}
			}
			return ans;
		} else {
			int ans = Integer.MAX_VALUE;
			int cur = arr[i];
			for (int j = 0; j < k; j++) {
				sets.get(j).sum += cur;
				sets.get(j).cnt += 1;
				ans = Math.min(ans, f(arr, i + 1, k, sets));
				sets.get(j).sum -= cur;
				sets.get(j).cnt -= 1;
			}
			return ans;
		}
	}

	// 正式方法
	// 时间复杂度O(n * logn)
	public static int minAverageSum2(int[] arr, int k) {
		if (arr.length < k) {
			return -1;
		}
		Arrays.sort(arr);
		int ans = 0;
		for (int i = 0; i < k - 1; i++) {
			ans += arr[i];
		}
		int sum = 0;
		for (int i = k - 1; i < arr.length; i++) {
			sum += arr[i];
		}
		ans += sum / (arr.length - k + 1);
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 8;
		int V = 10000;
		int testTimes = 2000;
		System.out.println("测试开始");
		for (int i = 1; i <= testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(n, V);
			int k = (int) (Math.random() * n) + 1;
			int ans1 = minAverageSum1(arr, k);
			int ans2 = minAverageSum2(arr, k);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
			if (i % 100 == 0) {
				System.out.println("测试到第" + i + "组");
			}
		}
		System.out.println("测试结束");
	}

}
