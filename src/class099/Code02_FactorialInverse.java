package class099;

import java.math.BigInteger;

// 阶乘结果逆元表的线性递推
public class Code02_FactorialInverse {

	public static int mod = 1000000007;

	public static int limit = 100;

	// 阶乘表
	public static long[] fac = new long[limit + 1];

	// 阶乘结果的逆元表
	public static long[] inv = new long[limit + 1];

	public static long power(long x, int n) {
		long ans = 1;
		while (n > 0) {
			if ((n & 1) == 1) {
				ans = (ans * x) % mod;
			}
			x = (x * x) % mod;
			n >>= 1;
		}
		return ans;
	}

	public static void build() {
		fac[1] = 1;
		for (int i = 2; i <= limit; i++) {
			fac[i] = ((long) i * fac[i - 1]) % mod;
		}
		// 0的阶乘是1
		inv[0] = 1;
		// 费马小定理计算乘法逆元
		// for (int i = 1; i <= limit; i++) {
		// inv[i] = power(fac[i], mod - 2);
		// }
		// 费马小定理计算乘法逆元，优化如下
		inv[limit] = power(fac[limit], mod - 2);
		for (int i = limit; i > 1; i--) {
			inv[i - 1] = ((long) i * inv[i]) % mod;
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
		BigInteger ans1 = a.divide(b.multiply(c)).mod(new BigInteger(String.valueOf(mod)));
		System.out.println("方式1结果 : " + ans1.toString());
		// 方式2 : 计算C(100, 49) = 100! / (49! * 51!)
		// 阶乘结果逆元表的线性递推
		build();
		long ans2 = fac[100];
		ans2 = (ans2 * inv[49]) % mod;
		ans2 = (ans2 * inv[51]) % mod;
		System.out.println("方式2结果 : " + ans2);
	}

}
