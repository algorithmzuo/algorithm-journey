package class103;

// 扩展KMP，又称Z算法(Leetcode基本版)
// 给定一个字符串str，求出一个数组
// str与str每一个后缀串的最长公共前缀长度
// 返回这个数组所有值的和
// 测试链接 : https://leetcode.cn/problems/sum-of-scores-of-built-strings/
// 扩展KMP和KMP算法差别挺大，反而和Manacher算法非常像
public class Code02_ExpandKMP1 {

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
		if (n >= 2) {
			for (int i = 2, t = 1, r = 1 + zxt[1]; i < n; i++) {
				zxt[i] = Math.max(0, Math.min(r - i, zxt[i - t]));
				while (i + zxt[i] < n && s[i + zxt[i]] == s[zxt[i]]) {
					zxt[i]++;
				}
				if (i + zxt[i] > r) {
					r = i + zxt[i];
					t = i;
				}
			}
		}
		return zxt;
	}

}
