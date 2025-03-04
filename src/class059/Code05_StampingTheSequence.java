package class059;

import java.util.ArrayList;
import java.util.Arrays;

// 戳印序列
// 你想最终得到"abcbc"，认为初始序列为"?????"。印章是"abc"
// 那么可以先用印章盖出"??abc"的状态，
// 然后用印章最左字符和序列的0位置对齐，就盖出了"abcbc"
// 这个过程中，"??abc"中的a字符，被印章中的c字符覆盖了
// 每次盖章的时候，印章必须完全盖在序列内
// 给定一个字符串target是最终的目标，长度为n，认为初始序列为n个'?'
// 给定一个印章字符串stamp，目标是最终盖出target
// 但是印章的使用次数必须在10*n次以内
// 返回一个数组，该数组由每个回合中被印下的最左边字母的索引组成
// 上面的例子返回[2,0]，表示印章最左字符依次和序列2位置、序列0位置对齐盖下去，就得到了target
// 如果不能在10*n次内印出序列，就返回一个空数组
// 测试链接 : https://leetcode.cn/problems/stamping-the-sequence/
public class Code05_StampingTheSequence {
    /**
        stamp：abc targart aabcbc
          a a b c b c
        0:a x x               :2->0
        1:  a b c             :0
        2:    x x x           :3->1->0
        3:      x b c         :1->0
    todo 大致原理为：先假设每个位置都是错误的，对比每个位置开头的字符和印章是否匹配，如果完全匹配就入队列，表示这个位置可以修正别的点
                如果不匹配就建图，表示哪个位置错误被哪些开头的字符串包含，如果找到可以修正这些位置的点就--。
     */
	public static int[] movesToStamp(String stamp, String target) {
		char[] s = stamp.toCharArray();
		char[] t = target.toCharArray();
		int m = s.length;
		int n = t.length;
		int[] indegree = new int[n - m + 1];
        //todo 入度初始为stamp字符串的长度
		Arrays.fill(indegree, m);
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        //todo 最多盖n次图章
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		int[] queue = new int[n - m + 1];
		int l = 0, r = 0;
		// O(n*m)
        //todo 以哪个下标开始的图章
		for (int i = 0; i <= n - m; i++) {
			// i开头....(m个)
			// i+0 i+1 i+m-1
			for (int j = 0; j < m; j++) {
                //todo 图章位置是对的，入度就--，如果入度为0就加入队列，否则i+j位置的图章需要i位置的图章处理
				if (t[i + j] == s[j]) {
					if (--indegree[i] == 0) {
						queue[r++] = i;
					}
				} else {
					// i + j 
					// from : 错误的位置
					// to : i开头的下标
					graph.get(i + j).add(i);
				}
			}
		}
		// 同一个位置取消错误不要重复统计
		boolean[] visited = new boolean[n];
		int[] path = new int[n - m + 1];
		int size = 0;
		while (l < r) {
			int cur = queue[l++];
			path[size++] = cur;
			for (int i = 0; i < m; i++) {
				// cur : 开头位置
				// cur + 0 cur + 1 cur + 2 ... cur + m - 1
				if (!visited[cur + i]) {
					visited[cur + i] = true;
					for (int next : graph.get(cur + i)) {
						if (--indegree[next] == 0) {
							queue[r++] = next;
						}
					}
				}
			}
		}
		if (size != n - m + 1) {
			return new int[0];
		}
		// path逆序调整
		for (int i = 0, j = size - 1; i < j; i++, j--) {
			int tmp = path[i];
			path[i] = path[j];
			path[j] = tmp;
		}
		return path;
	}
}
