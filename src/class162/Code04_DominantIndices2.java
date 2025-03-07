package class162;

// 哪个距离的点最多，java迭代版
// 一共有n个节点，给定n-1条边，所有节点连成一棵树，规定1号节点是头
// 定义d(u, x)，以u为头的子树中，到u的距离为x的节点数
// 那么对于任何点u，都有若干的d(u, x)值，想让x对应的值最大，并且x尽量小
// 规定任何点到自己的距离为0
// 打印每个点的答案
// 1 <= n <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/CF1009F
// 测试链接 : https://codeforces.com/problemset/problem/1009/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_DominantIndices2 {

	public static int MAXN = 1000001;
	public static int n;

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cnt = 0;

	// 长链剖分
	public static int[] len = new int[MAXN];
	public static int[] son = new int[MAXN];

	// 动态规划
	public static int[] start = new int[MAXN];
	public static int[] dp = new int[MAXN];
	public static int[] ans = new int[MAXN];

	public static void set(int u, int i, int v) {
		dp[start[u] + i] = v;
	}

	public static int get(int u, int i) {
		return dp[start[u] + i];
	}

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	// 不会改迭代版，去看讲解118，详解了从递归版改迭代版
	public static int[][] ufe = new int[MAXN][3];

	public static int stacksize, u, f, e;

	public static void push(int a, int b, int c) {
		ufe[stacksize][0] = a;
		ufe[stacksize][1] = b;
		ufe[stacksize][2] = c;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		u = ufe[stacksize][0];
		f = ufe[stacksize][1];
		e = ufe[stacksize][2];
	}

	// 迭代版
	public static void dfs1() {
		stacksize = 0;
		push(1, 0, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e);
				v = to[e];
				if (v != f) {
					push(v, u, -1);
				}
			} else {
				for (int ei = head[u]; ei > 0; ei = next[ei]) {
					v = to[ei];
					if (v != f) {
						if (son[u] == 0 || len[son[u]] < len[v]) {
							son[u] = v;
						}
					}
				}
				len[u] = len[son[u]] + 1;
			}
		}
	}

	// 迭代版
	public static void dfs2() {
		stacksize = 0;
		push(1, 0, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (e == -1) { // e == -1，表示第一次来到当前节点，对应第一部分
				set(u, 0, 1);
				if (son[u] == 0) {
					ans[u] = 0;
					continue;
				}
				start[son[u]] = start[u] + 1;
				int startSum = start[u] + len[u];
				for (int e = head[u]; e > 0; e = next[e]) {
					v = to[e];
					if (v != f && v != son[u]) {
						start[v] = startSum;
						startSum += len[v];
					}
				}
				push(u, f, -2); // 设置e = -2，再弹出就表示处理完长儿子了
				push(son[u], u, -1); // 长儿子的任务进入栈，对应第二部分
				continue;
			} else if (e == -2) { // e == -2，表示长儿子已经处理完，该处理其他儿子了
				e = head[u];
			} else { // 每个儿子都处理
				e = next[e];
			}
			if (e != 0) { // 对应第三部分
				push(u, f, e);
				v = to[e];
				if (v != f && v != son[u]) {
					push(v, u, -1);
				}
			} else { // 对应第四部分，计算答案
				ans[u] = ans[son[u]] + 1;
				for (int e = head[u]; e > 0; e = next[e]) {
					v = to[e];
					if (v != f && v != son[u]) {
						for (int i = 1; i <= len[v]; i++) {
							set(u, i, get(u, i) + get(v, i - 1));
							if (get(u, i) > get(u, ans[u]) || (get(u, i) == get(u, ans[u]) && i < ans[u])) {
								ans[u] = i;
							}
						}
					}
				}
				if (get(u, ans[u]) == 1) {
					ans[u] = 0;
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
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs1();
		start[1] = 1;
		dfs2();
		for (int i = 1; i <= n; i++) {
			out.println(ans[i]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
