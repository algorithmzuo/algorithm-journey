package class089;

import java.util.Arrays;
import java.util.PriorityQueue;

// 课程表III
// 这里有n门不同的在线课程，按从1到n编号
// 给你一个数组courses
// 其中courses[i]=[durationi, lastDayi]表示第i门课将会持续上durationi天课
// 并且必须在不晚于lastDayi的时候完成
// 你的学期从第 1 天开始
// 且不能同时修读两门及两门以上的课程
// 返回你最多可以修读的课程数目
// 测试链接 : https://leetcode.cn/problems/course-schedule-iii/
public class Code05_CourseScheduleIII {

	public static int scheduleCourse(int[][] courses) {
		// 0 : 代价
		// 1 : 截止
		Arrays.sort(courses, (a, b) -> a[1] - b[1]);
		// 大根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
		int time = 0;
		for (int[] c : courses) {
			if (time + c[0] <= c[1]) {
				heap.add(c[0]);
				time += c[0];
			} else {
				// time + c[0] > c[1]
				if (!heap.isEmpty() && heap.peek() > c[0]) {
					time += c[0] - heap.poll();
					heap.add(c[0]);
				}
			}
		}
		return heap.size();
	}

}
