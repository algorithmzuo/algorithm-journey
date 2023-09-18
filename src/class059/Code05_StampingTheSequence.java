package class059;

import java.util.ArrayList;
import java.util.Arrays;

// 戳印序列
// 你想要用小写字母组成一个目标字符串 target。 
// 开始的时候，序列由 target.length 个 '?' 记号组成
// 而你有一个小写字母印章 stamp。
// 在每个回合，你可以将印章放在序列上，并将序列中的每个字母替换为印章上的相应字母
// 你最多可以进行 10 * target.length  个回合
// 举个例子，如果初始序列为 "?????"，而你的印章 stamp 是 "abc"
// 那么在第一回合，你可以得到 "abc??"、"?abc?"、"??abc"
//（请注意，印章必须完全包含在序列的边界内才能盖下去。）
// 如果可以印出序列，那么返回一个数组，该数组由每个回合中被印下的最左边字母的索引组成
// 如果不能印出序列，就返回一个空数组。
// 例如，如果序列是 "ababc"，印章是 "abc"
// 那么我们就可以返回与操作 "?????" -> "abc??" -> "ababc" 相对应的答案 [0, 2]
// 另外，如果可以印出序列，那么需要保证可以在 10 * target.length 个回合内完成
// 任何超过此数字的答案将不被接受
// 测试链接 : https://leetcode.cn/problems/stamping-the-sequence/
public class Code05_StampingTheSequence {

	public static int[] movesToStamp(String stamp, String target) {
		char[] s = stamp.toCharArray();
		char[] t = target.toCharArray();
		int m = s.length;
		int n = t.length;
		int[] inDegrees = new int[n - m + 1];
		Arrays.fill(inDegrees, m);
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		int[] queue = new int[n - m + 1];
		int l = 0, r = 0;
		for (int i = 0; i <= n - m; i++) {
			for (int j = 0; j < m; j++) {
				if (t[i + j] == s[j]) {
					if (--inDegrees[i] == 0) {
						queue[r++] = i;
					}
				} else {
					graph.get(i + j).add(i);
				}
			}
		}
		boolean[] visited = new boolean[n];
		int[] path = new int[n - m + 1];
		int size = 0;
		while (l < r) {
			int cur = queue[l++];
			path[size++] = cur;
			for (int i = 0; i < m; i++) {
				if (!visited[cur + i]) {
					visited[cur + i] = true;
					for (int next : graph.get(cur + i)) {
						if (--inDegrees[next] == 0) {
							queue[r++] = next;
						}
					}
				}
			}
		}
		if (size != n - m + 1) {
			return new int[0];
		}
		for (int i = 0, j = size - 1; i < j; i++, j--) {
			int tmp = path[i];
			path[i] = path[j];
			path[j] = tmp;
		}
		return path;
	}

}
