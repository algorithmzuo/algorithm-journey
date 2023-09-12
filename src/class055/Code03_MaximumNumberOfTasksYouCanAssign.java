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

	public static int MAXN = 50001;

	public static int[] deque = new int[MAXN];

	public static int h, t;

	public static int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
		int n = tasks.length;
		Arrays.sort(tasks);
		Arrays.sort(workers);
		int ans = 0;
		for (int l = 0, r = n, mid; l <= r;) {
			mid = (l + r) / 2;
			if (need(tasks, 0, mid - 1, workers, workers.length - mid, workers.length - 1, strength) <= pills) {
				ans = mid;
				l = mid + 1;
			} else {
				r = mid - 1;
			}
		}
		return ans;
	}

	public static int need(int[] ts, int tl, int tr, int[] ws, int wl, int wr, int s) {
		if (wl < 0) {
			return Integer.MAX_VALUE;
		}
		h = t = 0;
		int ti = tl;
		int ans = 0;
		for (int wi = wl; wi <= wr; wi++) {
			for (; ti <= tr && ts[ti] <= ws[wi]; ti++) {
				deque[t++] = ti;
			}
			if (h < t && ts[deque[h]] <= ws[wi]) {
				h++;
			} else {
				for (; ti <= tr && ts[ti] <= ws[wi] + s; ti++) {
					deque[t++] = ti;
				}
				if (h < t) {
					ans++;
					t--;
				} else {
					return Integer.MAX_VALUE;
				}
			}
		}
		return ans;
	}

}
