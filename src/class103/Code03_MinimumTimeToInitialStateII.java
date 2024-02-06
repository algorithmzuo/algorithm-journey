package class103;

// 将单词恢复初始状态所需的最短时间II
// 给你一个下标从0开始的字符串word和一个整数k
// 在每一秒，必须执行以下操作
// 移除word的前k个字符
// 在word的末尾添加k个任意字符
// 添加的字符不必和移除的字符相同
// 返回将word恢复到初始状态所需的最短时间
// 该时间必须大于零
// 测试链接 : https://leetcode.cn/problems/minimum-time-to-revert-word-to-initial-state-ii/
public class Code03_MinimumTimeToInitialStateII {

	public static int minimumTimeToInitialState(String word, int k) {
		char[] s = word.toCharArray();
		int n = s.length;
		zArray(s, n);
		for (int i = k; i < n; i += k) {
			if (z[i] == n - i) {
				return i / k;
			}
		}
		return (n + k - 1) / k;
	}

	// leetcode增加了数据量
	// 所以把这个值改成10^6规模
	public static int MAXN = 1000001;

	public static int[] z = new int[MAXN];

	public static void zArray(char[] s, int n) {
		z[0] = n;
		for (int i = 1, c = 1, r = 1, len; i < n; i++) {
			len = r > i ? Math.min(r - i, z[i - c]) : 0;
			while (i + len < n && s[i + len] == s[len]) {
				len++;
			}
			if (i + len > r) {
				r = i + len;
				c = i;
			}
			z[i] = len;
		}
	}

}
