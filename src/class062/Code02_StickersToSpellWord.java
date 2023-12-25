package class062;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

// 贴纸拼词
// 我们有 n 种不同的贴纸。每个贴纸上都有一个小写的英文单词。
// 您想要拼写出给定的字符串 target ，方法是从收集的贴纸中切割单个字母并重新排列它们
// 如果你愿意，你可以多次使用每个贴纸，每个贴纸的数量是无限的。
// 返回你需要拼出 target 的最小贴纸数量。如果任务不可能，则返回 -1
// 注意：在所有的测试用例中，所有的单词都是从 1000 个最常见的美国英语单词中随机选择的
// 并且 target 被选择为两个随机单词的连接。
// 测试链接 : https://leetcode.cn/problems/stickers-to-spell-word/
public class Code02_StickersToSpellWord {

	public static int MAXN = 401;

	public static String[] queue = new String[MAXN];

	public static int l, r;

	// 下标0 -> a
	// 下标1 -> b
	// 下标2 -> c
	// ...
	// 下标25 -> z
	public static ArrayList<ArrayList<String>> graph = new ArrayList<>();

	static {
		for (int i = 0; i < 26; i++) {
			graph.add(new ArrayList<>());
		}
	}

	public static HashSet<String> visited = new HashSet<>();

	// 宽度优先遍历的解法
	// 也可以使用动态规划
	// 后续课程会有动态规划专题讲解
	public static int minStickers(String[] stickers, String target) {
		for (int i = 0; i < 26; i++) {
			graph.get(i).clear();
		}
		visited.clear();
		for (String str : stickers) {
			str = sort(str);
			for (int i = 0; i < str.length(); i++) {
				if (i == 0 || str.charAt(i) != str.charAt(i - 1)) {
					graph.get(str.charAt(i) - 'a').add(str);
				}
			}
		}
		target = sort(target);
		visited.add(target);
		l = r = 0;
		queue[r++] = target;
		int level = 1;
		// 使用队列的形式是整层弹出
		while (l < r) {
			int size = r - l;
			for (int i = 0; i < size; i++) {
				String cur = queue[l++];
				for (String s : graph.get(cur.charAt(0) - 'a')) {
					String next = next(cur, s);
					if (next.equals("")) {
						return level;
					} else if (!visited.contains(next)) {
						visited.add(next);
						queue[r++] = next;
					}
				}
			}
			level++;
		}
		return -1;
	}

	public static String sort(String str) {
		char[] s = str.toCharArray();
		Arrays.sort(s);
		return String.valueOf(s);
	}

	public static String next(String t, String s) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0, j = 0; i < t.length();) {
			if (j == s.length()) {
				builder.append(t.charAt(i++));
			} else {
				if (t.charAt(i) < s.charAt(j)) {
					builder.append(t.charAt(i++));
				} else if (t.charAt(i) > s.charAt(j)) {
					j++;
				} else {
					i++;
					j++;
				}
			}
		}
		return builder.toString();
	}

}
