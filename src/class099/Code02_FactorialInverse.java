package class099;

import java.math.BigInteger;

// 阶乘结果逆元表的线性递推
public class Code02_FactorialInverse {

	public static int MOD = 1000000007;

	public static int LIMIT = 1000;

	// 阶乘表
	public static long[] fac = new long[LIMIT + 1];

	// 阶乘结果的逆元表
	public static long[] inv = new long[LIMIT + 1];

	public static void build() {
		fac[1] = 1;
		for (int i = 2; i <= LIMIT; i++) {
			fac[i] = ((long) i * fac[i - 1]) % MOD;
		}
		// 0的阶乘是1
		inv[0] = 1;
		// 费马小定理计算乘法逆元
		// for (int i = 1; i <= limit; i++) {
		// inv[i] = power(fac[i], mod - 2);
		// }
		// 费马小定理计算乘法逆元，优化如下
		inv[LIMIT] = power(fac[LIMIT], MOD - 2);
		for (int i = LIMIT; i > 1; i--) {
			inv[i - 1] = ((long) i * inv[i]) % MOD;
		}
	}

	public static long power(long x, int n) {
		long ans = 1;
		while (n > 0) {
			if ((n & 1) == 1) {
				ans = (ans * x) % MOD;
			}
			x = (x * x) % MOD;
			n >>= 1;
		}
		return ans;
	}

	// 组合公式
	// 得到的结果 % MOD
	// BigInteger保证中间计算结果完全正确
	public static int c1(int n, int m) {
		BigInteger a = new BigInteger("1");
		BigInteger b = new BigInteger("1");
		BigInteger c = new BigInteger("1");
		for (int i = 1; i <= n; i++) {
			String cur = String.valueOf(i);
			a = a.multiply(new BigInteger(cur));
			if (i <= m) {
				b = b.multiply(new BigInteger(cur));
			}
			if (i <= n - m) {
				c = c.multiply(new BigInteger(cur));
			}
		}
		BigInteger ans = a.divide(b.multiply(c)).mod(new BigInteger(String.valueOf(MOD)));
		return ans.intValue();
	}

	// 组合公式
	// 得到的结果 % MOD
	// 阶乘结果逆元表的线性递推
	public static int c2(int n, int m) {
		long ans = fac[n];
		ans = (ans * inv[m]) % MOD;
		ans = (ans * inv[n - m]) % MOD;
		return (int) ans;
	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		build();
		// n不要超过LIMIT
		int n = 500;
		for (int m = 0; m <= n; m++) {
			int ans1 = c1(n, m);
			int ans2 = c2(n, m);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");

		System.out.println(c1(100, 48));
		System.out.println(c2(100, 48));
	}

}
