package class144;

// 分割的方法数
// 给定一个长度为n的数组A, 将其分割成数组B和数组C，满足A[i] = B[i] + C[i]
// 也就是一个数字分成两份，然后各自进入B和C，要求B[i], C[i] >= 1
// 同时要求，B数组从左到右不能降序，C数组从左到右不能升序
// 比如，A = { 5, 4, 5 }，一种有效的划分，B = { 2, 2, 3 }，C = { 3, 2, 2 }
// 返回有多少种有效的划分方式
// 1 <= n <= 10^7
// 1 <= A[i] <= 10^7
// 最终结果可能很大，答案对 1000000007 取模
// 来自真实大厂笔试题，该实现为对数器版本
// 有同学找到了测试链接，就是Code04_SplitWays2文件
public class Code04_SplitWays1 {

	// 暴力方法
	// 为了验证
	public static int ways1(int[] arr) {
		int ans = 0;
		for (int b = 1, c = arr[0] - 1; b < arr[0]; b++, c--) {
			ans += f(arr, 1, b, c);
		}
		return ans;
	}

	public static int f(int[] arr, int i, int preb, int prec) {
		if (i == arr.length) {
			return 1;
		}
		int ans = 0;
		for (int b = 1, c = arr[i] - 1; b < arr[i]; b++, c--) {
			if (preb <= b && prec >= c) {
				ans += f(arr, i + 1, b, c);
			}
		}
		return ans;
	}

	// 正式方法
	// 转化成杨辉三角
	public static final int MOD = 1000000007;

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
		return c(k + n - 1, n);
	}

	// 组合公式
	public static int c(int n, int k) {
		long fac = 1;
		long inv1 = 1;
		long inv2 = 1;
		for (int i = 1; i <= n; i++) {
			fac = (fac * i) % MOD;
			if (i == k) {
				inv1 = power(fac, MOD - 2); // 费马小定理求逆元
			}
			if (i == n - k) {
				inv2 = power(fac, MOD - 2); // 费马小定理求逆元
			}
		}
		return (int) ((((fac * inv1) % MOD) * inv2) % MOD);
	}

	// 乘法快速幂
	public static long power(long x, long p) {
		long ans = 1;
		while (p > 0) {
			if ((p & 1) == 1) {
				ans = (ans * x) % MOD;
			}
			x = (x * x) % MOD;
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
		System.out.println("功能测试开始");
		int N = 10;
		int V = 20;
		int test = 20000;
		for (int i = 0; i < test; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr = randomArray(n, V);
			int ans1 = ways1(arr);
			int ans2 = ways2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("==========");

		System.out.println("性能测试开始");
		int n = 10000000;
		int v = 10000000;
		long start, end;
		int[] arr = new int[n];
		System.out.println("随机生成的数据测试");
		System.out.println("数组长度 : " + n);
		System.out.println("数值范围 : [" + 1 + "," + v + "]");
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		start = System.currentTimeMillis();
		ways2(arr);
		end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " 毫秒");

		System.out.println();

		System.out.println("运行最慢的数据测试");
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
