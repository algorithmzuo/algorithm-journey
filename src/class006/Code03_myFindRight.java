package class006;

import java.util.Arrays;

// 有序数组中找<=num的最右位置
public class Code03_myFindRight {

	// 为了验证
	public static void main(String[] args) {
		int N = 5;
		int V = 1000;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N);
			int[] arr = randomArray(n, V);
			Arrays.sort(arr);
			int num = (int) (Math.random() * N);
			if (right(arr, num) != findRight(arr, num)) {
				System.out.println("出错了!");
				System.out.println(Arrays.toString(arr));
				System.out.println(num);
				System.out.println(right(arr,num)+"-" + findRight(arr,num));
				break;
			}
		}
		System.out.println("测试结束");
	}

	// 为了验证
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了验证
	// 保证arr有序，才能用这个方法
	public static int right(int[] arr, int num) {
		for (int i = arr.length - 1; i >= 0; i--) {
			if (arr[i] <= num) {
				return i;
			}
		}
		return -1;
	}

	// 保证arr有序，才能用这个方法
	// 有序数组中找<=num的最右位置
	public static int findRight(int[] arr, int num) {
		int L = 0, R =arr.length -1 , M= 0;
		int ans = -1;
		while(L  <= R){
			M = L + ((R-L)>>1);
			if(arr[M] <=num ){
				L = M + 1;
				ans = M;
			}else{
				R = M - 1;
			}
		}
		return ans;
	}

}
