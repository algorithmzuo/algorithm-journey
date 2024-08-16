
package class038;

import java.util.Stack;

// 用递归函数逆序栈
public class Code05_myReverseStackWithRecursive {

	public static void reverse(Stack<Integer> stack) {
		if(stack.size() ==1){
			return;
		}
		int val = bottomOut(stack);
		reverse(stack);
		stack.push(val);
	}

	public static int bottomOut(Stack<Integer> stack){
		if(stack.size() ==1){
			return stack.pop();
		}
		int topVal =  stack.pop();
		int ans = bottomOut(stack);
		stack.push(topVal);
		return ans;
	}

	// 栈底元素移除掉，上面的元素盖下来
	// 返回移除掉的栈底元素


	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		System.out.println("---------");

		while (!stack.isEmpty()) {
			System.out.println(stack.pop());
		}
	}

}
