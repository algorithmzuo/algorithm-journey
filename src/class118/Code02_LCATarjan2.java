package class118;

// LCA问题Tarjan算法解法
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 本文件和Code02_LCATarjan1文件区别只有find、tarjan实现方式的不同
// C++和java这么写都能通过，不能使用递归

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_LCATarjan2 {

	public static int MAXN = 500001;

	public static int[] treeHead = new int[MAXN];

	public static int[] treeNext = new int[MAXN << 1];

	public static int[] treeTo = new int[MAXN << 1];

	public static int tcnt;

	public static int[] queryHead = new int[MAXN];

	public static int[] queryNext = new int[MAXN << 1];

	public static int[] queryTo = new int[MAXN << 1];

	public static int[] queryIndex = new int[MAXN << 1];

	public static int qcnt;

	public static boolean[] visited = new boolean[MAXN];

	public static int[] father = new int[MAXN];

	public static int[] ans = new int[MAXN];

	public static void build(int n) {
		tcnt = qcnt = 1;
		Arrays.fill(treeHead, 1, n + 1, 0);
		Arrays.fill(queryHead, 1, n + 1, 0);
		Arrays.fill(visited, 1, n + 1, false);
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
	}

	public static void addEdge(int u, int v) {
		treeNext[tcnt] = treeHead[u];
		treeTo[tcnt] = v;
		treeHead[u] = tcnt++;
	}

	public static void addQuery(int u, int v, int i) {
		queryNext[qcnt] = queryHead[u];
		queryTo[qcnt] = v;
		queryIndex[qcnt] = i;
		queryHead[u] = qcnt++;
	}

	// 并查集找代表节点递归版
	// C++和java这么写都能通过，不能使用递归了
	// stack是为了实现迭代版而准备的栈
	public static int[] stack = new int[MAXN];

	public static int find(int i) {
		int n = 0;
		while (i != father[i]) {
			stack[n++] = i;
			i = father[i];
		}
		while (n > 0) {
			father[stack[--n]] = i;
		}
		return i;
	}

	// Tarjan算法递归版
	// C++和java这么写都能通过，不能使用递归了
	// nodes、fathers、edges是为了实现迭代版而准备的三个栈
	public static int[] nodes = new int[MAXN];

	public static int[] fathers = new int[MAXN];

	public static int[] edges = new int[MAXN];

	public static void tarjan(int root) {
		nodes[0] = root;
		fathers[0] = 0;
		edges[0] = -1;
		int n = 1, u, f, e, v;
		while (n > 0) {
			u = nodes[--n];
			f = fathers[n];
			e = edges[n];
			if (e == -1) {
				visited[u] = true;
				e = treeHead[u];
			} else {
				e = treeNext[e];
			}
			if (e != 0) {
				nodes[n] = u;
				fathers[n] = f;
				edges[n++] = e;
				v = treeTo[e];
				if (v != f) {
					nodes[n] = v;
					fathers[n] = u;
					edges[n++] = -1;
				}
			} else {
				for (int q = queryHead[u]; q != 0; q = queryNext[q]) {
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
