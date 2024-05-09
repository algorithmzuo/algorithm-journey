package class122;

// 最大压力(迭代版)
// 测试链接 : https://www.luogu.com.cn/problem/P3128

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_MaxFlow2 {

	public static int MAXN = 50001;

	public static int MAXM = 100001;

	// father数组不用作并查集，就是记录每个节点的父亲节点
	public static int[] father = new int[MAXN];

	public static int[] cnt = new int[MAXN];

	// 链式前向星建图
	public static int[] headEdge = new int[MAXN];

	public static int[] edgeNext = new int[MAXN << 1];

	public static int[] edgeTo = new int[MAXN << 1];

	public static int tcnt;

	// 以下结构都是tarjan算法所需要的
	public static int[] headQuery = new int[MAXN];

	public static int[] queryNext = new int[MAXM << 1];

	public static int[] queryTo = new int[MAXM << 1];

	public static int[] queryIndex = new int[MAXM << 1];

	public static int qcnt;

	public static boolean[] visited = new boolean[MAXN];

	// unionfind数组是tarjan算法专用的并查集结构
	public static int[] unionfind = new int[MAXN];

	// 问题双方
	public static int[] quesu = new int[MAXM];

	public static int[] quesv = new int[MAXM];

	// ans数组是tarjan算法的输出结果，记录每次旅行两端点的最低公共祖先
	public static int[] ans = new int[MAXM];

	public static void build(int n) {
		Arrays.fill(cnt, 1, n + 1, 0);
		tcnt = qcnt = 1;
		Arrays.fill(headEdge, 1, n + 1, 0);
		Arrays.fill(headQuery, 1, n + 1, 0);
		Arrays.fill(visited, 1, n + 1, false);
		for (int i = 1; i <= n; i++) {
			unionfind[i] = i;
		}
	}

	public static void addEdge(int u, int v) {
		edgeNext[tcnt] = headEdge[u];
		edgeTo[tcnt] = v;
		headEdge[u] = tcnt++;
	}

	public static void addQuery(int u, int v, int i) {
		queryNext[qcnt] = headQuery[u];
		queryTo[qcnt] = v;
		queryIndex[qcnt] = i;
		headQuery[u] = qcnt++;
	}

	// find方法的递归版改迭代版
	public static int[] stack = new int[MAXN];

	public static int find(int i) {
		int size = 0;
		while (i != unionfind[i]) {
			stack[size++] = i;
			i = unionfind[i];
		}
		while (size > 0) {
			unionfind[stack[--size]] = i;
		}
		return i;
	}

	// tarjan方法的递归版改迭代版
	public static int[][] ufe = new int[MAXN][3];

	public static int stackSize, u, f, e;

	public static void push(int u, int f, int e) {
		ufe[stackSize][0] = u;
		ufe[stackSize][1] = f;
		ufe[stackSize][2] = e;
		stackSize++;
	}

	public static void pop() {
		--stackSize;
		u = ufe[stackSize][0];
		f = ufe[stackSize][1];
		e = ufe[stackSize][2];
	}

	public static void tarjan(int root) {
		stackSize = 0;
		push(root, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				visited[u] = true;
				e = headEdge[u];
			} else {
				e = edgeNext[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (edgeTo[e] != f) {
					push(edgeTo[e], u, -1);
				}
			} else {
				for (int q = headQuery[u], v; q != 0; q = queryNext[q]) {
					v = queryTo[q];
					if (visited[v]) {
						ans[queryIndex[q]] = find(v);
					}
				}
				unionfind[u] = f;
				father[u] = f;
			}
		}
	}

	// dfs方法的递归版改迭代版
	public static void dfs(int root) {
		stackSize = 0;
		push(root, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				e = headEdge[u];
			} else {
				e = edgeNext[e];
			}
			if (e != 0) {
				push(u, f, e);
				if (edgeTo[e] != f) {
					push(edgeTo[e], u, -1);
				}
			} else {
				for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
					v = edgeTo[e];
					if (v != f) {
						cnt[u] += cnt[v];
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
		int n = (int) in.nval;
		build(n);
		in.nextToken();
		int m = (int) in.nval;
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1, u, v; i <= m; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			quesu[i] = u;
			quesv[i] = v;
			addQuery(u, v, i);
			addQuery(v, u, i);
		}
		out.println(compute(n, m));
		out.flush();
		out.close();
		br.close();
	}

	public static int compute(int n, int m) {
		tarjan(1);
		for (int i = 1, u, v, lca, lcafather; i <= m; i++) {
			u = quesu[i];
			v = quesv[i];
			lca = ans[i];
			lcafather = father[lca];
			cnt[u]++;
			cnt[v]++;
			cnt[lca]--;
			cnt[lcafather]--;
		}
		dfs(1);
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			ans = Math.max(ans, cnt[i]);
		}
		return ans;
	}

}
