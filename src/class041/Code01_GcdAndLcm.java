package class041;

// 求最大公约数、最小公倍数
public class Code01_GcdAndLcm {

	public static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static long lcm(long a, long b) {
		return (long) a / gcd(a, b) * b;
	}

}
