package class152;

// 文本编辑器，FHQ-Treap实现区间移动，java版本
// 一开始文本为空，光标在文本开头，也就是1位置，请实现如下6种操作
// Move k     : 将光标移动到第k个字符之后，操作保证光标不会到非法位置
// Insert n s : 在光标处插入长度为n的字符串s，光标位置不变
// Delete n   : 删除光标后的n个字符，光标位置不变，操作保证有足够字符
// Get n      : 输出光标后的n个字符，光标位置不变，操作保证有足够字符
// Prev       : 光标前移一个字符，操作保证光标不会到非法位置
// Next       : 光标后移一个字符，操作保证光标不会到非法位置
// Insert操作时，字符串s中ASCII码在[32,126]范围上的字符一定有n个，其他字符请过滤掉
// 测试链接 : https://www.luogu.com.cn/problem/P4008
// 如下实现是正确的，但java的版本无法通过所有测试用例
// 这是洛谷平台没有照顾各种语言的实现所导致的
// java的实现空间就是无法达标，C++的实现完全一样的逻辑，就是可以达标
// C++版本是Code03_TextEditor2文件，可以通过所有测试用例
// 在真正笔试、比赛时，一定是兼顾各种语言的，该实现是一定正确的

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code03_TextEditor1 {

	public static int MAXN = 2000001;

	public static int head = 0;

	public static int cnt = 0;

	public static char[] key = new char[MAXN];

	public static int[] left = new int[MAXN];

	public static int[] right = new int[MAXN];

	public static int[] size = new int[MAXN];

	public static double[] priority = new double[MAXN];

	public static char[] ans = new char[MAXN];

	public static int ansi;

	public static void up(int i) {
		size[i] = size[left[i]] + size[right[i]] + 1;
	}

	public static void split(int l, int r, int i, int rank) {
		if (i == 0) {
			right[l] = left[r] = 0;
		} else {
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
			right[l] = merge(right[l], r);
			up(l);
			return l;
		} else {
			left[r] = merge(l, left[r]);
			up(r);
			return r;
		}
	}

	public static void inorder(int i) {
		if (i != 0) {
			inorder(left[i]);
			ans[++ansi] = key[i];
			inorder(right[i]);
		}
	}

	// 我做了很多个版本的IO尝试，空间都无法达标
	// 以下风格只是其中一种，无所谓了，逻辑是对的
	// 想通过这个题看C++版本吧，完全一样的逻辑
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = Integer.valueOf(in.readLine());
		int pos = 0;
		String str;
		String op;
		int x;
		for (int i = 1; i <= n; i++) {
			str = in.readLine();
			if (str.equals("Prev")) {
				pos--;
			} else if (str.equals("Next")) {
				pos++;
			} else {
				String[] input = str.split(" ");
				op = input[0];
				x = Integer.valueOf(input[1]);
				if (op.equals("Move")) {
					pos = x;
				} else if (op.equals("Insert")) {
					split(0, 0, head, pos);
					int l = right[0];
					int r = left[0];
					left[0] = right[0] = 0;
					int add = 0;
					while (add < x) {
						char[] insert = in.readLine().toCharArray();
						for (int j = 0; j < insert.length; j++) {
							if (insert[j] >= 32 && insert[j] <= 126) {
								key[++cnt] = insert[j];
								size[cnt] = 1;
								priority[cnt] = Math.random();
								l = merge(l, cnt);
								add++;
							}
						}
					}
					head = merge(l, r);
				} else if (op.equals("Delete")) {
					split(0, 0, head, pos + x);
					int r = left[0];
					int lm = right[0];
					left[0] = right[0] = 0;
					split(0, 0, lm, pos);
					int l = right[0];
					left[0] = right[0] = 0;
					head = merge(l, r);
				} else {
					split(0, 0, head, pos + x);
					int r = left[0];
					int lm = right[0];
					left[0] = right[0] = 0;
					split(0, 0, lm, pos);
					int l = right[0];
					int m = left[0];
					left[0] = right[0] = 0;
					ansi = 0;
					inorder(m);
					head = merge(merge(l, m), r);
					for (int j = 1; j <= ansi; j++) {
						out.print((char) ans[j]);
					}
					out.println();
				}
			}
		}
		out.flush();
		out.close();
		in.close();
	}

}
