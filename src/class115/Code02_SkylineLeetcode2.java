package class115;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 天际线问题(Leetcode测试)
// 测试链接 : https://leetcode.cn/problems/the-skyline-problem/
public class Code02_SkylineLeetcode2 {

	// 堆结构由自己实现
	public static List<List<Integer>> getSkyline(int[][] arr) {
		int n = arr.length;
		int m = prepare(arr, n);
		for (int i = 1, j = 0; i <= m; i++) {
			for (; j < n && arr[j][0] <= i; j++) {
				push(arr[j][2], arr[j][1]);
			}
			while (!isEmpty() && peekEnd() < i) {
				poll();
			}
			if (!isEmpty()) {
				height[i] = peekHeight();
			}
		}
		List<List<Integer>> ans = new ArrayList<>();
		for (int i = 1, pre = 0; i <= m; i++) {
			if (pre != height[i]) {
				ans.add(Arrays.asList(xsort[i], height[i]));
			}
			pre = height[i];
		}
		return ans;
	}

	public static int MAXN = 100001;

	public static int[] xsort = new int[MAXN];

	public static int[] height = new int[MAXN];

	public static int[][] heap = new int[MAXN][2];

	public static int heapSize;

	public static int prepare(int[][] arr, int n) {
		int size = 0;
		for (int i = 0; i < n; i++) {
			xsort[++size] = arr[i][0];
			xsort[++size] = arr[i][1] - 1;
			xsort[++size] = arr[i][1];
		}
		Arrays.sort(xsort, 1, size + 1);
		int m = 1;
		for (int i = 1; i <= size; i++) {
			if (xsort[m] != xsort[i]) {
				xsort[++m] = xsort[i];
			}
		}
		for (int i = 0; i < n; i++) {
			arr[i][0] = rank(m, arr[i][0]);
			arr[i][1] = rank(m, arr[i][1] - 1);
		}
		Arrays.sort(arr, 0, n, (a, b) -> a[0] - b[0]);
		Arrays.fill(height, 1, m + 1, 0);
		return m;
	}

	public static int rank(int n, int v) {
		int ans = 0;
		int l = 1, r = n, mid;
		while (l <= r) {
			mid = (l + r) >> 1;
			if (xsort[mid] >= v) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	public static boolean isEmpty() {
		return heapSize == 0;
	}

	public static int peekHeight() {
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
		while (heap[i][0] > heap[(i - 1) / 2][0]) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	public static void heapify(int i) {
		int l = i * 2 + 1;
		while (l < heapSize) {
			int best = l + 1 < heapSize && heap[l + 1][0] > heap[l][0] ? l + 1 : l;
			best = heap[best][0] > heap[i][0] ? best : i;
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
