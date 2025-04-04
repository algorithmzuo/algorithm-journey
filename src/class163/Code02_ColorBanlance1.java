package class163;

// 颜色平衡的子树，java实现递归版
// 一共有n个节点，编号1~n，给定每个节点的颜色值和父亲节点编号
// 输入保证所有节点一定组成一棵树，并且1号节点是树头
// 如果一棵子树中，存在的每种颜色的节点个数都相同，这棵子树叫颜色平衡树
// 打印整棵树中有多少个子树是颜色平衡树
// 1 <= n、颜色值 <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P9233
// 提交以下的code，提交时请把类名改成"Main"
// 因为树的深度太大，递归函数爆栈了，所以会有两个测试用例无法通过
// 迭代版可以完全通过，就是本节课Code02_ColorBanlance2文件

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_ColorBanlance1 {

	public static int MAXN = 200001;
	public static int n;
	public static int[] color = new int[MAXN];

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN];
	public static int[] to = new int[MAXN];
	public static int cnt = 0;

	public static int[] siz = new int[MAXN];
	public static int[] son = new int[MAXN];

	// colorCnt[i] = j，表示i这种颜色出现了j次
	public static int[] colorCnt = new int[MAXN];
	// colorNum[i] = j，表示出现次数为i的颜色一共有j种
	public static int[] colorNum = new int[MAXN];
	// 颜色平衡子树的个数
	public static int ans = 0;

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	public static void dfs1(int u) {
		siz[u] = 1;
		for (int e = head[u]; e > 0; e = next[e]) {
			dfs1(to[e]);
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			siz[u] += siz[v];
			if (son[u] == 0 || siz[son[u]] < siz[v]) {
				son[u] = v;
			}
		}
	}

	public static void effect(int u) {
		colorCnt[color[u]]++;
		colorNum[colorCnt[color[u]] - 1]--;
		colorNum[colorCnt[color[u]]]++;
		for (int e = head[u]; e > 0; e = next[e]) {
			effect(to[e]);
		}
	}

	public static void cancel(int u) {
		colorCnt[color[u]]--;
		colorNum[colorCnt[color[u]] + 1]--;
		colorNum[colorCnt[color[u]]]++;
		for (int e = head[u]; e > 0; e = next[e]) {
			cancel(to[e]);
		}
	}

	public static void dfs2(int u, int keep) {
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != son[u]) {
				dfs2(v, 0);
			}
		}
		if (son[u] != 0) {
			dfs2(son[u], 1);
		}
		colorCnt[color[u]]++;
		colorNum[colorCnt[color[u]] - 1]--;
		colorNum[colorCnt[color[u]]]++;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != son[u]) {
				effect(v);
			}
		}
		if (colorCnt[color[u]] * colorNum[colorCnt[color[u]]] == siz[u]) {
			ans++;
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
		for (int i = 1, father; i <= n; i++) {
			in.nextToken();
			color[i] = (int) in.nval;
			in.nextToken();
			father = (int) in.nval;
			if (i != 1) {
				addEdge(father, i);
			}
		}
		dfs1(1);
		dfs2(1, 0);
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
