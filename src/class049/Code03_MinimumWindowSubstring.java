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
		int[] map = new int[256];
		for (char cha : t) {
			map[cha]--;
		}
		// 最小覆盖子串的长度
		int len = Integer.MAX_VALUE;
		// 从哪个位置开头，发现的最小覆盖子串
		int start = 0;
		// 总债务
		int debt = t.length;
		for (int l = 0, r = 0; r < s.length; r++) {
			// 窗口右边界向右，给出字符
			if (map[s[r]]++ < 0) {
				debt--;
			}
			if (debt == 0) {
				// 窗口左边界向右，拿回字符
				while (map[s[l]] > 0) {
					map[s[l++]]--;
				}
				// 以r位置结尾的达标窗口，更新答案
				if (r - l + 1 < len) {
					len = r - l + 1;
					start = l;
				}
			}
		}
		return len == Integer.MAX_VALUE ? "" : str.substring(start, start + len);
	}

}
