package class165;

// 可撤销并查集模版题，java版
// 一共有n个点，每个点有两个小球，每个点给定两个小球的编号
// 一共有n-1条无向边，所有节点连成一棵树
// 对i号点，2 <= i <= n，都计算如下问题的答案并打印
// 从1号点到i号点的最短路径上，每个点只能拿一个小球，最多能拿几个编号不同的小球
// 1 <= n <= 2 * 10^5
// 测试链接 : https://www.luogu.com.cn/problem/AT_abc302_h
// 测试链接 : https://atcoder.jp/contests/abc302/tasks/abc302_h
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_UndoUnionFind1 {

	public static int MAXN = 200001;
	public static int[][] arr = new int[MAXN][2];

	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cnt;

	public static int[] father = new int[MAXN];
	public static int[] siz = new int[MAXN];
	public static int[] edgeCnt = new int[MAXN];

	public static int[][] rollback = new int[MAXN][2];
	public static int opsize = 0;

	public static int[] ans = new int[MAXN];
	public static int ball = 0;

	public static void addEdge(int u, int v) {
		next[++cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt;
	}

	public static int find(int i) {
		while (i != father[i]) {
			i = father[i];
		}
		return i;
	}

	public static void union(int x, int y) {
		int fx = find(x);
		int fy = find(y);
		if (siz[fx] < siz[fy]) {
			int tmp = fx;
			fx = fy;
			fy = tmp;
		}
		father[fy] = fx;
		siz[fx] += siz[fy];
		edgeCnt[fx] += edgeCnt[fy] + 1;
		rollback[++opsize][0] = fx;
		rollback[opsize][1] = fy;
	}

	public static void undo() {
		int fx = rollback[opsize][0];
		int fy = rollback[opsize--][1];
		father[fy] = fy;
		siz[fx] -= siz[fy];
		edgeCnt[fx] -= edgeCnt[fy] + 1;
	}

	public static void dfs(int u, int fa) {
		int fx = find(arr[u][0]);
		int fy = find(arr[u][1]);
		boolean unioned = false;
		int add = 0;
		if (fx == fy) {
			if (edgeCnt[fx] < siz[fx]) {
				ball++;
				add = 1;
			}
			edgeCnt[fx]++;
		} else {
			if (edgeCnt[fx] < siz[fx] || edgeCnt[fy] < siz[fy]) {
				ball++;
				add = 1;
			}
			union(fx, fy);
			unioned = true;
		}
		ans[u] = ball;
		for (int e = head[u]; e > 0; e = next[e]) {
			if (to[e] != fa) {
				dfs(to[e], u);
			}
		}
		if (unioned) {
			undo();
		} else {
			edgeCnt[fx]--;
		}
		ball -= add;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i][0] = (int) in.nval;
			in.nextToken();
			arr[i][1] = (int) in.nval;
		}
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		for (int i = 1; i <= n; i++) {
			father[i] = i;
			siz[i] = 1;
			edgeCnt[i] = 0;
		}
		dfs(1, 0);
		for (int i = 2; i < n; i++) {
			out.print(ans[i] + " ");
		}
		out.println(ans[n]);
		out.flush();
		out.close();
		br.close();
	}

}
