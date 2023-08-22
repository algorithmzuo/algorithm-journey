package class043;

import java.util.ArrayList;
import java.util.List;

// 如果一个正整数自身是回文数，而且它也是一个回文数的平方，那么我们称这个数为超级回文数。
// 现在，给定两个正整数 L 和 R （以字符串形式表示），
// 返回包含在范围 [L, R] 中的超级回文数的数目。
// 1 <= len(L) <= 18
// 1 <= len(R) <= 18
// L 和 R 是表示 [1, 10^18) 范围的整数的字符串
//测试链接 : https://leetcode.cn/problems/super-palindromes/
public class Code02_SuperPalindromes {

	public static int superpalindromesInRange1(String left, String right) {
		long l = Long.valueOf(left);
		long r = Long.valueOf(right);
		long limit = (long) Math.sqrt((double) r);
		long seed = 1;
		long num = 0;
		int ans = 0;
		do {
			num = evenEnlarge(seed);
			if (check(num * num, l, r)) {
				ans++;
			}
			num = oddEnlarge(seed);
			if (check(num * num, l, r)) {
				ans++;
			}
			seed++;
		} while (num < limit);
		return ans;
	}

	// 根据种子扩充到偶数长度的回文数字并返回
	public static long evenEnlarge(long seed) {
		long ans = seed;
		while (seed != 0) {
			ans = ans * 10 + seed % 10;
			seed /= 10;
		}
		return ans;
	}

	// 根据种子扩充到奇数长度的回文数字并返回
	public static long oddEnlarge(long seed) {
		long ans = seed;
		seed /= 10;
		while (seed != 0) {
			ans = ans * 10 + seed % 10;
			seed /= 10;
		}
		return ans;
	}

	// 判断ans是不是属于[l,r]范围的回文数
	public static boolean check(long ans, long l, long r) {
		return ans >= l && ans <= r && isPalindrome(ans);
	}

	public static boolean isPalindrome(long num) {
		long offset = 1;
		// 注意这么写是为了防止溢出
		while (num / offset >= 10) {
			offset *= 10;
		}
		// 首尾判断
		while (num != 0) {
			if (num / offset != num % 10) {
				return false;
			}
			num = (num % offset) / 10;
			offset /= 100;
		}
		return true;
	}

	// 打表的方法
	// 必然最优解
	// 连二分都懒得用
	public static int superpalindromesInRange2(String left, String right) {
		long l = Long.parseLong(left);
		long r = Long.parseLong(right);
		int i = 0;
		for (; i < record.length; i++) {
			if (record[i] >= l) {
				break;
			}
		}
		int j = record.length - 1;
		for (; j >= 0; j--) {
			if (record[j] <= r) {
				break;
			}
		}
		return j - i + 1;
	}

	public static long[] record = new long[] {

	};

	public static List<Long> collect() {
		long l = 1;
		long r = Long.MAX_VALUE;
		long limit = (long) Math.sqrt((double) r);
		long seed = 1;
		long enlarge = 0;
		ArrayList<Long> ans = new ArrayList<>();
		do {
			enlarge = evenEnlarge(seed);
			if (check(enlarge * enlarge, l, r)) {
				ans.add(enlarge * enlarge);
			}
			enlarge = oddEnlarge(seed);
			if (check(enlarge * enlarge, l, r)) {
				ans.add(enlarge * enlarge);
			}
			seed++;
		} while (enlarge < limit);
		ans.sort((a, b) -> a.compareTo(b));
		return ans;
	}

	public static void main(String[] args) {
		List<Long> ans = collect();
		for (long p : ans) {
			System.out.println(p + "L,");
		}
		System.out.println("size : " + ans.size());
	}

}
