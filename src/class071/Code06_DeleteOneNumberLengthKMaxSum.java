package class071;

// 删掉1个数字后长度为k的子数组最大累加和
// 给定一个数组nums，求必须删除一个数字后的新数组中
// 长度为k的子数组最大累加和，删除哪个数字随意
// 对数器验证
public class Code06_DeleteOneNumberLengthKMaxSum {

	// 暴力方法
	// 为了测试
	public static int maxSum1(int[] nums, int k) {
		int n = nums.length;
		if (n <= k) {
			return 0;
		}
		int ans = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			int[] rest = delete(nums, i);
			ans = Math.max(ans, lenKmaxSum(rest, k));
		}
		return ans;
	}

	// 暴力方法
	// 为了测试
	// 删掉index位置的元素，然后返回新数组
	public static int[] delete(int[] nums, int index) {
		int len = nums.length - 1;
		int[] ans = new int[len];
		int i = 0;
		for (int j = 0; j < nums.length; j++) {
			if (j != index) {
				ans[i++] = nums[j];
			}
		}
		return ans;
	}

	// 暴力方法
	// 为了测试
	// 枚举每一个子数组找到最大累加和
	public static int lenKmaxSum(int[] nums, int k) {
		int n = nums.length;
		int ans = Integer.MIN_VALUE;
		for (int i = 0; i <= n - k; i++) {
			int cur = 0;
			for (int j = i, cnt = 0; cnt < k; j++, cnt++) {
				cur += nums[j];
			}
			ans = Math.max(ans, cur);
		}
		return ans;
	}

	// 正式方法
	// 时间复杂度O(N)
	public static int maxSum2(int[] nums, int k) {
		int n = nums.length;
		if (n <= k) {
			return 0;
		}
		// 单调队列 : 维持窗口内最小值的更新结构，讲解054的内容
		int[] window = new int[n];
		int l = 0;
		int r = 0;
		// 窗口累加和
		long sum = 0;
		int ans = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			// 单调队列 : i位置进入单调队列
			while (l < r && nums[window[r - 1]] >= nums[i]) {
				r--;
			}
			window[r++] = i;
			sum += nums[i];
			if (i >= k) {
				ans = Math.max(ans, (int) (sum - nums[window[l]]));
				if (window[l] == i - k) {
					// 单调队列 : 如果单调队列最左侧的位置过期了，从队列中弹出
					l++;
				}
				sum -= nums[i - k];
			}
		}
		return ans;
	}

	// 为了测试
	// 生成长度为n，值在[-v, +v]之间的随机数组
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * (2 * v + 1)) - v;
		}
		return ans;
	}

	// 为了测试
	// 对数器
	public static void main(String[] args) {
		int n = 200;
		int v = 1000;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int len = (int) (Math.random() * n) + 1;
			int[] nums = randomArray(len, v);
			int k = (int) (Math.random() * n) + 1;
			int ans1 = maxSum1(nums, k);
			int ans2 = maxSum2(nums, k);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
