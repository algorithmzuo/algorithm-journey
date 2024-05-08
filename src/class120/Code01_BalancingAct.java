package class120;

// 平衡行为
// 一共有n个节点，编号1~n，有n-1条边形成一棵树
// 返回重心点，返回重心点最大子树的节点数
// 树的重心第一种求解方式
// 以某个节点为根时，最大子树的节点数最少，那么这个节点是重心
// 测试链接 : http://poj.org/problem?id=1655
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_BalancingAct {

	public static int MAXN = 20001;

	public static int n;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] size = new int[MAXN];

	public static int center;

	public static int best;

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		best = Integer.MAX_VALUE;
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	// 当前来到u节点，父亲节点是f
	public static void dfs(int u, int f) {
		size[u] = 1;
		// 以当前节点u做根节点，最大的子树有多少节点
		int maxsub = 0;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs(v, u);
				size[u] += size[v];
				maxsub = Math.max(maxsub, size[v]);
			}
		}
		maxsub = Math.max(maxsub, n - size[u]);
		// 题目要求找到编号最小的重心
		if (maxsub < best || (maxsub == best && u < center)) {
			best = maxsub;
			center = u;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int testCase = (int) in.nval;
		for (int t = 1; t <= testCase; t++) {
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
			dfs(1, 0);
			out.println(center + " " + best);
		}
		out.flush();
		out.close();
		br.close();
	}

}
