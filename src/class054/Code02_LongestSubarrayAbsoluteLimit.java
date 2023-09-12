package class054;

// 绝对差不超过限制的最长连续子数组
// 给你一个整数数组 nums ，和一个表示限制的整数 limit
// 请你返回最长连续子数组的长度
// 该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit
// 如果不存在满足条件的子数组，则返回 0
// 测试链接 : https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
public class Code02_LongestSubarrayAbsoluteLimit {

	public static int MAXN = 100001;

	public static int[] maxDeque = new int[MAXN];

	public static int[] minDeque = new int[MAXN];

	public static int maxh, maxt, minh, mint;

	public static int[] arr;

	public static int longestSubarray(int[] nums, int limit) {
		maxh = maxt = minh = mint = 0;
		arr = nums;
		int n = arr.length;
		int ans = 0;
		for (int l = 0, r = 0; l < n; l++) {
			while (r < n && ok(limit, nums[r])) {
				push(r++);
			}
			ans = Math.max(ans, r - l);
			pop(l);
		}
		return ans;
	}

	// 判断如果加入数字number，窗口最大值 - 窗口最小值是否依然 <= limit
	// 依然 <= limit，返回true
	// 不再 <= limit，返回false
	public static boolean ok(int limit, int number) {
		int max = maxh < maxt ? Math.max(arr[maxDeque[maxh]], number) : number;
		int min = minh < mint ? Math.min(arr[minDeque[minh]], number) : number;
		return max - min <= limit;
	}

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

	public static void pop(int l) {
		if (maxh < maxt && maxDeque[maxh] == l) {
			maxh++;
		}
		if (minh < mint && minDeque[minh] == l) {
			minh++;
		}
	}

}
