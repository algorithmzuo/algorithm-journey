package class072;

import java.util.Arrays;

// 最长数对链
// 给你一个由n个数对组成的数对数组pairs
// 其中 pairs[i] = [lefti, righti] 且 lefti < righti
// 现在，我们定义一种 跟随 关系，当且仅当 b < c 时
// 数对 p2 = [c, d] 才可以跟在 p1 = [a, b] 后面
// 我们用这种形式来构造 数对链
// 找出并返回能够形成的最长数对链的长度
// 测试链接 : https://leetcode.cn/problems/maximum-length-of-pair-chain/
public class Code04_MaximumLengthOfPairChain {

	public static int findLongestChain(int[][] pairs) {
		int n = pairs.length;
		// 数对根据开始位置排序，从小到大
		// 结束位置无所谓！
		Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
		// 结尾的数值
		int[] ends = new int[n];
		int len = 0;
		for (int[] pair : pairs) {
			int find = bs(ends, len, pair[0]);
			if (find == -1) {
				ends[len++] = pair[1];
			} else {
				ends[find] = Math.min(ends[find], pair[1]);
			}
		}
		return len;
	}

	// >= num最左位置
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
