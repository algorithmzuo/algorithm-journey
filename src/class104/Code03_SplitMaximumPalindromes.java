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

	// 扩展串ss从l位置开始往右寻找回文，且ss[l]一定是'#'
	// 一旦有某个中心的回文半径>k，马上返回最右下标
	// 表示找到了距离l最近且长度>=k的回文串
	// 返回的这个最右下标一定要命中'#'
	// 如果没有命中返回(最右下标+1)，让其一定命中'#'
	// 如果不存在距离l最近且长度>=k的回文串，返回-1
	public static int find(int l, int k) {
		for (int i = l, c = l, r = l, len; i < n; i++) {
			len = r > i ? Math.min(p[2 * c - i], r - i) : 1;
			while (i + len < n && i - len >= l && ss[i + len] == ss[i - len]) {
				if (++len > k) {
					return i + k + (ss[i + k] != '#' ? 1 : 0);
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
