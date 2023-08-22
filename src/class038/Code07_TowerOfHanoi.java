package class038;

import java.util.Stack;

// 打印n层汉诺塔问题的最优移动轨迹
// 1:42:17 ~ 结束
public class Code07_TowerOfHanoi {

    public static void hanoiRecursive(int n) {
        if (n > 0) {
            func(n, "左", "右", "中");
        }
    }

    public static void func(int i, String from, String via, String to) {
        if (i == 1) {
            System.out.println("移动圆盘 1 从 " + from + " 到 " + via);
            return;
        }
        func(i - 1, from, via, to);
        System.out.println("移动圆盘 " + i + " 从 " + from + " 到 " + via);
        func(i - 1, via, from, to);
    }

    public static void main(String[] args) {
        int n = 3;
        hanoiRecursive(n);
        System.out.println("----");
        hanoiRecursive(n);
    }

    public static void hanoiIterative(int n, char source, char auxiliary, char target) {
        // 创建三个栈，用于模拟汉诺塔的三个柱子
        Stack<Integer> sourceStack = new Stack<>();
        Stack<Integer> auxiliaryStack = new Stack<>();
        Stack<Integer> targetStack = new Stack<>();

        // 初始化源柱子
        for (int i = n; i >= 1; i--) {
            sourceStack.push(i);
        }

        char temp;
        if (n % 2 == 0) {
            // 如果盘子数为偶数，交换辅助柱子和目标柱子
            temp = auxiliary;
            auxiliary = target;
            target = temp;
        }

        int totalMoves = (int) (Math.pow(2, n) - 1); // 计算总共需要的移动次数

        for (int i = 1; i <= totalMoves; i++) {
            if (i % 3 == 1) {
                moveDisksBetweenPoles(sourceStack, targetStack, source, target);
            } else if (i % 3 == 2) {
                moveDisksBetweenPoles(sourceStack, auxiliaryStack, source, auxiliary);
            } else if (i % 3 == 0) {
                moveDisksBetweenPoles(auxiliaryStack, targetStack, auxiliary, target);
            }
        }
    }

    public static void moveDisksBetweenPoles(Stack<Integer> source, Stack<Integer> target, char s, char t) {
        int topDisk;
        if (!source.isEmpty()) {
            topDisk = source.pop();
            System.out.println("移动盘子 " + topDisk + " 从 " + s + " 到 " + t);
            target.push(topDisk);
        }
    }


}
