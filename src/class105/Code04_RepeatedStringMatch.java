package class105;

// 重复叠加字符串匹配
// 给定两个字符串a和b，寻找重复叠加字符串a的最小次数，使得字符串b成为叠加后的字符串a的子串
// 如果不存在则返回-1
// 字符串"abc"重复叠加0次是""
// 重复叠加1次是"abc"
// 重复叠加2次是"abcabc"
// 测试链接 : https://leetcode.cn/problems/repeated-string-match/
public class Code04_RepeatedStringMatch {

	public static int repeatedStringMatch(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		int m = s2.length;
		// m / n 向上取整
		int k = (m + n - 1) / n;
		int len = 0;
		for (int cnt = 0; cnt <= k; cnt++) {
			for (int i = 0; i < n; i++) {
				s[len++] = s1[i];
			}
		}
		build(len);
		long h2 = s2[0] - 'a' + 1;
		for (int i = 1; i < m; i++) {
			h2 = h2 * base + s2[i] - 'a' + 1;
		}
		for (int l = 0, r = m - 1; r < len; l++, r++) {
			if (hash(l, r) == h2) {
				return r < n * k ? k : (k + 1);
			}
		}
		return -1;
	}

	public static int MAXN = 30001;

	public static char[] s = new char[MAXN];

	public static int base = 499;

	public static long[] pow = new long[MAXN];

	public static long[] hash = new long[MAXN];

	public static void build(int n) {
		pow[0] = 1;
		for (int i = 1; i < n; i++) {
			pow[i] = pow[i - 1] * base;
		}
		hash[0] = s[0] - 'a' + 1;
		for (int i = 1; i < n; i++) {
			hash[i] = hash[i - 1] * base + s[i] - 'a' + 1;
		}
	}

	public static long hash(int l, int r) {
		long ans = hash[r];
		ans -= l == 0 ? 0 : (hash[l - 1] * pow[r - l + 1]);
		return ans;
	}

}
