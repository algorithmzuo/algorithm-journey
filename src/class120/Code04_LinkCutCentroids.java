package class120;

// 删增边使其重心唯一
// 一共有n个节点，编号1~n，有n-1条边形成一棵树
// 现在希望重心是唯一的节点，调整的方式是先删除一条边、然后增加一条边
// 如果树上只有一个重心，需要删掉连接重心的任意一条边，再把这条边加上(否则无法通过已经实测)
// 如果树上有两个重心，调整的方式是先删除一条边、然后增加一条边，使重心是唯一的
// 如果方案有多种，打印其中一种即可
// 比如先删除节点3和节点4之间的边，再增加节点4和节点7之间的边，那么打印:
// "3 4"
// "4 7"
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

	// size[i] : 从1号节点开始dfs的过程中，以i为头的子树的节点数
	public static int[] size = new int[MAXN];

	// maxsub[i] : 如果节点i做整棵树的根，最大子树的大小
	public static int[] maxsub = new int[MAXN];

	// 收集所有的重心
	public static int[] centers = new int[2];

	// 任何一个叶节点
	public static int anyLeaf;

	// 该叶节点的父亲节点
	public static int anyLeafFather;

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs(int u, int f) {
		size[u] = 1;
		maxsub[u] = 0;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs(v, u);
				size[u] += size[v];
				maxsub[u] = Math.max(maxsub[u], size[v]);
			}
		}
		maxsub[u] = Math.max(maxsub[u], n - size[u]);
	}

	// 随意找一个叶节点和该叶节点的父亲节点
	// 哪一组都可以
	public static void find(int u, int f) {
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				find(to[e], u);
				return;
			}
		}
		anyLeaf = u;
		anyLeafFather = f;
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
				out.println(anyLeafFather + " " + anyLeaf);
				out.println(centers[0] + " " + anyLeaf);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

	// 返回重心的数量
	public static int compute() {
		dfs(1, 0);
		int m = 0;
		for (int i = 1; i <= n; i++) {
			if (maxsub[i] <= n / 2) {
				centers[m++] = i;
			}
		}
		if (m == 2) {
			find(centers[1], centers[0]);
		}
		return m;
	}

}
