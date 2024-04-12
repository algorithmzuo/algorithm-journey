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
		int m = prepare(arr, n);
		// 0 : 高度
		// 1 : 影响到的位置
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
		for (int i = 1, j = 0; i <= m; i++) {
			for (; j < n && arr[j][0] <= i; j++) {
				heap.add(new int[] { arr[j][2], arr[j][1] });
			}
			while (!heap.isEmpty() && heap.peek()[1] < i) {
				heap.poll();
			}
			if (!heap.isEmpty()) {
				height[i] = heap.peek()[0];
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

	// 准备工作如下
	// 1) 收集大楼左边界、右边界-1、右边界的值
	// 2) 收集的所有值排序、去重
	// 3) 大楼的左边界和右边界，修改成排名值
	// 4) 大楼根据左边界排序
	// 5) 清空height数组
	// 6) 返回离散值的个数
	public static int prepare(int[][] arr, int n) {
		int size = 0;
		// 大楼的左边界、右边界-1、右边界，三个值都去离散化
		for (int i = 0; i < n; i++) {
			xsort[++size] = arr[i][0];
			xsort[++size] = arr[i][1] - 1;
			xsort[++size] = arr[i][1];
		}
		Arrays.sort(xsort, 1, size + 1);
		// 排序之后去重，去重后的数值有m个
		int m = 1;
		for (int i = 1; i <= size; i++) {
			if (xsort[m] != xsort[i]) {
				xsort[++m] = xsort[i];
			}
		}
		// 修改大楼影响到的左右边界，都变成排名值
		for (int i = 0; i < n; i++) {
			arr[i][0] = rank(m, arr[i][0]);
			// 大楼影响到的右边界，减少1！
			// 课上重点说明的内容
			arr[i][1] = rank(m, arr[i][1] - 1);
		}
		// 所有大楼根据左边界排序
		Arrays.sort(arr, 0, n, (a, b) -> a[0] - b[0]);
		// 高度数组清空
		Arrays.fill(height, 1, m + 1, 0);
		// 返回有多少个不同的离散值
		return m;
	}

	// 查询数值v的排名(离散值)
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

}
