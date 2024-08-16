package class035;

import java.util.PriorityQueue;

// 快速获得数据流的中位数的结构
public class Code05_myMedianFinder {

	// 测试链接 : https://leetcode.cn/problems/find-median-from-data-stream/
	static class MedianFinder {

		private PriorityQueue<Integer> maxHeap;
		private PriorityQueue<Integer> minHeap;

		public MedianFinder() {
			// create a maxHeap to store the values that are smaller or equal to the root value
			// create a minHeap to store the values that are greater or equal to the root value
			maxHeap =  new PriorityQueue<>((a,b) -> b-a);
			minHeap = new PriorityQueue<>((a,b) -> a-b);
		}

		public void addNum(int num) {
			// 1- if maxHeap is empty or num is smaller than the root of maxHeap
			//    else put the value into the minHeap
			if(maxHeap.isEmpty() || maxHeap.peek() >= num){
				maxHeap.add(num);
			}else{
				minHeap.add(num);
			}
			// 2- balance two heaps to keep the root of the heaps stay in the median position
			balance();
		}

		public double findMedian() {
			if(maxHeap.size() == minHeap.size()){
				return (double) (maxHeap.peek() + minHeap.peek()) /2;
			}else{
				// retur the root of the greater size heap => the value must be the median
				return maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
			}
		}

		// balance if the tree differences ==2
		private void balance() {
			if(Math.abs(maxHeap.size() - minHeap.size() )== 2){
				if(minHeap.size() < maxHeap.size()){
					minHeap.add(maxHeap.poll());
				}else{
					maxHeap.add(minHeap.poll());
				}
			}
		}

		public static void main(String[] args) {
			MedianFinder medianFinder = new MedianFinder();
			medianFinder.addNum(-1);
			medianFinder.addNum(-2);
			medianFinder.addNum(-3);
			System.out.println(medianFinder.findMedian());
			System.out.println(-1 >= -2);
		}

	}

}
