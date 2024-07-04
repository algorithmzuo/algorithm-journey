package class132;

// 休息k分钟最大会议和
// 给定一个长度为n的数组arr，表示从早到晚发生的会议，各自召开的分钟数
// 当选择一个会议并参加之后，必须休息k分钟
// 返回能参加的会议时长最大累加和
// 比如，arr = { 200, 5, 6, 14, 7, 300 }，k = 15
// 最好的选择为，选择200分钟的会议，然后必须休息15分钟
// 那么接下来的5分钟、6分钟、14分钟的会议注定错过
// 然后放弃7分钟的会议，而选择参加300分钟的会议
// 最终返回500
// 1 <= n、arr[i]、k <= 10^6
// 来自真实大厂笔试，对数器验证
public class Code01_MeetingRestK {

	// 不减少枚举的可能性
	// 时间复杂度O(n^2)
	public static long best1(int[] arr, int k) {
		int n = arr.length;
		long[] dp = new long[n + 1];
		for (int i = n - 1, j, sum; i >= 0; i--) {
			for (j = i + 1, sum = 0; j < n && sum < k; j++) {
				sum += arr[j];
			}
			dp[i] = Math.max(dp[i + 1], dp[j] + arr[i]);
		}
		return dp[0];
	}

	// 利用预处理结构减少枚举的可能性
	// 时间复杂度O(n)
	public static long best2(int[] arr, int k) {
		int n = arr.length;
		int[] jump = new int[n];
		// 窗口[l...r)，左闭右开，sum是窗口累加和
		for (int i = 0, l = 1, r = 1, sum = 0; i < n - 1; i++, l++) {
			while (r < n && sum < k) {
				sum += arr[r++];
			}
			jump[i] = r;
			sum -= arr[l];
		}
		jump[n - 1] = n;
		long[] dp = new long[n + 1];
		for (int i = n - 1; i >= 0; i--) {
			dp[i] = Math.max(dp[i + 1], dp[jump[i]] + arr[i]);
		}
		return dp[0];
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 1000;
		int v = 3000;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 1; i <= testTime; i++) {
			int size = (int) (Math.random() * n) + 1;
			int[] arr = randomArray(size, v);
			int k = (int) (Math.random() * v) + 1;
			long ans1 = best1(arr, k);
			long ans2 = best2(arr, k);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
