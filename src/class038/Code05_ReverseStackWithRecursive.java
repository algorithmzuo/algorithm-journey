package class038;

import java.util.Stack;

// 用递归函数逆序栈
// 1:06:54 ~ 1:15:49
public class Code05_ReverseStackWithRecursive {

    public static void reverse1(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int num = bottomOut(stack);
        reverse1(stack);
        stack.push(num);
    }

    // 栈底元素移除掉，上面的元素盖下来
    // 返回移除掉的栈底元素
    public static int bottomOut(Stack<Integer> stack) {
        int ans = stack.pop();
        if (stack.isEmpty()) {
            return ans;
        } else {
            int last = bottomOut(stack);
            stack.push(ans);
            return last;
        }
    }

    // form chatGPT
    // 递归函数用于逆序栈
    public static void reverse2(Stack<Integer> stack) {
        if (!stack.isEmpty()) {
            int temp = stack.pop(); // 取出栈顶元素
            reverse2(stack);    // 递归地逆序剩余的栈
            insertAtBottom(stack, temp); // 将取出的元素插入到栈底
        }
    }

    // 辅助函数，将元素插入到栈底
    public static void insertAtBottom(Stack<Integer> stack, int data) {
        if (stack.isEmpty()) {
            stack.push(data);
        } else {
            int temp = stack.pop();
            insertAtBottom(stack, data);
            stack.push(temp);
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(5);
        stack.push(4);
        stack.push(3);
        stack.push(2);
        stack.push(1);
        reverse1(stack);
        reverse2(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

}
