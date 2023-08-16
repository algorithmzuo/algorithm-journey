package class014;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// 用栈实现队列
// 用队列实现栈
public class ConvertQueueAndStack {

	// 测试链接 : https://leetcode.cn/problems/implement-queue-using-stacks/
	class MyQueue {

		public Stack<Integer> in;

		public Stack<Integer> out;

		public MyQueue() {
			in = new Stack<Integer>();
			out = new Stack<Integer>();
		}

		// 倒数据
		// 从in栈，把数据倒入out栈
		// 1) out空了，才能倒数据
		// 2) 如果倒数据，in必须倒完
		private void inToOut() {
			if (out.empty()) {
				while (!in.empty()) {
					out.push(in.pop());
				}
			}
		}

		public void push(int x) {
			in.push(x);
			inToOut();
		}

		public int pop() {
			inToOut();
			return out.pop();
		}

		public int peek() {
			inToOut();
			return out.peek();
		}

		public boolean empty() {
			return in.isEmpty() && out.isEmpty();
		}

	}

	// 测试链接 : https://leetcode.cn/problems/implement-stack-using-queues/
	class MyStack {

		Queue<Integer> queue;

		public MyStack() {
			queue = new LinkedList<Integer>();
		}

		// O(n)
		public void push(int x) {
			int n = queue.size();
			queue.offer(x);
			for (int i = 0; i < n; i++) {
				queue.offer(queue.poll());
			}
		}

		public int pop() {
			return queue.poll();
		}

		public int top() {
			return queue.peek();
		}

		public boolean empty() {
			return queue.isEmpty();
		}

	}

}
