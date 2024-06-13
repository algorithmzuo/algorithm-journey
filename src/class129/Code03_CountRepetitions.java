package class129;

import java.util.Arrays;

// 统计重复个数
// 如果字符串x删除一些字符，可以得到字符串y，那么就说y可以从x中获得
// 给定s1和a，代表s1拼接a次，记为字符串x
// 给定s2和b，代表s2拼接b次，记为字符串y
// 现在把y拼接m次之后，得到的字符串依然可能从x中获得，返回尽可能大的m
// s1、s2只由小写字母组成
// 1 <= s1长度、s2长度 <= 100
// 1 <= a、b <= 10^6
// 测试链接 : https://leetcode.cn/problems/count-the-repetitions/
public class Code03_CountRepetitions {

	// 该题的题解中有很多打败比例优异，但是时间复杂度不是最优的方法
	// 如果数据苛刻一些，就通过不了，所以一定要做到时间复杂度与a、b的值无关
	// 本方法时间复杂度O(s1长度 * s2长度)，一定是最优解，而且比其他方法更好理解
	public static int getMaxRepetitions(String str1, int a, String str2, int b) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int n = s1.length;
		// next[i][j] : 从i位置出发，至少需要多少长度，能找到j字符
		int[][] next = new int[n][26];
		// 时间复杂度O(s1长度 + s2长度)
		if (!find(s1, n, next, s2)) {
			return 0;
		}
		// st[i][p] : 从i位置出发，至少需要多少长度，可以获得2^p个s2
		long[][] st = new long[n][30];
		// 时间复杂度O(s1长度 * s2长度)
		for (int i = 0, cur, len; i < n; i++) {
			cur = i;
			len = 0;
			for (char c : s2) {
				len += next[cur][c - 'a'];
				cur = (cur + next[cur][c - 'a']) % n;
			}
			st[i][0] = len;
		}
		// 时间复杂度O(s1长度)
		for (int p = 1; p <= 29; p++) {
			for (int i = 0; i < n; i++) {
				st[i][p] = st[i][p - 1] + st[(int) ((st[i][p - 1] + i) % n)][p - 1];
			}
		}
		long ans = 0;
		// 时间复杂度O(1)
		for (int p = 29, start = 0; p >= 0; p--) {
			if (st[start % n][p] + start <= n * a) {
				ans += 1 << p;
				start += st[start % n][p];
			}
		}
		return (int) (ans / b);
	}

	// 时间复杂度O(s1长度 + s2长度)
	public static boolean find(char[] s1, int n, int[][] next, char[] s2) {
		int[] right = new int[26];
		Arrays.fill(right, -1);
		for (int i = n - 1; i >= 0; i--) {
			right[s1[i] - 'a'] = i + n;
		}
		for (int i = n - 1; i >= 0; i--) {
			right[s1[i] - 'a'] = i;
			for (int j = 0; j < 26; j++) {
				if (right[j] != -1) {
					next[i][j] = right[j] - i + 1;
				} else {
					next[i][j] = -1;
				}
			}
		}
		for (char c : s2) {
			if (next[0][c - 'a'] == -1) {
				return false;
			}
		}
		return true;
	}

}
