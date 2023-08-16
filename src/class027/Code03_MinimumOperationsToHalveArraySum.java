package class027;

import java.util.PriorityQueue;

// 将数组和减半的最少操作次数
// 测试链接 : https://leetcode.cn/problems/minimum-operations-to-halve-array-sum/
public class Code03_MinimumOperationsToHalveArraySum {

	// 提交时把halveArray1改名为halveArray
	public static int halveArray1(int[] nums) {
		// 大根堆
		PriorityQueue<Double> heap = new PriorityQueue<>((a, b) -> b.compareTo(a));
		double sum = 0;
		for (int num : nums) {
			heap.add((double) num);
			sum += num;
		}
		// sum，整体累加和，-> 要减少的目标！
		sum /= 2;
		int ans = 0;
		for (double minus = 0, cur; minus < sum; ans++, minus += cur) {
			cur = heap.poll() / 2;
			heap.add(cur);
		}
		return ans;
	}

	public static int MAXN = 100001;

	public static long[] heap = new long[MAXN];

	public static int size;

	// 提交时把halveArray2改名为halveArray
	public static int halveArray2(int[] nums) {
		size = nums.length;
		long sum = 0;
		for (int i = size - 1; i >= 0; i--) {
			heap[i] = (long) nums[i] << 20;
			sum += heap[i];
			heapify(i);
		}
		sum /= 2;
		int ans = 0;
		for (long minus = 0; minus < sum; ans++) {
			heap[0] /= 2;
			minus += heap[0];
			heapify(0);
		}
		return ans;
	}

	public static void heapify(int i) {
		int l = i * 2 + 1;
		while (l < size) {
			int best = l + 1 < size && heap[l + 1] > heap[l] ? l + 1 : l;
			best = heap[best] > heap[i] ? best : i;
			if (best == i) {
				break;
			}
			swap(best, i);
			i = best;
			l = i * 2 + 1;
		}
	}

	public static void swap(int i, int j) {
		long tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}

}
