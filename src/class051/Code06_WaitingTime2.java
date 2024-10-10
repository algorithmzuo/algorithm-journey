package class051;

// 完成旅途的最少时间(题目6的在线测试)
// 有同学找到了在线测试链接，和课上讲的题目6几乎是一个意思，但是有细微差别
// 实现的代码，除了一些变量需要改成long类型之外，仅有两处关键逻辑不同，都打上了注释
// 除此之外，和课上讲的题目6的实现，再无区别
// 可以仔细阅读如下测试链接里的题目，重点关注此题和题目6，在题意上的差别
// 测试链接 : https://leetcode.cn/problems/minimum-time-to-complete-trips/
public class Code06_WaitingTime2 {

	public static long minimumTime(int[] arr, int w) {
		int min = Integer.MAX_VALUE;
		for (int x : arr) {
			min = Math.min(min, x);
		}
		long ans = 0;
		for (long l = 0, r = (long) min * w, m; l <= r;) {
			m = l + ((r - l) >> 1);
			// 这里逻辑和课上讲的不同
			// 课上讲的题意，是需要等多少人才能获得服务，你是第w+1个
			// 在线测试的题意，是需要完成w趟旅行
			if (f(arr, m) >= w) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static long f(int[] arr, long time) {
		long ans = 0;
		for (int num : arr) {
			// 这里逻辑和课上讲的不同
			// 课上讲的题意，计算time时间内，(完成 + 开始)服务的人数，需要+1
			// 在线测试的题意，计算time时间内，能完成多少旅行，不需要+1
			ans += (time / num);
		}
		return ans;
	}

}
