package class038;

import java.util.Stack;

// 用递归函数排序栈
// 栈只提供push、pop、isEmpty三个方法
// 请完成无序栈的排序，要求排完序之后，从栈顶到栈底从小到大
// 只能使用栈提供的push、pop、isEmpty三个方法、以及递归函数
// 除此之外不能使用任何的容器，数组也不行
// 就是排序过程中只能用：
// 1) 栈提供的push、pop、isEmpty三个方法
// 2) 递归函数，并且返回值最多为单个整数
public class Code06_mySortStackWithRecursive {

	public static void sort(Stack<Integer> stack) {
		if(stack.isEmpty()){
			return;
		}
		int depth = getDepth(stack);
		while(depth > 0){
			int max = getMaxValue(stack, depth);
			int maxCount = getMaxCount(stack,depth, max);
			down(stack,depth,max,maxCount);
			depth-= maxCount;
		}


	}

	// 返回栈的深度
	// 不改变栈的数据状况
	public static int getDepth(Stack<Integer> stack) {
		if(stack.isEmpty()){
			return 0;
		}
		int topVal = stack.pop();
		int depth = getDepth(stack);
		stack.push(topVal);
		return depth + 1;

	}

	// 从栈当前的顶部开始，往下数deep层
	// 返回这deep层里的最大值
	public static int getMaxValue(Stack<Integer> stack, int deep) {
		if(deep == 0){
			return Integer.MIN_VALUE;
		}
		int topVal = stack.pop();
		int max = Math.max(getMaxValue(stack, deep -1) , topVal);
		stack.push(topVal);
		return max;
	}

	// 从栈当前的顶部开始，往下数deep层，已知最大值是max了
	// 返回，max出现了几次，不改变栈的数据状况
	public static int getMaxCount(Stack<Integer> stack, int deep, int max) {
		if(deep == 0){
			return 0;
		}
		int topVal = stack.pop();
		int cnt = getMaxCount(stack, deep -1 , max);
		stack.push(topVal);
		return (topVal == max) ? ++cnt : cnt;
	}

	// 从栈当前的顶部开始，往下数deep层，已知最大值是max，出现了k次
	// 请把这k个最大值沉底，剩下的数据状况不变
	public static void down(Stack<Integer> stack, int deep, int max, int k) {
		if(deep ==0){
			for (int i = 0; i < k; i++) {
				stack.push(max);
			}
			return;
		}
		int topVal = stack.pop();
		down(stack, deep -1 , max, k);
		if(topVal != max){
			stack.push(topVal);
		}
	}

	// 为了测试
	// 生成随机栈
	public static Stack<Integer> randomStack(int n, int v) {
		Stack<Integer> ans = new Stack<Integer>();
		for (int i = 0; i < n; i++) {
			ans.add((int) (Math.random() * v));
		}
		return ans;
	}

	// 为了测试
	// 检测栈是不是从顶到底依次有序
	public static boolean isSorted(Stack<Integer> stack) {
		int step = Integer.MIN_VALUE;
		while (!stack.isEmpty()) {
			if (step > stack.peek()) {
				return false;
			}
			step = stack.pop();
		}
		return true;
	}

	// 为了测试
	public static void main(String[] args) {
		Stack<Integer> test = new Stack<Integer>();
		test.add(1);
		test.add(5);
		test.add(4);
		test.add(5);
		test.add(3);
		test.add(2);
		test.add(3);
		test.add(1);
		test.add(4);
		test.add(2);
		sort(test);
		while (!test.isEmpty()) {
			System.out.print(test.pop()+" ");
		}
		System.out.println("\n-------------");
		// 随机测试
		int N = 20;
		int V = 20;
		int testTimes = 20000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N);
			Stack<Integer> stack = randomStack(n, V);
			sort(stack);
			if (!isSorted(stack)) {
				System.out.println("出错了!");
				break;
			}
		}
		System.out.println("测试结束");
	}
//	public static void main(String[] args) {
//		Stack<Integer> stack = new Stack<>();
//		stack.push(1);
//		stack.push(2);
//		stack.push(2);
//		down(stack,3,2,2);
//
//		while(!stack.isEmpty()){
//			System.out.print(stack.pop()+" ");
//		}
//
//	}

}
