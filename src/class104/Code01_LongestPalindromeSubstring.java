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
		for (int i = 0, c = 0, r = 0, len; i < n; i++) {
			len = r > i ? Math.min(p[2 * c - i], r - i) : 1;
			while (i + len < n && i - len >= 0 && ss[i + len] == ss[i - len]) {
				len++;
			}
			if (i + len > r) {
				r = i + len;
				c = i;
			}
			if (len > max) {
				max = len - 1;
				end = (i + len - 1) / 2;
			}
			p[i] = len;
		}
	}

	public static void manacherss(char[] a) {
		n = a.length * 2 + 1;
		for (int i = 0, j = 0; i < n; i++) {
			ss[i] = (i & 1) == 0 ? '#' : a[j++];
		}
	}

}
