package class084;

// 最大为N的数字组合
// 给定一个按 非递减顺序 排列的数字数组 digits
// 你可以用任意次数 digits[i] 来写的数字
// 例如，如果 digits = ['1','3','5']
// 我们可以写数字，如 '13', '551', 和 '1351315'
// 返回 可以生成的小于或等于给定整数 n 的正整数的个数
// 测试链接 : https://leetcode.cn/problems/numbers-at-most-n-given-digit-set/
public class Code02_NumbersAtMostGivenDigitSet {

	public static int atMostNGivenDigitSet(String[] strs, int number) {
		int m = strs.length;
		int[] digits = new int[m];
		for (int i = 0; i < m; i++) {
			digits[i] = Integer.valueOf(strs[i]);
		}
		int tmp = number / 10;
		int len = 1;
		int offset = 1;
		while (tmp > 0) {
			tmp /= 10;
			len++;
			offset *= 10;
		}
		int[] cnt = new int[len];
		cnt[0] = 1;
		int ans = 0;
		for (int prepare = m, i = 1; i < len; i++, prepare *= m) {
			cnt[i] = prepare;
			ans += prepare;
		}
		return ans + f(digits, cnt, number, offset, len);
	}

	public static int f(int[] digits, int[] cnt, int number, int offset, int len) {
		if (len == 0) {
			return 1;
		}
		int cur = (number / offset) % 10;
		int ans = 0;
		for (int i = 0; i < digits.length; i++) {
			if (digits[i] < cur) {
				ans += cnt[len - 1];
			} else if (digits[i] == cur) {
				ans += f(digits, cnt, number, offset / 10, len - 1);
			} else {
				break;
			}
		}
		return ans;
	}

}
