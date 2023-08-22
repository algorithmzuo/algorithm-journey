package class041;

import java.math.BigInteger;

// 加法、减法、乘法的同余原理
// 不包括除法，因为除法必须求逆元，后续课讲述
public class Code03_SameMod {

	// 为了测试
	public static long random() {
		return (long) (Math.random() * Long.MAX_VALUE);
	}

	// 计算 ((a + b) * (c - d) + (a * c - b * d)) % mod 的非负结果
	public static int f1(long a, long b, long c, long d, int mod) {
		BigInteger o1 = new BigInteger(String.valueOf(a)); // a
		BigInteger o2 = new BigInteger(String.valueOf(b)); // b
		BigInteger o3 = new BigInteger(String.valueOf(c)); // c
		BigInteger o4 = new BigInteger(String.valueOf(d)); // d
		BigInteger o5 = o1.add(o2); // a + b
		BigInteger o6 = o3.subtract(o4); // c - d
		BigInteger o7 = o1.multiply(o3); // a * c
		BigInteger o8 = o2.multiply(o4); // b * d
		BigInteger o9 = o5.multiply(o6); // (a + b) * (c - d)
		BigInteger o10 = o7.subtract(o8); // (a * c - b * d)
		BigInteger o11 = o9.add(o10); // ((a + b) * (c - d) + (a * c - b * d))
		// ((a + b) * (c - d) + (a * c - b * d)) % mod
		BigInteger o12 = o11.mod(new BigInteger(String.valueOf(mod)));
		if (o12.signum() == -1) {
			// 如果是负数那么+mod返回
			return o12.add(new BigInteger(String.valueOf(mod))).intValue();
		} else {
			// 如果不是负数直接返回
			return o12.intValue();
		}
	}

	// 计算 ((a + b) * (c - d) + (a * c - b * d)) % mod 的非负结果
	public static int f2(long a, long b, long c, long d, int mod) {
		int o1 = (int) (a % mod); // a
		int o2 = (int) (b % mod); // b
		int o3 = (int) (c % mod); // c
		int o4 = (int) (d % mod); // d
		int o5 = (o1 + o2) % mod; // a + b
		int o6 = (o3 - o4 + mod) % mod; // c - d
		int o7 = (int) (((long) o1 * o3) % mod); // a * c
		int o8 = (int) (((long) o2 * o4) % mod); // b * d
		int o9 = (int) (((long) o5 * o6) % mod); // (a + b) * (c - d)
		int o10 = (o7 - o8 + mod) % mod; // (a * c - b * d)
		int ans = (o9 + o10) % mod; // ((a + b) * (c - d) + (a * c - b * d)) % mod
		return ans;
	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		int testTime = 100000;
		int mod = 1000000007;
		for (int i = 0; i < testTime; i++) {
			long a = random();
			long b = random();
			long c = random();
			long d = random();
			if (f1(a, b, c, d, mod) != f2(a, b, c, d, mod)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");

		System.out.println("===");
		long a = random();
		long b = random();
		long c = random();
		long d = random();
		System.out.println("a : " + a);
		System.out.println("b : " + b);
		System.out.println("c : " + c);
		System.out.println("d : " + d);
		System.out.println("===");
		System.out.println("f1 : " + f1(a, b, c, d, mod));
		System.out.println("f2 : " + f2(a, b, c, d, mod));
	}

}