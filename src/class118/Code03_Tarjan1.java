package class118;

// tarjan算法解法
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 提交以下的code，提交时请把类名改成"Main"
// C++这么写能通过，java会因为递归层数太多而爆栈
// java能通过的写法参考本节课Code03_Tarjan2文件

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_Tarjan1 {

	public static int MAXN = 500001;

	// 链式前向星建图
	public static int[] headEdge = new int[MAXN];

	public static int[] edgeNext = new int[MAXN << 1];

	public static int[] edgeTo = new int[MAXN << 1];

	public static int tcnt;

	// 每个节点有哪些查询，也用链式前向星方式存储
	public static int[] headQuery = new int[MAXN];

	public static int[] queryNext = new int[MAXN << 1];

	public static int[] queryTo = new int[MAXN << 1];

	// 问题的编号，一旦有答案可以知道填写在哪
	public static int[] queryIndex = new int[MAXN << 1];

	public static int qcnt;

	// 某个节点是否访问过
	public static boolean[] visited = new boolean[MAXN];

	// 并查集
	public static int[] father = new int[MAXN];

	// 收集的答案
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

	// 并查集找头节点递归版
	// 一般来说都这么写，但是本题附加的测试数据很毒
	// java这么写就会因为递归太深而爆栈，C++这么写就能通过
	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	// tarjan算法递归版
	// 一般来说都这么写，但是本题附加的测试数据很毒
	// java这么写就会因为递归太深而爆栈，C++这么写就能通过
	public static void tarjan(int u, int f) {
		visited[u] = true;
		for (int e = headEdge[u], v; e != 0; e = edgeNext[e]) {
			v = edgeTo[e];
			if (v != f) {
				tarjan(v, u);
				father[v] = u;
			}
		}
		for (int e = headQuery[u], v; e != 0; e = queryNext[e]) {
			v = queryTo[e];
			if (visited[v]) {
				ans[queryIndex[e]] = find(v);
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
		tarjan(root, 0);
		for (int i = 1; i <= m; i++) {
			out.println(ans[i]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
