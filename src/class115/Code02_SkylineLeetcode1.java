package class115;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// 天际线问题(Leetcode测试)
// 测试链接 : https://leetcode.cn/problems/the-skyline-problem/
public class Code02_SkylineLeetcode1 {

	// 堆结构是现成结构
	public static List<List<Integer>> getSkyline(int[][] arr) {
		int n = arr.length;
		int m = build(arr, n);
		// 0 : 高度
		// 1 : 影响到的位置
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
		for (int i = 0, j = 0; i < m; i++) {
			for (; j < n && arr[j][0] <= i; j++) {
				heap.add(new int[] { arr[j][2], arr[j][1], });
			}
			while (!heap.isEmpty() && heap.peek()[1] < i) {
				heap.poll();
			}
			if (!heap.isEmpty()) {
				height[i] = heap.peek()[0];
			}
		}
		List<List<Integer>> ans = new ArrayList<>();
		for (int i = 0, pre = 0; i < m; i++) {
			if (pre != height[i]) {
				ans.add(Arrays.asList(sort[i], height[i]));
			}
			pre = height[i];
		}
		return ans;
	}

	public static int MAXN = 100001;

	public static int[] sort = new int[MAXN];

	public static int[] height = new int[MAXN];

	public static int build(int[][] arr, int n) {
		int size = 0;
		for (int i = 0; i < n; i++) {
			sort[size++] = arr[i][0];
			sort[size++] = arr[i][1] - 1;
			sort[size++] = arr[i][1];
		}
		Arrays.sort(sort, 0, size);
		int m = 1;
		for (int i = 1; i < size; i++) {
			if (sort[m - 1] != sort[i]) {
				sort[m++] = sort[i];
			}
		}
		for (int i = 0; i < n; i++) {
			arr[i][0] = rank(m, arr[i][0]);
			arr[i][1] = rank(m, arr[i][1] - 1);
		}
		Arrays.sort(arr, 0, n, (a, b) -> a[0] - b[0]);
		Arrays.fill(height, 0, m, 0);
		return m;
	}

	public static int rank(int n, int v) {
		int ans = 0;
		int l = 0, r = n - 1, mid;
		while (l <= r) {
			mid = (l + r) >> 1;
			if (sort[mid] >= v) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

}
