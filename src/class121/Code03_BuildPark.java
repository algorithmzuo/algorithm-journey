package class121;

// 造公园
// 一共n个节点，编号1~n，有m条边连接，边权都是1
// 所有节点可能形成多个联通区，每个联通区保证是树结构
// 有两种类型的操作
// 操作 1 x   : 返回x到离它最远的点的距离
// 操作 2 x y : 如果x和y已经联通，那么忽略
//              如果不联通，那么执行联通操作，把x和y各自的区域联通起来
//              并且要保证联通成的大区域的直径长度最小
// 测试链接 : https://www.luogu.com.cn/problem/P2195
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_BuildPark {

	public static int MAXN = 300001;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] father = new int[MAXN];

	public static int[] dist = new int[MAXN];

	// diameter[i] : 如果i是集合的头节点，diameter[i]表示整个集合的直径长度
	// 如果i不再是集合的头节点，diameter[i]的值以后不会用到了
	// 并查集 + 集合打标签技巧，不会的看讲解056、讲解057
	public static int[] diameter = new int[MAXN];

	public static void build(int n) {
		cnt = 1;
		Arrays.fill(head, 1, n, 0);
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
		Arrays.fill(dist, 1, n, 0);
		Arrays.fill(diameter, 1, n, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void dfs(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs(v, u);
			}
		}
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				diameter[u] = Math.max(diameter[u], Math.max(diameter[v], dist[u] + dist[v] + 1));
				dist[u] = Math.max(dist[u], dist[v] + 1);
			}
		}
	}

	public static int half(int v) {
		return (v + 1) / 2;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		in.nextToken();
		int q = (int) in.nval;
		build(n);
		for (int i = 1, u, v; i <= m; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
			u = find(u);
			v = find(v);
			father[u] = v;
		}
		for (int i = 1; i <= n; i++) {
			if (father[i] == i) {
				dfs(i, 0);
			}
		}
		for (int i = 1, op, u, v; i <= q; i++) {
			in.nextToken();
			op = (int) in.nval;
			if (op == 1) {
				in.nextToken();
				u = (int) in.nval;
				u = find(u);
				out.println(diameter[u]);
			} else {
				in.nextToken();
				u = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				u = find(u);
				v = find(v);
				if (u != v) {
					father[u] = v;
					diameter[v] = Math.max(half(diameter[u]) + half(diameter[v]) + 1,
							Math.max(diameter[u], diameter[v]));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
