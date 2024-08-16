package class015;

import java.util.Arrays;
import java.util.Stack;

// 最小栈
// 测试链接 : https://leetcode.cn/problems/min-stack/
public class myGetMinStack {

	// 提交时把类名、构造方法改成MinStack
	static class MinStack1 {
		Stack<Integer> data;
		Stack<Integer> min;
		public MinStack1() {
			data = new Stack<>();
			min = new Stack<>();
		}

		public void push(int val) {
			data.push(val);
			if(min.isEmpty() || val <= min.peek()){
				min.push(val);
			}else{
				min.push(min.peek());
			}
		}

		public void pop() {
			data.pop();
			min.pop();
		}

		public int top() {
			return data.peek();
		}

		public int getMin() {
			return  min.peek();
		}
	}

	// 提交时把类名、构造方法改成MinStack
	static class MinStack2 {
		// leetcode的数据在测试时，同时在栈里的数据不超过这个值
		// 这是几次提交实验出来的，哈哈
		// 如果leetcode补测试数据了，超过这个量导致出错，就调大

		public int[] data;
		public int[] min;
		public int size;
		private static final int MAX_SIZE = 5000;
		public MinStack2() {
			data = new int[MAX_SIZE];
			min = new int[MAX_SIZE];
			size = 0;
		}

		public void push(int val) {
			data[size] = val;
			if(size == 0 || val <= min[size-1]){
				min[size] = val;
			}else{
				min[++size] =  min[size -1];
			}
		}

		public void pop() {
			size--;

		}

		public int top() {
			return data[size -1];
		}

		public int getMin() {
			return min[size -1];
		}
	}

	public static void main(String[] args) {
/*
		MinStack2 stack = new MinStack2();
		stack.push(1);
		stack.push(3);
		System.out.println(Arrays.toString(stack.data));
		System.out.println(Arrays.toString(stack.min));
*/
		int size =1;
		int[] nums = {1,2,3,4};
		int[] nums1 = {0,0,0,0};
		int size1 =1;
		// nums[1] = nums[0] = 1  => size = 2
		nums[size++] = nums[size-1];
		System.out.println(Arrays.toString(nums));
		// nums[] = nums[]
		System.out.println(Arrays.toString(nums1));
		// size1 => 2 nums1[1]
		nums1[(++size1)] = nums[size1-1];
		System.out.println(Arrays.toString(nums1));
		System.out.println(size1);

 	}

}
