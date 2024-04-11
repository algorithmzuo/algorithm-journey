package class115;

import java.util.Arrays;

// 包含每个查询的最小区间
// 给你一个二维整数数组intervals，其中intervals[i] = [l, r]
// 表示第i个区间开始于l，结束于r，区间的长度是r-l+1
// 给你一个整数数组queries，queries[i]表示要查询的位置
// 答案是所有包含queries[i]的区间中，最小长度的区间是多长
// 返回数组对应查询的所有答案，如果不存在这样的区间那么答案是-1
// 测试链接 : https://leetcode.cn/problems/minimum-interval-to-include-each-query/
public class Code01_MinimumIntervalQuery2 {

	// 堆结构由自己实现
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
		heapSize = 0;
		int[] ans = new int[m];
		for (int i = 0, j = 0; i < m; i++) {
			for (; j < n && intervals[j][0] <= ques[i][0]; j++) {
				push(intervals[j][1] - intervals[j][0] + 1, intervals[j][1]);
			}
			while (!isEmpty() && peekEnd() < ques[i][0]) {
				poll();
			}
			if (!isEmpty()) {
				ans[ques[i][1]] = peekLength();
			} else {
				ans[ques[i][1]] = -1;
			}
		}
		return ans;
	}

	public static int MAXN = 100001;

	public static int[][] heap = new int[MAXN][2];

	public static int heapSize;

	public static boolean isEmpty() {
		return heapSize == 0;
	}

	public static int peekLength() {
		return heap[0][0];
	}

	public static int peekEnd() {
		return heap[0][1];
	}

	public static void push(int h, int e) {
		heap[heapSize][0] = h;
		heap[heapSize][1] = e;
		heapInsert(heapSize++);
	}

	public static void poll() {
		swap(0, --heapSize);
		heapify(0);
	}

	public static void heapInsert(int i) {
		while (heap[i][0] < heap[(i - 1) / 2][0]) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	public static void heapify(int i) {
		int l = i * 2 + 1;
		while (l < heapSize) {
			int best = l + 1 < heapSize && heap[l + 1][0] < heap[l][0] ? l + 1 : l;
			best = heap[best][0] < heap[i][0] ? best : i;
			if (best == i) {
				break;
			}
			swap(best, i);
			i = best;
			l = i * 2 + 1;
		}
	}

	public static void swap(int i, int j) {
		int[] tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}

}
