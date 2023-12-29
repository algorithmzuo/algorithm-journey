package class090;

// 分成k份的最大乘积
// 一个数字n一定要分成k份，得到的乘积尽量大是多少
// 数字n和k，可能非常大，到达10^12规模
// 结果可能更大，所以返回结果对1000000007取模
// 来自真实大厂笔试，没有在线测试，对数器验证
public class Code02_MaximumProduct {

	// 暴力递归
	// 为了验证
	public static int maxValue1(int n, int k) {
		return f1(n, k);
	}

	// 剩余的数字rest拆成k份
	// 返回最大乘积
	// 暴力尝试一定能得到最优解
	public static int f1(int rest, int k) {
		if (k == 1) {
			return rest;
		}
		int ans = Integer.MIN_VALUE;
		for (int cur = 1; cur <= rest && (rest - cur) >= (k - 1); cur++) {
			int curAns = cur * f1(rest - cur, k - 1);
			ans = Math.max(ans, curAns);
		}
		return ans;
	}

	// 贪心的解
	// 这是最优解
	// 如果结果很大，那么求余数
	public static int maxValue2(int n, int k) {
		int mod = 1000000007;
		long a = n / k;
		int b = n % k;
		long part1 = power(a + 1, b, mod);
		long part2 = power(a, k - b, mod);
		return (int) (part1 * part2) % mod;
	}

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

	// 对数器
	// 为了验证
	public static void main(String[] args) {
		int N = 30;
		int testTimes = 2000;
		System.out.println("测试开始");
		for (int i = 1; i <= testTimes; i++) {
			int n = (int) (Math.random() * N) + 1;
			int k = (int) (Math.random() * n) + 1;
			int ans1 = maxValue1(n, k);
			int ans2 = maxValue2(n, k);
			if (ans1 != ans2) {
				// 如果出错了
				// 可以增加打印行为找到一组出错的例子
				// 然后去debug
				System.out.println("出错了！");
			}
			if (i % 100 == 0) {
				System.out.println("测试到第" + i + "组");
			}
		}
		System.out.println("测试结束");
	}

}
