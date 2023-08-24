package class045;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 在二维字符数组中搜索可能的单词
// 给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words
// 返回所有二维网格上的单词。单词必须按照字母顺序，通过 相邻的单元格 内的字母构成
// 其中“相邻”单元格是那些水平相邻或垂直相邻的单元格
// 同一个单元格内的字母在一个单词中不允许被重复使用
// 1 <= m, n <= 12
// 1 <= words.length <= 3 * 10^4
// 1 <= words[i].length <= 10
// 测试链接 : https://leetcode.cn/problems/word-search-ii/
public class Code03_WordSearchII {

	public static List<String> findWords(char[][] board, String[] words) {
		build(words);
		List<String> ans = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				dfs(board, i, j, 1, ans);
			}
		}
		clear();
		return ans;
	}

	// board : 二维网格
	// i,j : 此时来到的格子位置，i行、j列
	// t : 前缀树的编号
	// List<String> ans : 收集到了哪些字符串，都放入ans
	// 返回值 : 收集到了几个字符串
	public static int dfs(char[][] board, int i, int j, int t, List<String> ans) {
		// 越界 或者 走了回头路，直接返回0
		if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] == 0) {
			return 0;
		}
		// 不越界 且 不是回头路
		// 用tmp记录当前字符
		char tmp = board[i][j];
		// 路的编号
		// a -> 0
		// b -> 1
		// ...
		// z -> 25
		int road = tmp - 'a';
		t = tree[t][road];
		if (pass[t] == 0) {
			return 0;
		}
		// i，j位置有必要来
		// fix ：从当前i，j位置出发，一共收集到了几个字符串
		int fix = 0;
		if (end[t] != null) {
			fix++;
			ans.add(end[t]);
			end[t] = null;
		}
		// 把i，j位置的字符，改成0，后续的过程，是不可以再来到i，j位置的！
		board[i][j] = 0;
		fix += dfs(board, i - 1, j, t, ans);
		fix += dfs(board, i + 1, j, t, ans);
		fix += dfs(board, i, j - 1, t, ans);
		fix += dfs(board, i, j + 1, t, ans);
		pass[t] -= fix;
		board[i][j] = tmp;
		return fix;
	}

	public static int MAXN = 10001;

	public static int[][] tree = new int[MAXN][26];

	public static int[] pass = new int[MAXN];

	public static String[] end = new String[MAXN];

	public static int cnt;

	public static void build(String[] words) {
		cnt = 1;
		for (String word : words) {
			int cur = 1;
			pass[cur]++;
			for (int i = 0, path; i < word.length(); i++) {
				path = word.charAt(i) - 'a';
				if (tree[cur][path] == 0) {
					tree[cur][path] = ++cnt;
				}
				cur = tree[cur][path];
				pass[cur]++;
			}
			end[cur] = word;
		}
	}

	public static void clear() {
		for (int i = 1; i <= cnt; i++) {
			Arrays.fill(tree[i], 0);
			pass[i] = 0;
			end[i] = null;
		}
	}

}