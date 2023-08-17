package class044;

// 如果一个正整数自身是回文数，而且它也是一个回文数的平方，那么我们称这个数为超级回文数。
// 现在，给定两个正整数 L 和 R （以字符串形式表示），
// 返回包含在范围 [L, R] 中的超级回文数的数目。
// 测试链接 : https://leetcode.cn/problems/super-palindromes/
public class Code02_SuperPalindromes {

	public static int superpalindromesInRange(String left, String right) {
		long l = Long.valueOf(left);
		long r = Long.valueOf(right);
		long limit = (long) Math.sqrt((double) r);
		int cnt = 0;
		long seed = 1;
		long enlarge = 0;
		do {
			enlarge = enlarge2(seed);
			if (isValid(enlarge * enlarge, l, r)) {
				cnt++;
			}
			enlarge = enlarge1(seed);
			if (isValid(enlarge * enlarge, l, r)) {
				cnt++;
			}
			seed++;
		} while (enlarge < limit);
		return cnt;
	}

	public static long enlarge1(long seed) {
		long ans = seed;
		seed /= 10;
		while (seed != 0) {
			ans = ans * 10 + seed % 10;
			seed /= 10;
		}
		return ans;
	}

	public static long enlarge2(long seed) {
		long ans = seed;
		while (seed != 0) {
			ans = ans * 10 + seed % 10;
			seed /= 10;
		}
		return ans;
	}

	public static boolean isValid(long ans, long l, long r) {
		return isPalindrome(ans) && ans >= l && ans <= r;
	}

	public static boolean isPalindrome(long n) {
		long help = 1;
		while (n / help >= 10) {
			help *= 10;
		}
		while (n != 0) {
			if (n / help != n % 10) {
				return false;
			}
			n = (n % help) / 10;
			help /= 100;
		}
		return true;
	}

}
