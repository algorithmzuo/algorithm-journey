package class046;

import java.util.Arrays;

// 每个元音包含偶数次的最长子字符串
// 给你一个字符串 s ，请你返回满足以下条件的最长子字符串的长度
// 每个元音字母，即 'a'，'e'，'i'，'o'，'u'
// 在子字符串中都恰好出现了偶数次。
// 测试链接 : https://leetcode.cn/problems/find-the-longest-substring-containing-vowels-in-even-counts/
public class Code07_EvenCountsLongestSubarray {

	public static int findTheLongestSubstring(String s) {
		int n = s.length();
		int[] map = new int[32];
		Arrays.fill(map, -2);
		map[0] = -1;
		int ans = 0;
		for (int i = 0, status = 0, m; i < n; i++) {
			m = move(s.charAt(i));
			if (m != -1) {
				status ^= 1 << m;
			}
			if (map[status] != -2) {
				ans = Math.max(ans, i - map[status]);
			} else {
				map[status] = i;
			}
		}
		return ans;
	}

	public static int move(char cha) {
		switch (cha) {
		    case 'a': return 0;
		    case 'e': return 1;
		    case 'i': return 2;
		    case 'o': return 3;
		    case 'u': return 4;
		    default: return -1;
		}
	}

}
