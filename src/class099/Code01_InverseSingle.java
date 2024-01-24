package class099;

// 单个除数求逆元
// 对数器验证

public class Code01_InverseSingle {

	public static void main(String[] args) {
		// 必须确保
		// 1) a/b能整除
		// 2) mod是质数
		// 3) b和mod互质(最大公约数是1)
		long t = 67312L;
		long b = 3671613L;
		long a = t * b;
		int mod = 41;
		System.out.println(compute1(a, b, mod));
		System.out.println(compute2(a, b, mod));
	}

	public static int compute1(long a, long b, int mod) {
		return (int) ((a / b) % mod);
	}

	public static int compute2(long a, long b, int mod) {
		long inv = power(b, mod - 2, mod);
		return (int) (((a % mod) * inv) % mod);
	}

	// 乘法快速幂
	// 计算b的n次方的结果%mod
	public static long power(long b, int n, int mod) {
		long ans = 1;
		while (n > 0) {
			if ((n & 1) == 1) {
				ans = (ans * b) % mod;
			}
			b = (b * b) % mod;
			n >>= 1;
		}
		return ans;
	}

}
