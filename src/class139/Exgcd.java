package class139;

public class Exgcd {

	// 扩展欧几里得算法
	// 对于方程ax + by = gcd(a,b)
	// 当a和b确定，那么gcd(a,b)也确定
	// 扩展欧几里得算法可以给出a和b的最大公约数d、以及其中一个特解x、y
	// 特别注意要保证入参a和b没有负数
	public static long d, x, y, px, py;

	public static void exgcd(long a, long b) {
		if (b == 0) {
			d = a;
			x = 1;
			y = 0;
		} else {
			exgcd(b, a % b);
			px = x;
			py = y;
			x = py;
			y = px - py * (a / b);
		}
	}

	// 讲解099，费马小定理计算逆元
	public static long fermat(long num, long mod) {
		return power(num, mod - 2, mod);
	}

	public static long power(long num, long pow, long mod) {
		long ans = 1;
		while (pow > 0) {
			if ((pow & 1) == 1) {
				ans = (ans * num) % mod;
			}
			num = (num * num) % mod;
			pow >>= 1;
		}
		return ans;
	}

	public static void main(String[] args) {
		// 扩展欧几里得算法例子
		int a = 220;
		int b = 170;
		exgcd(a, b);
		System.out.println("gcd(" + a + ", " + b + ")" + " = " + d);
		System.out.println("x = " + x + ", " + " y = " + y);

		// 扩展欧几里得算法可以去求逆元
		System.out.println("求逆元测试开始");
		long mod = 1000000007;
		long test = 10000000;
		for (long num = 1; num <= test; num++) {
			exgcd(num, mod);
			x = (x % mod + mod) % mod;
			if (x != fermat(num, mod)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("求逆元测试结束");
	}

}
