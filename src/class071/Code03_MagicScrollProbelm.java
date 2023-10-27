package class071;

// 魔法卷轴
// 给定一个数组nums，其中可能有正、负、0
// 1个魔法卷轴可以把nums中连续的一段全变成0
// 你希望数组整体的累加和尽可能大
// 一共有2个魔法卷轴，请返回数组尽可能大的累加和
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

	// 为了测试
	public static int mustOneScroll(int[] nums, int l, int r) {
		int ans = Integer.MIN_VALUE;
		for (int a = l; a <= r; a++) {
			for (int b = a; b <= r; b++) {
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
	// 时间复杂度O(N)
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
		// left[i] : 0~i范围上，一定要用1次卷轴的情况下最大累加和多少
		int[] left = new int[n];
		int sum = nums[0];
		int maxSum = Math.max(0, sum);
		for (int i = 1; i < n; i++) {
			left[i] = Math.max(left[i - 1] + nums[i], maxSum);
			sum += nums[i];
			maxSum = Math.max(maxSum, sum);
		}
		// 情况二 : 必须用1次卷轴
		int p2 = left[n - 1];
		// right[i] : i~n-1范围上，一定要用1次卷轴的情况下最大累加和多少
		int[] right = new int[n];
		sum = nums[n - 1];
		maxSum = Math.max(0, sum);
		for (int i = n - 2; i >= 0; i--) {
			right[i] = Math.max(nums[i] + right[i + 1], maxSum);
			sum += nums[i];
			maxSum = Math.max(maxSum, sum);
		}
		// 情况二 : 必须用2次卷轴
		int p3 = Integer.MIN_VALUE;
		for (int i = 1; i < n; i++) {
			p3 = Math.max(p3, left[i - 1] + right[i]);
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
