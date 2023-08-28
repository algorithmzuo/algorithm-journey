package class051;

// 最小覆盖子串
// 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串
// 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
// 测试链接 : https://leetcode.cn/problems/minimum-window-substring/
public class Code02_MinimumWindowSubstring {

	public static String minWindow(String str, String target) {
		if (str.length() < target.length()) {
			return "";
		}
		char[] s = str.toCharArray();
		char[] t = target.toCharArray();
		int[] map = new int[256];
		for (char cha : t) {
			map[cha]++;
		}
		int window = Integer.MAX_VALUE;
		int left = 0;
		for (int l = 0, r = 0, debt = t.length; r < s.length; r++) {
			if (--map[s[r]] >= 0) {
				debt--;
			}
			if (debt == 0) {
				while (debt == 0) {
					if (++map[s[l++]] > 0) {
						debt++;
					}
				}
				if (r - l + 2 < window) {
					window = r - l + 2;
					left = l - 1;
				}
			}
		}
		return window == Integer.MAX_VALUE ? "" : str.substring(left, left + window);
	}

}
