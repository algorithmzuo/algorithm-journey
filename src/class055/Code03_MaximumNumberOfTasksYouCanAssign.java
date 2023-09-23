package class055;

import java.util.Arrays;

// 你可以安排的最多任务数目
// 给你 n 个任务和 m 个工人。每个任务需要一定的力量值才能完成
// 需要的力量值保存在下标从 0 开始的整数数组 tasks 中，
// 第i个任务需要 tasks[i] 的力量才能完成
// 每个工人的力量值保存在下标从 0 开始的整数数组workers中，
// 第j个工人的力量值为 workers[j]
// 每个工人只能完成一个任务，且力量值需要大于等于该任务的力量要求值，即workers[j]>=tasks[i]
// 除此以外，你还有 pills 个神奇药丸，可以给 一个工人的力量值 增加 strength
// 你可以决定给哪些工人使用药丸，但每个工人 最多 只能使用 一片 药丸
// 给你下标从 0 开始的整数数组tasks 和 workers 以及两个整数 pills 和 strength
// 请你返回 最多 有多少个任务可以被完成。
// 测试链接 : https://leetcode.cn/problems/maximum-number-of-tasks-you-can-assign/
public class Code03_MaximumNumberOfTasksYouCanAssign {

	public static int[] tasks;

	public static int[] workers;

	public static int MAXN = 50001;

	public static int[] deque = new int[MAXN];

	public static int h, t;

	// 两个数组排序 : O(n * logn) + O(m * logm)
	// 二分答案的过程，每次二分都用一下双端队列 : O((n和m最小值)*log(n和m最小值))
	// 最复杂的反而是排序的过程了，所以时间复杂度O(n * logn) + O(m * logm)
	public static int maxTaskAssign(int[] ts, int[] ws, int pills, int strength) {
		tasks = ts;
		workers = ws;
		Arrays.sort(tasks);
		Arrays.sort(workers);
		int tsize = tasks.length;
		int wsize = workers.length;
		int ans = 0;
		// [0, Math.min(tsize, wsize)]
		for (int l = 0, r = Math.min(tsize, wsize), m; l <= r;) {
			// m中点，一定要完成的任务数量
			m = (l + r) / 2;
			if (f(0, m - 1, wsize - m, wsize - 1, strength, pills)) {
				ans = m;
				l = m + 1;
			} else {
				r = m - 1;
			}
		}
		return ans;
	}

	// tasks[tl....tr]需要力量最小的几个任务
	// workers[wl....wr]力量值最大的几个工人
	// 药效是s，一共有的药pills个
	// 在药的数量不超情况下，能不能每个工人都做一个任务
	public static boolean f(int tl, int tr, int wl, int wr, int s, int pills) {
		h = t = 0;
		// 已经使用的药的数量
		int cnt = 0;
		for (int i = wl, j = tl; i <= wr; i++) {
			// i : 工人的编号
			// j : 任务的编号
			for (; j <= tr && tasks[j] <= workers[i]; j++) {
				// 工人不吃药的情况下，去解锁任务
				deque[t++] = j;
			}
			if (h < t && tasks[deque[h]] <= workers[i]) {
				h++;
			} else {
				// 吃药之后的逻辑
				for (; j <= tr && tasks[j] <= workers[i] + s; j++) {
					deque[t++] = j;
				}
				if (h < t) {
					cnt++;
					t--;
				} else {
					return false;
				}
			}
		}
		return cnt <= pills;
	}

}
