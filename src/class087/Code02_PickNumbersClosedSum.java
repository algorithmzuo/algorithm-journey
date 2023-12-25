package class087;

// 选择k个数字使得两集合累加和相差不超过1
// 给定一个正数n，表示1~n这些数字都可以选择
// 给定一个正数k，表示要从1~n中选择k个数字组成集合A，剩下数字组成集合B
// 希望做到集合A和集合B的累加和相差不超过1
// 如果能做到，返回集合A选择了哪些数字，任何一种方案都可以
// 如果不能做到，返回长度为0的数组
// 2 <= n <= 10^6
// 1 <= k <= n
// 来自真实大厂笔试，没有测试链接，用对数器验证
public class Code02_PickNumbersClosedSum {

	// 正式方法
	// 最优解
	public static int[] pick(int n, int k) {
		long sum = (n + 1) * n / 2;
		int[] ans = generate(sum / 2, n, k);
		if (ans.length == 0 && (sum & 1) == 1) {
			ans = generate(sum / 2 + 1, n, k);
		}
		return ans;
	}

	// 1 ~ n这些数字挑选k个
	// 能不能凑够累加和sum
	// 能的话，返回挑选了哪些数字
	// 不能的话，返回长度为0的数组
	public static int[] generate(long sum, int n, int k) {
		long minKSum = (k + 1) * k / 2;
		int range = n - k;
		if (sum < minKSum || sum > minKSum + (long) range * k) {
			return new int[0];
		}
		// 100 15 -> 85
		long need = sum - minKSum;
		int rightSize = (int) (need / range);
		int midIndex = (k - rightSize) + (int) (need % range);
		int leftSize = k - rightSize - (need % range == 0 ? 0 : 1);
		int[] ans = new int[k];
		for (int i = 0; i < leftSize; i++) {
			ans[i] = i + 1;
		}
		if (need % range != 0) {
			ans[leftSize] = midIndex;
		}
		for (int i = k - 1, j = 0; j < rightSize; i--, j++) {
			ans[i] = n - j;
		}
		return ans;
	}

	// 为了验证
	// 检验得到的结果是否正确
	public static boolean pass(int n, int k, int[] ans) {
		if (ans.length == 0) {
			if (canSplit(n, k)) {
				return false;
			} else {
				return true;
			}
		} else {
			if (ans.length != k) {
				return false;
			}
			int sum = (n + 1) * n / 2;
			int pickSum = 0;
			for (int num : ans) {
				pickSum += num;
			}
			return Math.abs(pickSum - (sum - pickSum)) <= 1;
		}
	}

	// 记忆化搜索
	// 不是最优解，只是为了验证
	// 返回能不能做到
	public static boolean canSplit(int n, int k) {
		int sum = (n + 1) * n / 2;
		int wantSum = (sum / 2) + ((sum & 1) == 0 ? 0 : 1);
		int[][][] dp = new int[n + 1][k + 1][wantSum + 1];
		return f(n, 1, k, wantSum, dp);
	}

	public static boolean f(int n, int i, int k, int s, int[][][] dp) {
		if (k < 0 || s < 0) {
			return false;
		}
		if (i == n + 1) {
			return k == 0 && s == 0;
		}
		if (dp[i][k][s] != 0) {
			return dp[i][k][s] == 1;
		}
		boolean ans = f(n, i + 1, k, s, dp) || f(n, i + 1, k - 1, s - i, dp);
		dp[i][k][s] = ans ? 1 : -1;
		return ans;
	}

	// 为了验证
	// 对数器
	public static void main(String[] args) {
		int N = 60;
		int testTime = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 2;
			int k = (int) (Math.random() * n) + 1;
			int[] ans = pick(n, k);
			if (!pass(n, k, ans)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
