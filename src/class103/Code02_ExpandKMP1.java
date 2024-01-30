package class103;

// 扩展KMP，又称Z算法(Leetcode基本版)
// 给定一个字符串str，求出一个数组
// str与str每一个后缀串的最长公共前缀长度
// 返回这个数组所有值的和
// 测试链接 : https://leetcode.cn/problems/sum-of-scores-of-built-strings/
public class Code02_ExpandKMP1 {

	// 扩展KMP和KMP算法差别挺大，反而和Manacher算法非常像
	public static long sumScores(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[] z = zArray(s, n);
		long ans = 0;
		for (int num : z) {
			ans += num;
		}
		return ans;
	}

	public static int[] zArray(char[] s, int n) {
		int[] z = new int[n];
		z[0] = n;
		for (int i = 0, j = 1; j < n && s[i] == s[j]; i++, j++) {
			z[1]++;
		}
		if (n >= 2) {
			for (int i = 2, c = 1, r = 1 + z[1]; i < n; i++) {
				z[i] = Math.max(0, Math.min(r - i, z[i - c]));
				while (i + z[i] < n && s[i + z[i]] == s[z[i]]) {
					z[i]++;
				}
				if (i + z[i] > r) {
					r = i + z[i];
					c = i;
				}
			}
		}
		return z;
	}

}
