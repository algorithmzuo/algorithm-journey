package class101;

// 扩展KMP，又称Z函数(Leetcode基本版)
// 给定一个字符串str，求出一个数组
// str与str每一个后缀串的最长公共前缀长度
// 返回这个数组所有值的和
// 测试链接 : https://leetcode.cn/problems/sum-of-scores-of-built-strings/

public class Code05_ExpandKMP1 {

	public static long sumScores(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[] zxt = znext(s, n);
		long ans = 0;
		for (int num : zxt) {
			ans += num;
		}
		return ans;
	}

	public static int[] znext(char[] s, int n) {
		int[] zxt = new int[n];
		zxt[0] = n;
		for (int i = 0, j = 1; j < n && s[i] == s[j]; i++, j++) {
			zxt[1]++;
		}
		for (int i = 2, k = 1, j, r; i < n; i++) {
			r = k + zxt[k];
			j = zxt[i - k];
			if (i + j < r) {
				zxt[i] = j;
			} else {
				j = Math.max(0, r - i);
				while (i + j < n && s[i + j] == s[j]) {
					// 一旦成功就让右边界更往右了，而右边界最多走到n
					// 所以不要在乎每次while的代价
					// 要关注所有while行为的总代价为O(n)
					// 这一点和Manacher算法时间复杂度的估计很像
					j++;
				}
				zxt[i] = j;
				k = i;
			}
		}
		return zxt;
	}

}
