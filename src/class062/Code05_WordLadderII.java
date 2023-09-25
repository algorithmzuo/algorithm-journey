package class062;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

// 单词接龙 II
// 按字典 wordList 完成从单词 beginWord 到单词 endWord 转化
// 一个表示此过程的 转换序列 是形式上像 
// beginWord -> s1 -> s2 -> ... -> sk 这样的单词序列，并满足：
// 每对相邻的单词之间仅有单个字母不同
// 转换过程中的每个单词 si（1 <= i <= k）必须是字典 wordList 中的单词
// 注意，beginWord 不必是字典 wordList 中的单词
// sk == endWord
// 给你两个单词 beginWord 和 endWord ，以及一个字典 wordList
// 请你找出并返回所有从 beginWord 到 endWord 的 最短转换序列
// 如果不存在这样的转换序列，返回一个空列表
// 每个序列都应该以单词列表 [beginWord, s1, s2, ..., sk] 的形式返回
// 测试链接 : https://leetcode.cn/problems/word-ladder-ii/
public class Code05_WordLadderII {

	public static HashSet<String> dict;

	public static HashSet<String> curLevel = new HashSet<>();

	public static HashSet<String> nextLevel = new HashSet<>();

	public static HashMap<String, ArrayList<String>> graph = new HashMap<>();

	public static LinkedList<String> path = new LinkedList<>();

	public static List<List<String>> ans = new ArrayList<>();

	public static void build(String beginWord, List<String> wordList) {
		dict = new HashSet<>(wordList);
		graph.clear();
		ans.clear();
		curLevel.clear();
		nextLevel.clear();
	}

	public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
		build(beginWord, wordList);
		if (!dict.contains(endWord)) {
			return ans;
		}
		if (bfs(beginWord, endWord)) {
			dfs(endWord, beginWord);
		}
		return ans;
	}

	public static boolean bfs(String begin, String end) {
		boolean find = false;
		curLevel.add(begin);
		while (!curLevel.isEmpty()) {
			dict.removeAll(curLevel);
			for (String word : curLevel) {
				char[] w = word.toCharArray();
				for (int i = 0; i < w.length; i++) {
					char old = w[i];
					for (char ch = 'a'; ch <= 'z'; ch++) {
						w[i] = ch;
						String str = String.valueOf(w);
						if (dict.contains(str) && !str.equals(word)) {
							if (str.equals(end)) {
								find = true;
							}
							graph.putIfAbsent(str, new ArrayList<>());
							graph.get(str).add(word);
							if (!nextLevel.contains(str)) {
								nextLevel.add(str);
							}
						}
					}
					w[i] = old;
				}
			}
			if (find) {
				return true;
			} else {
				HashSet<String> tmp = curLevel;
				curLevel = nextLevel;
				nextLevel = tmp;
				nextLevel.clear();
			}
		}
		return false;
	}

	public static void dfs(String word, String aim) {
		path.addFirst(word);
		if (word.equals(aim)) {
			ans.add(new ArrayList<>(path));
		} else if (graph.containsKey(word)) {
			for (String next : graph.get(word)) {
				dfs(next, aim);
			}
		}
		path.removeFirst();
	}

}
