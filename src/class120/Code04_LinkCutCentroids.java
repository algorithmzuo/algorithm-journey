package class120;

// 删增边使其重心唯一
// 一共有n个节点，编号1~n，有n-1条边形成一棵树
// 现在希望重心是唯一的节点，调整的方式是先删除一条边、然后增加一条边
// 返回删除哪两个节点之间的边、增加哪两个节点之间的边
// 注意 : 删掉的边和加上的边可以是同一条
// 测试链接 : https://www.luogu.com.cn/problem/CF1406C
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_LinkCutCentroids {

	public static int MAXN = 100001;

	public static int n;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] size = new int[MAXN];

	public static int[] maxsub = new int[MAXN];

	public static int best;

	public static int[] centers = new int[2];

	public static int node1, node2;

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

	public static void dfs(int u, int f) {
		size[u] = maxsub[u] = 1;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs(v, u);
				size[u] += size[v];
				maxsub[u] = Math.max(maxsub[u], size[v]);
			}
		}
		maxsub[u] = Math.max(maxsub[u], n - size[u]);
		if (maxsub[u] < best) {
			best = maxsub[u];
		}
	}

	public static void find(int u, int f) {
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				find(to[e], u);
				return;
			}
		}
		node1 = u;
		node2 = f;
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
			if (compute() == 1) {
				out.println(centers[0] + " " + to[head[centers[0]]]);
				out.println(centers[0] + " " + to[head[centers[0]]]);
			} else {
				out.println(node2 + " " + node1);
				out.println(centers[0] + " " + node1);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		dfs(1, 0);
		int m = 0;
		for (int i = 1; i <= n; i++) {
			if (maxsub[i] == best) {
				centers[m++] = i;
			}
		}
		if (m != 1) {
			find(centers[1], centers[0]);
		}
		return m;
	}

}
