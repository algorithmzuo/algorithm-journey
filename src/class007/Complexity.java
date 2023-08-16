package class007;

import java.util.ArrayList;

public class Complexity {

	// 只用一个循环完成冒泡排序
	// 但这是时间复杂度O(N^2)的！
	public static void bubbleSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		int n = arr.length;
		int end = n - 1, i = 0;
		while (end > 0) {
			if (arr[i] > arr[i + 1]) {
				swap(arr, i, i + 1);
			}
			if (i < end - 1) {
				i++;
			} else {
				end--;
				i = 0;
			}
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public static void main(String[] args) {
		// 随机生成长度为n
		// 值在0~v-1之间
		// 且任意相邻两数不相等的数组
		int n = 10;
		int v = 4;
		int[] arr1 = new int[n];
		arr1[0] = (int) (Math.random() * v);
		for (int i = 1; i < n; i++) {
			do {
				arr1[i] = (int) (Math.random() * v);
			} while (arr1[i] == arr1[i - 1]);
		}
		for (int num : arr1) {
			System.out.print(num + " ");
		}
		System.out.println();
		System.out.println("=========");

		// java中的动态数组是ArrayList
		// 各个语言中的动态数组的初始大小和实际扩容因子可能会变化，但是均摊都是O(1)
		// 课上用2作为扩容因子只是举例而已
		ArrayList<Integer> arr2 = new ArrayList<>();
		arr2.add(5); // 0
		arr2.add(4); // 1
		arr2.add(9); // 2
		arr2.set(1, 6); // arr[1]由4改成了6
		System.out.println(arr2.get(1));
		System.out.println("=========");

		int[] arr = { 64, 31, 78, 0, 5, 7, 103 };
		bubbleSort(arr);
		for (int num : arr) {
			System.out.print(num + " ");
		}
		System.out.println();
		System.out.println("=========");

		int N = 200000;
		long start;
		long end;
		System.out.println("测试开始");
		start = System.currentTimeMillis();
		for (int i = 1; i <= N; i++) {
			for (int j = i; j <= N; j += i) {
				// 这两个嵌套for循环的流程，时间复杂度为O(N * logN)
				// 1/1 + 1/2 + 1/3 + 1/4 + 1/5 + ... + 1/n，也叫"调和级数"，收敛于O(logN)
				// 所以如果一个流程的表达式 : n/1 + n/2 + n/3 + ... + n/n
				// 那么这个流程时间复杂度O(N * logN)
			}
		}
		end = System.currentTimeMillis();
		System.out.println("测试结束，运行时间 : " + (end - start) + " 毫秒");

		System.out.println("测试开始");
		start = System.currentTimeMillis();
		for (int i = 1; i <= N; i++) {
			for (int j = i; j <= N; j++) {
				// 这两个嵌套for循环的流程，时间复杂度为O(N^2)
				// 很明显等差数列
			}
		}
		end = System.currentTimeMillis();
		System.out.println("测试结束，运行时间 : " + (end - start) + " 毫秒");

	}

}
