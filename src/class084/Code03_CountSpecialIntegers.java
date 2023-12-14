package class084;

// 完全没有重复的数字个数
// 给定正整数n，返回在[1, n]范围内每一位都互不相同的正整数个数
// 测试链接 : https://leetcode.cn/problems/count-special-integers/
public class Code03_CountSpecialIntegers {

	public static int countSpecialNumbers(int n) {
		int len = 1;
		int offset = 1;
		int tmp = n / 10;
		while (tmp > 0) {
			len++;
			offset *= 10;
			tmp /= 10;
		}
		// cnt[i] :
		// 一共长度为len，还剩i位没有确定，确定的前缀为len-i位
		// 没有选择的数字剩下10 - len + i个
		// 后续的i位上，还有多少种数字排列
		// 比如：len = 4
		// cnt[4]不计算
		// cnt[3] = 9 * 8 * 7
		// cnt[2] = 8 * 7
		// cnt[1] = 7
		// cnt[0] = 1，表示前缀已确定，后续也没有了，那么就是1种排列，就是前缀的状况
		// 再比如：len = 6
		// cnt[6]不计算
		// cnt[5] = 9 * 8 * 7 * 6 * 5
		// cnt[4] = 8 * 7 * 6 * 5
		// cnt[3] = 7 * 6 * 5
		// cnt[2] = 6 * 5
		// cnt[1] = 5
		// cnt[0] = 1，表示前缀已确定，后续也没有了，那么就是1种排列，就是前缀的状况
		// 下面for循环就是求解cnt的代码
		int[] cnt = new int[len];
		cnt[0] = 1;
		for (int i = 1, k = 10 - len + 1; i < len; i++, k++) {
			cnt[i] = cnt[i - 1] * k;
		}
		int ans = 0;
		if (len >= 2) {
			// 如果n的位数是len位
			// 计算位数少于len的数中，每一位都互不相同的正整数个数，并累加
			// 所有1位数中，每一位都互不相同的正整数个数 = 9
			// 所有2位数中，每一位都互不相同的正整数个数 = 9 * 9
			// 所有3位数中，每一位都互不相同的正整数个数 = 9 * 9 * 8
			// 所有4位数中，每一位都互不相同的正整数个数 = 9 * 9 * 8 * 7
			// ...
			ans = 9;
			for (int i = 2, a = 9, b = 9; i < len; i++, b--) {
				a *= b;
				ans += a;
			}
		}
		// 如果n的位数是len位，已经计算了位数少于len个的情况
		// 下面计算一定有len位的数字中，小于n且每一位都互不相同的正整数个数
		int first = n / offset;
		ans += (first - 1) * cnt[len - 1];
		ans += f(cnt, n, len - 1, offset / 10, 1 << first);
		return ans;
	}

	public static int f(int[] cnt, int num, int len, int offset, int status) {
		if (len == 0) {
			return 1;
		}
		int ans = 0;
		int first = (num / offset) % 10;
		for (int cur = 0; cur < first; cur++) {
			if ((status & (1 << cur)) == 0) {
				ans += cnt[len - 1];
			}
		}
		if ((status & (1 << first)) == 0) {
			ans += f(cnt, num, len - 1, offset / 10, status ^ (1 << first));
		}
		return ans;
	}

}
