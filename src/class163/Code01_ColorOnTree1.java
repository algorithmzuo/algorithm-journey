package class163;

// 树上数颜色，java版
// 测试链接 : https://www.luogu.com.cn/problem/U41492
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_ColorOnTree1 {

	public static int MAXN = 100001;
	public static int n, m;
	public static int[] arr = new int[MAXN];

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cnt = 0;

	// 树链剖分
	public static int[] fa = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];

	// 启发式合并
	public static int[] colorCnt = new int[MAXN];
	public static int[] ans = new int[MAXN];
	public static int total = 0;

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	public static void effect(int u) {
		if (++colorCnt[arr[u]] == 1) {
			total++;
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u]) {
				effect(v);
			}
		}
	}

	public static void cancle(int u) {
		if (--colorCnt[arr[u]] == 0) {
			total--;
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u]) {
				cancle(v);
			}
		}
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
		if (++colorCnt[arr[u]] == 1) {
			total++;
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				effect(v);
			}
		}
		ans[u] = total;
		if (keep == 0) {
			cancle(u);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
		}
		dfs1(1, 0);
		dfs2(1, 1);
		in.nextToken();
		m = (int) in.nval;
		for (int i = 1, cur; i <= m; i++) {
			in.nextToken();
			cur = (int) in.nval;
			out.println(ans[cur]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
