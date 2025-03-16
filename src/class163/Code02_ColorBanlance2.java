package class163;

// 颜色平衡的子树，java实现迭代版
// 测试链接 : https://www.luogu.com.cn/problem/P9233
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_ColorBanlance2 {

	public static int MAXN = 200001;
	public static int n;
	public static int[] arr = new int[MAXN];
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cnt = 0;
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] colorCnt = new int[MAXN];
	public static int[] cntCnt = new int[MAXN];
	public static int ans = 0;

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	// 不会改迭代版，去看讲解118，详解了从递归版改迭代版
	// stack1、size1、cur1、edge1，用于把effect、cancle、dfs1改成迭代版
	public static int[][] stack1 = new int[MAXN][2];
	public static int size1, cur1, edge1;

	// stack2、size2、cur2、edge2、keep2，用于把dfs2改成迭代版
	public static int[][] stack2 = new int[MAXN][3];
	public static int size2, cur2, edge2, keep2;

	public static void push1(int u, int e) {
		stack1[size1][0] = u;
		stack1[size1][1] = e;
		size1++;
	}

	public static void pop1() {
		--size1;
		cur1 = stack1[size1][0];
		edge1 = stack1[size1][1];
	}

	public static void push2(int u, int e, int k) {
		stack2[size2][0] = u;
		stack2[size2][1] = e;
		stack2[size2][2] = k;
		size2++;
	}

	public static void pop2() {
		--size2;
		cur2 = stack2[size2][0];
		edge2 = stack2[size2][1];
		keep2 = stack2[size2][2];
	}

	public static void effect(int root) {
		size1 = 0;
		push1(root, -1);
		while (size1 > 0) {
			pop1();
			if (edge1 == -1) {
				colorCnt[arr[cur1]]++;
				cntCnt[colorCnt[arr[cur1]] - 1]--;
				cntCnt[colorCnt[arr[cur1]]]++;
				edge1 = head[cur1];
			} else {
				edge1 = next[edge1];
			}
			if (edge1 != 0) {
				push1(cur1, edge1);
				push1(to[edge1], -1);
			}
		}
	}

	public static void cancle(int root) {
		size1 = 0;
		push1(root, -1);
		while (size1 > 0) {
			pop1();
			if (edge1 == -1) {
				colorCnt[arr[cur1]]--;
				cntCnt[colorCnt[arr[cur1]] + 1]--;
				cntCnt[colorCnt[arr[cur1]]]++;
				edge1 = head[cur1];
			} else {
				edge1 = next[edge1];
			}
			if (edge1 != 0) {
				push1(cur1, edge1);
				push1(to[edge1], -1);
			}
		}
	}

	public static void dfs1() {
		size1 = 0;
		push1(1, -1);
		while (size1 > 0) {
			pop1();
			if (edge1 == -1) {
				siz[cur1] = 1;
				edge1 = head[cur1];
			} else {
				edge1 = next[edge1];
			}
			if (edge1 != 0) {
				push1(cur1, edge1);
				push1(to[edge1], -1);
			} else {
				for (int e = head[cur1], v; e > 0; e = next[e]) {
					v = to[e];
					siz[cur1] += siz[v];
					if (son[cur1] == 0 || siz[son[cur1]] < siz[v]) {
						son[cur1] = v;
					}
				}
			}
		}
	}

	public static void dfs2() {
		size2 = 0;
		push2(1, -1, 1);
		while (size2 > 0) {
			pop2();
			if (edge2 == -2) {
				colorCnt[arr[cur2]]++;
				cntCnt[colorCnt[arr[cur2]] - 1]--;
				cntCnt[colorCnt[arr[cur2]]]++;
				for (int e = head[cur2], v; e > 0; e = next[e]) {
					v = to[e];
					if (v != son[cur2]) {
						effect(v);
					}
				}
				if (colorCnt[arr[cur2]] * cntCnt[colorCnt[arr[cur2]]] == siz[cur2]) {
					ans++;
				}
				if (keep2 == 0) {
					cancle(cur2);
				}
			} else {
				if (edge2 == -1) {
					edge2 = head[cur2];
				} else {
					edge2 = next[edge2];
				}
				if (edge2 > 0) {
					push2(cur2, edge2, keep2);
					if (to[edge2] != son[cur2]) {
						push2(to[edge2], -1, 0);
					}
				} else {
					push2(cur2, -2, keep2);
					if (son[cur2] != 0) {
						push2(son[cur2], -1, 1);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1, color, father; i <= n; i++) {
			in.nextToken();
			color = (int) in.nval;
			in.nextToken();
			father = (int) in.nval;
			arr[i] = color;
			if (i != 1) {
				addEdge(father, i);
			}
		}
		dfs1();
		dfs2();
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
