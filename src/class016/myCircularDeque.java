package class016;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

// 设计循环双端队列
// 测试链接 : https://leetcode.cn/problems/design-circular-deque/
public class myCircularDeque {

	// 提交时把类名、构造方法改成 : MyCircularDeque
	// 其实内部就是双向链表
	// 常数操作慢，但是leetcode数据量太小了，所以看不出劣势
	class MyCircularDeque1 {

		public Deque<Integer> deque = new LinkedList<>();
		public int size;
		public int limit;

		public MyCircularDeque1(int k) {
			size = 0;
			limit = k;
		}

		public boolean insertFront(int value) {
			if (isFull()) {
				return false;
			} else {
				deque.offerFirst(value);
				size++;
				return true;
			}
		}

		public boolean insertLast(int value) {
			if (isFull()) {
				return false;
			} else {
				deque.offerLast(value);
				size++;
				return true;
			}
		}

		public boolean deleteFront() {
			if (isEmpty()) {
				return false;
			} else {
				size--;
				deque.pollFirst();
				return true;
			}
		}

		public boolean deleteLast() {
			if (isEmpty()) {
				return false;
			} else {
				size--;
				deque.pollLast();
				return true;
			}
		}

		public int getFront() {
			if (isEmpty()) {
				return -1;
			} else {
				return deque.peekFirst();
			}
		}

		public int getRear() {
			if (isEmpty()) {
				return -1;
			} else {
				return deque.peekLast();
			}
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public boolean isFull() {
			return size == limit;
		}

	}

	// 提交时把类名、构造方法改成 : MyCircularDeque
	// 自己用数组实现，常数操作快，但是leetcode数据量太小了，看不出优势
	static class MyCircularDeque2 {

		public int[] deque;
		public int l, r, size, limit;

		public MyCircularDeque2(int k) {
			deque = new int[k];
			l = k - 1;
			r =  size = 0;
			limit = k;
		}

		public boolean insertFront(int value) {
			if(isFull()){
				return false;
			}
			deque[l] = value;
			l  = l == 0 ?  limit - 1: l-1;
			size++;
			return true;
		}

		public boolean deleteFront() {
			if(isEmpty()){
				return false;
			}
			l = l == limit -1 ? 0 : l+1;
			size--;
			return true;
		}
		public boolean insertLast(int value) {
			if(isFull()){
				return false;
			}
			deque[r] = value;
			r = r == limit -1 ? 0 : r+1;
			size++;
			return true;
		}

		public boolean deleteLast() {
			if(isEmpty()){
				return false;
			}
			r = r == 0 ? limit - 1 : r -1;
			size--;
			return true;
		}

		public int getFront() {
			if(isEmpty()){
				return -1;
			}
			return l == limit -1 ? deque[0] : deque[l+1];
		}

		public int getRear() {
			if(isEmpty()){
				return  -1;
			}
			return r == 0 ? deque[limit -1] : deque[r-1];

		}

		public boolean isEmpty() {
			return size == 0;
		}

		public boolean isFull() {
			return size == limit;
		}

	}

	public static void main(String[] args) {
		MyCircularDeque2 queue = new MyCircularDeque2(5);
		queue.insertFront(1);
		queue.insertFront(2);
		queue.insertFront(3);
		queue.insertLast(4);
		System.out.println(Arrays.toString(queue.deque));
		queue.deleteFront();
		System.out.println(Arrays.toString(queue.deque));

	}

}
