package class059;

import java.util.ArrayList;
import java.util.Arrays;

// 火星词典
// 现有一种使用英语字母的火星语言
// 这门语言的字母顺序对你来说是未知的。
// 给你一个来自这种外星语言字典的字符串列表 words
// words 中的字符串已经 按这门新语言的字母顺序进行了排序 。
// 如果这种说法是错误的，并且给出的 words 不能对应任何字母的顺序，则返回 ""
// 否则，返回一个按新语言规则的 字典递增顺序 排序的独特字符串
// 如果有多个解决方案，则返回其中任意一个
// words中的单词一定都是小写英文字母组成的
// 测试链接 : https://leetcode.cn/problems/alien-dictionary/
public class Code04_AlienDictionary {

	public static String alienOrder(String[] words) {
		// 入度表，26种字符
		int[] indegree = new int[26];
		Arrays.fill(indegree, -1);
		for (String w : words) {
			for (int i = 0; i < w.length(); i++) {
				indegree[w.charAt(i) - 'a'] = 0;
			}
		}
		// 'a' -> 0
		// 'b' -> 1
		// 'z' -> 25
		// x -> x - 'a'
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < 26; i++) {
			graph.add(new ArrayList<>());
		}
		for (int i = 0, j, len; i < words.length - 1; i++) {
			String cur = words[i];
			String next = words[i + 1];
			j = 0;
			len = Math.min(cur.length(), next.length());
			for (; j < len; j++) {
				if (cur.charAt(j) != next.charAt(j)) {
					graph.get(cur.charAt(j) - 'a').add(next.charAt(j) - 'a');
					indegree[next.charAt(j) - 'a']++;
					break;
				}
			}
			if (j < cur.length() && j == next.length()) {
				return "";
			}
		}
		int[] queue = new int[26];
		int l = 0, r = 0;
		int kinds = 0;
		for (int i = 0; i < 26; i++) {
			if (indegree[i] != -1) {
				kinds++;
			}
			if (indegree[i] == 0) {
				queue[r++] = i;
			}
		}
		StringBuilder ans = new StringBuilder();
		while (l < r) {
			int cur = queue[l++];
			ans.append((char) (cur + 'a'));
			for (int next : graph.get(cur)) {
				if (--indegree[next] == 0) {
					queue[r++] = next;
				}
			}
		}
		return ans.length() == kinds ? ans.toString() : "";
	}

}
