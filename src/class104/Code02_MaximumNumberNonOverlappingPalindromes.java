package class104;

// 不重叠回文子串的最多数目
// 给定一个字符串str和一个正数k
// 你可以随意把str切分成多个子串
// 目的是找到某一种划分方案，有尽可能多的回文子串
// 并且每个回文子串都要求长度>=k、且彼此没有重合的部分
// 返回最多能划分出几个这样的回文子串
// 测试链接 : https://leetcode.cn/problems/maximum-number-of-non-overlapping-palindrome-substrings/
public class Code02_MaximumNumberNonOverlappingPalindromes {

	// 时间复杂度O(n)
	public static int maxPalindromes(String s, int k) {
		char[] str = manacherString(s);
		int[] p = new int[str.length];
		int ans = 0;
		int next = 0;
		while ((next = find(str, p, next, k)) != -1) {
			next = str[next] == '#' ? next : (next + 1);
			ans++;
		}
		return ans;
	}

	public static char[] manacherString(String s) {
		char[] str = s.toCharArray();
		char[] ans = new char[s.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != ans.length; i++) {
			ans[i] = (i & 1) == 0 ? '#' : str[index++];
		}
		return ans;
	}

	// s[l...]字符串只在这个范围上，且s[l]一定是'#'
	// 从下标l开始，之前都不算，一旦有某个中心回文半径>k，马上返回右边界
	public static int find(char[] s, int[] p, int l, int k) {
		int c = l - 1;
		int r = l - 1;
		int n = s.length;
		for (int i = l; i < s.length; i++) {
			p[i] = r > i ? Math.min(p[2 * c - i], r - i) : 1;
			while (i + p[i] < n && i - p[i] > l - 1 && s[i + p[i]] == s[i - p[i]]) {
				if (++p[i] > k) {
					return i + k;
				}
			}
			if (i + p[i] > r) {
				r = i + p[i];
				c = i;
			}
		}
		return -1;
	}

}
