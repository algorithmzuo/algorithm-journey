package class122;

// 松鼠的新家(迭代版)
// 有n个节点形成一棵树
// 给定一个由点编号组成的数组，表示松鼠依次要去往的地点
// 松鼠每走到一个节点都必须拿一个糖果，否则松鼠就停止前进
// 松鼠来到最后一个地点时不需要吃糖果
// 打印每个节点上至少准备多少糖果才能让松鼠依次走完数组里的节点
// 测试链接 : https://www.luogu.com.cn/problem/P3258
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_SquirrelHome2 {

	public static int MAXN = 300001;

	public static int[] travel = new int[MAXN];

	public static int[] num = new int[MAXN];

	public static int[] headEdge = new int[MAXN];

	public static int[] edgeNext = new int[MAXN << 1];

	public static int[] edgeTo = new int[MAXN << 1];

	public static int tcnt;

	public static int[] headQuery = new int[MAXN];

	public static int[] queryNext = new int[MAXN << 1];

	public static int[] queryTo = new int[MAXN << 1];

	public static int[] queryIndex = new int[MAXN << 1];

	public static int qcnt;

	public static boolean[] visited = new boolean[MAXN];

	public static int[] unionfind = new int[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] ans = new int[MAXN];

	public static void build(int n) {
		Arrays.fill(num, 1, n + 1, 0);
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
	// 不会改，看讲解118，讲了怎么从递归版改成迭代版
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
	// 不会改，看讲解118，讲了怎么从递归版改成迭代版
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
						num[u] += num[v];
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
		for (int i = 1; i < n; i++) {
			addQuery(travel[i], travel[i + 1], i);
			addQuery(travel[i + 1], travel[i], i);
		}
		compute(n);
		for (int i = 1; i <= n; i++) {
			out.println(num[i]);
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void compute(int n) {
		tarjan(1);
		for (int i = 1, u, v, lca, lcafather; i < n; i++) {
			u = travel[i];
			v = travel[i + 1];
			lca = ans[i];
			lcafather = father[lca];
			num[u]++;
			num[v]++;
			num[lca]--;
			num[lcafather]--;
		}
		dfs(1);
		for (int i = 2; i <= n; i++) {
			num[travel[i]]--;
		}
	}

}