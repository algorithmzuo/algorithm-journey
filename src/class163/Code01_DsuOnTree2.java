package class163;

// 树上启发式合并模版题，java版
// 只需要关注有注释的代码
// 揭示了轻儿子取消自己的影响后
// 其实全局的信息统计就是空的
// 所以改成注释的代码也是正确的
// 测试链接 : https://www.luogu.com.cn/problem/U41492
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_DsuOnTree2 {

	public static int MAXN = 100001;
	public static int n, m;
	public static int[] color = new int[MAXN];
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cnt = 0;
	public static int[] fa = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] colorCnt = new int[MAXN];
	public static int[] ans = new int[MAXN];
	public static int diffColors = 0;

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	public static void effect(int u) {
		if (++colorCnt[color[u]] == 1) {
			diffColors++;
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u]) {
				effect(v);
			}
		}
	}

	public static void cancle(int u) {
		colorCnt[color[u]] = 0; // 出现任何颜色，直接把该颜色的计数重置为0
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
		if (++colorCnt[color[u]] == 1) {
			diffColors++;
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa[u] && v != son[u]) {
				effect(v);
			}
		}
		ans[u] = diffColors;
		if (keep == 0) {
			diffColors = 0; // 直接把全局的不同颜色数量重置为0
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
			color[i] = (int) in.nval;
		}
		dfs1(1, 0);
		dfs2(1, 0);
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
