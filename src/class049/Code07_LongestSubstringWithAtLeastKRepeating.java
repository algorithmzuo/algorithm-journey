package class049;

import java.util.Arrays;

// 至少有K个重复字符的最长子串
// 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串
// 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度
// 如果不存在这样的子字符串，则返回 0。
// 测试链接 : https://leetcode.cn/problems/longest-substring-with-at-least-k-repeating-characters/
public class Code07_LongestSubstringWithAtLeastKRepeating {

	public static int longestSubstring(String str, int k) {
		char[] s = str.toCharArray();
		int n = s.length;
		int[] cnts = new int[256];
		int ans = 0;
		for (int require = 1; require <= 26; require++) {
			Arrays.fill(cnts, 0);
			for (int l = 0, r = 0, collect = 0, satisfy = 0; r < n; r++) {
				cnts[s[r]]++;
				if (cnts[s[r]] == 1) {
					collect++;
				}
				if (cnts[s[r]] == k) {
					satisfy++;
				}
				while (collect > require) {
					if (cnts[s[l]] == 1) {
						collect--;
					}
					if (cnts[s[l]] == k) {
						satisfy--;
					}
					cnts[s[l++]]--;
				}
				if (satisfy == require) {
					ans = Math.max(ans, r - l + 1);
				}
			}
		}
		return ans;
	}

}
