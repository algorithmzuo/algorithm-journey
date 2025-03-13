package class162;

// 哪个距离的点最多，java版
// 一共有n个节点，给定n-1条边，所有节点连成一棵树，规定1号节点是头
// 规定任何点到自己的距离为0
// 定义d(u, x)，以u为头的子树中，到u的距离为x的节点数
// 对于每个点u，想知道哪个尽量小的x，能取得最大的d(u, x)值
// 打印每个点的答案x
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

public class Code06_DominantIndices1 {

	public static int MAXN = 1000001;
	public static int n;

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	// 长链剖分
	public static int[] len = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] dfn = new int[MAXN];
	public static int cntd = 0;

	// 动态规划
	public static int[] dp = new int[MAXN];
	public static int[] ansx = new int[MAXN];

	public static void setdp(int u, int i, int v) {
		dp[dfn[u] + i] = v;
	}

	public static int getdp(int u, int i) {
		return dp[dfn[u] + i];
	}

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版，C++可以通过，java会爆栈，迭代版是dfs3方法
	public static void dfs1(int u, int fa) {
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa) {
				if (son[u] == 0 || len[son[u]] < len[v]) {
					son[u] = v;
				}
			}
		}
		len[u] = len[son[u]] + 1;
	}

	// 递归版，C++可以通过，java会爆栈，迭代版是dfs4方法
	public static void dfs2(int u, int fa) {
		dfn[u] = ++cntd;
		setdp(u, 0, 1);
		ansx[u] = 0;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], u);
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa && v != son[u]) {
				dfs2(v, u);
			}
		}
		// 注意一定要在合并其他儿子dp信息的过程中，去更新ansx
		// 千万不要，最后再遍历一遍u的dp信息，然后更新ansx
		// 因为任何for循环，都不能是长链的规模！
		// 如果for循环是长链的规模，那么u遍历下去，u的重儿子又遍历下去，长链上每个节点都遍历下去
		// 时间复杂度必然不再是O(n)，而是O(n^2)，长链剖分的优势就不存在了！
		// 所以长链信息会被u直接继承，绝对不要有任何与长链的长度等规模的循环出现！
		ansx[u] = ansx[son[u]] + 1;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa && v != son[u]) {
				for (int i = 1; i <= len[v]; i++) {
					setdp(u, i, getdp(u, i) + getdp(v, i - 1));
					if (getdp(u, i) > getdp(u, ansx[u]) || (getdp(u, i) == getdp(u, ansx[u]) && i < ansx[u])) {
						ansx[u] = i;
					}
				}
			}
		}
		// 如果u的某个距离，获得的最大节点数为1
		// 那么u答案就是0距离，因为u到u自己的距离是0，也有1个节点了
		// 根据题目要求，返回尽量小的距离
		if (getdp(u, ansx[u]) == 1) {
			ansx[u] = 0;
		}
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

	// dfs1的迭代版
	public static void dfs3() {
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

	// dfs2的迭代版
	public static void dfs4() {
		stacksize = 0;
		push(1, 0, -1);
		int v;
		while (stacksize > 0) {
			pop();
			if (e == -1) {
				dfn[u] = ++cntd;
				setdp(u, 0, 1);
				ansx[u] = 0;
				if (son[u] == 0) {
					continue;
				}
				push(u, f, -2);
				push(son[u], u, -1);
				continue;
			} else if (e == -2) {
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e);
				v = to[e];
				if (v != f && v != son[u]) {
					push(v, u, -1);
				}
			} else {
				ansx[u] = ansx[son[u]] + 1;
				for (int e = head[u]; e > 0; e = next[e]) {
					v = to[e];
					if (v != f && v != son[u]) {
						for (int i = 1; i <= len[v]; i++) {
							setdp(u, i, getdp(u, i) + getdp(v, i - 1));
							if (getdp(u, i) > getdp(u, ansx[u]) || (getdp(u, i) == getdp(u, ansx[u]) && i < ansx[u])) {
								ansx[u] = i;
							}
						}
					}
				}
				if (getdp(u, ansx[u]) == 1) {
					ansx[u] = 0;
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
		dfs3();
		dfs4();
		for (int i = 1; i <= n; i++) {
			out.println(ansx[i]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
