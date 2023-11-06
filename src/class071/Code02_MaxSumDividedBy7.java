package class071;

// 必须是7的倍数的子序列最大累加和
// 给定一个非负数组nums，可以任意选择数字组成子序列
// 使子集累加和最大且为7的倍数，返回最大累加和
// 对数器验证
public class Code02_MaxSumDividedBy7 {

	// 暴力方法
	// 为了验证
	public static int maxSum1(int[] nums) {
		return f1(nums, 0, 0);
	}

	public static int f1(int[] nums, int i, int s) {
		if (i == nums.length) {
			return s % 7 == 0 ? s : 0;
		}
		return Math.max(f1(nums, i + 1, s), f1(nums, i + 1, s + nums[i]));
	}

	// 正式方法
	// 时间复杂度O(n)
	public static int maxSum2(int[] nums) {
		int n = nums.length;
		int[][] dp = new int[n][7];
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < 7; j++) {
				dp[i][j] = -1;
			}
		}
		dp[0][nums[0] % 7] = nums[0];
		for (int i = 1, cur; i < n; i++) {
			cur = nums[i] % 7;
			for (int j = 0, find; j < 7; j++) {
				dp[i][j] = dp[i - 1][j];
				find = (7 + j - cur) % 7;
				if (dp[i - 1][find] != -1) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][find] + nums[i]);
				}
			}
		}
		return dp[n - 1][0] == -1 ? 0 : dp[n - 1][0];
	}

	// 为了测试
	// 生成随机数组
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	// 对数器
	public static void main(String[] args) {
		int n = 15;
		int v = 30;
		int testTime = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * n) + 1;
			int[] nums = randomArray(len, v);
			int ans1 = maxSum1(nums);
			int ans2 = maxSum2(nums);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
