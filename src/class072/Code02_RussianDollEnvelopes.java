package class072;

import java.util.Arrays;

// 俄罗斯套娃信封问题
// 给你一个二维整数数组envelopes ，其中envelopes[i]=[wi, hi]
// 表示第 i 个信封的宽度和高度
// 当另一个信封的宽度和高度都比这个信封大的时候
// 这个信封就可以放进另一个信封里，如同俄罗斯套娃一样
// 请计算 最多能有多少个信封能组成一组“俄罗斯套娃”信封
// 即可以把一个信封放到另一个信封里面，注意不允许旋转信封
// 测试链接 : https://leetcode.cn/problems/russian-doll-envelopes/
public class Code02_RussianDollEnvelopes {

	public static int maxEnvelopes(int[][] envelopes) {
		int n = envelopes.length;
		// 排序策略:
		// 宽度从小到大
		// 宽度一样，高度从大到小
		Arrays.sort(envelopes, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (b[1] - a[1]));
		int[] ends = new int[n];
		int len = 0;
		for (int i = 0, find, num; i < n; i++) {
			num = envelopes[i][1];
			find = bs(ends, len, num);
			if (find == -1) {
				ends[len++] = num;
			} else {
				ends[find] = num;
			}
		}
		return len;
	}

	public static int bs(int[] ends, int len, int num) {
		int l = 0, r = len - 1, m, ans = -1;
		while (l <= r) {
			m = (l + r) / 2;
			if (ends[m] >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}
