package class136;

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
			boolean pick = false;
			for (int j = m; j >= 0; j--) {
				if (arr[i] >> j == 1) {
					if (basis1[j] == 0) {
						basis1[j] = arr[i];
						pick = true;
						break;
					}
					arr[i] ^= basis1[j];
				}
			}
			if (!pick) {
				zero1 = true;
			}
		}
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
	}

}
