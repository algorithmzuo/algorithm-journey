package class100;

// kmp算法模版
// 测试链接 : https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/
public class Code01_KMP {

	public static int strStr(String s1, String s2) {
		// s1.indexOf(s2);
		return kmp(s1, s2);
	}

	// kmp算法
	public static int kmp(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length, m = s2.length, x = 0, y = 0;
		int[] next = nextArray(s2, m);
		while (x < n && y < m) {
			if (s1[x] == s2[y]) {
				x++;
				y++;
			} else if (y == 0) {
				x++;
			} else {
				y = next[y];
			}
		}
		return y == m ? x - y : -1;
	}

	// 得到next数组
	public static int[] nextArray(char[] s, int m) {
		if (m == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[m];
		next[0] = -1;
		next[1] = 0;
		int i = 2, cn = 0;
		while (i < m) {
			if (s[i - 1] == s[cn]) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
		return next;
	}

}
