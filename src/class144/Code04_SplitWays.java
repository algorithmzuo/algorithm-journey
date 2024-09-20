package class144;

// 分割的方法数
// 给定一个长度为n的数组A, 将其分割成数组B和数组C，满足A[i] = B[i] + C[i]
// 也就是一个数字分成两份，然后各自进入B和C，要求B[i], C[i] >= 1
// 同时要求，B数组从左到右不能降序，C数组从左到右不能升序
// 比如，A = { 5, 4, 5 }，一种有效的划分，B = { 2, 2, 3 }，C = { 3, 2, 2 }
// 返回有多少种有效的划分方式
// 1 <= n <= 10^7
// 1 <= A[i] <= 10^7
// 最终结果可能很大，对1000000007取余
// 来自真实大厂笔试题，对数器验证
public class Code04_SplitWays {

	// 暴力方法
	// 为了验证
	public static int ways1(int[] arr) {
		int ans = 0;
		for (int a = 1, b = arr[0] - 1; a < arr[0]; a++, b--) {
			ans += f(arr, 1, a, b);
		}
		return ans;
	}

	public static int f(int[] arr, int i, int ip, int dp) {
		if (i == arr.length) {
			return 1;
		}
		int ans = 0;
		for (int a = 1, b = arr[i] - 1; a < arr[i]; a++, b--) {
			if (ip <= a && dp >= b) {
				ans += f(arr, i + 1, a, b);
			}
		}
		return ans;
	}

	// 正式方法
	// 转化成杨辉三角形
	public static int ways2(int[] arr) {
		int n = arr.length;
		int k = arr[0] - 1;
		for (int i = 1; i < n && k > 0; i++) {
			if (arr[i - 1] > arr[i]) {
				k -= arr[i - 1] - arr[i];
			}
		}
		if (k <= 0) {
			return 0;
		}
		return c(k - 1 + n, n);
	}

	// 组合公式
	public static int c(int n, int r) {
		int mod = 1000000007;
		long up = 1;
		long inv1 = 1;
		long inv2 = 1;
		for (int i = 1; i <= n; i++) {
			up = (up * i) % mod;
			if (i == r) {
				inv1 = power(up, mod - 2, mod);
			}
			if (i == n - r) {
				inv2 = power(up, mod - 2, mod);
			}
		}
		return (int) ((((up * inv1) % mod) * inv2) % mod);
	}

	public static long power(long x, long p, long mod) {
		long ans = 1;
		while (p > 0) {
			if ((p & 1) == 1) {
				ans = (ans * x) % mod;
			}
			x = (x * x) % mod;
			p >>= 1;
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v) + 1;
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		// 展示一下pascalTriangleModulus的用法
		System.out.println("打印部分杨辉三角形");
		for (int n = 0; n <= 10; n++) {
			for (int r = 0; r <= n; r++) {
				System.out.print(c(n, r) + " ");
			}
			System.out.println();
		}
		int N = 10;
		int V = 20;
		int testTimes = 20000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(n, V);
			int ans1 = ways1(arr);
			int ans2 = ways2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");
		int n = 10000000;
		int v = 10000000;
		long start, end;
		int[] arr = new int[n];
		System.out.println("随机生成的数据测试 : ");
		System.out.println("数组长度 : " + n);
		System.out.println("数值范围 : [" + 1 + "," + v + "]");
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}

		start = System.currentTimeMillis();
		ways2(arr);
		end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");

		System.out.println("运行最慢的数据测试 : ");
		System.out.println("数组长度 : " + n);
		System.out.println("数值都是 : " + v);
		System.out.println("这种情况其实是复杂度最高的情况");
		for (int i = 0; i < n; i++) {
			arr[i] = v;
		}
		start = System.currentTimeMillis();
		ways2(arr);
		end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");
		System.out.println("性能测试结束");
	}

}
