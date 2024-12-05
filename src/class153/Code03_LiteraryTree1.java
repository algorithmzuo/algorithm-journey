package class153;

// 文艺平衡树，Splay实现范围翻转，java版本
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

public class Code03_LiteraryTree1 {

	public static int MAXN = 100005;

	public static int head = 0;

	public static int cnt = 0;

	public static int[] num = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static boolean[] reverse = new boolean[MAXN];

	public static int[] stack = new int[MAXN];

	public static int si;

	public static int[] ans = new int[MAXN];

	public static int ai;

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static int lr(int i) {
		return right[father[i]] == i ? 1 : 0;
	}

	public static void rotate(int i) {
		int f = father[i], g = father[f], soni = lr(i), sonf = lr(f);
		if (soni == 1) {
			right[f] = left[i];
			if (right[f] != 0) {
				father[right[f]] = f;
			}
			left[i] = f;
		} else {
			left[f] = right[i];
			if (left[f] != 0) {
				father[left[f]] = f;
			}
			right[i] = f;
		}
		if (g != 0) {
			if (sonf == 1) {
				right[g] = i;
			} else {
				left[g] = i;
			}
		}
		father[f] = i;
		father[i] = g;
		up(f);
		up(i);
	}

	public static void splay(int i, int goal) {
		int f = father[i], g = father[f];
		while (f != goal) {
			if (g != goal) {
				if (lr(i) == lr(f)) {
					rotate(f);
				} else {
					rotate(i);
				}
			}
			rotate(i);
			f = father[i];
			g = father[f];
		}
		if (goal == 0) {
			head = i;
		}
	}

	public static void down(int i) {
		if (reverse[i]) {
			reverse[left[i]] = !reverse[left[i]];
			reverse[right[i]] = !reverse[right[i]];
			int tmp = left[i];
			left[i] = right[i];
			right[i] = tmp;
			reverse[i] = false;
		}
	}

	public static int find(int rank) {
		int i = head;
		while (i != 0) {
			down(i);
			if (size[left[i]] + 1 == rank) {
				return i;
			} else if (size[left[i]] >= rank) {
				i = left[i];
			} else {
				rank -= size[left[i]] + 1;
				i = right[i];
			}
		}
		return 0;
	}

	public static void add(int x) {
		num[++cnt] = x;
		size[cnt] = 1;
		father[cnt] = head;
		right[head] = cnt;
		splay(cnt, 0);
	}

	// 注意l永远不会是最左位置，r永远不会是最右位置
	// 因为最左和最右位置提前加入了预备值，永远不会修改
	public static void reverse(int l, int r) {
		int i = find(l - 1);
		int j = find(r + 1);
		splay(i, 0);
		splay(j, i);
		reverse[left[right[head]]] = !reverse[left[right[head]]];
	}

	// 递归方式实现二叉树中序遍历
	// 对本题来说，递归不会爆栈，但其实是有风险的
	public static void inorder(int i) {
		if (i != 0) {
			down(i);
			inorder(left[i]);
			ans[++ai] = num[i];
			inorder(right[i]);
		}
	}

	// 迭代方式实现二叉树中序遍历，防止递归爆栈
	// 不懂的话，看讲解018，迭代方式实现二叉树中序遍历
	// 遍历时候懒更新任务也要下发
	public static void inorder() {
		si = 0;
		int i = head;
		while (si != 0 || i != 0) {
			if (i != 0) {
				down(i);
				stack[++si] = i;
				i = left[i];
			} else {
				i = stack[si--];
				ans[++ai] = num[i];
				i = right[i];
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		add(0);
		for (int i = 1; i <= n; i++) {
			add(i);
		}
		add(0);
		for (int i = 1, x, y; i <= m; i++) {
			in.nextToken();
			x = (int) in.nval;
			in.nextToken();
			y = (int) in.nval;
			reverse(x + 1, y + 1);
		}
		ai = 0;
//		inorder(head);
		inorder();
		for (int i = 2; i < ai; i++) {
			out.print(ans[i] + " ");
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

}
