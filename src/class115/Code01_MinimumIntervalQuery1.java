package class115;

import java.util.Arrays;
import java.util.PriorityQueue;

// 包含每个查询的最小区间
// 给你一个二维整数数组intervals，其中intervals[i] = [l, r]
// 表示第i个区间开始于l，结束于r，区间的长度是r-l+1
// 给你一个整数数组queries，queries[i]表示要查询的位置
// 答案是所有包含queries[i]的区间中，最小长度的区间是多长
// 返回数组对应查询的所有答案，如果不存在这样的区间那么答案是-1
// 测试链接 : https://leetcode.cn/problems/minimum-interval-to-include-each-query/
public class Code01_MinimumIntervalQuery1 {

	// 堆结构是现成结构
	public static int[] minInterval(int[][] intervals, int[] queries) {
		int n = intervals.length;
		int m = queries.length;
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		int[][] ques = new int[m][2];
		for (int i = 0; i < m; i++) {
			ques[i][0] = queries[i];
			ques[i][1] = i;
		}
		Arrays.sort(ques, (a, b) -> a[0] - b[0]);
		// 0 : 长度
		// 1 : 影响到的位置
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		int[] ans = new int[m];
		for (int i = 0, j = 0; i < m; i++) {
			for (; j < n && intervals[j][0] <= ques[i][0]; j++) {
				heap.add(new int[] { intervals[j][1] - intervals[j][0] + 1, intervals[j][1] });
			}
			while (!heap.isEmpty() && heap.peek()[1] < ques[i][0]) {
				heap.poll();
			}
			if (!heap.isEmpty()) {
				ans[ques[i][1]] = heap.peek()[0];
			} else {
				ans[ques[i][1]] = -1;
			}
		}
		return ans;
	}

}
