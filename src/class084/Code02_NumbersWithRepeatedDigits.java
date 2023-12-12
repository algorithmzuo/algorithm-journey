package class084;

// 至少有1位重复的数字
// 给定正整数n
// 返回在[1, n]范围内具有至少1位重复数字的正整数的个数
// 测试链接 : https://leetcode.cn/problems/numbers-with-repeated-digits/
public class Code02_NumbersWithRepeatedDigits {

	public static int numDupDigitsAtMostN(int n) {
		if (n <= 10) {
			return 0;
		}
		int len = 1;
		int offset = 1;
		int tmp = n / 10;
		while (tmp > 0) {
			len++;
			offset *= 10;
			tmp /= 10;
		}
		int noRepeat = 0;
		for (int i = 1, cnt = 9, cur = 9; i < len; i++) {
			if (i == 1) {
				noRepeat += 10;
			} else {
				cnt *= cur--;
				noRepeat += cnt;
			}
		}
		int[] cnt = new int[len];
		cnt[0] = 1;
		for (int i = 1, ans = 1, base = 10 - len + 1; i < len; i++, base++) {
			ans *= base;
			cnt[i] = ans;
		}
		int status = 0b1111111111;
		noRepeat += ((n / offset) - 1) * cnt[len - 1];
		noRepeat += f(cnt, len - 1, offset / 10, status ^ (1 << (n / offset)), n);
		return n + 1 - noRepeat;
	}

	public static int f(int[] cnt, int len, int offset, int status, int n) {
		if (len == 0) {
			return 1;
		}
		int ans = 0;
		int first = (n / offset) % 10;
		for (int cur = 0; cur < first; cur++) {
			if ((status & (1 << cur)) != 0) {
				ans += cnt[len - 1];
			}
		}
		if ((status & (1 << first)) != 0) {
			ans += f(cnt, len - 1, offset / 10, status ^ (1 << first), n);
		}
		return ans;
	}

}
