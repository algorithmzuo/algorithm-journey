package class122;

// 运输计划(迭代版)
// 有n个节点，给定n-1条边使其连接成一棵树，每条边有正数边权
// 给定很多运输计划，每个运输计划(a,b)表示从a去往b
// 每个运输计划的代价就是沿途边权和，运输计划之间完全互不干扰
// 你只能选择一条边，将其边权变成0
// 你的目的是让所有运输计划代价的最大值尽量小
// 返回所有运输计划代价的最大值最小能是多少
// 测试链接 : https://www.luogu.com.cn/problem/P2680
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_TransportPlan2 {

	public static int MAXN = 300001;

	public static int MAXM = 300001;

	public static int n;

	public static int m;

	public static int[] num = new int[MAXN];

	public static int[] headEdge = new int[MAXN];

	public static int[] edgeNext = new int[MAXN << 1];

	public static int[] edgeTo = new int[MAXN << 1];

	public static int[] edgeWeight = new int[MAXN << 1];

	public static int tcnt;

	public static int[] headQuery = new int[MAXN];

	public static int[] queryNext = new int[MAXM << 1];

	public static int[] queryTo = new int[MAXM << 1];

	public static int[] queryIndex = new int[MAXM << 1];

	public static int qcnt;

	public static boolean[] visited = new boolean[MAXN];

	public static int[] unionfind = new int[MAXN];

	public static int[] quesu = new int[MAXM];

	public static int[] quesv = new int[MAXM];

	public static int[] distance = new int[MAXN];

	public static int[] lca = new int[MAXM];

	public static int[] cost = new int[MAXM];

	public static int maxCost;

	public static void build() {
		tcnt = qcnt = 1;
		Arrays.fill(headEdge, 1, n + 1, 0);
		Arrays.fill(headQuery, 1, n + 1, 0);
		Arrays.fill(visited, 1, n + 1, false);
		for (int i = 1; i <= n; i++) {
			unionfind[i] = i;
		}
		maxCost = 0;
	}

	public static void addEdge(int u, int v, int w) {
		edgeNext[tcnt] = headEdge[u];
		edgeTo[tcnt] = v;
		edgeWeight[tcnt] = w;
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
	// 不会改，看讲解118，讲了怎么从递归版改成迭代版
	public static int[][] ufwe = new int[MAXN][4];

	public static int stackSize, u, f, w, e;

	public static void push(int u, int f, int w, int e) {
		ufwe[stackSize][0] = u;
		ufwe[stackSize][1] = f;
		ufwe[stackSize][2] = w;
		ufwe[stackSize][3] = e;
		stackSize++;
	}

	public static void pop() {
		--stackSize;
		u = ufwe[stackSize][0];
		f = ufwe[stackSize][1];
		w = ufwe[stackSize][2];
		e = ufwe[stackSize][3];
	}

	public static void tarjan(int root) {
		stackSize = 0;
		push(root, 0, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				visited[u] = true;
				distance[u] = distance[f] + w;
				e = headEdge[u];
			} else {
				e = edgeNext[e];
			}
			if (e != 0) {
				push(u, f, w, e);
				if (edgeTo[e] != f) {
					push(edgeTo[e], u, edgeWeight[e], -1);
				}
			} else {
				for (int q = headQuery[u], v, i; q != 0; q = queryNext[q]) {
					v = queryTo[q];
					if (visited[v]) {
						i = queryIndex[q];
						lca[i] = find(v);
						cost[i] = distance[u] + distance[v] - 2 * distance[lca[i]];
						maxCost = Math.max(maxCost, cost[i]);
					}
				}
				unionfind[u] = f;
			}
		}
	}

	public static boolean f(int limit) {
		atLeast = maxCost - limit;
		Arrays.fill(num, 1, n + 1, 0);
		beyond = 0;
		for (int i = 1; i <= m; i++) {
			if (cost[i] > limit) {
				num[quesu[i]]++;
				num[quesv[i]]++;
				num[lca[i]] -= 2;
				beyond++;
			}
		}
		return beyond == 0 || dfs(1);
	}

	// 至少要减少多少边权
	public static int atLeast;

	// 超过要求的运输计划有几个
	public static int beyond;

	// dfs方法的递归版改迭代版
	// 不会改，看讲解118，讲了怎么从递归版改成迭代版
	public static boolean dfs(int root) {
		stackSize = 0;
		push(root, 0, 0, -1);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				e = headEdge[u];
			} else {
				e = edgeNext[e];
			}
			if (e != 0) {
				push(u, f, w, e);
				if (edgeTo[e] != f) {
					push(edgeTo[e], u, edgeWeight[e], -1);
				}
			} else {
				for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
					v = edgeTo[e];
					if (v != f) {
						num[u] += num[v];
					}
				}
				if (num[u] == beyond && w >= atLeast) {
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		build();
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1, u, v, w; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			in.nextToken();
			w = (int) in.nval;
			addEdge(u, v, w);
			addEdge(v, u, w);
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
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		tarjan(1);
		int l = 0, r = maxCost, mid;
		int ans = 0;
		while (l <= r) {
			mid = (l + r) / 2;
			if (f(mid)) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

}