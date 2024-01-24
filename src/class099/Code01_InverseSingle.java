package class099;

// 单个除数求逆元
// 对数器验证
public class Code01_InverseSingle {

	public static void main(String[] args) {
		// 1) 必须保证a/b可以整除
		// 2) 必须保证mod是质数
		// 3) 必须保证b和mod的最大公约数为1
		int mod = 41;
		long b = 3671613L;
		long a = 67312L * b;
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
