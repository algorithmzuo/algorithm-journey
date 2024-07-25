package class136;

import java.util.Arrays;

public class ShowDetails {

	public static int MAXN = 101;

	public static int MAXM = 64;

	public static long[] arr = new long[MAXN];

	public static int n, m;

	public static void maxbit() {
		long max = arr[1];
		for (int i = 2; i <= n; i++) {
			max = Math.max(max, arr[i]);
		}
		m = 0;
		while ((max >> (m + 1)) != 0) {
			m++;
		}
	}

	public static long[] basis1 = new long[MAXM];

	public static boolean zero1;

	// 普通消元
	public static void compute1() {
		for (int i = 1; i <= n; i++) {
			if (!insert(arr[i])) {
				zero1 = true;
			}
		}
	}

	// 线性基里插入num
	// 如果线性基增加了，返回true，否则返回false
	public static boolean insert(long num) {
		for (int i = m; i >= 0; i--) {
			if (num >> i == 1) {
				if (basis1[i] == 0) {
					basis1[i] = num;
					return true;
				}
				num ^= basis1[i];
			}
		}
		return false;
	}

	public static long[] basis2 = new long[MAXN];

	public static int len;

	public static boolean zero2;

	// 高斯消元
	// 因为不需要维护主元和自由元的依赖关系
	// 所以高斯消元的写法可以得到简化
	public static void compute2() {
		len = 1;
		for (long bit = 1L << m; bit != 0; bit >>= 1) {
			for (int i = len; i <= n; i++) {
				if ((basis2[i] & bit) != 0) {
					swap(i, len);
					break;
				}
			}
			if ((basis2[len] & bit) != 0) {
				for (int i = 1; i <= n; i++) {
					if (i != len && (basis2[i] & bit) != 0) {
						basis2[i] ^= basis2[len];
					}
				}
				len++;
			}
		}
		len--;
		zero2 = len != n;
	}

	public static void swap(int a, int b) {
		long tmp = basis2[a];
		basis2[a] = basis2[b];
		basis2[b] = tmp;
	}

	public static void main(String[] args) {
		Arrays.fill(basis1, 0);
		Arrays.fill(basis2, 0);
		arr[1] = basis2[1] = 15;
		arr[2] = basis2[2] = 13;
		arr[3] = basis2[3] = 8;
		n = 3;
		maxbit();

		System.out.println("普通消元");
		compute1();
		for (int i = m; i >= 0; i--) {
			if (basis1[i] != 0) {
				System.out.print(basis1[i] + " ");
			}
		}
		System.out.println();
		System.out.println("是否能异或出0 : " + zero1);

		System.out.println("===================");

		System.out.println("高斯消元");
		compute2();
		for (int i = 1; i <= len; i++) {
			System.out.print(basis2[i] + " ");
		}
		System.out.println();
		System.out.println("是否能异或出0 : " + zero2);

		System.out.println();
		System.out.println();
		System.out.println();

		// 如果想得到第k小的异或和
		// 必须用高斯消元，不能用普通消元，比如
		// arr = { 7, 10, 4 }
		// 普通消元得到的异或空间线性基是 : 10 7 3
		// 第三小异或和是4，第四小异或和是10，这是错误的
		// 高斯消元可以得到正确结果
		// 高斯消元得到的异或空间线性基是 : 9 4 3
		// 第三小异或和是7，第四小异或和是9，这是正确的
		Arrays.fill(basis1, 0);
		Arrays.fill(basis2, 0);
		arr[1] = basis2[1] = 7;
		arr[2] = basis2[2] = 10;
		arr[3] = basis2[3] = 4;
		n = 3;
		maxbit();

		System.out.println("普通消元");
		compute1();
		for (int i = m; i >= 0; i--) {
			if (basis1[i] != 0) {
				System.out.print(basis1[i] + " ");
			}
		}
		System.out.println();
		System.out.println("是否能异或出0 : " + zero1);

		System.out.println("===================");

		System.out.println("高斯消元");
		compute2();
		for (int i = 1; i <= len; i++) {
			System.out.print(basis2[i] + " ");
		}
		System.out.println();
		System.out.println("是否能异或出0 : " + zero2);
	}

}
