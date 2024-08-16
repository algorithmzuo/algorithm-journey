package class014;

import java.util.*;

// 用栈实现队列
// 用队列实现栈
public class myConvertQueueAndStack {

	// 测试链接 : https://leetcode.cn/problems/implement-queue-using-stacks/
	// convert stack to queues
	static class MyQueue {
		Stack<Integer> inStack;
		Stack<Integer> outStack;


		public MyQueue() {
			inStack = new Stack<>();
			outStack = new Stack<>();

		}

		// 倒数据
		// 从in栈，把数据倒入out栈
		// 1) out空了，才能倒数据
		// 2) 如果倒数据，in必须倒完
		private void inStackToOutStack() {
			int size = inStack.size();
			for (int i = 0; i < size; i++) {
				int x = inStack.pop();
				outStack.push(x);
			}

		}

		public void push(int x) {
			inStack.push(x);

		}

		public int pop() {
			if(!this.empty()){
				if(outStack.empty())
					inStackToOutStack();
				return outStack.pop();
			}
			return -1;
		}

		public int peek() {
			// 1- make sure there's elements in the stack
			if(!empty()){
				if(outStack.empty())
					inStackToOutStack();
				return outStack.peek();
			}
			return -1;

		}

		public boolean empty() {
			return inStack.isEmpty() && outStack.empty();
		}

	}
	// convert queue to stack
	// 测试链接 : https://leetcode.cn/problems/implement-stack-using-queues/
	class MyStack {

		Queue<Integer> queue;
		public MyStack() {
			queue = new LinkedList<>();
		}

		// O(n)
		public void push(int x) {
			int size = queue.size();
			queue.offer(x);
			for (int i = 0 ; i < size ;i++){
				queue.offer(queue.poll());
			}

		}

		public int pop() {
			return queue.poll();
		}

		public int top(){
			return queue.peek();
		}

		public boolean empty() {
			return queue.isEmpty();
		}

	}

	public static void main(String[] args) {
		MyQueue queue = new MyQueue();
		queue.push(1);
		queue.push(2);
		queue.push(3);
		queue.pop();

	}

}
