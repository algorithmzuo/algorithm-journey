package class152;

// 文本编辑器(java版本)
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

	// 我写了很多个版本的IO实现，空间都无法达标
	// 以下风格只是其中一种，无所谓了，逻辑是对的
	// 想通过这个题看C++版本吧，完全一样的逻辑
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(in.readLine());
		int pos = 0, len;
		String[] input = getInput(in);
		String str;
		String[] part;
		String op;
		int l, m, lm, r;
		for (int i = 1, inputIndex = 0; i <= n; i++) {
			str = input[inputIndex++].trim();
			if (str.equals("Prev")) {
				pos--;
			} else if (str.equals("Next")) {
				pos++;
			} else {
				part = str.split(" ");
				op = part[0];
				len = Integer.parseInt(part[1]);
				if (op.equals("Move")) {
					pos = len;
				} else if (op.equals("Insert")) {
					split(0, 0, head, pos);
					l = right[0];
					r = left[0];
					for (int j = 1; j <= len;) {
						for (char c : input[inputIndex++].toCharArray()) {
							if (c >= 32 && c <= 126) {
								key[++cnt] = c;
								size[cnt] = 1;
								priority[cnt] = Math.random();
								l = merge(l, cnt);
								j++;
							}
						}
					}
					head = merge(l, r);
				} else if (op.equals("Delete")) {
					split(0, 0, head, pos + len);
					lm = right[0];
					r = left[0];
					split(0, 0, lm, pos);
					l = right[0];
					head = merge(l, r);
				} else {
					split(0, 0, head, pos + len);
					lm = right[0];
					r = left[0];
					split(0, 0, lm, pos);
					l = right[0];
					m = left[0];
					ansi = 0;
					inorder(m);
					head = merge(merge(l, m), r);
					for (int j = 1; j <= ansi; j++) {
						out.print(ans[j]);
					}
					out.println();
				}
			}
		}
		out.flush();
		out.close();
		in.close();
	}

	public static String[] getInput(BufferedReader in) throws IOException {
		StringBuilder inputBuffer = new StringBuilder();
		char[] tempBuffer = new char[8192];
		int bytesRead;
		while ((bytesRead = in.read(tempBuffer)) != -1) {
			inputBuffer.append(tempBuffer, 0, bytesRead);
		}
		String[] input = inputBuffer.toString().split("\n");
		return input;
	}

}
