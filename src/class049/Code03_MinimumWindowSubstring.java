package class049;

// 最小覆盖子串
// 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串
// 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
// 测试链接 : https://leetcode.cn/problems/minimum-window-substring/
public class Code03_MinimumWindowSubstring {

	public static String minWindow(String str, String tar) {
		if (str.length() < tar.length()) {
			return "";
		}
		char[] s = str.toCharArray();
		char[] t = tar.toCharArray();
		int[] cnts = new int[256];
		for (char cha : t) {
			cnts[cha]--;
		}
		// 最小覆盖子串的长度
		int len = Integer.MAX_VALUE;
		// 从哪个位置开头，发现的这个最小覆盖子串
		int start = 0;
		for (int l = 0, r = 0, debt = t.length; r < s.length; r++) {
			// s[r] 当前字符 -> int
			// cnts[s[r]] : 当前字符欠债情况，负数就是欠债，正数就是多给的
			if (cnts[s[r]]++ < 0) {
				debt--;
			}
			if (debt == 0) {
				// r位置结尾，真的有覆盖子串！
				// 看看这个覆盖子串能不能尽量短
				while (cnts[s[l]] > 0) {
					// l位置的字符能拿回
					cnts[s[l++]]--;
				}
				// 从while里面出来，
				// l....r就是r位置结尾的最小覆盖子串
				if (r - l + 1 < len) {
					len = r - l + 1;
					start = l;
				}
			}
		}
		return len == Integer.MAX_VALUE ? "" : str.substring(start, start + len);
	}

}
