package class107;

import java.math.BigDecimal;
import java.math.RoundingMode;

// 有100个犯人被关在监狱里，编号0~99
// 监狱长构思了一个处决犯人的计划
// 监狱长准备了100个盒子，每个盒子里面装有一个犯人的名字
// 一开始每个犯人都知道自己的盒子在哪
// 因为自己的编号，与盒子的编号一致，
// 这100个盒子排成一排，放在一个房间里面，盒子编号0~99，从左到右排列
// 监狱长彻底随机的打乱了盒子的排列，并且犯人并没有看到打乱的过程
// 监狱长规定，每个犯人依次进入房间，每个犯人都可以开启50个盒子，然后关上
// 每一个犯人全程无法进行任何交流，完全无法传递任何信息
// 监狱长规定，每个犯人在尝试50次的过程中，都需要找到自己的名字
// 如果有哪怕一个犯人没有做到这一点，100个罪犯全部处决
// 但是监狱长允许这个游戏开始之前，所有犯人在一起商量策略
// 请尽量制定一个让所有人存活概率最大的策略
// 来自论文<The Cell Probe Complexity of Succinct Data Structures>
// 作者Anna Gal和Peter Bro Miltersen写于2007年
// 如今该题变成了流行题，还有大量科普视频
public class Code07_PrisonersEscapeGame {

	// 通过多次模拟实验得到的概率
	public static double escape1(int people, int tryTimes, int testTimes) {
		int escape = 0;
		for (int i = 0; i < testTimes; i++) {
			int[] arr = generateRandomArray(people);
			if (maxCircle(arr) <= tryTimes) {
				escape++;
			}
		}
		return (double) escape / (double) testTimes;
	}

	// 求arr中最大环的长度
	public static int maxCircle(int[] arr) {
		int maxCircle = 1;
		for (int i = 0; i < arr.length; i++) {
			int curCircle = 1;
			while (i != arr[i]) {
				swap(arr, i, arr[i]);
				curCircle++;
			}
			maxCircle = Math.max(maxCircle, curCircle);
		}
		return maxCircle;
	}

	// 生成随机arr
	// 原本每个位置的数都等概率出现在自己或者其他位置
	public static int[] generateRandomArray(int len) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = i;
		}
		for (int i = len - 1; i > 0; i--) {
			swap(arr, i, (int) (Math.random() * (i + 1)));
		}
		return arr;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 公式计算得到的概率
	// 一定要保证tryTimes大于等于people的一半，否则该函数失效
	// 导致死亡的情况数 : C(r,100) * (r-1)! * (100-r)!，r从51~100
	public static double escape2(int people, int tryTimes) {
		BigDecimal kill = new BigDecimal("0");
		for (int maxCircle = tryTimes + 1; maxCircle <= people; maxCircle++) {
			// 选出maxCircle个人组成最大环的选择方案数
			BigDecimal a = c(maxCircle, people);
			// 确保这maxCircle个人一定能组成一个大环
			// 而不是多个环的方法数
			BigDecimal b = factorial(maxCircle - 1);
			// 剩下人随意站形成的方法数
			BigDecimal c = factorial(people - maxCircle);
			kill = kill.add(a.multiply(b).multiply(c));
		}
		return 1D - kill.divide(factorial(people), 10, RoundingMode.HALF_UP).doubleValue();
	}

	// 组合公式
	// 从n个数里选a个数的方法数
	public static BigDecimal c(int a, int n) {
		BigDecimal all = factorial(n);
		BigDecimal down1 = factorial(a);
		BigDecimal down2 = factorial(n - a);
		return all.divide(down1).divide(down2);
	}

	// 返回number的阶乘
	public static BigDecimal factorial(int number) {
		BigDecimal ans = new BigDecimal("1");
		for (int i = 1; i <= number; i++) {
			ans = ans.multiply(new BigDecimal(i));
		}
		return ans;
	}

	// 公式化简之后的最终简洁版
	// 一定要保证tryTimes大于等于people的一半，否则该函数失效
	public static double escape3(int people, int tryTimes) {
		double a = 0;
		for (int r = tryTimes + 1; r <= people; r++) {
			a += (double) 1 / (double) r;
		}
		return (double) 1 - a;
	}

	public static void main(String[] args) {
		int people = 100;
		// 一定要保证tryTimes大于等于people的一半
		int tryTimes = 50;
		int testTimes = 100000;
		System.out.println("参与游戏的人数 : " + people);
		System.out.println("每人的尝试次数 : " + tryTimes);
		System.out.println("模拟实验的次数 : " + testTimes);
		System.out.println("通过模拟实验得到的概率为 : " + escape1(people, tryTimes, testTimes));
		System.out.println("通过公式计算得到的概率为 : " + escape2(people, tryTimes));
		System.out.println("通过化简公式得到的概率为 : " + escape3(people, tryTimes));
	}

}
