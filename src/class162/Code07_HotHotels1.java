package class162;

// 火热旅馆，java版
// 一共有n个节点，给定n-1条边，所有节点连成一棵树
// 三个点构成一个点组(a, b, c)，打乱顺序依然认为是同一个点组
// 求树上有多少点组，两两之间的距离是一样的
// 1 <= n <= 10^5
// 答案一定在long类型范围内
// 测试链接 : https://www.luogu.com.cn/problem/P5904
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code07_HotHotels1 {

	public static int MAXN = 100001;
	public static int n;

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	public static int[] fa = new int[MAXN];
	public static int[] son = new int[MAXN];
	public static int[] len = new int[MAXN];
	public static int cntd = 0;

	public static int[] fid = new int[MAXN];
	public static int[] gid = new int[MAXN];
	public static long[] f = new long[MAXN];
	public static long[] g = new long[MAXN << 1];
	public static long ans = 0;

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	// 递归版，居然不改迭代版也能通过，那就不改了
	public static void dfs1(int u, int f) {
		fa[u] = f;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				if (son[u] == 0 || len[son[u]] < len[v]) {
					son[u] = v;
				}
			}
		}
		len[u] = len[son[u]] + 1;
	}

	// 递归版，居然不改迭代版也能通过，那就不改了
	public static void dfs2(int u, int t) {
		fid[u] = cntd++;
		if (son[u] == 0) {
			gid[u] = fid[t] * 2;
			return;
		}
		dfs2(son[u], t);
		gid[u] = gid[son[u]] + 1;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != son[u] && v != fa[u]) {
				dfs2(v, v);
			}
		}
	}

	public static void setf(int u, int i, long v) {
		f[fid[u] + i] = v;
	}

	public static long getf(int u, int i) {
		return f[fid[u] + i];
	}

	public static void setg(int u, int i, long v) {
		g[gid[u] + i] = v;
	}

	public static long getg(int u, int i) {
		return g[gid[u] + i];
	}

	// 递归版，居然不改迭代版也能通过，那就不改了
	public static void dfs3(int u) {
		if (son[u] == 0) {
			setf(u, 0, 1);
			return;
		}
		dfs3(son[u]);
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != son[u] && v != fa[u]) {
				dfs3(v);
			}
		}
		ans += getg(u, 0);
		setf(u, 0, getf(u, 0) + 1);
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != son[u] && v != fa[u]) {
				for (int i = 0; i <= len[v]; i++) {
					if (i > 0) {
						ans += getg(u, i) * getf(v, i - 1);
					}
					if (i + 1 < len[v]) {
						ans += getf(u, i) * getg(v, i + 1);
					}
				}
				for (int i = 0; i <= len[v]; i++) {
					if (i > 0) {
						setg(u, i, getg(u, i) + getf(u, i) * getf(v, i - 1));
					}
				}
				for (int i = 0; i <= len[v]; i++) {
					if (i < len[v]) {
						setg(u, i, getg(u, i) + getg(v, i + 1));
					}
					if (i > 0) {
						setf(u, i, getf(u, i) + getf(v, i - 1));
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
		n = (int) in.nval;
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs1(1, 0);
		dfs2(1, 1);
		dfs3(1);
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}