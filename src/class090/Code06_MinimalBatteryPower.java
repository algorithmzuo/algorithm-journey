package class090;

import java.util.Arrays;

// 执行所有任务的最少初始电量
// 每一个任务有两个参数，需要耗费的电量、至少多少电量才能开始这个任务
// 返回手机至少需要多少的初始电量，才能执行完所有的任务
// 来自真实大厂笔试，没有在线测试，对数器验证
public class Code06_MinimalBatteryPower {

	// 为了验证
	// 二分 + 暴力检查
	// 时间复杂度O(n! * logs)
	public static int power1(int[][] jobs) {
		int max = 0;
		int sum = 0;
		for (int[] job : jobs) {
			max = Math.max(max, job[1]);
			sum += job[0];
		}
		int l = 0, r = max + sum, m;
		int ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (f1(jobs, 0, m)) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static boolean f1(int[][] jobs, int i, int m) {
		if (i == jobs.length) {
			for (int j = 0; j < i; j++) {
				if (m < jobs[j][1]) {
					return false;
				}
				m -= jobs[j][0];
			}
			return m >= 0;
		} else {
			for (int j = i; j < jobs.length; j++) {
				swap(jobs, i, j);
				boolean ans = f1(jobs, i + 1, m);
				swap(jobs, i, j);
				if (ans) {
					return true;
				}
			}
			return false;
		}
	}

	public static void swap(int[][] jobs, int i, int j) {
		int[] tmp = jobs[i];
		jobs[i] = jobs[j];
		jobs[j] = tmp;
	}

	// 正式方法
	// 二分 + 贪心检查
	// 时间复杂度O(n * logs)
	public static int power2(int[][] jobs) {
		int max = 0;
		int sum = 0;
		for (int[] job : jobs) {
			max = Math.max(max, job[1]);
			sum += job[0];
		}
		int l = 0, r = max + sum, m;
		Arrays.sort(jobs, (a, b) -> (b[1] - b[0]) - (a[1] - a[0]));
		int ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (f2(jobs, m)) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

	public static boolean f2(int[][] jobs, int m) {
		for (int i = 0; i < jobs.length; i++) {
			if (m < jobs[i][1]) {
				return false;
			}
			m -= jobs[i][0];
		}
		return m >= 0;
	}

	// 为了验证
	public static int[][] randomJobs(int n, int v) {
		int[][] jobs = new int[n][2];
		for (int i = 0; i < n; i++) {
			jobs[i][0] = (int) (Math.random() * v) + 1;
			jobs[i][1] = (int) (Math.random() * v) + 1;
		}
		return jobs;
	}

	// 为了验证
	public static void main(String[] args) {
		int N = 10;
		int V = 20;
		int testTimes = 2000;
		System.out.println("测试开始");
		for (int i = 1; i <= testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[][] jobs = randomJobs(n, V);
			int ans1 = power1(jobs);
			int ans2 = power2(jobs);
			if (ans1 != ans2) {
				System.out.println("出错了！");
			}
			if (i % 100 == 0) {
				System.out.println("测试到第" + i + "组");
			}
		}
		System.out.println("测试结束");
	}

}
