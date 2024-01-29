package class103;

// 不重叠回文子字符串的最大数目
// 给定一个字符串str和一个正数k
// 你可以随意的划分str成多个子串，目的是找到在某一种划分方案中
// 有尽可能多的回文子串，要求长度>=k并且没有重合
// 返回所有划分方案中，最多能划分出几个这样的回文子串
// 测试链接 : https://leetcode.cn/problems/maximum-number-of-non-overlapping-palindrome-substrings/
public class Code03 {

	// 时间复杂度O(n)
	public static int maxPalindromes(String s, int k) {
		char[] str = manacherString(s);
		int[] p = new int[str.length];
		int ans = 0;
		int next = 0;
		while ((next = manacherFind(str, p, next, k)) != -1) {
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
	public static int manacherFind(char[] s, int[] p, int l, int k) {
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
