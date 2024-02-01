package class104;

// 不重叠回文子串的最多数目
// 给定一个字符串str和一个正数k
// 你可以随意把str切分成多个子串
// 目的是找到某一种划分方案，有尽可能多的回文子串
// 并且每个回文子串都要求长度>=k、且彼此没有重合的部分
// 返回最多能划分出几个这样的回文子串
// 测试链接 : https://leetcode.cn/problems/maximum-number-of-non-overlapping-palindrome-substrings/
public class Code03_SplitMaximumPalindromes {

	// 时间复杂度O(n)
	public static int maxPalindromes(String str, int k) {
		manacherss(str.toCharArray());
		int ans = 0;
		int next = 0;
		while ((next = find(next, k)) != -1) {
			next = ss[next] == '#' ? next : (next + 1);
			ans++;
		}
		return ans;
	}

	public static int MAXN = 2001;

	public static char[] ss = new char[MAXN << 1];

	public static int[] p = new int[MAXN << 1];

	public static int n;

	public static void manacherss(char[] a) {
		n = a.length * 2 + 1;
		for (int i = 0, j = 0; i < n; i++) {
			ss[i] = (i & 1) == 0 ? '#' : a[j++];
		}
	}

	// s[l...]字符串只在这个范围上，且s[l]一定是'#'
	// 从下标l开始，之前都不算，一旦有某个中心回文半径>k，马上返回右边界
	public static int find(int l, int k) {
		for (int i = l, c = l - 1, r = l - 1, len; i < n; i++) {
			len = r > i ? Math.min(p[2 * c - i], r - i) : 1;
			while (i + len < n && i - len > l - 1 && ss[i + len] == ss[i - len]) {
				if (++len > k) {
					return i + k;
				}
			}
			if (i + len > r) {
				r = i + len;
				c = i;
			}
			p[i] = len;
		}
		return -1;
	}

}
