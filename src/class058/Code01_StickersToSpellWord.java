package class058;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// 贴纸拼词
// 我们有 n 种不同的贴纸。每个贴纸上都有一个小写的英文单词。
// 您想要拼写出给定的字符串 target ，方法是从收集的贴纸中切割单个字母并重新排列它们
// 如果你愿意，你可以多次使用每个贴纸，每个贴纸的数量是无限的。
// 返回你需要拼出 target 的最小贴纸数量。如果任务不可能，则返回 -1
// 注意：在所有的测试用例中，所有的单词都是从 1000 个最常见的美国英语单词中随机选择的
// 并且 target 被选择为两个随机单词的连接。
// 测试链接 : https://leetcode.cn/problems/stickers-to-spell-word/
public class Code01_StickersToSpellWord {

	// 如果leetcode增大了数据量导致不够用，就改大这个值
	public static int MAXN = 501;

	public static String[] queue = new String[MAXN];

	public static int l, r;

	public static ArrayList<ArrayList<String>> teams = new ArrayList<>();

	public static HashMap<String, Integer> distance = new HashMap<>();

	static {
		for (int i = 0; i < 26; i++) {
			teams.add(new ArrayList<>());
		}
	}

	// 宽度优先遍历的解法
	// 也可以动态规划的解法，不过并不好理解
	// 推荐宽度优先遍历的解法
	public static int minStickers(String[] stickers, String target) {
		for (int i = 0; i < 26; i++) {
			teams.get(i).clear();
		}
		distance.clear();
		for (String str : stickers) {
			str = convert(str);
			for (int i = 0; i < str.length(); i++) {
				teams.get(str.charAt(i) - 'a').add(str);
			}
		}
		target = convert(target);
		distance.put(target, 0);
		l = r = 0;
		queue[r++] = target;
		while (l < r) {
			String t = queue[l++];
			int dis = distance.get(t);
			for (String s : teams.get(t.charAt(0) - 'a')) {
				String next = minus(t, s);
				if (next.equals("")) {
					return dis + 1;
				} else {
					if (!distance.containsKey(next)) {
						distance.put(next, dis + 1);
						queue[r++] = next;
					}
				}
			}
		}
		return -1;
	}

	public static String convert(String str) {
		char[] s = str.toCharArray();
		Arrays.sort(s);
		return String.valueOf(s);
	}

	public static String minus(String t, String s) {
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
