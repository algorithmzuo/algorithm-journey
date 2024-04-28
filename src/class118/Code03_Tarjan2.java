package class118;

// tarjan算法解法迭代版
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 所有递归函数一律改成等义的迭代版
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_Tarjan2 {

	public static int MAXN = 500001;

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

	public static int[] father = new int[MAXN];

	public static int[] ans = new int[MAXN];

	public static void build(int n) {
		tcnt = qcnt = 1;
		Arrays.fill(headEdge, 1, n + 1, 0);
		Arrays.fill(headQuery, 1, n + 1, 0);
		Arrays.fill(visited, 1, n + 1, false);
		for (int i = 1; i <= n; i++) {
			father[i] = i;
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

	// 为了实现迭代版而准备的栈
	public static int[] stack = new int[MAXN];

	// 并查集找头节点迭代版
	public static int find(int i) {
		int size = 0;
		while (i != father[i]) {
			stack[size++] = i;
			i = father[i];
		}
		while (size > 0) {
			father[stack[--size]] = i;
		}
		return i;
	}

	// 为了实现迭代版而准备的栈
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

	// 为了容易改成迭代版，修改一下递归版
	public static void tarjan(int u, int f) {
		visited[u] = true;
		for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
			v = edgeTo[e];
			if (v != f) {
				tarjan(v, u);
				// 注意这里，注释了一行
//				father[v] = u;
			}
		}
		for (int e = headQuery[u], v; e != 0; e = queryNext[e]) {
			v = queryTo[e];
			if (visited[v]) {
				ans[queryIndex[e]] = find(v);
			}
		}
		// 注意这里，增加了一行
		father[u] = f;
	}

	// tarjan算法迭代版，根据上面的递归版改写
	public static void tarjan(int root) {
		stackSize = 0;
		// 栈里存放三个信息
		// u : 当前处理的点
		// f : 当前点u的父节点
		// e : 处理到几号边了
		// 如果e==-1，表示之前没有处理过u的任何边
		// 如果e==0，表示u的边都已经处理完了
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
				// e == 0代表u后续已经没有边需要处理了
				for (int q = headQuery[u], v; q != 0; q = queryNext[q]) {
					v = queryTo[q];
					if (visited[v]) {
						ans[queryIndex[q]] = find(v);
					}
				}
				father[u] = f;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		in.nextToken();
		int root = (int) in.nval;
		build(n);
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
			addQuery(u, v, i);
			addQuery(v, u, i);
		}
		tarjan(root);
		for (int i = 1; i <= m; i++) {
			out.println(ans[i]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
