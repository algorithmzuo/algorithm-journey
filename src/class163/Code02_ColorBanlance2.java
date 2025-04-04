package class163;

// 颜色平衡的子树，java实现迭代版
// 不会改迭代版，去看讲解118，详解了dfs从递归版改迭代版
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
	public static int[] color = new int[MAXN];
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cnt = 0;
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] colorCnt = new int[MAXN];
	public static int[] colorNum = new int[MAXN];
	public static int ans = 0;

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	// stack1、size1、cur1、edge1
	// 用于把effect、cancel、dfs1改成迭代版
	public static int[][] stack1 = new int[MAXN][2];
	public static int size1, cur1, edge1;

	// stack2、size2、cur2、keep2、edge2
	// 用于把dfs2改成迭代版
	public static int[][] stack2 = new int[MAXN][3];
	public static int size2, cur2, keep2, edge2;

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

	public static void push2(int u, int k, int e) {
		stack2[size2][0] = u;
		stack2[size2][1] = k;
		stack2[size2][2] = e;
		size2++;
	}

	public static void pop2() {
		--size2;
		cur2 = stack2[size2][0];
		keep2 = stack2[size2][1];
		edge2 = stack2[size2][2];
	}

	public static void dfs1(int u) {
		size1 = 0;
		push1(u, -1);
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

	public static void effect(int root) {
		size1 = 0;
		push1(root, -1);
		while (size1 > 0) {
			pop1();
			if (edge1 == -1) {
				colorCnt[color[cur1]]++;
				colorNum[colorCnt[color[cur1]] - 1]--;
				colorNum[colorCnt[color[cur1]]]++;
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

	public static void cancel(int root) {
		size1 = 0;
		push1(root, -1);
		while (size1 > 0) {
			pop1();
			if (edge1 == -1) {
				colorCnt[color[cur1]]--;
				colorNum[colorCnt[color[cur1]] + 1]--;
				colorNum[colorCnt[color[cur1]]]++;
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

	// 迭代版的dfs2，用edge2变量标记一个节点的不同阶段
	// edge2 == -1，表示第一次来到当前节点，接下来依次处理轻儿子的子树
	// edge2 > 0，表示正在依次处理轻儿子的子树
	// edge2 == 0，表示处理完了所有轻儿子的子树，接下来处理重儿子的子树
	// edge2 == -2，表示处理完了重儿子的子树，轮到启发式合并了
	public static void dfs2(int u, int keep) {
		size2 = 0;
		push2(u, keep, -1);
		while (size2 > 0) {
			pop2();
			if (edge2 != -2) {
				if (edge2 == -1) {
					edge2 = head[cur2];
				} else {
					edge2 = next[edge2];
				}
				if (edge2 > 0) {
					push2(cur2, keep2, edge2);
					if (to[edge2] != son[cur2]) {
						push2(to[edge2], 0, -1);
					}
				} else {
					push2(cur2, keep2, -2);
					if (son[cur2] != 0) {
						push2(son[cur2], 1, -1);
					}
				}
			} else {
				colorCnt[color[cur2]]++;
				colorNum[colorCnt[color[cur2]] - 1]--;
				colorNum[colorCnt[color[cur2]]]++;
				for (int e = head[cur2], v; e > 0; e = next[e]) {
					v = to[e];
					if (v != son[cur2]) {
						effect(v);
					}
				}
				if (colorCnt[color[cur2]] * colorNum[colorCnt[color[cur2]]] == siz[cur2]) {
					ans++;
				}
				if (keep2 == 0) {
					cancel(cur2);
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
		for (int i = 1, father; i <= n; i++) {
			in.nextToken();
			color[i] = (int) in.nval;
			in.nextToken();
			father = (int) in.nval;
			if (i != 1) {
				addEdge(father, i);
			}
		}
		dfs1(1);
		dfs2(1, 0);
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
