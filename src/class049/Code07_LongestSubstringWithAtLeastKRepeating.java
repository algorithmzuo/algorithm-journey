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
		// 每次要求子串必须含有require种字符，每种字符都必须>=k次，这样的最长子串是多长
		for (int require = 1; require <= 26; require++) {
			Arrays.fill(cnts, 0);
			// collect : 窗口中一共收集到的种类数
			// satisfy : 窗口中达标的种类数(次数>=k)
			for (int l = 0, r = 0, collect = 0, satisfy = 0; r < n; r++) {
				cnts[s[r]]++;
				if (cnts[s[r]] == 1) {
					collect++;
				}
				if (cnts[s[r]] == k) {
					satisfy++;
				}
				// l....r 种类超了！
				// l位置的字符，窗口中吐出来！
				while (collect > require) {
					if (cnts[s[l]] == 1) {
						collect--;
					}
					if (cnts[s[l]] == k) {
						satisfy--;
					}
					cnts[s[l++]]--;
				}
				// l.....r : 子串以r位置的字符结尾，且种类数不超的，最大长度！
				if (satisfy == require) {
					ans = Math.max(ans, r - l + 1);
				}
			}
		}
		return ans;
	}

}
