package class099;

import java.math.BigInteger;

// 连续阶乘逆元的线性递推
// 实现组合公式C(n,m)的计算
// 最终结果 % 1000000007后返回
// 0 <= m <= n <= 1000
// 对数器验证
public class Code03_InverseFactorial {

	public static int MOD = 1000000007;

	public static int LIMIT = 1000;

	// 阶乘表
	// fac[i]代表 i! 在 %MOD 意义下的余数
	public static long[] fac = new long[LIMIT + 1];

	// 阶乘结果的逆元表
	// inv[i]代表i! 在 %MOD 意义下的逆元(1 / i!)
	public static long[] inv = new long[LIMIT + 1];

	public static void build() {
		fac[1] = 1;
		for (int i = 2; i <= LIMIT; i++) {
			fac[i] = ((long) i * fac[i - 1]) % MOD;
		}
		// 单个阶乘的逆元
//		inv[0] = 1;
//		for (int i = 1; i <= LIMIT; i++) {
//			inv[i] = power(fac[i], MOD - 2);
//		}
		// 利用线性递推优化
		inv[LIMIT] = power(fac[LIMIT], MOD - 2);
		for (int i = LIMIT - 1; i >= 0; i--) {
			inv[i] = ((long) (i + 1) * inv[i + 1]) % MOD;
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
		// 要保证 n <= LIMIT
		int n = 500;
		for (int m = 0; m <= n; m++) {
			int ans1 = c1(n, m);
			int ans2 = c2(n, m);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");

		// 要保证 b <= a <= LIMIT
		int a = 537;
		int b = 367;
		System.out.println("计算 C ( " + a + " , " + b + " ) % " + MOD);
		System.out.println("方法1答案 : " + c1(a, b));
		System.out.println("方法2答案 : " + c2(a, b));
	}

}
