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
		// 总词表
		HashSet<String> dict = new HashSet<>(wordList);
		if (!dict.contains(end)) {
			return 0;
		}
		// 数量小的一侧
		HashSet<String> smallLevel = new HashSet<>();
		// 数量大的一侧
		HashSet<String> bigLevel = new HashSet<>();
		// 由数量小的一侧，所扩展出的下一层列表
		HashSet<String> nextLevel = new HashSet<>();
		smallLevel.add(begin);
		bigLevel.add(end);
		for (int len = 2; !smallLevel.isEmpty(); len++) {
			for (String w : smallLevel) {
				// 从小侧扩展
				char[] word = w.toCharArray();
				for (int j = 0; j < word.length; j++) {
					// 每一位字符都试
					char old = word[j];
					for (char change = 'a'; change <= 'z'; change++) {
						// // 每一位字符都从a到z换一遍
						if (change != old) {
							word[j] = change;
							String next = String.valueOf(word);
							if (bigLevel.contains(next)) {
								return len;
							}
							if (dict.contains(next)) {
								dict.remove(next);
								nextLevel.add(next);
							}
						}
					}
					word[j] = old;
				}
			}
			if (nextLevel.size() <= bigLevel.size()) {
				HashSet<String> tmp = smallLevel;
				smallLevel = nextLevel;
				nextLevel = tmp;
			} else {
				HashSet<String> tmp = smallLevel;
				smallLevel = bigLevel;
				bigLevel = nextLevel;
				nextLevel = tmp;
			}
			nextLevel.clear();
		}
		return 0;
	}

}
