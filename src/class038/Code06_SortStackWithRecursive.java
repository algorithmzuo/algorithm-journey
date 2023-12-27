package class038;

import test.Performance_test;

import java.util.Stack;

// 用递归函数排序栈
// 栈只提供push、pop、isEmpty三个方法
// 请完成无序栈的排序，要求排完序之后，从栈顶到栈底从小到大
// 只能使用栈提供的push、pop、isEmpty三个方法、以及递归函数
// 除此之外不能使用任何的容器，数组也不行
// 就是排序过程中只能用：
// 1) 栈提供的push、pop、isEmpty三个方法
// 2) 递归函数，并且返回值最多为单个整数
// 1:16:01 ~ 1:42:17
public class Code06_SortStackWithRecursive {

    public static void sort2(Stack<Integer> stack) {
        int deep = deep(stack);
        while (deep > 0) {
            int max = max(stack, deep);
            int k = times(stack, deep, max);
            down(stack, deep, max, k);
            deep -= k;
        }
    }

    // 返回栈的深度
    // 不改变栈的数据状况
    public static int deep(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        int num = stack.pop();
        int deep = deep(stack) + 1;
        stack.push(num);
        return deep;
    }

    // 从栈当前的顶部开始，往下数deep层
    // 返回这deep层里的最大值
    public static int max(Stack<Integer> stack, int deep) {
        if (deep == 0) {
            return Integer.MIN_VALUE;
        }
        int num = stack.pop();
        int restMax = max(stack, deep - 1);
        int max = Math.max(num, restMax);
        stack.push(num);
        return max;
    }

    // 从栈当前的顶部开始，往下数deep层，已知最大值是max了
    // 返回，max出现了几次，不改变栈的数据状况
    public static int times(Stack<Integer> stack, int deep, int max) {
        if (deep == 0) {
            return 0;
        }
        int num = stack.pop();
        int restTimes = times(stack, deep - 1, max);
        int times = restTimes + (num == max ? 1 : 0);
        stack.push(num);
        return times;
    }

    // 从栈当前的顶部开始，往下数deep层，已知最大值是max，出现了k次
    // 请把这k个最大值沉底，剩下的数据状况不变
    public static void down(Stack<Integer> stack, int deep, int max, int k) {
        if (deep == 0) {
            for (int i = 0; i < k; i++) {
                stack.push(max);
            }
        } else {
            int num = stack.pop();
            down(stack, deep - 1, max, k);
            if (num != max) {
                stack.push(num);
            }
        }
    }

    // from chatGPT
    // 递归函数用于对栈进行排序
    public static void sort1(Stack<Integer> stack) {
        if (!stack.isEmpty()) {
            int temp = stack.pop(); // 取出栈顶元素
            sort1(stack); // 递归地对剩余的栈排序
            insertSorted(stack, temp); // 将取出的元素插入到已排序的栈中
        }
    }

    // 辅助函数，将元素插入到已排序的栈中
    public static void insertSorted(Stack<Integer> stack, int data) {
        if (stack.isEmpty() || data < stack.peek()) {
            stack.push(data);
        } else {
            int temp = stack.pop();
            insertSorted(stack, data);
            stack.push(temp);
        }
    }

    // 为了测试
    // 生成随机栈
    public static Stack<Integer> randomStack(int n, int v) {
        Stack<Integer> ans = new Stack<>();
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
        sort1(test);
        while (!test.isEmpty()) {
            System.out.print(test.pop() + " ");
        }

        // 随机测试
        int N = 20, V = 20, testTimes = 33333;
        System.out.println();
        Performance_test Test = new Performance_test();
        Performance_test.info info = Test.initializeInfo(2);
        boolean success = true;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int n = (int) (Math.random() * N);
            Stack<Integer> stack1 = randomStack(n, V);
            Stack<Integer> stack2 = randomStack(n, V);

            sort1(stack1);
            sort2(stack2);

            info.time[1] += Test.runTime(() -> sort1(stack1));
            info.time[2] += Test.runTime(() -> sort2(stack2));

            info.memory[1] += Test.memoryConsumption(() -> sort1(stack1));
            info.memory[2] += Test.memoryConsumption(() -> sort2(stack2));

            if (!isSorted(stack1) && !isSorted(stack2)) {
                success = false;
                info.ac = i;
                System.out.println("Oops!");
                break;
            }
        }
        if (success) {
            Test.printInfo();
        } else {
            info.print();
        }
        System.out.println("测试结束");
    }

}
