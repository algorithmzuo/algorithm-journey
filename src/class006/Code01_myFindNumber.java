package class006;

import java.util.Arrays;

// 有序数组中是否存在一个数字
public class Code01_myFindNumber {

	// 为了验证
	public static void main(String[] args) {
		int N = 10;
		int V = 1000;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N);
			int[] arr = randomArray(n, V);
			Arrays.sort(arr);
			int num = (int) (Math.random() * V);
			if (right(arr, num) != exist(arr, num)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");

		int[] arr = {1,2,3,4,5};
		System.out.println(exist(arr,5));
	}

	// 为了验证
	public static int[] randomArray(int n, int v) {
		int[] arr= new int[n];
		for (int i = 0; i < arr.length ; i++) {
			arr[i] =  (int)(Math.random() * v) +1;
		}
		return arr;
	}

	// 为了验证
	// 保证arr有序，才能用这个方法
	public static boolean right(int[] sortedArr, int num) {
		for (int curNum: sortedArr) {
			if(curNum == num){
				return true;
			}
		}
		return false;
	}

	// 保证arr有序，才能用这个方法
	public static boolean exist(int[] arr, int num) {
		if(arr == null || arr.length ==0){
			return false;
		}
		int L = 0;
		int R = arr.length - 1;
		while(L<=R){
			int M = L + ((R - L) >> 1);
			if(arr[M] == num){
				return true;
			}else if (num < arr[M]){
				R = M -1;
			}else{
				L = M +1;
			}
		}
		return false;

	}

}
