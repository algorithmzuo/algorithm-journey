package class027;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// 将数组和减半的最少操作次数
// 测试链接 : https://leetcode.cn/problems/minimum-operations-to-halve-array-sum/
public class Code03_myMinimumOperationsToHalveArraySum {

	// 提交时把halveArray1改名为halveArray
	public static int halveArray1(int[] nums) {
		PriorityQueue<Double> heap = new PriorityQueue<>((o1,o2)->(o2.compareTo(o1)));
		double sum = 0;
		int ans = 0;
		for (int num:nums) {
			heap.add((double) num);
			sum += num;
		}
		sum /= 2;
		for(double min = 0; min<sum; ans++){
			double cur_val = heap.poll()/2;
			min+=cur_val;
			heap.add(cur_val);
		}
		return ans;
	}

	public static int MAXN = 10;

	public static long[] heap = new long[MAXN];

	public static int size;

	// 提交时把halveArray2改名为halveArray
	public static int halveArray2(int[] nums) {
		size =  nums.length;
		long sum = 0;
		int ans = 0;
		for (int i = nums.length -1 ; i >=0 ; i--) {
			heap[i] = (long) nums[i] << 20;
			sum += heap[i];
			heapify(i);
		}
		sum /= 2;

		for(long curSum = 0; curSum < sum; ans++ ){
			heap[0] /= 2;
			curSum +=  heap[0];
			heapify(0);
		}
		return ans;
	}

	public static void heapify(int i) {
		int l = i * 2 + 1;
		while(l< size){
			int bestIndex = l + 1 < size && heap[l+1] > heap[l] ? l+1 : l;
			bestIndex = heap[bestIndex] > heap[i] ? bestIndex : i;
			if(i == bestIndex){
				break;
			}
			swap(bestIndex, i);
			i = bestIndex;
			l =  i * 2 +1;
		}
	}

	public static void swap(int i, int j) {
		long tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}

	public static void main(String[] args) {
		int[] nums = new int[]{1,5,3,4,2,6,8};
		int result = halveArray2(nums);
	}

}
