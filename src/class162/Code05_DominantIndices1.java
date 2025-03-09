package class162;

// 哪个距离的点最多，java递归版
// 一共有n个节点，给定n-1条边，所有节点连成一棵树，规定1号节点是头
// 规定任何点到自己的距离为0
// 定义d(u, x)，以u为头的子树中，到u的距离为x的节点数
// 对于每个点u，想知道哪个尽量小的x，能取得最大的d(u, x)值
// 打印每个点的答案
// 1 <= n <= 10^6
// 测试链接 : https://www.luogu.com.cn/problem/CF1009F
// 测试链接 : https://codeforces.com/problemset/problem/1009/F
// 提交以下的code，提交时请把类名改成"Main"，因为递归爆栈所以无法通过
// 迭代版的实现就是本节课Code05_DominantIndices2文件

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_DominantIndices1 {

	public static int MAXN = 1000001;
	public static int n;

	// 链式前向星
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cnt = 0;

	// 长链剖分
	public static int[] len = new int[MAXN];
	public static int[] son = new int[MAXN];

	// 动态规划
	public static int[] start = new int[MAXN];
	public static int[] dp = new int[MAXN];
	public static int[] ansx = new int[MAXN];

	public static void set(int u, int i, int v) {
		dp[start[u] + i] = v;
	}

	public static int get(int u, int i) {
		return dp[start[u] + i];
	}

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	// 递归版，C++可以通过，java会爆栈
	public static void dfs1(int u, int fa) {
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa) {
				dfs1(v, u);
			}
		}
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa) {
				if (son[u] == 0 || len[son[u]] < len[v]) {
					son[u] = v;
				}
			}
		}
		len[u] = len[son[u]] + 1;
	}

	// 递归版，C++可以通过，java会爆栈
	public static void dfs2(int u, int fa) {
		// 第一部分，给所有儿子设置start位置
		set(u, 0, 1);
		if (son[u] == 0) {
			ansx[u] = 0;
			return;
		}
		start[son[u]] = start[u] + 1;
		int startSum = start[u] + len[u];
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa && v != son[u]) {
				start[v] = startSum;
				startSum += len[v];
			}
		}
		// 第二部分，先遍历长儿子，得到的答案已经放在dp里了，自动复用
		dfs2(son[u], u);
		// 第三部分，处理其他儿子
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa && v != son[u]) {
				dfs2(v, u);
			}
		}
		// 第四部分，计算答案
		ansx[u] = ansx[son[u]] + 1;
		for (int e = head[u], v; e > 0; e = next[e]) {
			v = to[e];
			if (v != fa && v != son[u]) {
				for (int i = 1; i <= len[v]; i++) {
					set(u, i, get(u, i) + get(v, i - 1));
					if (get(u, i) > get(u, ansx[u]) || (get(u, i) == get(u, ansx[u]) && i < ansx[u])) {
						ansx[u] = i;
					}
				}
			}
		}
		if (get(u, ansx[u]) == 1) {
			ansx[u] = 0;
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
		start[1] = 1;
		dfs2(1, 0);
		for (int i = 1; i <= n; i++) {
			out.println(ansx[i]);
		}
		out.flush();
		out.close();
		br.close();
	}

}
