package class128;

import java.util.Arrays;

// 相邻必选的子序列最大中位数
// 给定一个长度为n的数组arr
// 合法子序列定义为，任意相邻的两个数至少要有一个被挑选所组成的子序列
// 求所有合法子序列中，最大中位数是多少
// 中位数的定义为上中位数
// [1, 2, 3, 4]的上中位数是2
// [1, 2, 3, 4, 5]的上中位数是3
// 2 <=  n <= 10^5
// 1 <= arr[i] <= 10^9
// 来自真实大厂笔试，对数器验证
public class Code05_MaximizeMedian {

	// 正式方法
	// 时间复杂度O(n * log n)
	public static int maximizeMedian(int[] arr) {
		int n = arr.length;
		int[] sort = new int[n];
		for (int i = 0; i < n; i++) {
			sort[i] = arr[i];
		}
		Arrays.sort(sort);
		int l = 0;
		int r = n - 1;
		int m = 0;
		int ans = -1;
		int[] help = new int[n];
		int[][] dp = new int[n + 1][2];
		while (l <= r) {
			m = (l + r) / 2;
			if (check(arr, help, dp, sort[m], n)) {
				ans = sort[m];
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	// 任意相邻的两数至少选一个来生成子序列
	// 到底有没有一个合法子序列，能让其中>=x的数达到一半以上
	public static boolean check(int[] arr, int[] help, int[][] dp, int x, int n) {
		for (int i = 0; i < n; i++) {
			help[i] = arr[i] >= x ? 1 : -1;
		}
		return dp(help, dp, n) > 0;
	}

	// 任意相邻的两数至少选一个来生成子序列
	// 返回合法子序列的最大累加和
	public static int dp(int[] arr, int[][] dp, int n) {
		for (int i = n - 1; i >= 0; i--) {
			// dp[i][0] : i位置的数字，选和不选皆可，i...范围上形成合法子序列的最大累加和
			// dp[i][1] : i位置的数字，一定要选，i...范围上形成合法子序列的最大累加和
			dp[i][0] = Math.max(arr[i] + dp[i + 1][0], dp[i + 1][1]);
			dp[i][1] = arr[i] + dp[i + 1][0];
		}
		return dp[0][0];
	}

	// 暴力方法
	// 为了验证
	public static int right(int[] arr) {
		int[] path = new int[arr.length];
		return dfs(arr, 0, true, path, 0);
	}

	// 暴力方法
	// 为了验证
	public static int dfs(int[] arr, int i, boolean pre, int[] path, int size) {
		if (i == arr.length) {
			if (size == 0) {
				return 0;
			}
			int[] sort = new int[size];
			for (int j = 0; j < size; j++) {
				sort[j] = path[j];
			}
			Arrays.sort(sort);
			return sort[(sort.length - 1) / 2];
		} else {
			path[size] = arr[i];
			int ans = dfs(arr, i + 1, true, path, size + 1);
			if (pre) {
				ans = Math.max(ans, dfs(arr, i + 1, false, path, size));
			}
			return ans;
		}
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
		int n = 20;
		int v = 1000;
		int testTime = 10000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int size = (int) (Math.random() * n) + 1;
			int[] arr = randomArray(size, v);
			int ans1 = right(arr);
			int ans2 = maximizeMedian(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");
		System.out.println();

		System.out.println("性能测试开始");
		n = 100000;
		v = 50000000;
		System.out.println("数组长度 : " + n);
		System.out.println("数值范围 : " + v);
		int[] arr = randomArray(n, v);
		long start = System.currentTimeMillis();
		maximizeMedian(arr);
		long end = System.currentTimeMillis();
		System.out.println("正式方法的运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

}
