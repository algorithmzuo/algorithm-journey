package class085;

// 不含连续1的非负整数
// 给定一个正整数n，请你统计在[0, n]范围的非负整数中
// 有多少个整数的二进制表示中不存在连续的1
// 测试链接 : https://leetcode.cn/problems/non-negative-integers-without-consecutive-ones/
public class Code03_IntegersWithoutConsecutiveOnes {

	public static int findIntegers1(int n) {
		int[] cnt = new int[31];
		cnt[0] = 1;
		cnt[1] = 2;
		for (int len = 2; len <= 30; len++) {
			cnt[len] = cnt[len - 1] + cnt[len - 2];
		}
		return f(cnt, n, 30);
	}

	public static int f(int[] cnt, int n, int i) {
		if (i == -1) {
			return 1;
		}
		int ans = 0;
		if ((n & (1 << i)) != 0) {
			ans += cnt[i];
			if ((n & (1 << (i + 1))) != 0) {
				return ans;
			}
		}
		ans += f(cnt, n, i - 1);
		return ans;
	}

	// 只是把方法1从递归改成迭代而已
	// 完全是等义改写，没有新东西
	public static int findIntegers2(int n) {
		int[] cnt = new int[31];
		cnt[0] = 1;
		cnt[1] = 2;
		for (int len = 2; len <= 30; len++) {
			cnt[len] = cnt[len - 1] + cnt[len - 2];
		}
		int ans = 0;
		for (int i = 30; i >= -1; i--) {
			if (i == -1) {
				ans++;
				break;
			}
			if ((n & (1 << i)) != 0) {
				ans += cnt[i];
				if ((n & (1 << (i + 1))) != 0) {
					break;
				}
			}
		}
		return ans;
	}

}
