package class058;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;

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
public class Code06_WordLadderII {

	public static List<List<String>> findLadders(String start, String end, List<String> list) {
		list.add(start);
		HashMap<String, List<String>> graph = generateGraph(list);
		HashMap<String, Integer> fromDistance = distances(start, graph);
		List<List<String>> ans = new ArrayList<>();
		if (!fromDistance.containsKey(end)) {
			return ans;
		}
		HashMap<String, Integer> toDistance = distances(end, graph);
		ArrayList<String> path = new ArrayList<>();
		paths(start, end, graph, fromDistance, toDistance, path, ans);
		return ans;
	}

	public static HashMap<String, List<String>> generateGraph(List<String> words) {
		HashSet<String> dict = new HashSet<>(words);
		HashMap<String, List<String>> nexts = new HashMap<>();
		for (int i = 0; i < words.size(); i++) {
			nexts.put(words.get(i), nextWords(words.get(i), dict));
		}
		return nexts;
	}

	public static List<String> nextWords(String word, HashSet<String> dict) {
		ArrayList<String> ans = new ArrayList<String>();
		char[] chs = word.toCharArray();
		for (char cur = 'a'; cur <= 'z'; cur++) {
			for (int i = 0; i < chs.length; i++) {
				if (chs[i] != cur) {
					char tmp = chs[i];
					chs[i] = cur;
					if (dict.contains(String.valueOf(chs))) {
						ans.add(String.valueOf(chs));
					}
					chs[i] = tmp;
				}
			}
		}
		return ans;
	}

	public static HashMap<String, Integer> distances(String start, HashMap<String, List<String>> graph) {
		HashMap<String, Integer> distances = new HashMap<>();
		distances.put(start, 0);
		Queue<String> queue = new ArrayDeque<>();
		queue.offer(start);
		while (!queue.isEmpty()) {
			String cur = queue.poll();
			for (String next : graph.get(cur)) {
				if (!distances.containsKey(next)) {
					distances.put(next, distances.get(cur) + 1);
					queue.offer(next);
				}
			}
		}
		return distances;
	}

	public static void paths(String cur, String end, HashMap<String, List<String>> graph,
			HashMap<String, Integer> fromDistance, HashMap<String, Integer> toDistance, ArrayList<String> path,
			List<List<String>> ans) {
		path.add(cur);
		if (end.equals(cur)) {
			ans.add(new ArrayList<String>(path));
		} else {
			for (String next : graph.get(cur)) {
				if (fromDistance.get(next) == fromDistance.get(cur) + 1
						&& toDistance.get(next) == toDistance.get(cur) - 1) {
					paths(next, end, graph, fromDistance, toDistance, path, ans);
				}
			}
		}
		path.remove(path.size() - 1);
	}

}
