package class071;

// 可以翻转1次的情况下子数组最大累加和
// 给定一个数组nums，
// 现在允许你随意选择数组连续一段进行翻转
// 比如翻转[1,2,3,4,5,6]的[2~4]范围，得到的是[1,2,5,4,3,6]
// 返回随意翻转1次之后数组的子数组最大累加和是多少
// 对数器验证
public class Code06_ReverseArraySubarrayMaxSum {

	// 暴力方法
	// 为了验证
	public static int maxSumReverse1(int[] nums) {
		int ans = Integer.MIN_VALUE;
		for (int l = 0; l < nums.length; l++) {
			for (int r = l; r < nums.length; r++) {
				reverse(nums, l, r);
				ans = Math.max(ans, maxSum(nums));
				reverse(nums, l, r);
			}
		}
		return ans;
	}

	public static void reverse(int[] nums, int l, int r) {
		while (l < r) {
			int tmp = nums[l];
			nums[l++] = nums[r];
			nums[r--] = tmp;
		}
	}

	public static int maxSum(int[] nums) {
		int pre = nums[0];
		int max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			pre = Math.max(nums[i], nums[i] + pre);
			max = Math.max(max, pre);
		}
		return max;
	}

	// 正式方法
	// 时间复杂度O(n)
	public static int maxSumReverse2(int[] nums) {
		int n = nums.length;
		int[] prefix = new int[n];
		prefix[n - 1] = nums[n - 1];
		for (int i = n - 2; i >= 0; i--) {
			prefix[i] = nums[i] + Math.max(0, prefix[i + 1]);
		}
		int ans = prefix[0];
		int suffix = nums[0];
		int maxSuffix = suffix;
		for (int i = 1; i < n; i++) {
			ans = Math.max(ans, maxSuffix + prefix[i]);
			suffix = nums[i] + Math.max(0, suffix);
			maxSuffix = Math.max(maxSuffix, suffix);
		}
		ans = Math.max(ans, maxSuffix);
		return ans;
	}

	// 为了测试
	// 生成随机数组
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * (v * 2 + 1)) - v;
		}
		return ans;
	}

	// 为了测试
	// 对数器
	public static void main(String[] args) {
		int n = 50;
		int v = 200;
		int testTime = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * n) + 1;
			int[] arr = randomArray(len, v);
			int ans1 = maxSumReverse1(arr);
			int ans2 = maxSumReverse2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
