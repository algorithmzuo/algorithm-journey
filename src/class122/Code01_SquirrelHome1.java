package class122;

// 松鼠的新家(递归版)
// 测试链接 : https://www.luogu.com.cn/problem/P3258

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_SquirrelHome1 {

	public static int MAXN = 300001;

	// 依次去往节点的顺序
	public static int[] travel = new int[MAXN];

	// father数组不用作并查集，就是记录每个节点的父亲节点
	public static int[] father = new int[MAXN];

	// 每个节点需要分配多少糖果
	public static int[] candy = new int[MAXN];

	// 链式前向星建图
	public static int[] headEdge = new int[MAXN];

	public static int[] edgeNext = new int[MAXN << 1];

	public static int[] edgeTo = new int[MAXN << 1];

	public static int tcnt;

	// 以下结构都是tarjan算法所需要的
	public static int[] headQuery = new int[MAXN];

	public static int[] queryNext = new int[MAXN << 1];

	public static int[] queryTo = new int[MAXN << 1];

	public static int[] queryIndex = new int[MAXN << 1];

	public static int qcnt;

	public static boolean[] visited = new boolean[MAXN];

	// unionfind数组是tarjan算法专用的并查集结构
	public static int[] unionfind = new int[MAXN];

	// ans数组是tarjan算法的输出结果，记录每次旅行两端点的最低公共祖先
	public static int[] ans = new int[MAXN];

	public static void build(int n) {
		Arrays.fill(candy, 1, n + 1, 0);
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

	public static int find(int i) {
		if (i != unionfind[i]) {
			unionfind[i] = find(unionfind[i]);
		}
		return unionfind[i];
	}

	public static void tarjan(int u, int f) {
		visited[u] = true;
		for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
			v = edgeTo[e];
			if (v != f) {
				tarjan(v, u);
			}
		}
		for (int e = headQuery[u], v; e != 0; e = queryNext[e]) {
			v = queryTo[e];
			if (visited[v]) {
				ans[queryIndex[e]] = find(v);
			}
		}
		unionfind[u] = f;
		father[u] = f;
	}

	public static void dfs(int u, int f) {
		for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
			v = edgeTo[e];
			if (v != f) {
				dfs(v, u);
			}
		}
		for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
			v = edgeTo[e];
			if (v != f) {
				candy[u] += candy[v];
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
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			travel[i] = (int) in.nval;
		}
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		compute(n);
		for (int i = 1; i <= n; i++) {
			out.println(candy[i]);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void compute(int n) {
		for (int i = 1; i < n; i++) {
			addQuery(travel[i], travel[i + 1], i);
			addQuery(travel[i + 1], travel[i], i);
		}
		tarjan(1, 0);
		for (int i = 1, u, v, lca, lcafather; i < n; i++) {
			u = travel[i];
			v = travel[i + 1];
			lca = ans[i];
			lcafather = father[lca];
			candy[u]++;
			candy[v]++;
			candy[lca]--;
			candy[lcafather]--;
		}
		dfs(1, 0);
		for (int i = 2; i <= n; i++) {
			candy[travel[i]]--;
		}
	}

}
