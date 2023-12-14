package class084;

// 最大为N的数字组合
// 给定一个按 非递减顺序 排列的数字数组 digits
// 已知digits一定不包含'0'，可能包含'1' ~ '9'，且无重复字符
// 你可以用任意次数 digits[i] 来写的数字
// 例如，如果 digits = ['1','3','5']
// 我们可以写数字，如 '13', '551', 和 '1351315'
// 返回 可以生成的小于或等于给定整数 n 的正整数的个数
// 测试链接 : https://leetcode.cn/problems/numbers-at-most-n-given-digit-set/
public class Code02_NumbersAtMostGivenDigitSet {

	public static int atMostNGivenDigitSet(String[] strs, int num) {
		int tmp = num / 10;
		int len = 1;
		int offset = 1;
		while (tmp > 0) {
			tmp /= 10;
			len++;
			offset *= 10;
		}
		int m = strs.length;
		int[] digits = new int[m];
		for (int i = 0; i < m; i++) {
			digits[i] = Integer.valueOf(strs[i]);
		}
		return f(digits, num, offset, len, 0, 0);
	}

	public static int f(int[] digits, int num, int offset, int len, int less, int fix) {
		if (len == 0) {
			return fix == 1 ? 1 : 0;
		}
		int ans = 0;
		int cur = (num / offset) % 10;
		if (fix == 0) {
			ans += f(digits, num, offset / 10, len - 1, 1, 0);
		}
		if (less == 0) {
			for (int i : digits) {
				if (i < cur) {
					ans += f(digits, num, offset / 10, len - 1, 1, 1);
				} else if (i == cur) {
					ans += f(digits, num, offset / 10, len - 1, 0, 1);
				} else {
					break;
				}
			}
		} else {
			ans += digits.length * f(digits, num, offset / 10, len - 1, 1, 1);
		}
		return ans;
	}

}
