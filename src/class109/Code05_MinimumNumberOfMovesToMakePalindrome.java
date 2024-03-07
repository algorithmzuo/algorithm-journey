package class109;

import java.util.Arrays;

// 得到回文串的最少操作次数
// 给你一个只包含小写英文字母的字符串s
// 每一次操作可以选择s中两个相邻的字符进行交换
// 返回将s变成回文串的最少操作次数
// 输入数据会确保s一定能变成一个回文串
// 测试链接 : https://leetcode.cn/problems/minimum-number-of-moves-to-make-palindrome/
public class Code05_MinimumNumberOfMovesToMakePalindrome {

	public static int MAXN = 2001;

	public static int MAXV = 26;

	public static int n, cnt;

	public static char[] s;

	// 链式前向星
	public static int[] head = new int[MAXV];

	public static int[] next = new int[MAXN];

	public static int[] to = new int[MAXN];

	// 树状数组
	public static int[] tree = new int[MAXN];

	// 归并分治
	public static int[] arr = new int[MAXN];

	public static int[] help = new int[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 0, MAXV, 0);
		Arrays.fill(arr, 1, n + 1, 0);
		Arrays.fill(tree, 1, n + 1, 0);
		for (int i = 1; i <= n; i++) {
			add(i, 1);
		}
	}

	public static void addEdge(int v, int j) {
		next[cnt] = head[v];
		to[cnt] = j;
		head[v] = cnt++;
	}

	public static int removeEdge(int v) {
		int ans = to[head[v]];
		head[v] = next[head[v]];
		return ans;
	}

	public static int lowbit(int i) {
		return i & -i;
	}

	public static void add(int i, int v) {
		while (i <= n) {
			tree[i] += v;
			i += lowbit(i);
		}
	}

	public static int sum(int i) {
		int ans = 0;
		while (i > 0) {
			ans += tree[i];
			i -= lowbit(i);
		}
		return ans;
	}

	public static int minMovesToMakePalindrome(String str) {
		s = str.toCharArray();
		n = s.length;
		build();
		for (int i = 0, j = 1; i < n; i++, j++) {
			addEdge(s[i] - 'a', j);
		}
		for (int i = 0, l = 1, r, k; i < n; i++, l++) {
			if (arr[l] == 0) {
				r = removeEdge(s[i] - 'a');
				if (l == r) {
					arr[l] = (1 + n) / 2;
					add(l, -1);
				} else {
					k = sum(l);
					arr[l] = k;
					arr[r] = n - k + 1;
					add(r, -1);
				}
			}
		}
		return number(1, n);
	}

	public static int number(int l, int r) {
		if (l >= r) {
			return 0;
		}
		int m = (l + r) / 2;
		return number(l, m) + number(m + 1, r) + merge(l, m, r);
	}

	public static int merge(int l, int m, int r) {
		int i = r, p1 = m, p2 = r, ans = 0;
		while (p1 >= l && p2 > m) {
			ans += arr[p1] > arr[p2] ? (p2 - m) : 0;
			help[i--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
		}
		while (p1 >= l) {
			help[i--] = arr[p1--];
		}
		while (p2 > m) {
			help[i--] = arr[p2--];
		}
		for (i = l; i <= r; i++) {
			arr[i] = help[i];
		}
		return ans;
	}

}
