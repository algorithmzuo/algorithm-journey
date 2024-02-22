package class105;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 串联所有单词的子串
// 给定一个字符串s和一个字符串数组words
// words中所有字符串长度相同
// s中的串联子串是指一个包含 words中所有字符串以任意顺序排列连接起来的子串
// 例如words = { "ab","cd","ef" }
// 那么"abcdef"、"abefcd"、"cdabef"、"cdefab"、"efabcd"、"efcdab"都是串联子串。 
// "acdbef"不是串联子串，因为他不是任何words排列的连接
// 返回所有串联子串在s中的开始索引
// 你可以以任意顺序返回答案
// 测试链接 : https://leetcode.cn/problems/substring-with-concatenation-of-all-words/
public class Code05_ConcatenationAllWords {

	// 如果s的长度为n，words里所有单词的总长度为m
	// 时间复杂度O(n + m)，最优解的时间复杂度与单词个数、单词长度是无关的
	// 所有题解都没有做到这个复杂度的
	// 虽然这个做法打败比例没有到100%，但那是因为测试数据量不够大
	// 所以最优解的时间复杂度优势没有体现出来
	// 这个方法绝对是最优解，只有用字符串哈希，时间复杂度才能到最优
	public static List<Integer> findSubstring(String s, String[] words) {
		List<Integer> ans = new ArrayList<>();
		if (s == null || s.length() == 0 || words == null || words.length == 0) {
			return ans;
		}
		// words的词频表
		HashMap<Long, Integer> map = new HashMap<>();
		for (String key : words) {
			long v = hash(key);
			map.put(v, map.getOrDefault(v, 0) + 1);
		}
		build(s);
		int n = s.length();
		int wordLen = words[0].length();
		int wordNum = words.length;
		int allLen = wordLen * wordNum;
		// 窗口的词频表
		HashMap<Long, Integer> window = new HashMap<>();
		for (int init = 0; init < wordLen && init + allLen <= n; init++) {
			// init是当前组的首个开头
			int debt = wordNum;
			// 建立起窗口
			for (int l = init, r = init + wordLen, part = 0; part < wordNum; l += wordLen, r += wordLen, part++) {
				long cur = hash(l, r);
				window.put(cur, window.getOrDefault(cur, 0) + 1);
				if (window.get(cur) <= map.getOrDefault(cur, 0)) {
					debt--;
				}
			}
			if (debt == 0) {
				ans.add(init);
			}
			// 接下来窗口进一个、出一个
			for (int l1 = init, r1 = init + wordLen, l2 = init + allLen,
					r2 = init + allLen + wordLen; r2 <= n; l1 += wordLen, r1 += wordLen, l2 += wordLen, r2 += wordLen) {
				long out = hash(l1, r1);
				long in = hash(l2, r2);
				window.put(out, window.get(out) - 1);
				if (window.get(out) < map.getOrDefault(out, 0)) {
					debt++;
				}
				window.put(in, window.getOrDefault(in, 0) + 1);
				if (window.get(in) <= map.getOrDefault(in, 0)) {
					debt--;
				}
				if (debt == 0) {
					ans.add(r1);
				}
			}
			window.clear();
		}
		return ans;
	}

	public static int MAXN = 10001;

	public static int base = 499;

	public static long[] pow = new long[MAXN];

	public static long[] hash = new long[MAXN];

	public static void build(String str) {
		pow[0] = 1;
		for (int j = 1; j < MAXN; j++) {
			pow[j] = pow[j - 1] * base;
		}
		hash[0] = str.charAt(0) - 'a' + 1;
		for (int j = 1; j < str.length(); j++) {
			hash[j] = hash[j - 1] * base + str.charAt(j) - 'a' + 1;
		}
	}

	// 范围是s[l,r)，左闭右开
	public static long hash(int l, int r) {
		long ans = hash[r - 1];
		ans -= l == 0 ? 0 : (hash[l - 1] * pow[r - l]);
		return ans;
	}

	// 计算一个字符串的哈希值
	public static long hash(String str) {
		if (str.equals("")) {
			return 0;
		}
		int n = str.length();
		long ans = str.charAt(0) - 'a' + 1;
		for (int j = 1; j < n; j++) {
			ans = ans * base + str.charAt(j) - 'a' + 1;
		}
		return ans;
	}

}
