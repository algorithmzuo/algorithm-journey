package class090;

// 砍竹子II
// 现需要将一根长为正整数bamboo_len的竹子砍为若干段
// 每段长度均为正整数
// 请返回每段竹子长度的最大乘积是多少
// 答案需要对1000000007取模
// 测试链接 : https://leetcode.cn/problems/jian-sheng-zi-ii-lcof/
public class Code01_CuttingBamboo {

	public static int mod = 1000000007;

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

	public static int cuttingBamboo(int n) {
		if (n == 2) {
			return 1;
		}
		if (n == 3) {
			return 2;
		}
		int rest = n % 3 == 0 ? n : (n % 3 == 1 ? (n - 4) : (n - 2));
		int last = n % 3 == 0 ? 1 : (n % 3 == 1 ? 4 : 2);
		return (int) ((power(3, rest / 3) * last) % mod);
	}

}
