package class099;

import java.math.BigInteger;

// 阶乘结果逆元表的线性递推
public class Code02_FactorialInverse {

	public static int MOD = 1000000007;

	public static int LIMIT = 100;

	// 阶乘表
	public static long[] fac = new long[LIMIT + 1];

	// 阶乘结果的逆元表
	public static long[] inv = new long[LIMIT + 1];

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

	public static void main(String[] args) {
		// 方式1 : 计算C(100, 49) = 100! / (49! * 51!)
		// BigInteger保证中间计算结果完全正确
		BigInteger a = new BigInteger("1");
		BigInteger b = new BigInteger("1");
		BigInteger c = new BigInteger("1");
		for (int i = 1; i <= 100; i++) {
			String cur = String.valueOf(i);
			a = a.multiply(new BigInteger(cur));
			if (i <= 49) {
				b = b.multiply(new BigInteger(cur));
			}
			if (i <= 51) {
				c = c.multiply(new BigInteger(cur));
			}
		}
		BigInteger ans1 = a.divide(b.multiply(c)).mod(new BigInteger(String.valueOf(MOD)));
		System.out.println("方式1结果 : " + ans1.toString());
		// 方式2 : 计算C(100, 49) = 100! / (49! * 51!)
		// 阶乘结果逆元表的线性递推
		build();
		long ans2 = fac[100];
		ans2 = (ans2 * inv[49]) % MOD;
		ans2 = (ans2 * inv[51]) % MOD;
		System.out.println("方式2结果 : " + ans2);
	}

}
