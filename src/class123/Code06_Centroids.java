package class123;

// 哪些点可以改造成重心
// 给定一棵n个点的树，你可以随便删掉一条边，然后随便加上一条边
// 通过这种方式可以让很多点变成重心
// 打印所有能变成重心的点
// 测试链接 : https://www.luogu.com.cn/problem/CF708C
// 测试链接 : https://codeforces.com/problemset/problem/708/C
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code06_Centroids {

	public static int MAXN = 400001;

	public static int n;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	// size[i]: i内部，整棵子树大小
	public static int[] size = new int[MAXN];

	// maxsub[i]: i内部，最大子树，是i节点的哪个儿子拥有，记录节点编号
	public static int[] maxsub = new int[MAXN];

	// inner1[i]: i内部，<=n/2且第一大的子树是多大，记录大小
	public static int[] inner1 = new int[MAXN];

	// inner2[i]: i内部，<=n/2且第二大的子树是多大，记录大小
	public static int[] inner2 = new int[MAXN];

	// 注意: inner1[i]和inner2[i]，所代表的子树一定要来自i的不同儿子

	// choose[i]: inner1[i]所代表的子树，是i节点的哪个儿子拥有，记录节点编号
	public static int[] choose = new int[MAXN];

	// outer[i]: i外部，<=n/2且第一大的子树是多大，记录大小
	public static int[] outer = new int[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(maxsub, 1, n + 1, 0);
		Arrays.fill(choose, 1, n + 1, 0);
		Arrays.fill(inner1, 1, n + 1, 0);
		Arrays.fill(inner2, 1, n + 1, 0);
		Arrays.fill(outer, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs1(int u, int f) {
		size[u] = 1;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
				size[u] += size[v];
				if (size[maxsub[u]] < size[v]) {
					maxsub[u] = v;
				}
				int innerSize = size[v] <= n / 2 ? size[v] : inner1[v];
				if (inner1[u] < innerSize) {
					choose[u] = v;
					inner2[u] = inner1[u];
					inner1[u] = innerSize;
				} else if (inner2[u] < innerSize) {
					inner2[u] = innerSize;
				}
			}
		}
	}

	public static void dfs2(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				if (n - size[v] <= n / 2) {
					outer[v] = n - size[v];
				} else if (choose[u] != v) {
					outer[v] = Math.max(outer[u], inner1[u]);
				} else {
					outer[v] = Math.max(outer[u], inner2[u]);
				}
				dfs2(v, u);
			}
		}
	}

	public static boolean check(int u) {
		if (size[maxsub[u]] > n / 2) {
			return size[maxsub[u]] - inner1[maxsub[u]] <= n / 2;
		}
		if (n - size[u] > n / 2) {
			return n - size[u] - outer[u] <= n / 2;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		build();
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs1(1, 0);
		dfs2(1, 0);
		for (int i = 1; i <= n; i++) {
			out.print(check(i) ? "1 " : "0 ");
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

}
