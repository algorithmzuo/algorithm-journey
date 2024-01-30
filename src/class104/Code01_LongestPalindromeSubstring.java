package class104;

// 最长回文子串
// 找到字符串s中最长的回文子串并返回
// 测试链接 : https://leetcode.cn/problems/longest-palindromic-substring/
public class Code01_LongestPalindromeSubstring {

	public static String longestPalindrome(String s) {
		manacher(s);
		return s.substring(end - max, end);
	}

	public static int MAXN = 1001;

	public static char[] ss = new char[MAXN << 1];

	public static int[] p = new int[MAXN << 1];

	public static int n, max, end;

	public static void manacher(String str) {
		manacherss(str.toCharArray());
		max = end = 0;
		for (int i = 0, c = -1, r = -1; i < n; i++) {
			p[i] = r > i ? Math.min(p[2 * c - i], r - i) : 1;
			while (i + p[i] < n && i - p[i] >= 0 && ss[i + p[i]] == ss[i - p[i]]) {
				p[i]++;
			}
			if (i + p[i] > r) {
				r = i + p[i];
				c = i;
			}
			if (p[i] > max) {
				max = p[i] - 1;
				end = (i + p[i]) / 2;
			}
		}
	}

	public static void manacherss(char[] a) {
		n = a.length * 2 + 1;
		for (int i = 0, j = 0; i < n; i++) {
			ss[i] = (i & 1) == 0 ? '#' : a[j++];
		}
	}

}
