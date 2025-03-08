package class162;

// 攻略，java版
// 一共有n个节点，给定n-1条边，所有节点连成一棵树，每个点给定点权
// 规定1号点是头，任何路径都必须从头开始，然后走到某个叶节点停止
// 路径上的点，点权的累加和，叫做这个路径的收益
// 给定数字k，你可以随意选出k条路径，所有路径经过的点，需要取并集，也就是去重
// 并集中的点，点权的累加和，叫做k条路径的收益
// 打印k条路径的收益最大值
// 1 <= n、k <= 2 * 10^5
// 所有点权都是int类型的正数
// 测试链接 : https://www.luogu.com.cn/problem/P10641
// 提交以下的code，提交时请把类名改成"Main"，因为递归爆栈所以无法通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_Walkthrough1 {

	public static int MAXN = 200001;
	public static int n, k;
	public static int[] arr = new int[MAXN];

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cnt = 0;

	// 长链剖分的改写，根据money的值来标记最值钱儿子
	public static int[] fa = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] top = new int[MAXN];
	public static long[] money = new long[MAXN];

	// 每条链的头节点收益排序
	public static long[] sorted = new long[MAXN];

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	// 递归版
	public static void dfs1(int u, int f) {
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				if (son[u] == 0 || money[son[u]] < money[v]) {
					son[u] = v;
				}
			}
		}
		fa[u] = f;
		money[u] = money[son[u]] + arr[u];
	}

	// 递归版
	public static void dfs2(int u, int t) {
		top[u] = t;
		if (son[u] == 0) {
			return;
		}
		dfs2(son[u], t);
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, v);
			}
		}
	}

	public static int[][] fse = new int[MAXN][3];

	public static int stacksize, first, second, edge;

	public static void push(int fir, int sec, int edg) {
		fse[stacksize][0] = fir;
		fse[stacksize][1] = sec;
		fse[stacksize][2] = edg;
		stacksize++;
	}

	public static void pop() {
		--stacksize;
		first = fse[stacksize][0];
		second = fse[stacksize][1];
		edge = fse[stacksize][2];
	}

	// dfs1的迭代版
	public static void dfs3() {
		stacksize = 0;
		push(1, 0, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) {
				edge = head[first];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(first, second, edge);
				if (to[edge] != second) {
					push(to[edge], first, -1);
				}
			} else {
				for (int e = head[first], v; e > 0; e = next[e]) {
					v = to[e];
					if (v != second) {
						if (son[first] == 0 || money[son[first]] < money[v]) {
							son[first] = v;
						}
					}
				}
				fa[first] = second;
				money[first] = money[son[first]] + arr[first];
			}
		}
	}

	// dfs2的迭代版
	public static void dfs4() {
		stacksize = 0;
		push(1, 1, -1);
		while (stacksize > 0) {
			pop();
			if (edge == -1) {
				top[first] = second;
				if (son[first] == 0) {
					continue;
				}
				push(first, second, -2);
				push(son[first], second, -1);
				continue;
			} else if (edge == -2) {
				edge = head[first];
			} else {
				edge = next[edge];
			}
			if (edge != 0) {
				push(first, second, edge);
				if (to[edge] != fa[first] && to[edge] != son[first]) {
					push(to[edge], to[edge], -1);
				}
			}
		}
	}

	public static long compute() {
		int len = 0;
		for (int i = 1; i <= n; i++) {
			if (top[i] == i) {
				sorted[++len] = money[i];
			}
		}
		Arrays.sort(sorted, 1, len + 1);
		long ans = 0;
		for (int i = 1, j = len; i <= k; i++, j--) {
			ans += sorted[j];
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
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
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}
