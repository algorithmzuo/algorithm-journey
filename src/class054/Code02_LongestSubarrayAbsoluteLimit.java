package class054;

// 绝对差不超过限制的最长连续子数组
// 给你一个整数数组 nums ，和一个表示限制的整数 limit
// 请你返回最长连续子数组的长度
// 该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit
// 如果不存在满足条件的子数组，则返回 0
// 测试链接 : https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
public class Code02_LongestSubarrayAbsoluteLimit {

	public static int MAXN = 100001;

	// 窗口内最大值的更新结构（单调队列）
	public static int[] maxDeque = new int[MAXN];

	// 窗口内最小值的更新结构（单调队列）
	public static int[] minDeque = new int[MAXN];

	public static int maxh, maxt, minh, mint;

	public static int[] arr;

	public static int longestSubarray(int[] nums, int limit) {
		maxh = maxt = minh = mint = 0;
		arr = nums;
		int n = arr.length;
		int ans = 0;
		for (int l = 0, r = 0; l < n; l++) {
			// [l,r)，r永远是没有进入窗口的、下一个数所在的位置
			while (r < n && ok(limit, nums[r])) {
				push(r++);
			}
			// 从while出来的时候，[l,r)是l开头的子数组能向右延伸的最大范围
			ans = Math.max(ans, r - l);
			pop(l);
		}
		return ans;
	}

	// 判断如果加入数字number，窗口最大值 - 窗口最小值是否依然 <= limit
	// 依然 <= limit，返回true
	// 不再 <= limit，返回false
	public static boolean ok(int limit, int number) {
		// max : 如果number进来，新窗口的最大值
		int max = maxh < maxt ? Math.max(arr[maxDeque[maxh]], number) : number;
		// min : 如果number进来，新窗口的最小值
		int min = minh < mint ? Math.min(arr[minDeque[minh]], number) : number;
		return max - min <= limit;
	}

	// r位置的数字进入窗口，修改窗口内最大值的更新结构、修改窗口内最小值的更新结构
	public static void push(int r) {
		while (maxh < maxt && arr[maxDeque[maxt - 1]] <= arr[r]) {
			maxt--;
		}
		maxDeque[maxt++] = r;
		while (minh < mint && arr[minDeque[mint - 1]] >= arr[r]) {
			mint--;
		}
		minDeque[mint++] = r;
	}

	// 窗口要吐出l位置的数了！检查过期！
	public static void pop(int l) {
		if (maxh < maxt && maxDeque[maxh] == l) {
			maxh++;
		}
		if (minh < mint && minDeque[minh] == l) {
			minh++;
		}
	}

}
