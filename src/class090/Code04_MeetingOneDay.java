package class090;

import java.util.Arrays;
import java.util.PriorityQueue;

// 会议只占一天的最大会议数量
// 给定若干会议的开始、结束时间
// 任何会议的召开期间，你只需要抽出1天来参加
// 但是你安排的那一天，只能参加这个会议，不能同时参加其他会议
// 返回你能参加的最大会议数量
// 测试链接 : https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended/
public class Code04_MeetingOneDay {

	public static int maxEvents(int[][] events) {
		int n = events.length;
		// events[i][0] : i号会议开始时间
		// events[i][1] : i号会议结束时间
		Arrays.sort(events, (a, b) -> a[0] - b[0]);
		int min = events[0][0];
		int max = events[0][1];
		for (int i = 1; i < n; i++) {
			max = Math.max(max, events[i][1]);
		}
		// 小根堆 : 会议的结束时间
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		int i = 0, ans = 0;
		for (int day = min; day <= max; day++) {
			while (i < n && events[i][0] == day) {
				heap.add(events[i++][1]);
			}
			while (!heap.isEmpty() && heap.peek() < day) {
				heap.poll();
			}
			if (!heap.isEmpty()) {
				heap.poll();
				ans++;
			}
		}
		return ans;
	}

}
