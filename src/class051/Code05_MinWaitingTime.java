package class051;

import java.util.PriorityQueue;

// 计算等位时间
// 给定一个数组arr长度为n，表示n个服务员，每服务一个人的时间
// 给定一个正数m，表示有m个人等位，如果你是刚来的人，请问你需要等多久？
// 假设：m远远大于n，比如n <= 1000, m <= 10的9次方，该怎么做？
// 谷歌的面试，这个题连考了2个月
// 没有找到测试链接，写了对数器验证
public class Code05_MinWaitingTime {

	// 堆模拟
	// 为了验证
	// 如果m很大，该方法会超时
	public static int minWaitingTime1(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			heap.add(new int[] { 0, arr[i] });
		}
		for (int i = 0; i < m; i++) {
			int[] cur = heap.poll();
			cur[0] += cur[1];
			heap.add(cur);
		}
		return heap.peek()[0];
	}

	// 二分答案（最优解）
	public static int minWaitingTime2(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		int best = Integer.MAX_VALUE;
		for (int num : arr) {
			best = Math.min(best, num);
		}
		int left = 0;
		int right = best * m;
		int mid = 0;
		int near = 0;
		while (left <= right) {
			mid = (left + right) / 2;
			int cover = 0;
			for (int num : arr) {
				cover += (mid / num) + 1;
			}
			if (cover >= m + 1) {
				near = mid;
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return near;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		System.out.println("测试开始");
		int N = 50;
		int V = 30;
		int M = 3000;
		int testTime = 20000;
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(n, V);
			int m = (int) (Math.random() * M);
			int ans1 = minWaitingTime1(arr, m);
			int ans2 = minWaitingTime2(arr, m);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
