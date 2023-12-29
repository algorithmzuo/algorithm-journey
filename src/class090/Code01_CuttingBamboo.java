package class090;

// 砍竹子II
// 现需要将一根长为正整数bamboo_len的竹子砍为若干段
// 每段长度均为正整数
// 请返回每段竹子长度的最大乘积是多少
// 答案需要对1000000007取模
// 测试链接 : https://leetcode.cn/problems/jian-sheng-zi-ii-lcof/
public class Code01_CuttingBamboo {

	// 快速幂，求余数
	// 求x的n次方，最终得到的结果 % mod
	public static long power(long x, int n, int mod) {
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

	public static int cuttingBamboo(int n) {
		if (n == 2) {
			return 1;
		}
		if (n == 3) {
			return 2;
		}
		int mod = 1000000007;
		// n = 4  -> 2 * 2
		// n = 5  -> 3 * 2
		// n = 6  -> 3 * 3
		// n = 7  -> 3 * 2 * 2
		// n = 8  -> 3 * 3 * 2
		// n = 9  -> 3 * 3 * 3
		// n = 10 -> 3 * 3 * 2 * 2
		// n = 11 -> 3 * 3 * 3 * 2
		// n = 12 -> 3 * 3 * 3 * 3
		int tail = n % 3 == 0 ? 1 : (n % 3 == 1 ? 4 : 2);
		int power = (tail == 1 ? n : (n - tail)) / 3;
		return (int) (power(3, power, mod) * tail % mod);
	}

}
