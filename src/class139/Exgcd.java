package class139;

public class Exgcd {

	// 扩展欧几里得算法
	public static long x, y;

	public static void exgcd(long a, long b) {
		long n = 0, m = 1, pn = 1, pm = 0, tmp, q, r;
		while (b != 0) {
			q = a / b;
			r = a % b;
			a = b;
			b = r;
			tmp = n;
			n = pn - q * n;
			pn = tmp;
			tmp = m;
			m = pm - q * m;
			pm = tmp;
		}
		x = pn;
		y = pm;
	}

	// 讲解099，费马小定理计算逆元
	public static long fermat(long num, long mod) {
		return power(num, mod - 2, mod);
	}

	public static long power(long num, long p, long mod) {
		long ans = 1;
		while (p > 0) {
			if ((p & 1) == 1) {
				ans = (ans * num) % mod;
			}
			num = (num * num) % mod;
			p >>= 1;
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println("测试开始");
		long mod = 1000000007;
		long test = 10000000;
		for (long num = 1; num <= test; num++) {
			exgcd(num, mod);
			x = (x % mod + mod) % mod;
			if (x != fermat(num, mod)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
