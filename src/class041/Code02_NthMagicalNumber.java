package class041;

// 一个正整数如果能被 a 或 b 整除，那么它是神奇的。
// 给定三个整数 n , a , b ，返回第 n 个神奇的数字。
// 因为答案可能很大，所以返回答案 对 1000000007 取模
// 测试链接 : https://leetcode.cn/problems/nth-magical-number/
public class Code02_NthMagicalNumber {

	public static int nthMagicalNumber(int n, int a, int b) {
		long lcm = lcm(a, b);
		long ans = 0;
		// l = 0
		// r = (long) n * Math.min(a, b)
		// l......r
        //todo r估计最小满足的范围
		for (long l = 0, r = (long) n * Math.min(a, b), m = 0; l <= r;) {
            //todo 取中间值判断多还是少
			m = (l + r) / 2;
			// 1....m
            //多就往左走 todo 这里m/a是中间值能被整除的个数， m/lcm是重复的值
			if (m / a + m / b - m / lcm >= n) {
				ans = m;
				r = m - 1;
			} else {
                //少就往右走
				l = m + 1;
			}
		}
		return (int) (ans % 1000000007);
	}

	public static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static long lcm(long a, long b) {
		return (long) a / gcd(a, b) * b;
	}

}
