package class051;

import java.util.Arrays;

// 至少有 K 个重复字符的最长子串
// 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串
// 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度
// 如果不存在这样的子字符串，则返回 0。
// 测试链接 : https://leetcode.cn/problems/longest-substring-with-at-least-k-repeating-characters/
public class Code05_LongestSubstringWithAtLeastKRepeating {

	public static int longestSubstring(String s, int k) {
		char[] str = s.toCharArray();
		int N = str.length;
		int max = 0;
		int[] cnts = new int[26];
		for (int require = 1; require <= 26; require++) {
			Arrays.fill(cnts, 0);
			int collect = 0;
			int satisfy = 0;
			int R = -1;
			for (int L = 0; L < N; L++) {
				while (R + 1 < N && !(collect == require && cnts[str[R + 1] - 'a'] == 0)) {
					R++;
					if (cnts[str[R] - 'a'] == 0) {
						collect++;
					}
					if (cnts[str[R] - 'a'] == k - 1) {
						satisfy++;
					}
					cnts[str[R] - 'a']++;
				}
				if (satisfy == require) {
					max = Math.max(max, R - L + 1);
				}
				if (cnts[str[L] - 'a'] == 1) {
					collect--;
				}
				if (cnts[str[L] - 'a'] == k) {
					satisfy--;
				}
				cnts[str[L] - 'a']--;
			}
		}
		return max;
	}

}
