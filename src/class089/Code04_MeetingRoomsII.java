package class089;

import java.util.Arrays;
import java.util.PriorityQueue;

// 会议室II
// 给你一个会议时间安排的数组 intervals
// 每个会议时间都会包括开始和结束的时间intervals[i]=[starti, endi]
// 返回所需会议室的最小数量
// 测试链接 : https://leetcode.cn/problems/meeting-rooms-ii/
public class Code04_MeetingRoomsII {

	public static int minMeetingRooms(int[][] meeting) {
		int n = meeting.length;
		Arrays.sort(meeting, (a, b) -> a[0] - b[0]);
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		int ans = 0;
		for (int i = 0; i < n; i++) {
			while (!heap.isEmpty() && heap.peek() <= meeting[i][0]) {
				heap.poll();
			}
			heap.add(meeting[i][1]);
			ans = Math.max(ans, heap.size());
		}
		return ans;
	}

}
