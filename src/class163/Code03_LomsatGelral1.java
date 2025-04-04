package class163;

// 主导颜色累加和，java版
// 一共有n个节点，编号1~n，给定n-1条边，所有节点连成一棵树，1号节点为树头
// 每个节点给定一种颜色值，主导颜色累加和定义如下
// 以x为头的子树上，哪种颜色出现最多，那种颜色就是主导颜色，主导颜色可能不止一种
// 所有主导颜色的值累加起来，每个主导颜色只累加一次，就是该子树的主导颜色累加和
// 打印1~n每个节点为头的子树的主导颜色累加和
// 1 <= n、颜色值 <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/CF600E
// 测试链接 : https://codeforces.com/problemset/problem/600/E
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_LomsatGelral1 {

	public static int MAXN = 100001;
	public static int n;
	public static int[] color = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cnt = 0;

	public static int[] fa = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];

	public static int[] colorCnt = new int[MAXN];
	public static int[] maxCnt = new int[MAXN];
	public static long[] ans = new long[MAXN];

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	public static void dfs1(int u, int f) {
		fa[u] = f;
		siz[u] = 1;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				siz[u] += siz[v];
				if (son[u] == 0 || siz[son[u]] < siz[v]) {
					son[u] = v;
				}
			}
		}
	}

	public static void effect(int u, int h) {
		colorCnt[color[u]]++;
		if (colorCnt[color[u]] == maxCnt[h]) {
			ans[h] += color[u];
		} else if (colorCnt[color[u]] > maxCnt[h]) {
			maxCnt[h] = colorCnt[color[u]];
			ans[h] = color[u];
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u]) {
				effect(v, h);
			}
		}
	}

	public static void cancel(int u) {
		colorCnt[color[u]] = 0;
		maxCnt[u] = 0;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u]) {
				cancel(v);
			}
		}
	}

	public static void dfs2(int u, int keep) {
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				dfs2(v, 0);
			}
		}
		if (son[u] != 0) {
			dfs2(son[u], 1);
		}
		maxCnt[u] = maxCnt[son[u]];
		ans[u] = ans[son[u]];
		colorCnt[color[u]]++;
		if (colorCnt[color[u]] == maxCnt[u]) {
			ans[u] += color[u];
		} else if (colorCnt[color[u]] > maxCnt[u]) {
			maxCnt[u] = colorCnt[color[u]];
			ans[u] = color[u];
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				effect(v, u);
			}
		}
		if (keep == 0) {
			cancel(u);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			color[i] = (int) in.nval;
		}
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
			out.print(ans[i] + " ");
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

}
