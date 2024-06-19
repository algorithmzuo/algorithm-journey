package class130;

// 子数组最大变序和
// 给定一个长度为n的数组arr，变序和定义如下
// 数组中每个值都可以减小或者不变，必须把整体变成严格升序的
// 所有方案中，能得到的最大累加和，叫做数组的变序和
// 比如[1,100,7]，变序和14，方案为变成[1,6,7]
// 比如[5,4,9]，变序和16，方案为变成[3,4,9]
// 比如[1,4,2]，变序和3，方案为变成[0,1,2]
// 返回arr所有子数组的变序和中，最大的那个
// 1 <= n、arr[i] <= 10^6
// 来自真实大厂笔试，对数器验证
public class Code07_MaximumOrderSum {

	// 暴力方法
	// 为了验证
	// 时间复杂度O(n * v)
	public static long maxSum1(int[] arr) {
		int n = arr.length;
		int max = 0;
		for (int num : arr) {
			max = Math.max(max, num);
		}
		long ans = 0;
		long[][] dp = new long[n][max + 1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= max; j++) {
				dp[i][j] = -1;
			}
		}
		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, f1(arr, i, arr[i], dp));
		}
		return ans;
	}

	// 暴力方法
	// 为了验证
	// 子数组一定要以i位置结尾，并且i位置变成的数字不能大于p的情况下
	// 返回子数组的最大变序和
	public static long f1(int[] arr, int i, int p, long[][] dp) {
		if (p <= 0 || i == -1) {
			return 0;
		}
		if (dp[i][p] != -1) {
			return dp[i][p];
		}
		int cur = Math.min(arr[i], p);
		long next = f1(arr, i - 1, cur - 1, dp);
		long ans = (long) cur + next;
		dp[i][p] = ans;
		return cur + next;
	}

	// 正式方法
	// 时间复杂度O(n)
	public static long maxSum2(int[] arr) {
		int n = arr.length;
		int[] stack = new int[n];
		int size = 0;
		long[] dp = new long[n];
		long ans = 0;
		for (int i = 0; i < n; i++) {
			int curIdx = i;
			int curVal = arr[curIdx];
			while (curVal > 0 && size > 0) {
				int topIdx = stack[size - 1];
				int topVal = arr[topIdx];
				if (topVal >= curVal) {
					size--;
				} else {
					int idxDiff = curIdx - topIdx;
					int valDiff = curVal - topVal;
					if (valDiff >= idxDiff) {
						dp[i] += sum(curVal, idxDiff) + dp[topIdx];
						curVal = 0;
						curIdx = 0;
						break;
					} else {
						dp[i] += sum(curVal, idxDiff);
						curVal -= idxDiff;
						curIdx = topIdx;
						size--;
					}
				}
			}
			if (curVal > 0) {
				dp[i] += sum(curVal, curIdx + 1);
			}
			stack[size++] = i;
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	// 最大值从max开始，往下一共有n项，只要正数的部分，返回累加和
	public static long sum(int max, int n) {
		n = Math.min(max, n);
		return (((long) max * 2 - n + 1) * n) / 2;
	}

	// 为了验证
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了验证
	public static void main(String[] args) {
		int n = 100;
		int v = 100;
		int testTimes = 50000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTimes; i++) {
			int size = (int) (Math.random() * n) + 1;
			int[] arr = randomArray(size, v);
			long ans1 = maxSum1(arr);
			long ans2 = maxSum2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		n = 1000000;
		v = 1000000;
		System.out.println("数组长度 : " + n);
		System.out.println("数值范围 : " + v);
		int[] arr = randomArray(n, v);
		long start = System.currentTimeMillis();
		maxSum2(arr);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

}
