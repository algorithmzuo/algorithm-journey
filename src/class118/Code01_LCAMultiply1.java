package class118;

// LCA问题树上倍增解法
// 测试链接 : https://www.luogu.com.cn/problem/P3379
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_LCAMultiply1 {

	public static int MAXN = 500001;

	public static int LIMIT = 20;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[][] father = new int[MAXN][LIMIT];

	public static int[] deep = new int[MAXN];

	public static void build(int n) {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	// 递归版
	// 一般来说都这么写，不过本题附加的测试数据设计的很毒
	// c++能通过，java这么写就会因为递归太深而爆栈
	// 都能通过的写法参考本节课Code01_LCAMultiply2文件
	public static void dfs(int u, int f) {
		deep[u] = deep[f] + 1;
		father[u][0] = f;
		for (int s = 1; (1 << s) <= deep[u]; s++) {
			father[u][s] = father[father[u][s - 1]][s - 1];
		}
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				dfs(to[e], u);
			}
		}
	}

	public static int lca(int a, int b) {
		if (deep[a] < deep[b]) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		for (int s = LIMIT - 1; s >= 0; s--) {
			if (deep[a] - (1 << s) >= deep[b]) {
				a = father[a][s];
			}
		}
		if (a == b) {
			return a;
		}
		for (int s = LIMIT - 1; s >= 0; s--) {
			if (father[a][s] != father[b][s]) {
				a = father[a][s];
				b = father[b][s];
			}
		}
		return father[a][0];
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
		dfs(root, 0);
		for (int i = 1, a, b; i <= m; i++) {
			in.nextToken();
			a = (int) in.nval;
			in.nextToken();
			b = (int) in.nval;
			out.println(lca(a, b));
		}
		out.flush();
		out.close();
		br.close();
	}

}
