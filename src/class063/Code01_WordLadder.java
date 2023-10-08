package class063;

import java.util.HashSet;
import java.util.List;

// 单词接龙
// 字典 wordList 中从单词 beginWord 和 endWord 的 转换序列
// 是一个按下述规格形成的序列 beginWord -> s1 -> s2 -> ... -> sk ：
// 每一对相邻的单词只差一个字母。
// 对于 1 <= i <= k 时，每个 si 都在 wordList 中
// 注意， beginWord 不需要在 wordList 中。sk == endWord
// 给你两个单词 beginWord 和 endWord 和一个字典 wordList
// 返回 从 beginWord 到 endWord 的 最短转换序列 中的 单词数目
// 如果不存在这样的转换序列，返回 0 。
// 测试链接 : https://leetcode.cn/problems/word-ladder/
public class Code01_WordLadder {

	public static int ladderLength(String begin, String end, List<String> wordList) {
		HashSet<String> dict = new HashSet<>(wordList);
		if (!dict.contains(end)) {
			return 0;
		}
		HashSet<String> froms = new HashSet<>();
		HashSet<String> ends = new HashSet<>();
		HashSet<String> nexts = new HashSet<>();
		froms.add(begin);
		ends.add(end);
		for (int len = 2; !froms.isEmpty(); len++) {
			nexts.clear();
			for (String word : froms) {
				for (int j = 0; j < word.length(); j++) {
					char[] ch = word.toCharArray();
					for (char c = 'a'; c <= 'z'; c++) {
						if (c != word.charAt(j)) {
							ch[j] = c;
							String next = String.valueOf(ch);
							if (ends.contains(next)) {
								return len;
							}
							if (dict.contains(next)) {
								dict.remove(next);
								nexts.add(next);
							}
						}
					}
				}
			}
			if (nexts.size() <= ends.size()) {
				HashSet<String> tmp = froms;
				froms = nexts;
				nexts = tmp;
			} else {
				HashSet<String> tmp = froms;
				froms = ends;
				ends = nexts;
				nexts = tmp;
			}
		}
		return 0;
	}

}
