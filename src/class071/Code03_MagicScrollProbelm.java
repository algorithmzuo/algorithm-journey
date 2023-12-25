package class071;

// 魔法卷轴
// 给定一个数组nums，其中可能有正、负、0
// 每个魔法卷轴可以把nums中连续的一段全变成0
// 你希望数组整体的累加和尽可能大
// 卷轴使不使用、使用多少随意，但一共只有2个魔法卷轴
// 请返回数组尽可能大的累加和
// 对数器验证
public class Code03_MagicScrollProbelm {

	// 暴力方法
	// 为了测试
	public static int maxSum1(int[] nums) {
		int p1 = 0;
		for (int num : nums) {
			p1 += num;
		}
		int n = nums.length;
		int p2 = mustOneScroll(nums, 0, n - 1);
		int p3 = Integer.MIN_VALUE;
		for (int i = 1; i < n; i++) {
			p3 = Math.max(p3, mustOneScroll(nums, 0, i - 1) + mustOneScroll(nums, i, n - 1));
		}
		return Math.max(p1, Math.max(p2, p3));
	}

	// 暴力方法
	// 为了测试
	// nums[l...r]范围上一定要用一次卷轴情况下的最大累加和
	public static int mustOneScroll(int[] nums, int l, int r) {
		int ans = Integer.MIN_VALUE;
		// l...r范围上包含a...b范围
		// 如果a...b范围上的数字都变成0
		// 返回剩下数字的累加和
		// 所以枚举所有可能的a...b范围
		// 相当暴力，但是正确
		for (int a = l; a <= r; a++) {
			for (int b = a; b <= r; b++) {
				// l...a...b...r
				int curAns = 0;
				for (int i = l; i < a; i++) {
					curAns += nums[i];
				}
				for (int i = b + 1; i <= r; i++) {
					curAns += nums[i];
				}
				ans = Math.max(ans, curAns);
			}
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(n)
	public static int maxSum2(int[] nums) {
		int n = nums.length;
		if (n == 0) {
			return 0;
		}
		// 情况1 : 完全不使用卷轴
		int p1 = 0;
		for (int num : nums) {
			p1 += num;
		}
		// prefix[i] : 0~i范围上一定要用1次卷轴的情况下，0~i范围上整体最大累加和多少
		int[] prefix = new int[n];
		// 每一步的前缀和
		int sum = nums[0];
		// maxPresum : 之前所有前缀和的最大值
		int maxPresum = Math.max(0, nums[0]);
		for (int i = 1; i < n; i++) {
			prefix[i] = Math.max(prefix[i - 1] + nums[i], maxPresum);
			sum += nums[i];
			maxPresum = Math.max(maxPresum, sum);
		}
		// 情况二 : 必须用1次卷轴
		int p2 = prefix[n - 1];
		// suffix[i] : i~n-1范围上一定要用1次卷轴的情况下，i~n-1范围上整体最大累加和多少
		int[] suffix = new int[n];
		sum = nums[n - 1];
		maxPresum = Math.max(0, sum);
		for (int i = n - 2; i >= 0; i--) {
			suffix[i] = Math.max(nums[i] + suffix[i + 1], maxPresum);
			sum += nums[i];
			maxPresum = Math.max(maxPresum, sum);
		}
		// 情况二 : 必须用2次卷轴
		int p3 = Integer.MIN_VALUE;
		for (int i = 1; i < n; i++) {
			// 枚举所有的划分点i
			// 0~i-1 左
			// i~n-1 右
			p3 = Math.max(p3, prefix[i - 1] + suffix[i]);
		}
		return Math.max(p1, Math.max(p2, p3));
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * (v * 2 + 1)) - v;
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 50;
		int v = 100;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * n);
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
