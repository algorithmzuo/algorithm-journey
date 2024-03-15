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

	public static int n;

	public static char[] s;

	// 所有字符的位置列表
	public static int[] end = new int[MAXV];
	public static int[] pre = new int[MAXN];

	// 树状数组
	public static int[] tree = new int[MAXN];

	// 归并分治
	public static int[] arr = new int[MAXN];
	public static int[] help = new int[MAXN];

	public static void build() {
		Arrays.fill(end, 0, MAXV, 0);
		Arrays.fill(arr, 1, n + 1, 0);
		Arrays.fill(tree, 1, n + 1, 0);
		for (int i = 1; i <= n; i++) {
			add(i, 1);
		}
	}

	// 字符v把下标j加入列表
	public static void push(int v, int j) {
		pre[j] = end[v];
		end[v] = j;
	}

	// 弹出当前v字符最后的下标
	public static int pop(int v) {
		int ans = end[v];
		end[v] = pre[end[v]];
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

	// 时间复杂度O(n * logn)
	public static int minMovesToMakePalindrome(String str) {
		s = str.toCharArray();
		n = s.length;
		build();
		for (int i = 0, j = 1; i < n; i++, j++) {
			push(s[i] - 'a', j);
		}
		// arr[i]记录每个位置的字符最终要去哪
		for (int i = 0, l = 1, r, k; i < n; i++, l++) {
			if (arr[l] == 0) {
				r = pop(s[i] - 'a');
				if (l < r) {
					k = sum(l);
					arr[l] = k;
					arr[r] = n - k + 1;
				} else {
					arr[l] = (1 + n) / 2;
				}
				add(r, -1);
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
		int ans = 0;
		for (int i = m, j = r; i >= l; i--) {
			while (j >= m + 1 && arr[i] <= arr[j]) {
				j--;
			}
			ans += j - m;
		}
		int i = l;
		int a = l;
		int b = m + 1;
		while (a <= m && b <= r) {
			help[i++] = arr[a] <= arr[b] ? arr[a++] : arr[b++];
		}
		while (a <= m) {
			help[i++] = arr[a++];
		}
		while (b <= r) {
			help[i++] = arr[b++];
		}
		for (i = l; i <= r; i++) {
			arr[i] = help[i];
		}
		return ans;
	}

}
