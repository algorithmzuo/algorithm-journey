package class051;

import java.util.Arrays;

// 无重复字符的最长子串
// 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
// 测试链接 : https://leetcode.cn/problems/longest-substring-without-repeating-characters/
public class Code01_LongestSubstringWithoutRepeatingCharacters {

	public static int lengthOfLongestSubstring(String str) {
		int n = str.length();
		int[] last = new int[256];
		Arrays.fill(last, -1);
		int ans = 0;
		for (int i = 0, window = 0; i < n; i++) {
			window = Math.min(i - last[str.charAt(i)], window + 1);
			ans = Math.max(ans, window);
			last[str.charAt(i)] = i;
		}
		return ans;
	}

}
