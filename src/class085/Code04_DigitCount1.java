package class085;

// 范围内的数字计数
// 给定一个在 0 到 9 之间的整数 d，和两个正整数 low 和 high 分别作为上下界
// 返回 d 在 low 和 high 之间的整数中出现的次数，包括边界 low 和 high
// 测试链接 : https://leetcode.cn/problems/digit-count-in-range/
public class Code04_DigitCount1 {

	public static int digitsCount(int d, int low, int high) {
		return count(high, d) - count(low - 1, d);
	}

	public static int count(int n, int d) {
		int ans = 0;
		for (int i = 1, tmp = n, high, cur; tmp != 0; i *= 10, tmp /= 10) {
			high = tmp / 10;
			if (d == 0) {
				if (high == 0) {
					break;
				}
				high--;
			}
			ans += high * i;
			cur = tmp % 10;
			if (cur > d) {
				ans += i;
			} else if (cur == d) {
				ans += n % i + 1;
			}
		}
		return ans;
	}

}
