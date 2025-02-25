package class090;

import java.util.Arrays;

// 会议必须独占时间段的最大会议数量
// 给定若干会议的开始、结束时间
// 你参加某个会议的期间，不能参加其他会议
// 返回你能参加的最大会议数量
// 同学找到了Leetcode的在线测试，题意类似
// 测试链接 :https://leetcode.cn/problems/non-overlapping-intervals/
public class Code03_MeetingMonopoly1 {

	// 测试链接 :https://leetcode.cn/problems/non-overlapping-intervals/
	// 测试链接中，问至少删除多少会议，可以让剩下的会议都不重合
	// 那么求出，最多能不重合的参加多少会议，然后 n - 这个数量，就是答案
	// 同时注意，测试链接中，会议的时间点范围[- 5 * 10 ^ 4 ~ + 5 * 10 ^ 4]
	// 其实就是课上讲的方法，稍微改动一下即可，改动的地方已经加上注释
	public static int eraseOverlapIntervals(int[][] meeting) {
		Arrays.sort(meeting, (a, b) -> a[1] - b[1]);
		int n = meeting.length;
		int ans = 0;
		// cur初始设置为-50001，因为题目数据状况如此
		for (int i = 0, cur = -50001; i < n; i++) {
			if (cur <= meeting[i][0]) {
				ans++;
				cur = meeting[i][1];
			}
		}
		// 会议总数 - 参加的最大会议数量
		return n - ans;
	}

	// 暴力方法
	// 为了验证
	// 时间复杂度O(n!)
	public static int maxMeeting1(int[][] meeting) {
		return f(meeting, meeting.length, 0);
	}

	// 把所有会议全排列
	// 其中一定有安排会议次数最多的排列
	public static int f(int[][] meeting, int n, int i) {
		int ans = 0;
		if (i == n) {
			for (int j = 0, cur = -1; j < n; j++) {
				if (cur <= meeting[j][0]) {
					ans++;
					cur = meeting[j][1];
				}
			}
		} else {
			for (int j = i; j < n; j++) {
				swap(meeting, i, j);
				ans = Math.max(ans, f(meeting, n, i + 1));
				swap(meeting, i, j);
			}
		}
		return ans;
	}

	public static void swap(int[][] meeting, int i, int j) {
		int[] tmp = meeting[i];
		meeting[i] = meeting[j];
		meeting[j] = tmp;
	}

	// 正式方法
	// 时间复杂度O(n*logn)
	public static int maxMeeting2(int[][] meeting) {
		// meeting[i][0] : i号会议开始时间
		// meeting[i][1] : i号会议结束时间
		Arrays.sort(meeting, (a, b) -> a[1] - b[1]);
		int n = meeting.length;
		int ans = 0;
		for (int i = 0, cur = -1; i < n; i++) {
			if (cur <= meeting[i][0]) {
				ans++;
				cur = meeting[i][1];
			}
		}
		return ans;
	}

	// 为了验证
	// 生成随机会议
	public static int[][] randomMeeting(int n, int m) {
		int[][] ans = new int[n][2];
		for (int i = 0, a, b; i < n; i++) {
			a = (int) (Math.random() * m);
			b = (int) (Math.random() * m);
			if (a == b) {
				ans[i][0] = a;
				ans[i][1] = a + 1;
			} else {
				ans[i][0] = Math.min(a, b);
				ans[i][1] = Math.max(a, b);
			}
		}
		return ans;
	}

	// 对数器
	// 为了验证
	public static void main(String[] args) {
		int N = 10;
		int M = 12;
		int testTimes = 2000;
		System.out.println("测试开始");
		for (int i = 1; i <= testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[][] meeting = randomMeeting(n, M);
			int ans1 = maxMeeting1(meeting);
			int ans2 = maxMeeting2(meeting);
			if (ans1 != ans2) {
				// 如果出错了
				// 可以增加打印行为找到一组出错的例子
				// 然后去debug
				System.out.println("出错了！");
			}
			if (i % 100 == 0) {
				System.out.println("测试到第" + i + "组");
			}
		}
		System.out.println("测试结束");
	}

}
