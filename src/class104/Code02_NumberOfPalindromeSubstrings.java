package class104;

// 回文子串数量
// 返回字符串s的回文子串数量
// 测试链接 : https://leetcode.cn/problems/palindromic-substrings/
public class Code02_NumberOfPalindromeSubstrings {

	public static int countSubstrings(String s) {
		manacher(s);
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans += p[i] / 2;
		}
		return ans;
	}

	public static int MAXN = 1001;

	public static char[] ss = new char[MAXN << 1];

	public static int[] p = new int[MAXN << 1];

	public static int n;

	public static void manacher(String str) {
		manacherss(str.toCharArray());
		for (int i = 0, c = 0, r = 0, len; i < n; i++) {
			len = r > i ? Math.min(p[2 * c - i], r - i) : 1;
			while (i + len < n && i - len >= 0 && ss[i + len] == ss[i - len]) {
				len++;
			}
			if (i + len > r) {
				r = i + len;
				c = i;
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
