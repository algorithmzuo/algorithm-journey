package class152;

// 文艺平衡树，FHQ-Treap实现范围翻转，java版本
// 长度为n的序列，下标从1开始，一开始序列为1, 2, ..., n
// 接下来会有k个操作，每个操作给定l，r，表示从l到r范围上的所有数字翻转
// 做完k次操作后，从左到右打印所有数字
// 1 <= n, k <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3391
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_LiteraryTree1 {

	public static int MAXN = 100001;

	public static int head = 0;

	public static int cnt = 0;

	public static int[] key = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static double[] priority = new double[MAXN];

	public static boolean[] reverse = new boolean[MAXN];

	public static int[] ans = new int[MAXN];

	public static int ansi;

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static void down(int i) {
		if (reverse[i]) {
			int tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
			reverse[left[i]] = !reverse[left[i]];
			reverse[right[i]] = !reverse[right[i]];
			reverse[i] = false;
		}
	}

	public static void split(int l, int r, int i, int rank) {
		if (i == 0) {
			right[l] = left[r] = 0;
		} else {
			down(i);
			if (size[left[i]] + 1 <= rank) {
				right[l] = i;
				split(i, r, right[i], rank - size[left[i]] - 1);
			} else {
				left[r] = i;
				split(l, i, left[i], rank);
			}
			up(i);
		}
	}

	public static int merge(int l, int r) {
		if (l == 0 || r == 0) {
			return l + r;
		}
		if (priority[l] >= priority[r]) {
			down(l);
			right[l] = merge(right[l], r);
			up(l);
			return l;
		} else {
			down(r);
			left[r] = merge(l, left[r]);
			up(r);
			return r;
		}
	}

	public static void inorder(int i) {
		if (i != 0) {
			down(i);
			inorder(left[i]);
			ans[++ansi] = key[i];
			inorder(right[i]);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int k = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			key[++cnt] = i;
			size[cnt] = 1;
			priority[cnt] = Math.random();
			head = merge(head, cnt);
		}
		for (int i = 1, x, y, l, m, lm, r; i <= k; i++) {
			in.nextToken();
			x = (int) in.nval;
			in.nextToken();
			y = (int) in.nval;
			split(0, 0, head, y);
			lm = right[0];
			r = left[0];
			split(0, 0, lm, x - 1);
			l = right[0];
			m = left[0];
			reverse[m] = !reverse[m];
			head = merge(merge(l, m), r);
		}
		ansi = 0;
		inorder(head);
		for (int i = 1; i <= ansi; i++) {
			out.print(ans[i] + " ");
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

}
